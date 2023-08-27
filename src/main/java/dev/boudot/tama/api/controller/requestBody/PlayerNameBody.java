package dev.boudot.tama.api.controller.requestBody;

public class PlayerNameBody {
    public String name;

    public boolean isLegal() {
        return name != null && name.length() > 0;
    }
}
