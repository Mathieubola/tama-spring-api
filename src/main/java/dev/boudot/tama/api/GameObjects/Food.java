package dev.boudot.tama.api.GameObjects;

public class Food {

    private final String name;
    private final int price;
    private final int value;


    public Food(String name, int price, int value) {
        this.name = name;
        this.price = price;
        this.value = value;
    }

    public boolean equals(Food food) {
        return this.name == food.name && this.price == food.price && this.value == food.value;
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
