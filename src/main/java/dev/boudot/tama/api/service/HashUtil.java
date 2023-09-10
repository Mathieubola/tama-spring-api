package dev.boudot.tama.api.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for working with flattened and unflattened hash maps.
 */
public class HashUtil {

    /**
     * Flattens a nested hash map by converting keys into dot-separated strings.
     *
     * @param prefix The prefix to be added to flattened keys.
     * @param map The nested hash map to be flattened.
     * @return A flattened hash map with dot-separated keys.
     */
    public static HashMap<String, Object> flattenMap(String prefix, HashMap<String, Object> map) {
        HashMap<String, Object> flatMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                flatMap.putAll(flattenMap(prefix + entry.getKey() + ".", (HashMap<String, Object>) entry.getValue()));
            } else {
                flatMap.put(prefix + entry.getKey(), entry.getValue());
            }
        }

        return flatMap;
    }

    /**
     * Unflattens a hash map with dot-separated keys into a nested hash map.
     *
     * @param map The flattened hash map to be unflattened.
     * @return A nested hash map.
     */
    public static HashMap<String, Object> unFlattenMap(HashMap<String, Object> map) {
        HashMap<String, Object> unFlattenedMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String[] keys = entry.getKey().split("\\.");
            HashMap<String, Object> currentMap = unFlattenedMap;

            for (int i = 0; i < keys.length - 1; i++) {
                if (currentMap.get(keys[i]) == null) {
                    currentMap.put(keys[i], new HashMap<String, Object>());
                }
                currentMap = (HashMap<String, Object>) currentMap.get(keys[i]);
            }

            currentMap.put(keys[keys.length - 1], entry.getValue());
        }

        return unFlattenedMap;

    }

}
