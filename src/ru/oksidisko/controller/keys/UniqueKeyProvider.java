package ru.oksidisko.controller.keys;

import java.util.HashMap;
import java.util.Map;

public class UniqueKeyProvider {
    private static Map<KeyCategory, Long> map = new HashMap<>();
    static {
        initKeys();
    }

    private static void initKeys() {
        map.put(KeyCategory.USER, (long) 1);
        map.put(KeyCategory.TOPIC, (long) 1);
        map.put(KeyCategory.PROTOCOL, (long) 1);
    }

    public static long generateLongId(KeyCategory key) {
        long newKey = map.get(key);
        newKey++;
        map.put(key, newKey);
        return newKey;
    }
}
