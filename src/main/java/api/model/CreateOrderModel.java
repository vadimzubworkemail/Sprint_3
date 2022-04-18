package api.model;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

import static api.client.Utils.createDateString;

public class CreateOrderModel extends Request {
    private static Random random = new Random();

    public final String firstName;
    public final String lastName;
    public final String address;
    public final String metroStation;
    public final String phone;
    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public final String[] colors;

    public CreateOrderModel(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] colors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colors = colors;
    }

    public static CreateOrderModel getRandom(String[] colors) {
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        final String lastName = RandomStringUtils.randomAlphabetic(10);
        final String address = RandomStringUtils.randomAlphabetic(10);
        final String metroStation = RandomStringUtils.randomAlphabetic(10);
        final String phone = RandomStringUtils.randomAlphabetic(10);
        final int rentTime = random.nextInt();
        final String deliveryDate = createDateString();
        final String comment = RandomStringUtils.randomAlphabetic(10);
        return new CreateOrderModel(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, colors);
    }

    public static CreateOrderModel getRandom() {
        return getRandom(null);
    }

}
