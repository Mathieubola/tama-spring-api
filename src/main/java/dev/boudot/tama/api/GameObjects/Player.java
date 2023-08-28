package dev.boudot.tama.api.GameObjects;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@RedisHash("Player")
public class Player implements Serializable {
    @Id
    private final String userName;
    private final PlayerInventory playerInventory;

    public static final long serialVersionUID = 3721950011792878116L;


    public Player(String userName) {
        this.userName = userName;
        this.playerInventory = new PlayerInventory();
    }

    @JsonCreator
    public Player(@JsonProperty("userName") String userName, @JsonProperty("playerInventory") PlayerInventory playerInventory) {
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

}
