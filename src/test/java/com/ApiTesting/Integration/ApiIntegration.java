package com.ApiTesting.Integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiIntegration {
    // Create a token = POST - auth api
    // Create a Booking = POST - Create Booking
    // Perform a update in booking - Patch - Partial Update Booking
    // Verify that PUT is success by GET request - GET GetBooking
    // Delete the ID - DELETE DeleteBooking
    // Verify if doesn't exist - GET GetBooking

    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;

    String token;
    String bookingId;

    public String getToken(){
        String payload = "{\n" +
                " \"username\" : \"admin\",\n" +
                " \"password\" : \"password123\"\n" +
                " }";
        //Given
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/auth");
        requestSpecification.contentType(ContentType.JSON).log().all();
        requestSpecification.body(payload);
        //When
        response = requestSpecification.when().post();
        //Then
        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        //Token
        token = response.jsonPath().getString("token");
        System.out.println(token);
        return token;
    }
    public String getBookingId(){
        String bookingPayload = "{\n" +
        "                           \"firstname\" : \"Yashi\",\n" +
        "                           \"lastname\" : \"Pareek\",\n" +
        "                           \"depositpaid\" : true, \n" +
        "                           \"bookingdates\" : { \n" +
        "                                   \"checkin\" : \"2024-12-11\",\n" +
        "                                   \"checkout\" : \"2024-12-13\" \n" +
        "                            },\n" +
        "                           \"additionalneeds\" : \"Breakfast\" \n" +
                                  "}";

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking");
        requestSpecification.contentType(ContentType.JSON).log().all();
        requestSpecification.body(bookingPayload);

        //When
        response = requestSpecification.when().post();

        //Then
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);
        bookingId = response.jsonPath().getString("bookingid");
        System.out.println(bookingId);
        return bookingId;

    }
    @Test(priority = 1)
    public void test_update_request_put(){
        token = getToken();
        bookingId = getBookingId();
        String payloadPUT = "{\n" +
                "                     \"firstname\" : \"Yashdeep\", \n" +
                "                     \"lastname\" : \"Pareek\", \n" +
                "                     \"totalprice\" : 110, \n" +
                "                     \"depositpaid\" : true,\n" +
                "                     \"bookingdates\" : {\n" +
                "                         \"checkin\" : \"2024-12-11\", \n" +
                "                         \"checkout\" : \"2024-12-13\"\n" +
                "                         },\n" +
                "                      \"additionalneeds\" : \"Breakfast\" \n" +
                " }";
        //Given
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking/" + bookingId);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token", token);
        requestSpecification.body(payloadPUT).log().all();

        //When
        response = requestSpecification.when().put();

        //then
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

    }

    @Test (priority = 2)
    public void test_update_request_get(){
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking/" + bookingId);

        response = requestSpecification.when().log().all().get();
        requestSpecification.then().log().all().statusCode(200);

        String firstname = response.jsonPath().getString("firstname");
        Assert.assertEquals(firstname,"Yashdeep");
    }
    @Test(priority = 3)
    public void test_delete_booking(){
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking/" + bookingId);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token", token);

        response = requestSpecification.when().delete();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);

    }


}
