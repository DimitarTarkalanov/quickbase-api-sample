package api.records;

import api.Request;

public class RecordsQueryRequest extends Request {
    private static final String RECORDS_QUERY_PATH = "/records/query";

    public RecordsQueryRequest(String baseUri, int statusCode, RecordsQueryPayload payload) {
        super(baseUri, RECORDS_QUERY_PATH, statusCode, RequestMethod.POST, payload);
    }
}
