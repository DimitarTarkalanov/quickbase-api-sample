package api.records;

import api.Request;

public class RecordsRequest extends Request {
    private static final String RECORDS_PATH = "/records";

    public RecordsRequest(String baseUri, int statusCode, RequestMethod requestMethod, RecordsPayload payload) {
        super(baseUri, RECORDS_PATH, statusCode, requestMethod, payload);
    }


}
