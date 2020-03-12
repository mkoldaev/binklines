package com.koldaev;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisFactory {
	
private static JedisPool jedisPool;
private static JedisFactory instance = null;

public JedisFactory() {

    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setTestOnBorrow(true);
    jedisPool = new JedisPool(poolConfig, "localhost", 6379);

}

public JedisPool getJedisPool() {
    return jedisPool;
}

public static JedisFactory getInstance() {
    if (instance == null) {
        instance = new JedisFactory();
    }
    return instance;
}

}