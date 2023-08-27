package dev.boudot.tama.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Service;

import dev.boudot.tama.api.GameObjects.Player;

@Service
public class PlayerService {

    @Autowired
    private RedisTemplate<String, Player> redisTemplate;

    @Autowired
    private Jackson2HashMapper playerHashMapper;

    private final String HASH_KEY = "players"; // Key for the Redis Hash

    public void savePlayer(Player player) {
        String key = player.getUserName();
        if (!redisTemplate.opsForHash().hasKey(HASH_KEY, key)) {
            redisTemplate.opsForHash().put(HASH_KEY, key, playerHashMapper.toHash(player));
        }
    }

    public List<Player> getPlayer() {
        List<Object> players = redisTemplate.opsForHash().values(HASH_KEY);
        return players.stream()
            .map(playerObject -> (Player) playerObject)
            .collect(Collectors.toList());
    }

    public Player getPlayer(String name) {
        return (Player) redisTemplate.opsForHash().get(HASH_KEY, name);
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
