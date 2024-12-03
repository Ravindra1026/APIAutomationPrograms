package com.ApiTesting.RestAssuredBasics.GET;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class APITesting_BDDStyleGET {
    @Test
    public void test_GET_Req_POSITVIE(){
        String pin_code = "302012";
        RestAssured
                .given()
                    .baseUri("http://api.zippopotam.us")
                    .basePath("/IN/" + pin_code)
                .when()
                    .log().all()
                    .get()
                .then()
                    .log().all()
                    .statusCode(200);
    }

    @Test
    public void test_GET_Req_NEGITVIE(){
        String pin_code = "-12";
        RestAssured
                .given()
                .baseUri("http://api.zippopotam.us")
                .basePath("/IN/" + pin_code)
                .when()
                .log().all()
                .get()
                .then()
                .log().all()
                .statusCode(404);
    }
    @Test
    public void test_GET_Req_NEGITVIE2(){
        String pin_code = "Term";
        RestAssured
                .given()
                .baseUri("http://api.zippopotam.us")
                .basePath("/IN/" + pin_code)
                .when()
                .log().all()
                .get()
                .then()
                .log().all()
                .statusCode(404);
    }
}
