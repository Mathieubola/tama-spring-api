package dev.boudot.tama.api.GameObjects;

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

}
