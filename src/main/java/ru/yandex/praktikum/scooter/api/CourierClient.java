package ru.yandex.praktikum.scooter.api;

import io.qameta.allure.Step;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    public final String PATH = BASE_URL + "courier/";


    @Step("Create courier {courier}")
    public boolean create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }
    @Step("Create courier {courier} with extract status code")
    public int createExtractStatusCode(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .statusCode();
    }


    @Step("Create courier {courier} with missed data")
    public String createWithMissedData(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Create courier {courier} duplicate")
    public String createDuplicate (Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .assertThat()
                .statusCode(409)
                .extract()
                .path("message");
    }

    @Step("Login as {courierCredentials} with valid data")
    public int loginWithValidData (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Login as {courierCredentials} with extract statusCode")
    public int loginWithValidDataReturnStatusCode (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .statusCode();
    }

    @Step("Login as {courierCredentials} with incorrect data")
    public String loginWithIncorrectData (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }

    @Step("Login as {courierCredentials} with missed data")
    public String loginWithMissedData (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }


    @Step("Delete courier {courierId}")
    public boolean deleteCourier (int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

}