package ru.yandex.praktikum.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    public final String PATH = BASE_URL + "courier/";


    @Step("Create courier {courier}")
    public boolean createCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .extract()
                .path("ok");
    }

    @Step("Create courier {courier} status code 201")
    public int createCourierReturnStatusCode(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .extract()
                .statusCode();
    }


    @Step("Create courier {courier} with missed data")
    public String createCourierWithMissedData(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .extract()
                .path("message");
    }

    @Step("Create courier {courier} with missed data status code 400")
    public int createCourierWithMissedDataReturnStatusCode(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .extract()
                .statusCode();

    }

    @Step("Create courier {courier} duplicate")
    public String createCourierDuplicate (Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .extract()
                .path("message");
    }

    @Step("Create courier {courier} duplicate 409")
    public int createCourierDuplicateReturnStatusCode (Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .extract()
                .statusCode();
    }

    @Step("Login as {courierCredentials} with valid data")
    public int loginCourierWithValidData (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .extract()
                .path("id");
    }

    @Step("Login as {courierCredentials} with valid data 200")
    public int loginCourierWithValidDataReturnStatusCode (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .extract()
                .statusCode();
    }

    @Step("Login as {courierCredentials} with incorrect data")
    public String loginCourierWithIncorrectData (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .extract()
                .path("message");
    }

    @Step("Login as {courierCredentials} with incorrect data 404")
    public int loginCourierWithIncorrectDataReturnStatusCode (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .extract()
                .statusCode();
    }

    @Step("Login as {courierCredentials} with missed data")
    public String loginCourierWithMissedData (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .extract()
                .path("message");
    }

    @Step("Login as {courierCredentials} with missed data 400")
    public int loginCourierWithMissedDataReturnStatusCode (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .extract()
                .statusCode();
    }


    @Step("Delete courier {courierId}")
    public boolean deleteCourier (int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(PATH + courierId)
                .then()
                .extract()
                .path( "ok");

    }


}