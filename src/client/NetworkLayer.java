package client;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

import common.AddNoteResult;
import common.AuthorizationResult;
import common.ChangePasswdResult;
import common.CloudFile;
import common.Credential;
import common.DeleteFileResult;
import common.DeleteNoteResult;
import common.DownloadFileResult;
import common.DownloadFileResult.DownloadFileStatus;
import common.FileDirectoryResult;
import common.FileDirectoryResult.FileDirectoryStatus;
import common.LoginResult;
import common.Note;
import common.NoteListResult;
import common.NoteListResult.NoteListStatus;
import common.RegisterResult;
import common.Authorization;
import common.RenameFileResult;
import common.UploadFileResult;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
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

	/**
     * 重写客户端Net接口方法
     */
	@Override
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
		if (!(httpResponse.getStatusLine().getStatusCode()==200)){
		    httpClient.close();
		    return LoginResult.unknownError;                      //判断响应码
		}
			
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
		if (!(httpResponse.getStatusLine().getStatusCode()==200)){
		    httpClient.close();
		    return RegisterResult.unknownError;               //判断响应码
		}
			
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
		if (statusCode==401){                                  //判断响应码
		    httpClient.close();
		    return ChangePasswdResult.unAuthorized;          
		}	
		else if (statusCode!=200){
		    httpClient.close();
		    return ChangePasswdResult.unknownError;
		}
			
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
	
	public UploadFileResult uploadFile(CloudFile cloudFile,byte[] content) 
			throws ClientProtocolException, IOException{
		/**
		 * 创建客户端
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
		String filename=cloudFile.getFilename();
		HttpPost httpPost = new HttpPost(serverUri+"/file");   		
																	//Post方法
		/**
		 * 设置发送内容
		 */
		httpPost.addHeader("Cookie", cookie);   		//Cookie加入Header里
		httpPost.addHeader("Content-Disposition", "form-data;name="+URLEncoder.encode(filename,"UTF-8"));
		HttpEntity requestBody=new ByteArrayEntity(content);
														//文件加入requestBody
		httpPost.setEntity(requestBody);              //加入请求Body，ContentType自动设置
		/**
		 * 获得响应并解析
		 */
		HttpResponse httpResponse=httpClient.execute(httpPost); //发出请求并获得响应
		int statusCode=httpResponse.getStatusLine().getStatusCode();
		if (statusCode==401){
		    httpClient.close();
		    return UploadFileResult.unAuthorized;             //判断响应码
		}	
		else if (statusCode!=200){
		    httpClient.close();
		    return UploadFileResult.unknownError;
		}
			
		HttpEntity responseEntity=httpResponse.getEntity();    	//获得Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");//获得Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
		String status=jsonResponse.getString("status");         //获取状态
		/**
		 * 关闭客户端并返回结果给上层
		 */
		httpClient.close();										//关闭服务器
		try{                                                    //传回上传文件结果
			UploadFileResult fileResult=UploadFileResult.valueOf(status);	
			return fileResult;
		}catch (IllegalArgumentException e){
			return UploadFileResult.unknownError;
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
		if (statusCode==401){                                 //判断响应码
		    httpClient.close();
		    return new FileDirectoryResult(FileDirectoryStatus.unAuthorized);   
		}
		else if (statusCode!=200){
		    httpClient.close();
		    return new FileDirectoryResult(FileDirectoryStatus.unknownError);
		}
			
		
		/**
		 * 解析响应内容
		 */
		HttpEntity responseEntity=httpResponse.getEntity();    	//获得Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");
																//获得Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
		String status=jsonResponse.getString("status");         //获取状态
		
		try{                                                    //若不是OK，则直接返回结果
            if (!status.equals("OK")){
                httpClient.close();
                return new FileDirectoryResult(FileDirectoryStatus.valueOf(status));
            }
        }catch (IllegalArgumentException e){
            httpClient.close();
            return new FileDirectoryResult(FileDirectoryStatus.unknownError);
        }
		
		JSONArray directoryArray=jsonResponse.getJSONArray("directory");
		ArrayList<CloudFile> directory=new ArrayList<CloudFile>();
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
        Collections.sort(directory);
		/**
		 * 关闭客户端并返回结果给上层
		 */
		httpClient.close();										//关闭服务器
		return new FileDirectoryResult(directory,FileDirectoryStatus.OK);		
	}

	@Override
	public DeleteFileResult deleteFile(String fileID) throws IOException {
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
		if (statusCode==401){									//判断响应码
			httpClient.close();
		    return DeleteFileResult.unAuthorized;
		}
		else if (statusCode!=200){
		    httpClient.close();
		    return DeleteFileResult.unknownError;
		}
			
		
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
		httpClient.close();
		try{
		    DeleteFileResult fileResult							//传回结果
				=DeleteFileResult.valueOf(status);						
			return fileResult;
		}catch (IllegalArgumentException e){
			return DeleteFileResult.unknownError;
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
        if (statusCode==401){                                   //判断响应码
            httpClient.close();
            return new DownloadFileResult(DownloadFileStatus.unAuthorized);  
        }
        else if (statusCode==403){
            httpClient.close();
            return new DownloadFileResult(DownloadFileStatus.wrong);
        }
        else if (statusCode!=200){
            httpClient.close();
            return new DownloadFileResult(DownloadFileStatus.unknownError);
        }
           
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
            =new DownloadFileResult(content,DownloadFileStatus.OK);                     
        return downloadFileResult;     
    }

    @Override
    /**
     * 重命名
     */
    public RenameFileResult renameFile(String fileID, String newFilename) throws IOException {
        /**
         * 创建客户端
         */
        CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
        HttpPost httpPost = new HttpPost(serverUri+"/file?fileID="+fileID);   
                                                                    //Post方法
        /**
         * 设置发送内容
         */
        httpPost.addHeader("Cookie", cookie);           //Cookie加入Header里
        JSONObject jsonRequest=new JSONObject();        //包装为JSON对象
        jsonRequest.put("newFilename", newFilename);
        HttpEntity requestBody=
                new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestBody);              //加入请求Body，ContentType自动设置
        
        /**
         * 获得响应并解析
         */
        HttpResponse httpResponse=httpClient.execute(httpPost); //发出请求并获得响应
        int statusCode=httpResponse.getStatusLine().getStatusCode();
        if (statusCode==401){                                  //判断响应码
            httpClient.close();
            return RenameFileResult.unAuthorized;          
        }   
        else if (statusCode!=200){
            httpClient.close();
            return RenameFileResult.unknownError;
        }
            
        HttpEntity responseEntity=httpResponse.getEntity();     //获得Entity
        String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");//获得Body
        
        JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
        String status=jsonResponse.getString("status");         //获取状态
        /**
         * 关闭客户端并返回结果给上层
         */
        httpClient.close();                                     //关闭服务器
        try{
            RenameFileResult result=RenameFileResult.valueOf(status);   
            return result;                                      //传回修改密码结果
        }catch (IllegalArgumentException e){
            return RenameFileResult.unknownError;
        }       
    }
    
    @Override
    public AddNoteResult addNote(Note note) throws IOException{
        /**
         * 创建客户端
         */
        CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
        HttpPost httpPost = new HttpPost(serverUri+"/note");   //Post方法
        /**
         * 设置发送内容
         */
        httpPost.addHeader("Cookie", cookie);           //Cookie加入Header里
        JSONObject jsonRequest=new JSONObject();        //包装为JSON对象
        jsonRequest.put("content", note.getContent());
        jsonRequest.put("fileID", note.getFileID());
        HttpEntity requestBody=
                new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestBody);              //加入请求Body，ContentType自动设置
        
        /**
         * 获得响应并解析
         */
        HttpResponse httpResponse=httpClient.execute(httpPost); //发出请求并获得响应
        int statusCode=httpResponse.getStatusLine().getStatusCode();
        if (statusCode==401){
            httpClient.close();
            return AddNoteResult.unAuthorized;                     //判断响应码
        }
            
        else if (statusCode!=200){
            httpClient.close();
            return AddNoteResult.unknownError;
        }
         
        HttpEntity responseEntity=httpResponse.getEntity();     //获得Entity
        String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");//获得Body
        
        JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
        String status=jsonResponse.getString("status");         //获取状态
        /**
         * 关闭客户端并返回结果给上层
         */
        httpClient.close();
        try{
            AddNoteResult result=AddNoteResult.valueOf(status); 
            return result;                                      //传回增加备注结果
        }catch (IllegalArgumentException e){
            return AddNoteResult.unknownError;
        }
    }

    @Override
    public DeleteNoteResult deleteNote(Note note) throws IOException {
        /**
         * 创建客户端
         */
        CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
        String noteID=note.getNoteID();
        String fileID=note.getFileID();
        HttpDelete httpDelete = 
                new HttpDelete(serverUri+"/note?noteID="+noteID+"&fileID="+fileID);           
                                                                    //Delete方法
        /**
         * 设置发送内容
         */
        httpDelete.addHeader("Cookie", cookie);         //Cookie加入Header里
        
        /**
         * 获得响应码
         */
        HttpResponse httpResponse=httpClient.execute(httpDelete); //发出请求并获得响应
        int statusCode=httpResponse.getStatusLine().getStatusCode();
        if (statusCode==401){                                    //判断响应码
            httpClient.close();
            return DeleteNoteResult.unAuthorized;  
        }
                       
        else if (statusCode!=200){
            httpClient.close();
            return DeleteNoteResult.unknownError;
        }
        
        /**
         * 解析响应内容
         */
        HttpEntity responseEntity=httpResponse.getEntity();     //获得Entity
        String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");
                                                                //获得Body
        
        JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
        String status=jsonResponse.getString("status");         //获取状态
        
        /**
         * 关闭客户端并返回结果给上层
         */
        httpClient.close();                                     //关闭服务器
        try{
            DeleteNoteResult noteResult                         //传回结果
                =DeleteNoteResult.valueOf(status);                        
            return noteResult;
        }catch (IllegalArgumentException e){
            return DeleteNoteResult.unknownError;
        }                       
    }

    @Override
    public NoteListResult getNoteList(String fileID) throws IOException {
        /**
         * 创建客户端
         */
        CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
        HttpGet httpGet = new HttpGet(serverUri+"/getNoteList?fileID="+fileID);        
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
        if (statusCode==401){
            httpClient.close();                             //判断响应码
            return new NoteListResult(NoteListStatus.unAuthorized);
        }
        else if (statusCode!=200){
            httpClient.close();
            return new NoteListResult(NoteListStatus.unknownError);
        }
            
        
        /**
         * 解析响应内容
         */
        HttpEntity responseEntity=httpResponse.getEntity();     //获得Entity
        String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");
                                                                //获得Body
        
        JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
        String status=jsonResponse.getString("status");         //获取状态
        try{                                                    //若不是OK，则直接返回结果
            if (!status.equals("OK")){
                httpClient.close();
                return new NoteListResult(NoteListStatus.valueOf(status));
            }
        }catch (IllegalArgumentException e){
            httpClient.close();
            return new NoteListResult(NoteListStatus.unknownError);
        }
        
        JSONArray noteListArray=jsonResponse.getJSONArray("noteList");
        ArrayList<Note> noteList=new ArrayList<Note>();
        for (int index=0;index<noteListArray.length();index++){
            JSONObject aJSON=noteListArray.getJSONObject(index);
            Note aNote=new Note();
            aNote.setCreator(aJSON.getString("creator"));
            aNote.setFileID(aJSON.getString("fileID"));
            aNote.setNoteID(aJSON.getString("noteID"));
            aNote.setUploadTime(aJSON.getString("uploadTime"));
            aNote.setContent(aJSON.getString("content"));
            noteList.add(aNote);
        }
        
        /**
         * 关闭客户端并返回结果给上层
         */
        httpClient.close();                                     //关闭服务器
        return new NoteListResult(noteList,NoteListStatus.OK);    
    }

    @Override
    public AuthorizationResult setAuthorization(String fileID,
            Authorization authorization) throws  IOException {
        /**
         * 创建客户端
         */
        CloseableHttpClient httpClient=HttpClients.createDefault(); //创建HttpClient
        HttpPost httpPost = new HttpPost(serverUri+"/setAuthorization?fileID="+fileID);   
                                                                    //Post方法
        /**
         * 设置发送内容
         */
        httpPost.addHeader("Cookie", cookie);           //Cookie加入Header里
        JSONObject jsonRequest=new JSONObject();        //包装为JSON对象
        jsonRequest.put("authorization", authorization.getStatus());
        HttpEntity requestBody=
                new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestBody);              //加入请求Body，ContentType自动设置
        
        /**
         * 获得响应并解析
         */
        HttpResponse httpResponse=httpClient.execute(httpPost); //发出请求并获得响应
        int statusCode=httpResponse.getStatusLine().getStatusCode();
        if (statusCode==401){                                  //判断响应码
            httpClient.close();
            return AuthorizationResult.unAuthorized;          
        }   
        else if (statusCode!=200){
            httpClient.close();
            return AuthorizationResult.unknownError;
        }
            
        HttpEntity responseEntity=httpResponse.getEntity();     //获得Entity
        String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");//获得Body
        
        JSONObject jsonResponse=new JSONObject(responseBody);   //转为JSON对象
        String status=jsonResponse.getString("status");         //获取状态
        /**
         * 关闭客户端并返回结果给上层
         */
        httpClient.close();                                     //关闭服务器
        try{
            AuthorizationResult result= AuthorizationResult.valueOf(status);   
            return result;                                      //传回设置权限结果
        }catch (IllegalArgumentException e){
            return AuthorizationResult.unknownError;
        }              
    }

  
}
