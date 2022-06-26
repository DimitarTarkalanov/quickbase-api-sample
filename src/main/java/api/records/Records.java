package api.records;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Records {
    private List<Map<String, FieldValue>> data = null;
    private RecordsQuery.Metadata metadata;

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public class Metadata {
        private List<Integer> createdRecordIds = null;
        private Integer totalNumberOfRecordsProcessed;
        private List<Integer> unchangedRecordIds = null;
        private List<Integer> updatedRecordIds = null;

    }
}
