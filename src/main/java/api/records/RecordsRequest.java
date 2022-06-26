package api.records;

import api.Request;

public class RecordsRequest extends Request {
    private static final String RECORDS_PATH = "/records";

    public RecordsRequest(String baseUri, int statusCode, RecordsPayload payload) {
        super(baseUri, RECORDS_PATH, statusCode, RequestMethod.POST, payload);
    }

    public RecordsRequest(String baseUri, int statusCode, RecordsQueryPayload payload) {
        super(baseUri, RECORDS_PATH, statusCode, RequestMethod.DELETE, payload);
    }
}
