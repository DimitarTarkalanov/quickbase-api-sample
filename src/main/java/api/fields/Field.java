package api.fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Field {
    private Integer id;
    private String label;
    private String fieldType;
    private Boolean noWrap;
    private Boolean bold;
    private Boolean required;
    private Boolean appearsByDefault;
    private Boolean findEnabled;
    private Boolean unique;
    private Boolean doesDataCopy;
    private String fieldHelp;
    private Boolean audited;
    private Properties properties;
    private List<Permission> permissions = null;
    private Boolean addToForms;

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public class Permission {
        private String permissionType;
        private String role;
        private Integer roleId;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public class Properties {
        private Boolean primaryKey;
        private Boolean foreignKey;
        private Integer numLines;
        private Integer maxLength;
        private Boolean appendOnly;
        private Boolean allowHTML;
        private Boolean sortAsGiven;
        private Boolean carryChoices;
        private Boolean allowNewChoices;
        private String formula;
        private String defaultValue;
        private Integer numberFormat;
        private Integer decimalPlaces;
        private Boolean doesAverage;
        private Boolean doesTotal;
        private Boolean blankIsZero;
    }
}
