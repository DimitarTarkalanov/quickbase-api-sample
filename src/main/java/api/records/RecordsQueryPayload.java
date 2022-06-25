package api.records;

import api.RequestPayLoad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RecordsQueryPayload implements RequestPayLoad {
    private String from;
}
