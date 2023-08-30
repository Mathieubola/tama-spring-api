package dev.boudot.tama.api.GameObjects;

import java.util.HashMap;

import org.springframework.data.annotation.Id;

public class Player {
    @Id
    private final String userName;
    private final PlayerInventory playerInventory;


    public Player(String userName) {
        this.userName = userName;
        this.playerInventory = new PlayerInventory();
    }

    public Player(String userName, PlayerInventory playerInventory) {
        this.userName = userName;
        this.playerInventory = playerInventory;
    }

    public String getUserName() {
        return this.userName;
    }

    public PlayerInventory getPlayerInventory() {
        return this.playerInventory;
    }

    @Override
    public String toString() {
        return "{" +
            " userName='" + getUserName() + "'" +
            ", playerInventory='" + getPlayerInventory().toString() + "'" +
            "}";
    }

    public HashMap<String, Object> getHash() {
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("userName", this.userName);
        hash.put("playerInventory", this.playerInventory.getHash());
        return hash;
    }

    public static Player fromHash(HashMap<String, Object> hash) {
        return new Player(
            (String) hash.get("userName"),
            PlayerInventory.fromHash((HashMap<String, Object>) hash.get("playerInventory"))
        );
    }

}
