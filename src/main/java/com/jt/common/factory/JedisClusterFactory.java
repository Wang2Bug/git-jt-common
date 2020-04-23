package com.jt.common.factory;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterFactory implements FactoryBean<JedisCluster>
{
	private Resource propertySource;//引入配置文件
	private JedisPoolConfig poolConfig;//引入池配置文件
	private String redisNodePrefix;//添加配置前缀
	
	public Resource getPropertySource() {
		return propertySource;
	}
	public void setPropertySource(Resource propertySource) {
		this.propertySource = propertySource;
	}
	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}
	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	public String getRedisNodePrefix() {
		return redisNodePrefix;
	}
	public void setRedisNodePrefix(String redisNodePrefix) {
		this.redisNodePrefix = redisNodePrefix;
	}
	
	public Set<HostAndPort> getNodes()
	{
		Set<HostAndPort> nodes = new HashSet<>();
		try 
		{
			//加载配置文件
			Properties properties = new Properties();
			properties.load(propertySource.getInputStream());//读取资源文件
			for (Object key : properties.keySet()) 
			{
				String strkey = (String) key;
				//判断前缀是否匹配
				if(strkey.startsWith(redisNodePrefix)) 
				{
					//redis.cluster0=172.16.10.71:7000
					String value = properties.getProperty(strkey);
					String[] args = value.split(":");
					HostAndPort hostAndPort = new HostAndPort(args[0], Integer.parseInt(args[1]));
					nodes.add(hostAndPort);
				}
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return nodes;
	}
	//@Override
	public JedisCluster getObject() throws Exception 
	{
		JedisCluster jedisCluster = new JedisCluster(getNodes(), poolConfig);
		return jedisCluster;
	}
	//@Override
	public Class<?> getObjectType() 
	{
		return JedisCluster.class;
	}
	//@Override
	public boolean isSingleton() 
	{
		// TODO Auto-generated method stub
		return false;
	}
}
