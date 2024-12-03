package com.ApiTesting.RestAssuredBasics.PATCH;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;





public class APITestingPatch {
    @Description("Verify the Put Api for Restfull booker apis")
    @Test
    public void test_put_non_Bdd(){
        String token = "48ca1b473559245";
        String bookingId = "2218";
        String payloadPUT = "{\n" +
          "                     \"firstname\" : \"Yashi\", \n" +
          "                     \"lastname\" : \"Pareek\", \n" +
          "                     \"totalprice\" : 110, \n" +
          "                     \"depositpaid\" : true,\n" +
          "                     \"bookingdates\" : {\n" +
          "                         \"checkin\" : \"2024-12-11\", \n" +
          "                         \"checkout\" : \"2024-12-13\"\n" +
          "                         },\n" +
          "                      \"additionalneeds\" : \"Breakfast\" \n" +
        " }";

        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking/" + bookingId);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token",token);
        requestSpecification.body(payloadPUT).log().all();

        Response response = requestSpecification.when().patch();
        ValidatableResponse validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

    }

}
