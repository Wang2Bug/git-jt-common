package com.jt.common.service;

import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;



@Service
public class HttpClientService 
{
    @Autowired(required=false)
    private CloseableHttpClient httpClient;

    //@Autowired(required=false)
    //private RequestConfig requestConfig;
    
    public String doGet(String url,Map<String, String> params,String charset) 
    {
    	String result = null;
    	//1.判断字符集编码是否为空
    	if(StringUtils.isEmpty(charset)) 
    	{
    		charset = "UTF-8";
    	}
    	//2.封装用户提交的参数
    	//http://manage.jt.com?id=1&name=tom.....
    	//		url = url + "?";
		//		for (Map.Entry<String, String> entry : params.entrySet()) 
    	//    	{
		//    		String key = entry.getKey();
		//    		String value = entry.getValue();
		//    		url = url + key + "=" + value + "&";
		//		}
		//		url = url.substring(0,url.length()-1);  
    	
    	//使用API
    	if(params != null) 
    	{
    		try 
    		{
    			URIBuilder builder = new URIBuilder(url);
    			for (Map.Entry<String, String> entry : params.entrySet()) 
    			{
					String key = entry.getKey();
					String value = entry.getValue();
					builder.addParameter(key,value);
				}    			
    			url = builder.build().toString();
			} 
    		catch (Exception e) 
    		{
				e.printStackTrace();
			}
    	}
    	//3.封装请求的参数类型
    	HttpGet get = new HttpGet(url);
		try 
		{
			//4.发起请求,获取相应结果
			CloseableHttpResponse respones = httpClient.execute(get);
			//5.判断返回值是否正确
			if(respones.getStatusLine().getStatusCode() == 200 ) 
			{
				HttpEntity entity = respones.getEntity();
				result = EntityUtils.toString(entity,charset);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return result;
	}
    //重载
    public String doGet(String url) 
    {
    	return doGet(url, null, null);
	}
    //重载
    public String doGet(String url,Map<String,String> params) 
    {
    	return doGet(url, params, null);
	}
   
}
