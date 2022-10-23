package com.shervilg.spinboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import java.time.Duration;

@Configuration
public class CustomRedisCacheConfiguration {

  @Bean
  public RedisCacheConfiguration redisCacheConfiguration() {
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
    redisCacheConfiguration.disableCachingNullValues();
    redisCacheConfiguration.entryTtl(Duration.ofDays(1));
    redisCacheConfiguration.serializeValuesWith(
        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
    );

    return redisCacheConfiguration;
  }
}
