package api.model;

import api.client.Utils;

public class Request {
    public String toJsonString() {
        return Utils.serializeToJsonIgnoreNulls(this);
    }

}
