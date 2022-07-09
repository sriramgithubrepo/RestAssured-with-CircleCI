package org.example.test.setUp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;


public class Utility {

    public static RequestSpecification reqBuilder(String Url) {
        RestAssured.baseURI= Url;
        RequestSpecification request = RestAssured.given();
        return request;
    }
    public static Response getResponse(String Url) {
        Response response = reqBuilder(Url).get();
        return response;
    }
    public static boolean isAPICallSuccess(Response response) {
        int code = response.getStatusCode();
        if( code == 200 || code == 201 || code == 202 || code == 203 || code == 204 || code == 205) return true;
        return false;
    }
    public static String getResponseContent(Response response) {
        return response.getBody().asString();
    }

    public static ArrayNode constructListFromResponseString(String resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode =(ArrayNode) mapper.readTree(resp);
        return arrayNode;
    }
}
