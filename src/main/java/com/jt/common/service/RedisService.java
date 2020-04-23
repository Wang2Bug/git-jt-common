package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.jt.common.factory.JedisClusterFactory;

//import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisSentinelPool;
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService 
{
	@Autowired(required = false)
	private JedisCluster jedisCluster;
	
	public String get(String key) 
	{
		String result = jedisCluster.get(key);
		return result;
	}
	//
	public void set(String key,String value) 
	{
		jedisCluster.set(key, value);
	}
	
	
//	//二.哨兵
//	@Autowired(required = false)
//	private JedisSentinelPool jedisSentinelPool;
//	
//	//
//	public String get(String key) 
//	{
//		Jedis jedis = jedisSentinelPool.getResource();
//		String result = jedis.get(key);
//		jedisSentinelPool.returnResource(jedis);
//		return result;
//	}
//	
//	//
//	public void set(String key,String value) 
//	{
//		Jedis jedis = jedisSentinelPool.getResource();
//		jedis.set(key, value);
//		jedisSentinelPool.returnResource(jedis);
//	}
//	//
//	public void setex(String key,String value,Integer seconds) 
//	{
//		Jedis jedis = jedisSentinelPool.getResource();
//		//jedis.set(key, value);
//		//jedis.expire(key, seconds);
//		jedis.setex(key, seconds, value);
//		jedisSentinelPool.returnResource(jedis);
//	}
		
//    //一.分片
//	  有的工程需要，有的工程不需要。设置required=false，有就注入，没有就不注入。
//    @Autowired(required = false)
//    private ShardedJedisPool shardedJedisPool;
//
//    //实现分片的set操作
//    public void set(String key,String value) 
//    {
//    	//池中拿到redis链接
//    	ShardedJedis jedis = shardedJedisPool.getResource();
//    	//存入redis
//    	jedis.set(key, value);
//    	shardedJedisPool.returnResource(jedis);
//    }
//    
//    //实现分片get操作
//    public String get(String key) {
//    	//池中拿到redis链接
//    	ShardedJedis jedis = shardedJedisPool.getResource();
//    	//获取redis数据
//    	String result = jedis.get(key);
//    	shardedJedisPool.returnResource(jedis);
//    	
//    	return result;
//	}
//    
//    //超时时间
//    public void expire(String key,Integer seconds) 
//    {
//    	ShardedJedis jedis = shardedJedisPool.getResource();
//    	//
//    	jedis.expire(key, seconds);
//    	shardedJedisPool.returnResource(jedis);
//	}
//  //设定超时时间并set
//    public void setex(String key,Integer seconds,String value) 
//    {
//    	ShardedJedis jedis = shardedJedisPool.getResource();
//    	jedis.setex(key, seconds, value);
//    	//
//    	shardedJedisPool.returnResource(jedis);
//	}
}
