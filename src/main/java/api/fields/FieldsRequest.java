package api.fields;

import api.Request;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class FieldsRequest extends Request {
    private static final String TABLES_PATH = "/fields";
    private final String tableId;

    public FieldsRequest(String baseUri, int statusCode, String tableId) {
        super(baseUri, TABLES_PATH, statusCode, RequestMethod.GET);
        this.tableId = tableId;
    }

    @Override
    protected RequestSpecification initGetRequest() {
        return super.initRequest(
                Map.of(
                        "tableId", tableId
                )
        );
    }
}
