package org.example.test.setUp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

/**@description This Class contains common Utility methods */

public class Utility {
    /**@description This method build and returns an instance for RestAssured
      RequestSpecification by receiving URL as parameter */
    public static RequestSpecification reqBuilder(String Url) {
        RestAssured.baseURI= Url;
        RequestSpecification request = RestAssured.given();
        return request;
    }
    /**@description This method build and returns an instance for RestAssured
    Response based on above method */
    public static Response getResponse(String Url) {
        Response response = reqBuilder(Url).get();
        return response;
    }
    /**@description This method verifies if API call is Success by validating Status code */
    public static boolean isAPICallSuccess(Response response) {
        int code = response.getStatusCode();
        if( code == 200 ) return true;
        return false;
    }
    /**@description This method converts RestAssured Response body into String */
    public static String getResponseContent(Response response) {
        return response.getBody().asString();
    }
    /**@description This method constructs list object using Object mapper from response*/
    public static ArrayNode constructListFromResponseString(String resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode =(ArrayNode) mapper.readTree(resp);
        return arrayNode;
    }
}
