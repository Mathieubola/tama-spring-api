package dev.boudot.tama.api.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Service;

import dev.boudot.tama.api.GameObjects.Player;

@Service
public class PlayerService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;
    private final Jackson2HashMapper hashMapper;

    Logger logger = LoggerFactory.getLogger(PlayerService.class);

    public PlayerService(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.hashMapper = new Jackson2HashMapper(true);
    }

    private final String HASH_KEY = "players"; // Key for the Redis Hash

    public void savePlayer(Player player) {
        Map<String, Object> mappedHash = hashMapper.toHash(player);
        logger.info("Saving player: " + player.getUserName() + " to Redis with hash: " + mappedHash);

        hashOperations.putAll(HASH_KEY + ":" + player.getUserName(), mappedHash);
    }

    public List<Player> getPlayer() {
        Set<String> keys = redisTemplate.keys(HASH_KEY + ":*");
        List<Object> hashes = hashOperations.multiGet(HASH_KEY, keys);
        return hashes
            .stream()
            .map(hash -> (Player) hashMapper.fromHash((Map<String, Object>) hash))
            .collect(Collectors.toList());
    }

    public Player getPlayer(String name) {
        Map<String, Object> hash = hashOperations.entries(HASH_KEY + ":" + name);
        if (hash == null || hash.isEmpty() ) {
            return null;
        }
        return (Player) hashMapper.fromHash(hash);
    }

    public boolean deletePlayer(String name) {
        try {
            redisTemplate.opsForHash().delete(HASH_KEY, name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // private Map<String, Object> flatten(Object object) {
    //     logger.info("[START FLATTEN] Flattening object: " + object);
    //     Map<String, Object> flattenObj = hashMapper.toHash(object);
    //     logger.info("hash: " + flattenObj);

    //     flattenObj.forEach((key, value) -> {
    //         logger.info("key: " + key + " value: " + value);
    //         if (!(value instanceof String) && !(value instanceof Integer) && !(value instanceof Double) && !(value instanceof Boolean)) {
    //             logger.info("value is a flattenable");
    //             Map<String, Object> flattenValue = flatten(value);
    //             flattenValue.forEach((subKey, subValue) -> {
    //                 flattenObj.put(key + "." + subKey, subValue);
    //             });
    //             flattenObj.remove(key);
    //         } else {
    //             logger.info("value is not flattenable, was type " + value.getClass().getName());
    //         }
    //     });

    //     return flattenObj;
    // }

}
