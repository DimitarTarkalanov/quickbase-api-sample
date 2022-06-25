package api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public abstract class Request {
    private final String baseUri;
    private final String path;
    private final RequestMethod requestMethod;
    private final int statusCode;
    private RequestPayLoad payLoad;

    protected Request(String baseUri, String path, int statusCode, RequestMethod requestMethod) {
        this.baseUri = baseUri;
        this.path = path;
        this.statusCode = statusCode;
        this.requestMethod = requestMethod;
    }

    protected Request(String baseUri, String path, int statusCode, RequestMethod requestMethod, RequestPayLoad payLoad) {
        this(baseUri, path, statusCode, requestMethod);
        this.payLoad = payLoad;
    }

    protected RequestSpecification initGetRequest() {
        return initRequest();
    }

    protected RequestSpecification initPostRequest() {
        return initRequest(payLoad);
    }

    protected RequestSpecification initPutRequest() {
        return initRequest(payLoad);
    }

    protected RequestSpecification initDeleteRequest() {
        return initRequest(payLoad);
    }

    protected final RequestSpecification initRequest() {
        return RestAssured.given().baseUri(baseUri)
                .headers(Map.of(
                        "Authorization", "QB-USER-TOKEN " + System.getenv("QUICKBASE_TOKEN"),
                        "QB-Realm-Hostname", "team.quickbase.com"
                        )
                )
                .contentType(ContentType.JSON);
    }

    protected final RequestSpecification initRequest(RequestPayLoad payload) {
        return initRequest()
                .body(payload);
    }

    protected final RequestSpecification initRequest(Map<String, Object> queryParamsMap) {
        return initRequest()
                .queryParams(queryParamsMap);
    }

    @Step("Execute Get Request: {path}")
    private Response executeGet(String path) {
        Response response = initGetRequest().get(path);
        assertStatusCode(response, path);
        return response;
    }

    @Step("Execute Post Request: {path}")
    private Response executePost(String path) {
        Response response = initPostRequest().post(path);
        assertStatusCode(response, path);
        return response;
    }

    @Step("Execute Put Request: {path}")
    private Response executePut(String path) {
        Response response = initPutRequest().put(path);
        assertStatusCode(response, path);
        return response;
    }

    @Step("Execute Delete Request: {path}")
    private Response executeDelete(String path) {
        Response response = initDeleteRequest().delete(path);
        assertStatusCode(response, path);
        return response;
    }

    private void assertStatusCode(Response response, String path) {
        String payload = "";

        if (payLoad != null) {
            payload = payLoad.toString();
        }

        if (response.getStatusCode() != statusCode)
            throw new ResponseException(String.format("Expected status code [%d], but found [%d] %n Path:%s %n Request Payload: [%s] %n Response Body: [%s] %n",
                    statusCode, response.getStatusCode(), path, payload, response.getBody().asString()));
    }

    public final Response executeRequest() {
        switch (requestMethod) {
            case GET:
                return executeGet(path);
            case POST:
                return executePost(path);
            case PUT:
                return executePut(path);
            case DELETE:
                return executeDelete(path);
            default:
                throw new ResponseException(String.format("Invalid request method: [%s]", requestMethod.name()));
        }
    }

    public enum RequestMethod {
        POST, GET, PUT, DELETE
    }
}
