package dev.boudot.tama.api.GameObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerInventory {

    private final ConsumableInventory consumableInventory;
    private int money;

    private Tama currentTama;
    private final List<Tama> pastTamas;


    public PlayerInventory() {
        this.consumableInventory = new ConsumableInventory();
        this.money = 900;
        this.pastTamas = new ArrayList<>();
    }

    public PlayerInventory(ConsumableInventory consumableInventory, int money, Tama currentTama, List<Tama> pastTamas) {
        this.consumableInventory = consumableInventory;
        this.money = money;
        this.currentTama = currentTama;
        this.pastTamas = pastTamas;
    }

    public ConsumableInventory getConsumableInventory() {
        return this.consumableInventory;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        this.money = Math.min(amount, 99999);
    }

    public boolean takeMoney(int amount) {
        if ( money < amount ) {
            return false;
        }
        money -= amount;
        return true;
    }

    public Tama getCurrentTama() {
        return this.currentTama;
    }

    public void newTama(String name) {
        if ( currentTama != null ) {
            pastTamas.add(currentTama);
        }
        currentTama = new Tama(name);
    }

    public List<Tama> getPastTamas() {
        return this.pastTamas;
    }

    @Override
    public String toString() {
        String currentTama = getCurrentTama() != null ? getCurrentTama().toString() : "None";
        return "{" +
            " consumableInventory='" + getConsumableInventory().toString() + "'" +
            ", money='" + getMoney() + "'" +
            ", currentTama='" + currentTama + "'" +
            ", pastTamas='" + getPastTamas().toString() + "'" +
            "}";
    }

    public HashMap<String, Object> getHash() {
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("consumableInventory", this.consumableInventory.getHash());
        hash.put("money", this.money);
        if ( this.currentTama != null ) {
            hash.put("currentTama", this.currentTama.getHash());
        }
        for (int i = 0; i < this.pastTamas.size(); i++) {
            hash.put("pastTamas." + i, this.pastTamas.get(i).getHash());
        }
        return hash;
    }

    public static PlayerInventory fromHash(HashMap<String, Object> hash) {
        ConsumableInventory consumableInventory;
        if ( hash.containsKey("consumableInventory")) {
            consumableInventory = ConsumableInventory.fromHash((HashMap<String, Object>) hash.get("consumableInventory"));
        } else {
            consumableInventory = new ConsumableInventory();
        }
        int money = (int) hash.get("money");
        Tama currentTama = null;
        if ( hash.containsKey("currentTama") ) {
            currentTama = Tama.fromHash((HashMap<String, Object>) hash.get("currentTama"));
        }
        List<Tama> pastTamas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if ( hash.containsKey("pastTamas." + i) ) {
                pastTamas.add(Tama.fromHash((HashMap<String, Object>) hash.get("pastTamas." + i)));
            }
        }
        return new PlayerInventory(consumableInventory, money, currentTama, pastTamas);
    }

}
