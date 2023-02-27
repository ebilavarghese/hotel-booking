package com.management.hotelbooking.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
public class HotelBookingStepDef {
    private String response;
    private JsonPath jsonPath;
    private static int bookingId;
    private String token;

    @Value("${test.hotel.booking.endpoint}")
    private String hotelBookingEndpoint;

    @Value("${test.hotel.auth.endpoint}")
    private String hotelAuthEndpoint;

    @When("a request is made to create a new booking")
    public void aRequestIsMadeToCreateANewBooking() {
        String createBookingPayload = "{\"firstname\" : \"Luke\",\"lastname\" : \"Abraham\"," +
                "\"totalprice\" : 100,\"depositpaid\" :true, \"bookingdates\" :{\"checkin\" : \"2018-01-01\"," +
                " \"checkout\" : \"2018-01-10\"}, \"additionalneeds\" : \"Breakfast\"}";

        response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(createBookingPayload)
                .post(hotelBookingEndpoint)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();
        log.info("A new booking is made");
    }

    @Then("the booking is done successfully")
    public void theBookingIsDoneSuccessfully() {
        jsonPath = JsonPath.from(response);
        assertThat(response.contains("bookingid"));
        bookingId = jsonPath.get("bookingid");
        log.info("The booking is successful");
    }

    @Given("that a valid token is obtained")
    public void thatAValidTokenIsObtained() {
        String authorizationPayload = "{\"username\" : \"admin\",\"password\" : \"password123\"}";
        response = given()
                .header("Content-Type", "application/json")
                .body(authorizationPayload)
                .post(hotelAuthEndpoint)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();
        log.info("Token is requested");
        jsonPath=JsonPath.from(response);
        token = jsonPath.get("token");
    }

    @When("a request is made to update a booking")
    public void aRequestIsMadeToUpdateABooking() {
        String updateBookingPayload = "{\"firstname\" : \"Luke\",\"lastname\" : \"Abrahamm\",\"totalprice\" : 100,\"depositpaid\" :true, \"bookingdates\" :{\"checkin\" : \"2018-02-01\", \"checkout\" : \"2018-02-10\"}, \"additionalneeds\" : \"Breakfast\"}";
        response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie","token =" + token)
                .body(updateBookingPayload)
                .put(hotelBookingEndpoint + "/" + bookingId)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();
        log.info("A request is made to update the details of hotel booking");
    }

    @Then("the booking is updated successfully")
    public void theBookingIsUpdatedSuccessfully() {
        jsonPath = JsonPath.from(response);
        Assert.assertEquals("2018-02-01", jsonPath.get("bookingdates.checkin"));
        log.info("The booking is successfully modified");
    }

    @When("a request is made to partially update a booking")
    public void aRequestIsMadeToPartiallyUpdateABooking() {
        String partialUpdateBookingPayload = "{\"additionalneeds\" : \"Airport pick-up and drop-off\"}";
        response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token =" + token)
                .body(partialUpdateBookingPayload)
                .patch(hotelBookingEndpoint + "/" + bookingId)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();
        log.info("A request is made to update the booking partially");
    }

    @Then("the booking is partially updated successfully")
    public void theBookingIsPartiallyUpdatedSuccessfully() {
        jsonPath= JsonPath.from(response);
        Assert.assertEquals("Airport pick-up and drop-off", jsonPath.get("additionalneeds"));
        log.info("The details of a booking are modified successfully");
    }
    @When("a request is made to get all booking Ids")
    public void aRequestIsMadeToGetAllBookingIds() {
        response = given()
                .get(hotelBookingEndpoint)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();
        log.info("A request is made to return all booking ids");
    }

    @Then("the booking Ids are returned successfully")
    public void theBookingIdsAreReturnedSuccessfully() {
        jsonPath = JsonPath.from(response);
        List<Integer> bookingIds =jsonPath.get("bookingid");
        assertThat(bookingIds.contains(bookingId));
        log.info("The booking id is retrieved successfully");
    }
    @When("a request is made to delete a booking")
    public void aRequestIsMadeToDeleteABooking() {
        given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token =" + token)
                .delete(hotelBookingEndpoint + "/" + bookingId)
                .then()
                .statusCode(201)
                .extract()
                .response()
                .asString();
        log.info("A request is made to delete the hotel booking");
    }

    @Then("the booking is deleted successfully")
    public void theBookingIsDeletedSuccessfully() {
        given()
                .get(hotelBookingEndpoint + "/" + bookingId)
                .then()
                .statusCode(404)
                .extract()
                .response()
                .asString();
        log.info("The hotel booking is deleted successfully");
    }
}
