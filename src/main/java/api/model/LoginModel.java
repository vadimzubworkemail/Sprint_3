package api.model;

public class LoginModel extends Request{
    public final String login;
    public final String password;

    public LoginModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static LoginModel from(CourierModel courier) {
        return new LoginModel(courier.login, courier.password);
    }
}
