package client;

import java.io.File;
import java.io.IOException;

import common.ChangePasswdResult;
import common.CloudFile;
import common.Convert;
import common.Credential;
import common.FileResult;
import common.LoginResult;
import common.RegisterResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;


public class NetworkLayer implements INetworkLayer{
	private String serverUri; //服务器地址
	private String cookie;
	
	public NetworkLayer(){
		serverUri="http://localhost:13051/cloudshare";
		cookie="";
	}

	@Override
	/**
	 * 重写客户端Net接口方法
	 */
	public LoginResult login(Credential cred) 
			throws ClientProtocolException, IOException {
		/**
		 * 创建客户端
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
		HttpPost httpPost = new HttpPost(serverUri+"/login");   //Post方法
		/**
		 * 设置发送内容
		 */
		JSONObject jsonRequest=new JSONObject();   		//包装为JSON对象
		jsonRequest.put("userID", cred.getUserID());
		jsonRequest.put("password", cred.getPassword());
		HttpEntity requestBody=
				new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
		httpPost.setEntity(requestBody);              //加入请求Body，ContentType自动设置
		/**
		 * 获得响应并解析
		 */
		HttpResponse httpResponse=httpClient.execute(httpPost); //发出请求并获得响应
		if (!(httpResponse.getStatusLine().getStatusCode()==200))
			return LoginResult.unknownError;      				//判断响应码
		HttpEntity responseEntity=httpResponse.getEntity();    	//获得Entity
		String responseBody=Convert.toString(responseEntity.getContent()); 
																//获得Body
		JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
		String status=jsonResponse.getString("status");         //获取状态
		if (httpResponse.containsHeader("Set-Cookie")){			//获得Cookie
			this.cookie=httpResponse.getFirstHeader("Set-Cookie").getValue();  
		}
		/**
		 * 关闭客户端并返回结果给上层
		 */
		httpClient.close();										//关闭服务器
		return Convert.toLoginResult(status);					//返回登录结果
	}

	@Override
	public RegisterResult register(Credential cred) 
			throws ClientProtocolException, IOException {
		/**
		 * 创建客户端
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
		HttpPost httpPost = new HttpPost(serverUri+"/register");   //Post方法
		/**
		 * 设置发送内容
		 */
		JSONObject jsonRequest=new JSONObject();   		//包装为JSON对象
		jsonRequest.put("userID", cred.getUserID());
		jsonRequest.put("password", cred.getPassword());
		HttpEntity requestBody=
				new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
		httpPost.setEntity(requestBody);              //加入请求Body，ContentType自动设置
		/**
		 * 获得响应并解析
		 */
		HttpResponse httpResponse=httpClient.execute(httpPost); //发出请求并获得响应
		if (!(httpResponse.getStatusLine().getStatusCode()==200))
			return RegisterResult.unknownError;      			//判断响应码
		HttpEntity responseEntity=httpResponse.getEntity();    	//获得Entity
		String responseBody=Convert.toString(responseEntity.getContent()); 
																//获得Body
		JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
		String status=jsonResponse.getString("status");         //获取状态
		/**
		 * 关闭客户端并返回结果给上层
		 */
		httpClient.close();										//关闭服务器
		return Convert.toRegisterResult(status);				//返回登录结果
	}

	@Override
	public ChangePasswdResult changePasswd(String password, String newPassword) 
			throws ClientProtocolException, IOException {
		/**
		 * 创建客户端
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
		HttpPost httpPost = new HttpPost(serverUri+"/changePw");   //Post方法
		/**
		 * 设置发送内容
		 */
		httpPost.addHeader("Cookie", cookie);   		//Cookie加入Header里
		JSONObject jsonRequest=new JSONObject();   		//包装为JSON对象
		jsonRequest.put("password", password);
		jsonRequest.put("newPassword", newPassword);
		HttpEntity requestBody=
				new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
		httpPost.setEntity(requestBody);              //加入请求Body，ContentType自动设置
		
		/**
		 * 获得响应并解析
		 */
		HttpResponse httpResponse=httpClient.execute(httpPost); //发出请求并获得响应
		int statusCode=httpResponse.getStatusLine().getStatusCode();
		if (statusCode==403)
			return ChangePasswdResult.unAuthorized;      		//判断响应码
		else if (statusCode!=200)
			return ChangePasswdResult.unknownError;
		HttpEntity responseEntity=httpResponse.getEntity();    	//获得Entity
		String responseBody=Convert.toString(responseEntity.getContent());//获得Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
		String status=jsonResponse.getString("status");         //获取状态
		/**
		 * 关闭客户端并返回结果给上层
		 */
		httpClient.close();										//关闭服务器
		return Convert.toChangePasswdResult(status);			//返回登录结果	
	}
	
	public FileResult uploadFile(CloudFile cloudFile,File file) 
			throws ClientProtocolException, IOException{
		/**
		 * 创建客户端
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
		String filename=cloudFile.getFilename();
		HttpPost httpPost = new HttpPost(serverUri+"/file?filename="+filename);   		
																	//Post方法
		/**
		 * 设置发送内容
		 */
		httpPost.addHeader("Cookie", cookie);   		//Cookie加入Header里
		HttpEntity requestBody=new FileEntity(file,ContentType.DEFAULT_BINARY);
														//文件加入requestBody
		httpPost.setEntity(requestBody);              //加入请求Body，ContentType自动设置
		/**
		 * 获得响应并解析
		 */
		HttpResponse httpResponse=httpClient.execute(httpPost); //发出请求并获得响应
		int statusCode=httpResponse.getStatusLine().getStatusCode();
		if (statusCode==403)
			return FileResult.unAuthorized;      		//判断响应码
		else if (statusCode!=200)
			return FileResult.unknownError;
		HttpEntity responseEntity=httpResponse.getEntity();    	//获得Entity
		String responseBody=Convert.toString(responseEntity.getContent());//获得Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
		String status=jsonResponse.getString("status");         //获取状态
		/**
		 * 关闭客户端并返回结果给上层
		 */
		httpClient.close();										//关闭服务器
		return Convert.toFileResult(status);			//返回登录结果			
	}
}
