package com.shervilg.spinboard.helper;

public interface RedisHelper {
  <T> T getKey(String key, Class<T> classType);
  <T> void setKey(String key, T value);
}
