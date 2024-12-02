package com.ApiTesting.RestAssuredBasics.GET;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class APITesting_NonBDDStyle {
    static RequestSpecification r = RestAssured.given();
    @Description("TC1 - NonBDDStyleGET - Positive TestCase")

    @Test
    public void test_NonBDDStyleGET_Positive(){
        r.baseUri("https://api.zippopotam.us");
        r.basePath("/IN/302012");
        r.when().log().all().get();
        r.then().log().all().statusCode(200);
    }

    @Description("TC2 - NonBDDStyleGET - Negative TestCase")
    @Test
    public void test_NonBDDStyleGET_Negative(){
        r.baseUri("https://api.zippopotam.us");
        r.basePath("/IN/-12");
        r.when().log().all().get();
        r.then().log().all().statusCode(404);
    }
}
