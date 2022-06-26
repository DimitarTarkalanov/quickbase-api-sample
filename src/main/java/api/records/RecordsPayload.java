package api.records;

import api.RequestPayLoad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter @Setter
@ToString
public class RecordsPayload implements RequestPayLoad {
    public String to;
    public List<Map<String, FieldValue>> data = null;
    public List<Integer> fieldsToReturn = null;
}
