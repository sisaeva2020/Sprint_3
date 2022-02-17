package ru.yandex.praktikum;

import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.yandex.praktikum.scooter.api.CourierClient;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourierLoginTest {
    private CourierClient courierClient;
    private int courierId;
    private String login2 = RandomStringUtils.randomAlphabetic(5);
    private String password2 = RandomStringUtils.randomAlphabetic(5);
    String message;
    int statusCode;

    @BeforeAll
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Description("Курьер может авторизоваться")
    @Test
    public void courierCanLoginSuccessfully() {

        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        statusCode = courierClient.loginWithValidDataReturnStatusCode(new CourierCredentials(courier.login, courier.password));
        courierId = courierClient.loginWithValidData(new CourierCredentials(courier.login, courier.password));

        assertThat(statusCode, equalTo(200));

        courierClient.deleteCourier(courierId);

    }

    @Description("Успешный запрос возвращает id")
    @Test
    public void RequestReturnCourierId() {

        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        courierId = courierClient.loginWithValidData(new CourierCredentials(courier.login, courier.password));

        assertThat(courierId, is(not(0)));

        courierClient.deleteCourier(courierId);

    }

    @Description("Система вернёт ошибку, если неправильно указать логин")
    @Test
    public void returnErrorWithIncorrectLogin() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        message = courierClient.loginWithIncorrectData(new CourierCredentials(login2, courier.password));
        assertThat(message, equalTo("Учетная запись не найдена"));

    }

    @Description("Система вернёт ошибку, если неправильно указать пароль")
    @Test
    public void returnErrorWithIncorrectPassword() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        message = courierClient.loginWithIncorrectData(new CourierCredentials(courier.login, password2));
        assertThat(message, equalTo("Учетная запись не найдена"));

    }

    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    @Test
    public void returnErrorIfLoginWithNonExistentCredentials() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        message = courierClient.loginWithIncorrectData(new CourierCredentials(login2, password2));
        assertThat(message, equalTo("Учетная запись не найдена"));

    }

    @Description("Если логина нет, запрос возвращает ошибку")
    @Test
    public void returnErrorWithMissedLogin() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        message = courierClient.loginWithMissedData(new CourierCredentials(null, courier.password));
        assertThat(message, equalTo("Недостаточно данных для входа"));

    }

    @Description("Если пароля нет, запрос возвращает ошибку")
    @Test
    public void returnErrorWithMissedPassword() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        message = courierClient.loginWithMissedData(new CourierCredentials(courier.login, null));
        assertThat(message, equalTo("Недостаточно данных для входа"));

    }

}
