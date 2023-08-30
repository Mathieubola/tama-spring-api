package dev.boudot.tama.api.GameObjects;

import java.util.HashMap;

public class Tama {
    protected String name;
    protected int foodLevel;
    protected int happinessLevel;
    protected tamaStage stage;

    protected final int maxFoodLevel = 5;
    protected final int maxHappinessLevel = 20;

    public enum tamaStage {
        EGG, BABY, CHILD, TEEN, ADULT
    }


    public Tama(String name) {
        this.name = name;
        this.foodLevel = maxFoodLevel;
        this.happinessLevel = maxHappinessLevel;
        this.stage = tamaStage.EGG;
    }

    public Tama(String name, int foodLevel, int happinessLevel, tamaStage stage) {
        this.name = name;
        this.foodLevel = foodLevel;
        this.happinessLevel = happinessLevel;
        this.stage = stage;
    }


    public void evolve() {
        assert this.stage != tamaStage.TEEN : "Teens cannot evolve";

        this.stage = tamaStage.values()[this.stage.ordinal() + 1];
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoodLevel() {
        return this.foodLevel;
    }

    public boolean incFoodLevel() {
        return this.incFoodLevel(1);
    }

    public boolean incFoodLevel(int amount) {
        if ( this.foodLevel >= maxFoodLevel ) {
            // If already full
            return false;
        }
        this.foodLevel = Math.min(this.maxFoodLevel, this.foodLevel + amount);
        return true;
    }

    public int getHappinessLevel() {
        return this.happinessLevel;
    }

    public void incHappinessLevel() {
        this.incHappinessLevel(1);
    }

    public void incHappinessLevel(int amount) {
        this.happinessLevel = Math.min(this.maxHappinessLevel, this.happinessLevel + amount);
    }

    public tamaStage getStage() {
        return this.stage;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", foodLevel='" + getFoodLevel() + "'" +
            ", happinessLevel='" + getHappinessLevel() + "'" +
            ", stage='" + getStage() + "'" +
            "}";
    }

    public HashMap<String, Object> getHash() {
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("name", this.name);
        hash.put("foodLevel", this.foodLevel);
        hash.put("happinessLevel", this.happinessLevel);
        hash.put("stage", this.stage.toString());
        return hash;
    }

    public static Tama fromHash(HashMap<String, Object> hash) {
        return new Tama(
            (String) hash.get("name"),
            (int) hash.get("foodLevel"),
            (int) hash.get("happinessLevel"),
            tamaStage.valueOf((String) hash.get("stage"))
        );
    }

}
