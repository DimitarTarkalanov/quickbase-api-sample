package api.tables;

import api.Request;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class TablesRequest extends Request {
    private static final String TABLES_PATH = "/tables";
    private final String appId;

    public TablesRequest(String baseUri, int statusCode, String appId) {
        super(baseUri, TABLES_PATH, statusCode, RequestMethod.GET);
        this.appId = appId;
    }

    @Override
    protected RequestSpecification initGetRequest() {
        return super.initRequest(
                Map.of(
                        "appId", appId
                )
        );
    }
}
