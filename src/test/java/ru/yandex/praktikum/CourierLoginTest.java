package ru.yandex.praktikum;

import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.yandex.praktikum.scooter.api.CourierClient;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourierLoginTest {
    CourierClient courierClient;
    int courierId;
    String login2 = RandomStringUtils.randomAlphabetic(5);
    String password2 = RandomStringUtils.randomAlphabetic(5);
    String message;
    int statusCode;
    int happyLoginStatus = 200;
    String loginIncorrectDataMsg = "Учетная запись не найдена";
    int loginIncorrectDataStatus = 404;
    String loginMissedDataMsg = "Недостаточно данных для входа";
    int loginMissedDataStatus = 400;

    @BeforeAll
    public void setUp() {
        courierClient = new CourierClient();
    }


    @Description("Курьер может авторизоваться")
    @Test
    public void courierCanLoginSuccessfully() {

        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        statusCode = courierClient.loginCourierWithValidDataReturnStatusCode(new CourierCredentials(courier.login, courier.password));
        courierId = courierClient.loginCourierWithValidData(new CourierCredentials(courier.login, courier.password));
        courierClient.deleteCourier(courierId);
        assertEquals(happyLoginStatus, statusCode);
    }

    @Description("Успешный запрос возвращает id")
    @Test
    public void RequestReturnCourierId() {

        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourierWithValidData(new CourierCredentials(courier.login, courier.password));
        courierClient.deleteCourier(courierId);
        assertNotEquals(0, courierId);
    }

    @Description("Система вернёт ошибку, если неправильно указать логин")
    @Test
    public void returnErrorWithIncorrectLogin() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        message = courierClient.loginCourierWithIncorrectData(new CourierCredentials(login2, courier.password));
        assertEquals(loginIncorrectDataMsg, message);

    }

    @Description("Система вернёт требуемый код, если неправильно указать логин")
    @Test
    public void incorrectLoginReturnCorrectStatusCode() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        statusCode = courierClient.loginCourierWithIncorrectDataReturnStatusCode(new CourierCredentials(login2, courier.password));
        assertEquals(loginIncorrectDataStatus, statusCode);

    }

    @Description("Система вернёт ошибку, если неправильно указать пароль")
    @Test
    public void returnErrorWithIncorrectPassword() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        message = courierClient.loginCourierWithIncorrectData(new CourierCredentials(courier.login, password2));
        assertEquals(loginIncorrectDataMsg, message);
    }


    @Description("Система вернёт требуемый код, если неправильно указать пароль")
    @Test
    public void incorrectPasswordReturnCorrectStatusCode() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        statusCode = courierClient.loginCourierWithIncorrectDataReturnStatusCode(new CourierCredentials(courier.login, password2));
        assertEquals(loginIncorrectDataStatus, statusCode);
    }

    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    @Test
    public void returnErrorIfLoginWithNonExistentCredentials() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        message = courierClient.loginCourierWithIncorrectData(new CourierCredentials(login2, password2));
        assertEquals(loginIncorrectDataMsg, message);
    }

    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает требуемый код")
    @Test
    public void loginWithNonExistentCredentialsReturnCorrectStatusCode() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        statusCode = courierClient.loginCourierWithIncorrectDataReturnStatusCode(new CourierCredentials(login2, password2));
        assertEquals(loginIncorrectDataStatus, statusCode);
    }

    @Description("Если логина нет, запрос возвращает ошибку")
    @Test
    public void returnErrorWithMissedLogin() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        message = courierClient.loginCourierWithMissedData(new CourierCredentials(null, courier.password));
        assertEquals(loginMissedDataMsg, message);
    }

    @Description("Если логина нет, запрос возвращает требуемый код")
    @Test
    public void missedLoginReturnCorrectStatusCode() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        statusCode = courierClient.loginCourierWithMissedDataReturnStatusCode(new CourierCredentials(null, courier.password));
        assertEquals(loginMissedDataStatus, statusCode);
    }

    @Description("Если пароля нет, запрос возвращает ошибку")
    @Test
    public void returnErrorWithMissedPassword() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        message = courierClient.loginCourierWithMissedData(new CourierCredentials(courier.login, null));
        assertEquals(loginMissedDataMsg, message);
    }

    @Description("Если пароля нет, запрос возвращает требуемый код")
    @Test
    public void missedPasswordReturnCorrectStatusCode() {
        Courier courier = Courier.getRandom();
        courierClient.createCourier(courier);
        statusCode = courierClient.loginCourierWithMissedDataReturnStatusCode(new CourierCredentials(courier.login, null));
        assertEquals(loginMissedDataStatus, statusCode);
    }

}
