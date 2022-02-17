package ru.yandex.praktikum;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.yandex.praktikum.scooter.api.OrderClient;
import ru.yandex.praktikum.scooter.api.model.Order;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderCreatingTest {
    int track;
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public String rentTime;
    public String deliveryDate;
    public String comment;
    public String[] color;
    private OrderClient orderClient;

    @BeforeAll
    public void setUp() {
        orderClient = new OrderClient();
    }

    @ParameterizedTest
    @CsvSource({"'max', 'max', 'jfgasjk', '124', '8906457865', '4', '2022', 'jhsahsdafsd', '{\"GREY\"}'",
            "'leo','leo','jhsfgws','124','89067853245','4','2022','jhsahsdafsd', '{\"GREY\", \"BLACK\"}'",
            "'roi', 'roi', 'llzuutyrt', '124', '8906785466', '4', '2022', 'kjghbiufhi'"})



    @Description("Можно указать один из цветов, Можно указать оба цвета, Можно совсем не указывать цвет, тело ответа содержит track")
    @Test
    public void orderCanBeCreatedWithBlackOrGreyColor() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        track = orderClient.createOrder(order);

        assertThat(track, is(not(0)));

        orderClient.cancelOrder(track);

    }

}
