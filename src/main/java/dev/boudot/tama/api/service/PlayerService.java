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

@Service
public class PlayerService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;
    
    public PlayerService(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    private final String HASH_KEY = "players"; // Key for the Redis Hash

    public void savePlayer(Player player) {
        player.getPlayerInventory().getConsumableInventory()
            .addFood(new Food("Pizza", 50, 50));

        HashMap<String, Object> flatHash = HashUtil.flattenMap("", player.getHash());

        String key = HASH_KEY + ":" + player.getUserName();
        hashOperations.putAll(key, flatHash);
    }

    public List<Player> getPlayer() {
        Cursor<Entry<Object, Object>> cur = redisTemplate.opsForHash().scan(HASH_KEY, ScanOptions.NONE);
        Set<Object> keys = cur.stream().map(Entry::getKey).collect(Collectors.toSet());
        List<Player> players = keys.stream()
            .map(key -> getPlayer(key.toString().split(":")[1]))
            .collect(Collectors.toList());
        return players;
    }

    public Player getPlayer(String name) {
        HashMap<String, Object> hash = (HashMap<String, Object>) hashOperations.entries(HASH_KEY + ":" + name);
        if (hash == null || hash.isEmpty() ) {
            return null;
        }
        hash = HashUtil.unFlattenMap(hash);
        Player player = Player.fromHash(hash);
        return player;
    }

    public boolean deletePlayer(String name) {
        try {
            redisTemplate.delete(HASH_KEY + ":" + name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
