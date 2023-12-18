package com.tdh.usermanagment.utils;

import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
     * 随机取得键值
     */
    public class RandomKeyFromMap {
    /**
     *  功能：输入一个map，随机返回key值
     * @param map 输入的map
     * @param <K>  键
     * @param <V> 值
     * @return 随机返回key值
     */
        public static <K, V> K getRandomKey(Map<K, V> map) {
            if (map.isEmpty()) {
                return null;
            }
            Set<K> keySet = map.keySet();
            int randomIndex = new Random().nextInt(keySet.size());
            // 将Set转为数组，然后根据随机索引取得随机键
            K[] keys = keySet.toArray((K[]) new Object[keySet.size()]);
            return keys[randomIndex];
        }
    }