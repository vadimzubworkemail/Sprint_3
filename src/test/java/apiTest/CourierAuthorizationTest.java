package apiTest;


import api.client.CourierClientSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import api.model.CourierModel;
import api.model.LoginModel;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CourierAuthorizationTest {

    CourierClientSteps courierClientSteps;
    List<String> ids;
    String login;
    String password;
    String firstName;
    CourierModel courier;

    @Before
    public void setUp() {
        courierClientSteps = new CourierClientSteps();
        ids = new ArrayList<>();

        courier = CourierModel.getRandom();
        courierClientSteps.registerNewCourier(courier)
                .assertThat()
                .statusCode(201);
        login = courier.login;
        password = courier.password;
        firstName = courier.firstName;

        LoginModel courierCredentials = LoginModel.from(courier);
        String courierId = courierClientSteps.loginCourier(courierCredentials)
                .assertThat().statusCode(200)
                .extract()
                .body()
                .path("id").toString();

        ids.add(courierId);
    }

    @After
    public void tearDown() {
        for (String id : ids) {
            courierClientSteps.deleteCourier(id);
        }
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверяем, что после авторизации возвращается код 200")
    public void positiveAuthorizationTest() {
        LoginModel courierCredentials = LoginModel.from(courier);
        courierClientSteps.loginCourier(courierCredentials)
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Авторизация с незаполненным полем логин")
    @Description("Проверяем, что возвращается код 400 и уведомление об ошибке")
    public void authorizationCourierWithoutLoginTest() {
        LoginModel courierCredentials = new LoginModel(null, password);
        courierClientSteps.loginCourier(courierCredentials)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }


    @Test
    @DisplayName("Авторизация с незаполненным полем пароль")
    @Description("Проверяем, что возвращается статус код 400 и уведомление об ошибке")
    public void authorizationCourierWithoutPasswordTest() {
        LoginModel courierCredentials = new LoginModel(login, null);
        courierClientSteps.loginCourier(courierCredentials)
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    @Description("Проверяем, что возвращается статус код 404 и уведомление об ошибке")
    public void authorizationCourierWithInvalidLoginTest() {
        String invalidLogin = "Pupkin";
        LoginModel courierCredentials = new LoginModel(invalidLogin, password);
        courierClientSteps.loginCourier(courierCredentials)
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Проверяем, что возвращается статус код 404 и уведомление об ошибке")
    public void authorizationCourierWithInvalidPasswordTest() {
        String invalidPassword = "qwerty";
        LoginModel courierCredentials = new LoginModel(login, invalidPassword);
        courierClientSteps.loginCourier(courierCredentials)
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
}
