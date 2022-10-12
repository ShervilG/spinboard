package com.shervilg.spinboard.helper.impl;

import org.springframework.stereotype.Service;
import com.shervilg.spinboard.helper.RedisHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SimpleRedisHelper implements RedisHelper {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Override
  public<T> T getKey(String key, Class<T> classType) {
    try {
      if (redisTemplate.hasKey(key)) {
        String valueString = redisTemplate.opsForValue().get(key);
        return objectMapper.convertValue(valueString, classType);
      }

      throw new Exception("Error while retrieving key: " + key);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public<T> void setKey(String key, T value) {
    try {
      String valueString = objectMapper.writeValueAsString(value);
      redisTemplate.opsForValue().set(key, valueString);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
