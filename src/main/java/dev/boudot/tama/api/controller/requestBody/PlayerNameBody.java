package dev.boudot.tama.api.controller.requestBody;

/**
 * A class representing the request body for a player name.
 */
public class PlayerNameBody {
    /**
     * The name of the player.
     */
    public String name;

    /**
     * Checks if the player name is legal.
     * @return true if the player name is legal, false otherwise.
     */
    public boolean isLegal() {
        return name != null && name.length() > 0;
    }
}
