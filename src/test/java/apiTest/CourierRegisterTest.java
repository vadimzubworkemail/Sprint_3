package apiTest;

import api.client.CourierClientSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import api.model.CourierModel;
import api.model.LoginModel;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;


public class CourierRegisterTest {

    CourierClientSteps courierClientSteps;

    List<String> ids;

    @Before
    public void setUp() {
        courierClientSteps = new CourierClientSteps();
        ids = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (String id : ids) {
            courierClientSteps.deleteCourier(id);
        }
    }

    @Test
    @DisplayName("Регистрация курьера")
    @Description("Проверяем, что возваращается ответ ок и статус код 201")
    public void testRegisterNewCourierReturn201True() {
        CourierModel courier = CourierModel.getRandom();
        courierClientSteps.registerNewCourier(courier)
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);

        LoginModel courierCredentials = LoginModel.from(courier);
        String id = courierClientSteps.loginCourier(courierCredentials)
                .assertThat().statusCode(200)
                .extract()
                .body()
                .path("id").toString();

        ids.add(id);
    }


    @Test
    @DisplayName("Регистрация двух одинаковых курьеров")
    @Description("Проверяем, что при регистрации двух одинаковых курьеров возвращается статус код 409 и уведомление о невозможнности такой регистрации")
    public void testRegisterTwoCouriersWithSameLoginReturn409UserExists() {
        CourierModel courier = CourierModel.getRandom();
        courierClientSteps.registerNewCourier(courier)
                .assertThat()
                .statusCode(201);
        courierClientSteps.registerNewCourier(courier)
                .assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(409);

        LoginModel courierCredentials = LoginModel.from(courier);
        String id = courierClientSteps.loginCourier(courierCredentials)
                .assertThat().statusCode(200)
                .extract()
                .body()
                .path("id").toString();
        ids.add(id);
    }


    @Test
    @DisplayName("Регистрация с незаполненным полем пароль")
    @Description("Проверяем, что при регистрации с незаполненным полем парооль возвращается статус код 400 и уведомление об ошибке")
    public void testRegisterCourierWithoutPasswordReturn400BadRequest() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        CourierModel courier = new CourierModel(login, null, firstName);
        courierClientSteps.registerNewCourier(courier)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }


    @Test
    @DisplayName("Регистрация с незаполненным полем логин")
    @Description("Проверяем, что при регистрации с незаполненным полем логин возвращается статус код 400 и уведомление об ошибке")
    public void testRegisterCourierWithoutLoginReturn400BadRequest() {
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        CourierModel courier = new CourierModel(null, password, firstName);
        courierClientSteps.registerNewCourier(courier)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }


}
