package api.records;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter @Setter
public class RecordsQuery {
    private List<Map<String, FieldValue>> data = null;
    private List<Field> fields = null;
    private Metadata metadata;

    @NoArgsConstructor
    @Getter @Setter
    public class Field {
        private Integer id;
        private String label;
        private String type;

    }

    @NoArgsConstructor
    @Getter @Setter
    public class Metadata {
        private Integer totalRecords;
        private Integer numRecords;
        private Integer numFields;
        private Integer skip;

    }

}
