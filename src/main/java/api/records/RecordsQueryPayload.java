package api.records;

import api.RequestPayLoad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RecordsQueryPayload implements RequestPayLoad {
    private String from;
    private List<Integer> select;
    private String where;
}
