package test;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import client.NetworkLayer;

import java.io.InputStream;

import common.Convert;
import common.Credential;
import common.RegisterResult;

public class TestHttpClient{
	public static void main(String args[]) throws ClientProtocolException, IOException{
		/*CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
		HttpGet httpGet = new HttpGet("http://www.baidu.com");   //Get方法
		HttpResponse response = httpClient.execute(httpGet); 
		HttpEntity entity=response.getEntity();
		System.out.println(response);
		Header header=response.getFirstHeader("Cache-Control");
		System.out.println(header.getValue());
		InputStream in=entity.getContent();
		System.out.println(Convert.toString(in));
		httpClient.close();*/
		client.NetworkLayer clientNET=new NetworkLayer();
		Credential cred=new Credential("yzj","123");
		RegisterResult result1=clientNET.register(cred);
		System.out.println(result1);
	}
	
	
}
