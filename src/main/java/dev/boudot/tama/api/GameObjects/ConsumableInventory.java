package dev.boudot.tama.api.GameObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsumableInventory {

    private List<Food> foods;
    private List<Integer> foodCounts;
    private List<Snack> snacks;
    private List<Integer> snackCounts;


    public ConsumableInventory() {
        this.foods = new ArrayList<>();
        this.foodCounts = new ArrayList<>();
        this.snacks = new ArrayList<>();
        this.snackCounts = new ArrayList<>();
    }

    public ConsumableInventory(List<Food> foods, List<Integer> foodCounts, List<Snack> snacks, List<Integer> snackCounts) {
        this.foods = foods;
        this.foodCounts = foodCounts;
        this.snacks = snacks;
        this.snackCounts = snackCounts;
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

    public HashMap<String, Object> getHash() {
        HashMap<String, Object> hash = new HashMap<>();
        for (int i = 0; i < this.foods.size(); i++) {
            hash.put("food." + i, this.foods.get(i).getHash());
            hash.put("foodCount." + i, this.foodCounts.get(i));
        }
        for (int i = 0; i < this.snacks.size(); i++) {
            hash.put("snack." + i, this.snacks.get(i).getHash());
            hash.put("snackCount." + i, this.snackCounts.get(i));
        }
        return hash;
    }

    public static ConsumableInventory fromHash(HashMap<String, Object> hash) {
        List<Food> foods = new ArrayList<>();
        List<Integer> foodCounts = new ArrayList<>();
        List<Snack> snacks = new ArrayList<>();
        List<Integer> snackCounts = new ArrayList<>();

        if ( hash.containsKey("food") ) {
            HashMap<String, Object> foodHash = (HashMap<String, Object>) hash.get("food");
            HashMap<String, Object> foodCountHash = (HashMap<String, Object>) hash.get("foodCount");

            for (int i = 0; i < 3; i++) {
                String i_str = String.valueOf(i);
                if ( foodHash.containsKey(i_str) ) {
                    foods.add(Food.fromHash((HashMap<String, Object>) foodHash.get(i_str)));
                    foodCounts.add((int) foodCountHash.get(i_str));
                }
            }
        }

        if ( hash.containsKey("snack") ) {
            HashMap<String, Object> snackHash = (HashMap<String, Object>) hash.get("snack");
            HashMap<String, Object> snackCountHash = (HashMap<String, Object>) hash.get("snackCount");

            for (int i = 0; i < 3; i++) {
                String i_str = String.valueOf(i);
                if ( snackHash.containsKey(i_str) ) {
                    snacks.add(Snack.fromHash((HashMap<String, Object>) snackHash.get(i_str)));
                    snackCounts.add((int) snackCountHash.get(i_str));
                }
            }
        }

        return new ConsumableInventory(foods, foodCounts, snacks, snackCounts);
    }

}
