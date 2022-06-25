package api.tables;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Table {
    private String alias;
    private String created;
    private Integer defaultSortFieldId;
    private String defaultSortOrder;
    private String description;
    private String id;
    private Integer keyFieldId;
    private String name;
    private Integer nextFieldId;
    private Integer nextRecordId;
    private String pluralRecordName;
    private String singleRecordName;
    private String sizeLimit;
    private String spaceRemaining;
    private String spaceUsed;
    private String updated;
}
