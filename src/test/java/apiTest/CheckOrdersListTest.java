package apiTest;

import api.client.ScooterOrderSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import api.model.CancelOrderModel;
import api.model.CreateOrderModel;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.notNullValue;

public class CheckOrdersListTest {
    ScooterOrderSteps scooterOrderSteps;
    List<String> tracks;

    @Before
    public void setUp() {
        scooterOrderSteps = new ScooterOrderSteps();
        tracks = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (String track : tracks) {
            scooterOrderSteps.cancelOrder(new CancelOrderModel(track));
        }
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяем, что в тело возвращается список заказов и статус код 200")
    public void testGetOrdersList() {
        ValidatableResponse newOrderResponse = scooterOrderSteps.createNewOrder(CreateOrderModel.getRandom());

        scooterOrderSteps.getOrdersList()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);

        String track = newOrderResponse
                .assertThat().statusCode(201)
                .extract()
                .body()
                .path("track").toString();

        tracks.add(track);
    }
}
