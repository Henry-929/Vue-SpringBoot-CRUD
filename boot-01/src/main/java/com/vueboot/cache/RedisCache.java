package com.vueboot.cache;

import com.vueboot.utils.ApplicationContextUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class RedisCache implements Cache {

    //slf4j的日志记录器
    private final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    //id就是mapper对应的namespace
    private final String id;

    public RedisCache(String id){
        this.id=id;
    }

    @Override
    public String getId() {
        return id;
    }

    /*
        保存缓存对象的方法
     */
    @Override
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
        redisTemplate.opsForHash().put(id.toString(),key.toString(),value);
        if ("com.vueboot.mapper.BookMapper".equals(id.toString())) {
            //设置缓存存在时长为30分钟
            redisTemplate.expire(id.toString(), 30, TimeUnit.MINUTES);
        }
        logger.info("保存缓存对象");
    }

    /*
       获取缓存对象的方法
    */
    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
        logger.info("获取缓存对象");
        return redisTemplate.opsForHash().get(id.toString(),key.toString());
    }

    /*
        删除缓存对象
     */
    @Override
    public Object removeObject(Object key) {
        logger.info("删除缓存对象");
        return null;
    }

    /*
        清空缓存对象
        当缓存的对象更新了的化，就执行此方法
     */
    @Override
    public void clear() {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
        redisTemplate.delete(id.toString());
        logger.info("清空缓存对象");
    }

    /*
        缓存命中率计算
     */
    @Override
    public int getSize() {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
        //获取当前namespace中缓存数据
        return redisTemplate.opsForHash().size(id.toString()).intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return new ReentrantReadWriteLock();
    }
}
