package dev.boudot.tama.api.GameObjects;

import java.util.HashMap;

public class Snack {

    private final String name;
    private final int price;
    private final int value;

    public Snack(String name, int price, int value) {
        this.name = name;
        this.price = price;
        this.value = value;
    }

    public boolean equals(Snack snack) {
        return this.name == snack.name && this.price == snack.price && this.value == snack.value;
    }


    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }

    public HashMap<String, Object> getHash() {
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("name", this.name);
        hash.put("price", this.price);
        hash.put("value", this.value);
        return hash;
    }

    public static Snack fromHash(HashMap<String, Object> hash) {
        return new Snack(
            (String) hash.get("name"),
            (int) hash.get("price"),
            (int) hash.get("value")
        );
    }

}
