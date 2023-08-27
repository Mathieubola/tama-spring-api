package dev.boudot.tama.api.GameObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("ConsumableInventory")
public class ConsumableInventory implements Serializable {

    private List<Food> foods;
    private List<Integer> foodCounts;
    private List<Snack> snacks;
    private List<Integer> snackCounts;

    public static final long serialVersionUID = -241827264821396181L;


    public ConsumableInventory() {
        this.foods = new ArrayList<>();
        this.foodCounts = new ArrayList<>();
        this.snacks = new ArrayList<>();
        this.snackCounts = new ArrayList<>();
    }


    private <T> boolean addConsumable(List<T> consumables, List<Integer> counts, T newConsumable, int amount) {
        if ( amount > 3 ) {
            return false;
        }

        int newConsumableIndex = -1;
        for (int i = 0; i < consumables.size(); i++) {
            if ( consumables.get(i).equals(newConsumable) ) {
                newConsumableIndex = i;
                break;
            }
        }

        if ( newConsumableIndex == -1 ) {
            consumables.add(newConsumable);
            counts.add(amount);
            return true;
        }

        int newConsumableAmount = counts.get(newConsumableIndex);

        if ( newConsumableAmount + amount > 3 ) {
            return false;
        }

        counts.set(newConsumableAmount, newConsumableAmount + amount);

        return true;
    }

    public boolean addFood(Food food) {
        return this.addFood(food, 1);
    }

    public boolean addFood(Food food, int amount) {
        return this.addConsumable(this.foods, this.foodCounts, food, amount);
    }

    public boolean addSnack(Snack snack) {
        return this.addSnack(snack, 1);
    }

    private boolean addSnack(Snack snack, int amount) {
        return this.addConsumable(snacks, snackCounts, snack, amount);
    }

    @Override
    public String toString() {
        return "{" +
            " foods='" + this.foods.toString() + "'" +
            ", foodCounts='" + this.foodCounts.toString() + "'" +
            ", snacks='" + this.snacks.toString() + "'" +
            ", snackCounts='" + this.snackCounts.toString() + "'" +
            "}";
    }

}
