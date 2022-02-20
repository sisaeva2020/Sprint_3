package ru.yandex.praktikum;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.scooter.api.OrderClient;
import ru.yandex.praktikum.scooter.api.model.Order;
import static org.junit.jupiter.api.Assertions.*;



@RunWith(Parameterized.class)
public class OrderCreatingTest {

    int track;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    private static String firstNameParam = RandomStringUtils.randomAlphabetic(5);
    private static String lastNameParam = RandomStringUtils.randomAlphabetic(5);
    private static String addressParam = RandomStringUtils.randomAlphabetic(7);
    private static String metroStationParam = RandomStringUtils.randomNumeric(2);
    private static String phoneParam = RandomStringUtils.randomNumeric(11);;
    private static String rentTimeParam = RandomStringUtils.randomNumeric(1);;
    private static String deliveryDateParam = RandomStringUtils.randomNumeric(4);;
    private static String commentparam = RandomStringUtils.randomAlphabetic(10);
    private static String[] colorBlack = new String[] {"BlACK"};
    private static String[] colorGrey = new String[] {"GREY"};
    private static String[] colorBlackGrey = new String[] {"BlACK","GREY"};

    public OrderCreatingTest(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this. comment = comment;
        this.color = color;

    }

    @Parameterized.Parameters
    public static Object[] getOrderData() {
        return new Object[][]{
                {firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentparam, colorBlack},
                {firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentparam, colorGrey},
                {firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentparam, colorBlackGrey},
                {firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentparam, null}
        };
    }

    @Test
    public void orderCanBeCreatedWithBlackOrGreyColor() {
        OrderClient orderClient = new OrderClient();
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        track = orderClient.createOrder(order);
        assertNotEquals(0, track);

    }
}

