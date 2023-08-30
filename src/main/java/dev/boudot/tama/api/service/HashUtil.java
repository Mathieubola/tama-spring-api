package dev.boudot.tama.api.service;

import java.util.HashMap;
import java.util.Map;

public class HashUtil {

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
