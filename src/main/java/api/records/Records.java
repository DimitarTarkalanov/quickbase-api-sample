package api.records;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class Records {
    private List<Map<String, FieldValue>> data = null;
    private RecordsQuery.Metadata metadata;

    @NoArgsConstructor
    @Getter @Setter
    public class Metadata {
        private List<Integer> createdRecordIds = null;
        private Integer totalNumberOfRecordsProcessed;
        private List<Integer> unchangedRecordIds = null;
        private List<Integer> updatedRecordIds = null;

    }
}
