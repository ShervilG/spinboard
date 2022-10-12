package com.shervilg.spinboard.controller;

import org.springframework.web.bind.annotation.*;
import com.shervilg.spinboard.helper.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/key-value")
public class KeyValueController {

  @Autowired
  private RedisHelper redisHelper;

  @PutMapping("/set")
  public void putKey(@RequestParam(name = "key") String key, @RequestParam(name = "value") String value) {
    redisHelper.setKey(key, value);
  }

  @GetMapping("/get")
  public String getKey(@RequestParam(name = "key") String key) {
    return redisHelper.getKey(key, String.class);
  }
}
