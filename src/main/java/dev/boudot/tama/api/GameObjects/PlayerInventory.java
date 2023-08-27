package dev.boudot.tama.api.GameObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("PlayerInventory")
public class PlayerInventory implements Serializable {

    private final ConsumableInventory consumableInventory;
    private int money;

    private Tama currentTama;
    private final List<Tama> pastTamas;

    public static final long serialVersionUID = -1015085005291025041L;


    public PlayerInventory() {
        this.consumableInventory = new ConsumableInventory();
        this.money = 900;
        this.pastTamas = new ArrayList<>();
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

}
