package client;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import common.ChangePasswdResult;
import common.CloudFile;
import common.Credential;
import common.DownloadFileResult;
import common.FileDirectoryResult;
import common.FileResult;
import common.LoginResult;
import common.RegisterResult;
import common.Authorization;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
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
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8"); 
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
		try{
			LoginResult result=LoginResult.valueOf(status);	
			return result;										//传回登录结果
		}catch (IllegalArgumentException e){
			return LoginResult.unknownError;
		}
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
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8"); 
																//获得Body
		JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
		String status=jsonResponse.getString("status");         //获取状态
		/**
		 * 关闭客户端并返回结果给上层
		 */
		httpClient.close();										//关闭服务器
		try{
			RegisterResult result=RegisterResult.valueOf(status);	
			return result;										//传回注册结果
		}catch (IllegalArgumentException e){
			return RegisterResult.unknownError;
		}
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
		if (statusCode==401)
			return ChangePasswdResult.unAuthorized;      		//判断响应码
		else if (statusCode!=200)
			return ChangePasswdResult.unknownError;
		HttpEntity responseEntity=httpResponse.getEntity();    	//获得Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");//获得Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
		String status=jsonResponse.getString("status");         //获取状态
		/**
		 * 关闭客户端并返回结果给上层
		 */
		httpClient.close();										//关闭服务器
		try{
			ChangePasswdResult result=ChangePasswdResult.valueOf(status);	
			return result;										//传回修改密码结果
		}catch (IllegalArgumentException e){
			return ChangePasswdResult.unknownError;
		}
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
		if (statusCode==401)
			return FileResult.unAuthorized;      		//判断响应码
		else if (statusCode!=200)
			return FileResult.unknownError;
		HttpEntity responseEntity=httpResponse.getEntity();    	//获得Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");//获得Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
		String status=jsonResponse.getString("status");         //获取状态
		/**
		 * 关闭客户端并返回结果给上层
		 */
		httpClient.close();										//关闭服务器
		try{
			FileResult fileResult=FileResult.valueOf(status);	//传回上传文件结果
			return fileResult;
		}catch (IllegalArgumentException e){
			return FileResult.unknownError;
		}
	}

	@Override
	public FileDirectoryResult getDirectory(String targetID) throws IOException {
		/**
		 * 创建客户端
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
		HttpGet httpGet = new HttpGet(serverUri+"/getDirectory?targetID="+targetID);   		
																	//Get方法
		/**
		 * 设置发送内容
		 */
		httpGet.addHeader("Cookie", cookie);   		//Cookie加入Header里
		
		/**
		 * 获得响应码
		 */
		HttpResponse httpResponse=httpClient.execute(httpGet); //发出请求并获得响应
		int statusCode=httpResponse.getStatusLine().getStatusCode();
		if (statusCode==401)									//判断响应码
			return new FileDirectoryResult(FileResult.unAuthorized);      		
		else if (statusCode!=200)
			return new FileDirectoryResult(FileResult.unknownError);
		
		/**
		 * 解析响应内容
		 */
		HttpEntity responseEntity=httpResponse.getEntity();    	//获得Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");
																//获得Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
		String status=jsonResponse.getString("status");         //获取状态
		JSONArray directoryArray=jsonResponse.getJSONArray("directory");
		HashSet<CloudFile> directory=new HashSet<CloudFile>();
	    for (int index=0;index<directoryArray.length();index++){
	    	JSONObject aJSON=directoryArray.getJSONObject(index);
	    	CloudFile afile=new CloudFile();
	    	afile.setCreator(aJSON.getString("creator"));
	    	afile.setFileID(aJSON.getString("fileID"));
	    	afile.setFilename(aJSON.getString("filename"));
	    	afile.setUploadTime(aJSON.getString("uploadTime"));
	    	afile.setAuthorization(Authorization.valueOf(aJSON.getString("authorization")));
	    	directory.add(afile);
	    }
		
		/**
		 * 关闭客户端并返回结果给上层
		 */
		httpClient.close();										//关闭服务器
		try{
			FileDirectoryResult directoryResult					//传回文件目录
				=new FileDirectoryResult(directory,FileResult.valueOf(status));						
			return directoryResult;
		}catch (IllegalArgumentException e){
			return new FileDirectoryResult(FileResult.unknownError);
		}		
	}

	@Override
	public FileResult deleteFile(String fileID) throws IOException {
		/**
		 * 创建客户端
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
		HttpDelete httpDelete = new HttpDelete(serverUri+"/file?fileID="+fileID);   		
																	//Delete方法
		/**
		 * 设置发送内容
		 */
		httpDelete.addHeader("Cookie", cookie);   		//Cookie加入Header里
		
		/**
		 * 获得响应码
		 */
		HttpResponse httpResponse=httpClient.execute(httpDelete); //发出请求并获得响应
		int statusCode=httpResponse.getStatusLine().getStatusCode();
		if (statusCode==401)									//判断响应码
			return FileResult.unAuthorized;      		
		else if (statusCode!=200)
			return FileResult.unknownError;
		
		/**
		 * 解析响应内容
		 */
		HttpEntity responseEntity=httpResponse.getEntity();    	//获得Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");
																//获得Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
		String status=jsonResponse.getString("status");         //获取状态
		
		/**
		 * 关闭客户端并返回结果给上层
		 */
		httpClient.close();										//关闭服务器
		try{
			FileResult fileResult								//传回结果
				=FileResult.valueOf(status);						
			return fileResult;
		}catch (IllegalArgumentException e){
			return FileResult.unknownError;
		}				
	}

    @Override
    public DownloadFileResult downloadFile(String fileID) throws UnsupportedOperationException, IOException {
        /**
         * 创建客户端
         */
        CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
        HttpGet httpGet = new HttpGet(serverUri+"/file?fileID="+fileID);        
                                                                    //Get方法
        /**
         * 设置发送内容
         */
        httpGet.addHeader("Cookie", cookie);        //Cookie加入Header里
        
        /**
         * 获得响应码
         */
        HttpResponse httpResponse=httpClient.execute(httpGet); //发出请求并获得响应
        int statusCode=httpResponse.getStatusLine().getStatusCode();
        if (statusCode==401)                                    //判断响应码
            return new DownloadFileResult(FileResult.unAuthorized);  
        else if (statusCode==403)
            return new DownloadFileResult();
        else if (statusCode!=200)
            return new DownloadFileResult(FileResult.unknownError);
        
        /**
         * 解析响应内容
         */
        HttpEntity responseEntity=httpResponse.getEntity();     //获得Entity
        byte[] content=IOUtils.toByteArray(responseEntity.getContent());
                                                                //获取文件内容
        /**
         * 关闭客户端并返回结果给上层
         */
        httpClient.close();                                     //关闭服务器
        DownloadFileResult downloadFileResult                 //传回文件目录
            =new DownloadFileResult(content,FileResult.OK);                     
        return downloadFileResult;     
    }
}
