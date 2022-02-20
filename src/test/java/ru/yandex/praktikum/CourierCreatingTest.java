package ru.yandex.praktikum;

import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import ru.yandex.praktikum.scooter.api.CourierClient;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;
import static org.junit.jupiter.api.Assertions.*;

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
    CourierClient courierClient;
    int courierHappyCreatingStatus = 201;
    String courierDuplicateMsg = "Этот логин уже используется. Попробуйте другой.";
    int courierDuplicateStatus = 409;
    String courierMussedDataMsg = "Недостаточно данных для создания учетной записи";
    int courierMissedDataStatus = 400;

    @BeforeAll
    public void setUp() {
        courierClient = new CourierClient();
    }


    @Description("Курьера можно создать")
    @Test
    public void courierCanBeCreated() {
        Courier courier = Courier.getRandom();
        ok = courierClient.createCourier(courier);
        courierId = courierClient.loginCourierWithValidData(new CourierCredentials(courier.login, courier.password));
        courierClient.deleteCourier(courierId);
        assertTrue(ok);
    }

    @Description("Запрос на создание курьера возвращает требуемый код ответа")
    @Test
    public void courierCreatedSuccessfullyReturnCorrectStatusCode() {
        Courier courier = Courier.getRandom();
        statusCode = courierClient.createCourierReturnStatusCode(courier);
        courierId = courierClient.loginCourierWithValidData(new CourierCredentials(courier.login, courier.password));
        courierClient.deleteCourier(courierId);
        assertEquals(courierHappyCreatingStatus, statusCode);
    }

    @Description("Успешный запрос на создание курьера возвращает ok:true")
    @Test
    public void courierCreatedSuccessfullyReturnOkTrue() {
        Courier courier = Courier.getRandom();
        ok = courierClient.createCourier(courier);
        courierId = courierClient.loginCourierWithValidData(new CourierCredentials(courier.login, courier.password));
        courierClient.deleteCourier(courierId);
        assertEquals(true, ok );
    }


    @Description("Нельзя создать двух одинаковых курьеров")
    @Test
    public void errorWhenCreateCourierDuplicate() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourierWithValidData(new CourierCredentials(courier.login, courier.password));
        Courier courierTwin = new Courier(login, password, firstName);
        message = courierClient.createCourierDuplicate(courierTwin);
        courierClient.deleteCourier(courierId);
        assertEquals(courierDuplicateMsg, message);
    }

    @Description("Если создать двух одинаковых курьеров возвращается требуемый код ответа")
    @Test
    public void errorWhenCreateCourierDuplicateReturnCorrectStatusCode() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourierWithValidData(new CourierCredentials(courier.login, courier.password));
        Courier courierTwin = new Courier(login, password, firstName);
        statusCode = courierClient.createCourierDuplicateReturnStatusCode(courierTwin);
        courierClient.deleteCourier(courierId);
        assertEquals(courierDuplicateStatus, statusCode );
   }

    @Description("Если логина нет, запрос возвращает ошибку")
    @Test
    public void errorWhenCreateCourierMissedLogin() {

        Courier courier = new Courier(null, password, firstName);
        message = courierClient.createCourierWithMissedData(courier);
        assertEquals(courierMussedDataMsg, message );
    }

    @Description("Если логина нет, запрос возвращает требуемый код ответа")
    @Test
    public void createCourierMissedLoginReturnCorrectStatusCode() {

        Courier courier = new Courier(null, password, firstName);
        statusCode = courierClient.createCourierWithMissedDataReturnStatusCode(courier);
        assertEquals(courierMissedDataStatus, statusCode);
    }

    @Description("Если пароля нет, запрос возвращает ошибку")
    @Test
    public void errorWhenCreateCourierMissedPassword() {
        Courier courier = new Courier(login, null, firstName);
        message = courierClient.createCourierWithMissedData(courier);
        assertEquals(courierMussedDataMsg, message );
    }

    @Description("Если пароля нет, запрос возвращает требуемый код ответа")
    @Test
    public void createCourierMissedPasswordReturnCorrectStatusCode() {
        Courier courier = new Courier(login, null, firstName);
        statusCode = courierClient.createCourierWithMissedDataReturnStatusCode(courier);
        assertEquals(courierMissedDataStatus, statusCode );
    }

    @Description("Если имени нет, запрос возвращает ошибку")
    @Test
    public void errorWhenCreateCourierMissedFirstName() {
        Courier courier = new Courier(login, password, null);
        message = courierClient.createCourierWithMissedData(courier);
        courierId = courierClient.loginCourierWithValidData(new CourierCredentials(courier.login, courier.password));
        courierClient.deleteCourier(courierId);
        assertEquals(courierMussedDataMsg, message );
    }

    @Description("Если имени нет, запрос возвращает требуемый код ответа")
    @Test
    public void createCourierMissedFirstNameReturnCorrectStatusCode() {
        Courier courier = new Courier(login, password, null);
        statusCode = courierClient.createCourierWithMissedDataReturnStatusCode(courier);
        courierId = courierClient.loginCourierWithValidData(new CourierCredentials(courier.login, courier.password));
        courierClient.deleteCourier(courierId);
        assertEquals(courierMissedDataStatus, statusCode);
    }

    @Description("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    @Test
    public void errorWhenCreateLoginDuplicate() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourierWithValidData(new CourierCredentials(courier.login, courier.password));
        Courier courierTwinLogin = new Courier(login, password2, firstName2);
        message = courierClient.createCourierDuplicate(courierTwinLogin);
        courierClient.deleteCourier(courierId);
        assertEquals(courierDuplicateMsg, message);
    }

    @Description("Если создать пользователя с логином, который уже есть, возвращается требуемый код ответа")
    @Test
    public void createLoginDuplicateReturnCorrectStatusCode() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourierWithValidData(new CourierCredentials(courier.login, courier.password));
        Courier courierTwinLogin = new Courier(login, password2, firstName2);
        statusCode = courierClient.createCourierDuplicateReturnStatusCode(courierTwinLogin);
        courierClient.deleteCourier(courierId);
        assertEquals(courierDuplicateStatus, statusCode);
    }
}