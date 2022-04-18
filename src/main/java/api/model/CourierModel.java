package api.model;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierModel extends Request{
    public final String login;
    public final String password;
    public final String firstName;

    public CourierModel(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static CourierModel getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierModel(login, password, firstName);
    }

}
