package api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import api.model.CourierModel;
import api.model.LoginModel;

import static io.restassured.RestAssured.given;

public class CourierClientSteps extends RestAssuredClient {
    private static final String PATH_COURIER = "/api/v1/courier/";

    @Step("Send POST request to /api/v1/courier")
    public ValidatableResponse registerNewCourier(CourierModel courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier.toJsonString())
                .when()
                .post(PATH_COURIER)
                .then();
    }

    @Step("Login courier and if success return response body")
    public ValidatableResponse loginCourier(LoginModel courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials.toJsonString())
                .when()
                .post(PATH_COURIER + "login")
                .then();
    }

    @Step("Send DELETE request to /api/v1/courier")
    public ValidatableResponse deleteCourier(String idCourier) {

        return given()
                .spec(getBaseSpec())
                .when()
                .delete(PATH_COURIER + idCourier)
                .then();
    }

}
