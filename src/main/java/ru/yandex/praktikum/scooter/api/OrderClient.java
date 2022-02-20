package ru.yandex.praktikum.scooter.api;

import io.qameta.allure.Step;
import ru.yandex.praktikum.scooter.api.model.Order;
import java.util.ArrayList;
import static io.restassured.RestAssured.given;
public class OrderClient extends ScooterRestClient {

    public final String ORDER_PATH = BASE_URL + "orders/";


    @Step("Create order {order}")
    public int createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .extract()
                .path("track");
    }

    @Step("Cancel order {track}")
    public boolean cancelOrder(int track) {
        return given()
                .spec(getBaseSpec())
                .when()
                .put(ORDER_PATH + "cancel/")
                .then()
                .extract()
                .path("ok");
    }

    @Step("Get order list")
    public ArrayList<String> getOrderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then()
                .extract()
                .path("orders.id");
    }
}