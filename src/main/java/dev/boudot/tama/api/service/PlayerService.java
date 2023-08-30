package dev.boudot.tama.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import dev.boudot.tama.api.GameObjects.Food;
import dev.boudot.tama.api.GameObjects.Player;

@Service
public class PlayerService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;

    Logger logger = LoggerFactory.getLogger(PlayerService.class);
    public PlayerService(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    private final String HASH_KEY = "players"; // Key for the Redis Hash

    public void savePlayer(Player player) {
        player.getPlayerInventory().getConsumableInventory()
            .addFood(new Food("Pizza", 50, 50));

        HashMap<String, Object> flatHash = HashUtil.flattenMap("", player.getHash());
        logger.info("Saving player: " + player.toString() + " to Redis with hash: " + flatHash.toString());

        hashOperations.putAll(HASH_KEY + ":" + player.getUserName(), flatHash);
    }

    public List<Player> getPlayer() {
        Set<String> keys = redisTemplate.keys(HASH_KEY + ":*");
        List<Object> hashes = hashOperations.multiGet(HASH_KEY, keys);
        return hashes
            .stream()
            .map(hash -> (Player) hash)
            .collect(Collectors.toList());
    }

    public String getPlayer(String name) {
        HashMap<String, Object> hash = (HashMap<String, Object>) hashOperations.entries(HASH_KEY + ":" + name);
        if (hash == null || hash.isEmpty() ) {
            return null;
        }
        hash = HashUtil.unFlattenMap(hash);
        logger.info("[getPlayer] Hash: " + hash.get("player").toString());
        Player player = Player.fromHash((HashMap<String, Object>) hash.get("player"));
        return player.toString();
    }

    public boolean deletePlayer(String name) {
        try {
            redisTemplate.opsForHash().delete(HASH_KEY, name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
