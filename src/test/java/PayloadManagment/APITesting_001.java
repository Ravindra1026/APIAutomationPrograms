package PayloadManagment;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static org.assertj.core.api.Assertions.*;
import org.testng.annotations.Test;

public class APITesting_001 {

    RequestSpecification requestSpecification;
    ValidatableResponse validatableResponse;

    @Test
    public void test_Positive(){
        Booking booking = new Booking();
        booking.setFirstname("Ravi");
        booking.setLastname("Kumar");
        booking.setTotalprice(133);
        booking.setDepositpaid(true);
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2024-12-11");
        bookingDates.setCheckout("2024-12-11");
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);

        Gson gson = new Gson();
        //Object -> JSON String
        String jsonStringBooking = gson.toJson(booking);
        System.out.println(jsonStringBooking);

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com");
        requestSpecification.basePath("/booking");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(jsonStringBooking).log().all();

        Response response = requestSpecification.when().post();
        String jsonResponseString = response.asString();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = gson.fromJson(jsonResponseString, BookingResponse.class);

        assertThat(bookingResponse.getBookingid()).isNotZero().isNotNull();
        assertThat(bookingResponse.getBooking().getFirstname()).isEqualTo("Ravi").isNotNull().isNotEmpty();




    }

}
