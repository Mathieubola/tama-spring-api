package dev.boudot.tama.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import dev.boudot.tama.api.GameObjects.Food;
import dev.boudot.tama.api.GameObjects.Player;

/**
 * Service class for managing player data in Redis.
 */
@Service
public class PlayerService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;
    
    /**
     * Constructs a PlayerService with the provided RedisTemplate.
     *
     * @param redisTemplate The RedisTemplate used for database operations.
     */
    public PlayerService(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    private final String HASH_KEY = "players"; // Key for the Redis Hash

    /**
     * Saves player data to Redis.
     *
     * @param player The player object to be saved.
     */
    public void savePlayer(Player player) {
        player.getPlayerInventory().getConsumableInventory()
            .addFood(new Food("Pizza", 50, 50));

        HashMap<String, Object> flatHash = HashUtil.flattenMap("", player.getHash());

        String key = HASH_KEY + ":" + player.getUserName();
        hashOperations.putAll(key, flatHash);
    }

    /**
     * Retrieves a list of player objects from Redis.
     *
     * @return A list of player objects stored in Redis.
     */
    public List<Player> getPlayer() {
        Cursor<Entry<Object, Object>> cur = redisTemplate.opsForHash().scan(HASH_KEY, ScanOptions.NONE);
        Set<Object> keys = cur.stream().map(Entry::getKey).collect(Collectors.toSet());
        List<Player> players = keys.stream()
            .map(key -> getPlayer(key.toString().split(":")[1]))
            .collect(Collectors.toList());
        return players;
    }

    /**
     * Retrieves a player object from Redis by their username.
     *
     * @param name The username of the player to retrieve.
     * @return The player object associated with the provided username, or null if not found.
     */
    public Player getPlayer(String name) {
        HashMap<String, Object> hash = (HashMap<String, Object>) hashOperations.entries(HASH_KEY + ":" + name);
        if (hash == null || hash.isEmpty() ) {
            return null;
        }
        hash = HashUtil.unFlattenMap(hash);
        Player player = Player.fromHash(hash);
        return player;
    }

    /**
     * Deletes a player object from Redis by their username.
     *
     * @param name The username of the player to delete.
     * @return true if the player is successfully deleted, false otherwise.
     */
    public boolean deletePlayer(String name) {
        try {
            redisTemplate.delete(HASH_KEY + ":" + name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
