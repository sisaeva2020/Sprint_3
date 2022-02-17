package ru.yandex.praktikum;

import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.yandex.praktikum.scooter.api.CourierClient;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourierCreatingTest {
    boolean ok;
    int statusCode;
    int courierId;
    String message;
    String login = RandomStringUtils.randomAlphabetic(5);
    String password = RandomStringUtils.randomAlphabetic(5);
    String firstName = RandomStringUtils.randomAlphabetic(5);
    String password2 = RandomStringUtils.randomAlphabetic(5);
    String firstName2 = RandomStringUtils.randomAlphabetic(5);
    private CourierClient courierClient;

    @BeforeAll
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Description("Курьера можно создать")
    @Test
    public void courierCanBeCreated() {
        Courier courier = Courier.getRandom();
        ok = courierClient.create(courier);
        courierId = courierClient.loginWithValidData(new CourierCredentials(courier.login, courier.password));

        assertTrue(ok);

        courierClient.deleteCourier(courierId);


    }

    @Description("Запрос возвращает правильный код ответа")
    @Test
    public void requestReturnCorrectAnswerCode() {
        Courier courier = Courier.getRandom();
        statusCode = courierClient.createExtractStatusCode(courier);
        courierId = courierClient.loginWithValidData(new CourierCredentials(courier.login, courier.password));

        assertThat(statusCode, equalTo(201));

        courierClient.deleteCourier(courierId);

    }

    @Description("Успешный запрос возвращает ok:true")
    @Test
    public void requestReturnOkTrue() {
        Courier courier = Courier.getRandom();
        ok = courierClient.create(courier);
        courierId = courierClient.loginWithValidData(new CourierCredentials(courier.login, courier.password));

        assertThat(ok, equalTo(true));

        courierClient.deleteCourier(courierId);

    }


    @Description("Нельзя создать двух одинаковых курьеров")
    @Test
    public void errorWhenCreateCourierDuplicate() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        message = courierClient.createDuplicate(courier);

        assertThat(message, equalTo("Этот логин уже используется. Попробуйте другой."));


    }

    @Description("Если логина нет, запрос возвращает ошибку")
    @Test
    public void errorWhenCreateCourierMissedLogin() {

        Courier courier = new Courier(null, password, firstName);
        message = courierClient.createWithMissedData(courier);
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));


    }

    @Description("Если пароля нет, запрос возвращает ошибку")
    @Test
    public void errorWhenCreateCourierMissedPassword() {
        Courier courier = new Courier(login, null, firstName);
        message = courierClient.createWithMissedData(courier);
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));


    }

    @Description("Если имени нет, запрос возвращает ошибку")
    @Test
    public void errorWhenCreateCourierMissedFirstName() {
        Courier courier = new Courier(login, password, null);
        message = courierClient.createWithMissedData(courier);
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Description("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    @Test
    public void errorWhenCreateLoginDuplicate() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.create(courier);
        Courier courierTwinLogin = new Courier(login, password2, firstName2);
        message = courierClient.createDuplicate(courierTwinLogin);
        assertThat(message, equalTo("Этот логин уже используется. Попробуйте другой."));


    }
}