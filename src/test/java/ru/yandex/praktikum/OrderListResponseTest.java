package ru.yandex.praktikum;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.yandex.praktikum.scooter.api.OrderClient;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderListResponseTest {
    ArrayList<String> orderList;
    private OrderClient orderClient;

    @BeforeAll
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Description("В тело ответа возвращается список заказов")
    @Test
    public void requestShouldBeReturnOrdersList() {
        orderList = orderClient.getOrderList();

        assertEquals(false, orderList.isEmpty());

    }
}
