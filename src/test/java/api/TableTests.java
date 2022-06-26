package api;

import api.fields.Field;
import api.fields.FieldsRequest;
import api.records.*;
import api.tables.Table;
import api.tables.TablesRequest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TableTests {

    @DataProvider(name = "setupInsertRecord")
    public Object[][] setupInsertRecord() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate starDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        Map<String, Object> taskRecord = Map.of(
                "Task Description", "Test Description",
                "Status", "In-Progress",
                "Priority", "Low",
                "Start Date", starDate.format(formatter),
                "End Date", endDate.format(formatter)
        );

        return new Object[][]{
                {"https://api.quickbase.com/v1", "bsgdugaef", "Tasks", Collections.singletonList(taskRecord)},
        };
    }

    @Test(dataProvider = "setupInsertRecord")
    public void testInsertRecord(String baseUri, String appId, String tableName, List<Map<String, Object>> records) {
        Table table = getAppTableByName(baseUri, appId, tableName);
        String tableId = table.getId();

        List<Field> tableFields = getTableFields(baseUri, tableId);
        List<Map<String, FieldValue>> expectedRecordDataList = generateRecordData(tableFields, records);
        List<Integer> fieldIds = expectedRecordDataList.get(0).keySet().stream().map(Integer::parseInt).collect(Collectors.toList());

        RecordsQuery existingRecords = getTableRecords(tableId, baseUri, fieldIds);
        expectedRecordDataList.forEach(recordData ->
                Assert.assertFalse(existingRecords.getData().stream().anyMatch(data -> data.equals(recordData)), "There is an existing table record that matches the generated test data!"));

        Records responseRecords = insertRecords(baseUri, tableId, expectedRecordDataList, fieldIds);

        //ToDo Include the record ids in the assertion
        responseRecords.getData().forEach(map -> map.remove("3"));

        List<Map<String, FieldValue>> actualRecordDataList = responseRecords.getData();
        Assert.assertEquals(actualRecordDataList, expectedRecordDataList, "The response records data doesn't match the payload records data!");

        RecordsQuery currentTableRecords = getTableRecords(tableId, baseUri, fieldIds);
        int actualNumRecords = currentTableRecords.getMetadata().getTotalRecords();
        int expectedNumRecords = existingRecords.getMetadata().getTotalRecords() + responseRecords.getData().size();
        Assert.assertEquals(actualNumRecords, expectedNumRecords, "The total number of table records is incorrect!");

        expectedRecordDataList.forEach(recordData ->
                Assert.assertTrue(currentTableRecords.getData().stream().anyMatch(data -> data.equals(recordData)), "The inserted records are not presented in the table!"));
    }

    private Table getAppTableByName(String baseUri, String appId, String tableName) {
        TablesRequest request = new TablesRequest(baseUri, 200, appId);
        Table[] tables = request.executeRequest().as(Table[].class);
        return Arrays.stream(tables).filter(t -> t.getName().equals(tableName)).findFirst().orElseThrow(() -> new ResponseException(String.format("There is no table with name:[%s]", tableName)));
    }

    private RecordsQuery getTableRecords(String tableId, String baseUri, List<Integer> fieldIds) {
        RecordsQueryPayload payload = new RecordsQueryPayload();
        payload.setFrom(tableId);
        payload.setSelect(fieldIds);
        RecordsQueryRequest recordsQueryRequest = new RecordsQueryRequest(baseUri, 200, payload);
        return recordsQueryRequest.executeRequest().as(RecordsQuery.class);
    }

    private List<Field> getTableFields(String baseUri, String tableId) {
        FieldsRequest request = new FieldsRequest(baseUri, 200, tableId);
        return Arrays.asList(request.executeRequest().as(Field[].class));
    }

    private List<Map<String, FieldValue>> generateRecordData(List<Field> tableFields, List<Map<String, Object>> taskRecords) {
        List<Map<String, FieldValue>> records = new ArrayList<>();
        Map<String, FieldValue> recordData;

        for (Map<String, Object> taskRecord : taskRecords) {
            recordData = new HashMap<>();
            records.add(recordData);
            Set<String> tableFieldNames = taskRecord.keySet();

            tableFieldNames_loop:
            for (String tableFieldName : tableFieldNames) {
                for (Field field : tableFields) {
                    if (field.getLabel().equals(tableFieldName)) {
                        recordData.put(String.valueOf(field.getId()), new FieldValue(taskRecord.get(tableFieldName)));
                        continue tableFieldNames_loop;
                    }
                }
                throw new ResponseException(String.format("Unable to find field with label:[%s]", tableFieldName));
            }
        }

        return records;
    }

    private Records insertRecords(String baseUri, String tableId, List<Map<String, FieldValue>> dataRecords, List<Integer> fieldIds) {
        RecordsPayload payload = new RecordsPayload();
        payload.setTo(tableId);
        payload.setData(dataRecords);
        payload.setFieldsToReturn(fieldIds);

        RecordsRequest request = new RecordsRequest(baseUri, 200, Request.RequestMethod.POST, payload);
        return request.executeRequest().getBody().as(Records.class);
    }
}
