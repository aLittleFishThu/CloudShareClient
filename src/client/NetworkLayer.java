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
	private String serverUri; //��������ַ
	private String cookie;
	
	public NetworkLayer(){
		serverUri="http://localhost:13051/cloudshare";
		cookie="";
	}

	@Override
	/**
	 * ��д�ͻ���Net�ӿڷ���
	 */
	public LoginResult login(Credential cred) 
			throws ClientProtocolException, IOException {
		/**
		 * �����ͻ���
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //����HttpClient
		HttpPost httpPost = new HttpPost(serverUri+"/login");   //Post����
		/**
		 * ���÷�������
		 */
		JSONObject jsonRequest=new JSONObject();   		//��װΪJSON����
		jsonRequest.put("userID", cred.getUserID());
		jsonRequest.put("password", cred.getPassword());
		HttpEntity requestBody=
				new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
		httpPost.setEntity(requestBody);              //��������Body��ContentType�Զ�����
		/**
		 * �����Ӧ������
		 */
		HttpResponse httpResponse=httpClient.execute(httpPost); //�������󲢻����Ӧ
		if (!(httpResponse.getStatusLine().getStatusCode()==200))
			return LoginResult.unknownError;      				//�ж���Ӧ��
		HttpEntity responseEntity=httpResponse.getEntity();    	//���Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8"); 
																//���Body
		JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
		String status=jsonResponse.getString("status");         //��ȡ״̬
		if (httpResponse.containsHeader("Set-Cookie")){			//���Cookie
			this.cookie=httpResponse.getFirstHeader("Set-Cookie").getValue();  
		}
		/**
		 * �رտͻ��˲����ؽ�����ϲ�
		 */
		httpClient.close();										//�رշ�����
		try{
			LoginResult result=LoginResult.valueOf(status);	
			return result;										//���ص�¼���
		}catch (IllegalArgumentException e){
			return LoginResult.unknownError;
		}
	}

	@Override
	public RegisterResult register(Credential cred) 
			throws ClientProtocolException, IOException {
		/**
		 * �����ͻ���
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //����HttpClient
		HttpPost httpPost = new HttpPost(serverUri+"/register");   //Post����
		/**
		 * ���÷�������
		 */
		JSONObject jsonRequest=new JSONObject();   		//��װΪJSON����
		jsonRequest.put("userID", cred.getUserID());
		jsonRequest.put("password", cred.getPassword());
		HttpEntity requestBody=
				new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
		httpPost.setEntity(requestBody);              //��������Body��ContentType�Զ�����
		/**
		 * �����Ӧ������
		 */
		HttpResponse httpResponse=httpClient.execute(httpPost); //�������󲢻����Ӧ
		if (!(httpResponse.getStatusLine().getStatusCode()==200))
			return RegisterResult.unknownError;      			//�ж���Ӧ��
		HttpEntity responseEntity=httpResponse.getEntity();    	//���Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8"); 
																//���Body
		JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
		String status=jsonResponse.getString("status");         //��ȡ״̬
		/**
		 * �رտͻ��˲����ؽ�����ϲ�
		 */
		httpClient.close();										//�رշ�����
		try{
			RegisterResult result=RegisterResult.valueOf(status);	
			return result;										//����ע����
		}catch (IllegalArgumentException e){
			return RegisterResult.unknownError;
		}
	}

	@Override
	public ChangePasswdResult changePasswd(String password, String newPassword) 
			throws ClientProtocolException, IOException {
		/**
		 * �����ͻ���
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //����HttpClient
		HttpPost httpPost = new HttpPost(serverUri+"/changePw");   //Post����
		/**
		 * ���÷�������
		 */
		httpPost.addHeader("Cookie", cookie);   		//Cookie����Header��
		JSONObject jsonRequest=new JSONObject();   		//��װΪJSON����
		jsonRequest.put("password", password);
		jsonRequest.put("newPassword", newPassword);
		HttpEntity requestBody=
				new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
		httpPost.setEntity(requestBody);              //��������Body��ContentType�Զ�����
		
		/**
		 * �����Ӧ������
		 */
		HttpResponse httpResponse=httpClient.execute(httpPost); //�������󲢻����Ӧ
		int statusCode=httpResponse.getStatusLine().getStatusCode();
		if (statusCode==401)
			return ChangePasswdResult.unAuthorized;      		//�ж���Ӧ��
		else if (statusCode!=200)
			return ChangePasswdResult.unknownError;
		HttpEntity responseEntity=httpResponse.getEntity();    	//���Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");//���Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
		String status=jsonResponse.getString("status");         //��ȡ״̬
		/**
		 * �رտͻ��˲����ؽ�����ϲ�
		 */
		httpClient.close();										//�رշ�����
		try{
			ChangePasswdResult result=ChangePasswdResult.valueOf(status);	
			return result;										//�����޸�������
		}catch (IllegalArgumentException e){
			return ChangePasswdResult.unknownError;
		}
	}
	
	public FileResult uploadFile(CloudFile cloudFile,File file) 
			throws ClientProtocolException, IOException{
		/**
		 * �����ͻ���
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //����HttpClient
		String filename=cloudFile.getFilename();
		HttpPost httpPost = new HttpPost(serverUri+"/file?filename="+filename);   		
																	//Post����
		/**
		 * ���÷�������
		 */
		httpPost.addHeader("Cookie", cookie);   		//Cookie����Header��
		HttpEntity requestBody=new FileEntity(file,ContentType.DEFAULT_BINARY);
														//�ļ�����requestBody
		httpPost.setEntity(requestBody);              //��������Body��ContentType�Զ�����
		/**
		 * �����Ӧ������
		 */
		HttpResponse httpResponse=httpClient.execute(httpPost); //�������󲢻����Ӧ
		int statusCode=httpResponse.getStatusLine().getStatusCode();
		if (statusCode==401)
			return FileResult.unAuthorized;      		//�ж���Ӧ��
		else if (statusCode!=200)
			return FileResult.unknownError;
		HttpEntity responseEntity=httpResponse.getEntity();    	//���Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");//���Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
		String status=jsonResponse.getString("status");         //��ȡ״̬
		/**
		 * �رտͻ��˲����ؽ�����ϲ�
		 */
		httpClient.close();										//�رշ�����
		try{
			FileResult fileResult=FileResult.valueOf(status);	//�����ϴ��ļ����
			return fileResult;
		}catch (IllegalArgumentException e){
			return FileResult.unknownError;
		}
	}

	@Override
	public FileDirectoryResult getDirectory(String targetID) throws IOException {
		/**
		 * �����ͻ���
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //����HttpClient
		HttpGet httpGet = new HttpGet(serverUri+"/getDirectory?targetID="+targetID);   		
																	//Get����
		/**
		 * ���÷�������
		 */
		httpGet.addHeader("Cookie", cookie);   		//Cookie����Header��
		
		/**
		 * �����Ӧ��
		 */
		HttpResponse httpResponse=httpClient.execute(httpGet); //�������󲢻����Ӧ
		int statusCode=httpResponse.getStatusLine().getStatusCode();
		if (statusCode==401)									//�ж���Ӧ��
			return new FileDirectoryResult(FileResult.unAuthorized);      		
		else if (statusCode!=200)
			return new FileDirectoryResult(FileResult.unknownError);
		
		/**
		 * ������Ӧ����
		 */
		HttpEntity responseEntity=httpResponse.getEntity();    	//���Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");
																//���Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
		String status=jsonResponse.getString("status");         //��ȡ״̬
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
		 * �رտͻ��˲����ؽ�����ϲ�
		 */
		httpClient.close();										//�رշ�����
		try{
			FileDirectoryResult directoryResult					//�����ļ�Ŀ¼
				=new FileDirectoryResult(directory,FileResult.valueOf(status));						
			return directoryResult;
		}catch (IllegalArgumentException e){
			return new FileDirectoryResult(FileResult.unknownError);
		}		
	}

	@Override
	public FileResult deleteFile(String fileID) throws IOException {
		/**
		 * �����ͻ���
		 */
		CloseableHttpClient httpClient=HttpClients.createDefault(); //����HttpClient
		HttpDelete httpDelete = new HttpDelete(serverUri+"/file?fileID="+fileID);   		
																	//Delete����
		/**
		 * ���÷�������
		 */
		httpDelete.addHeader("Cookie", cookie);   		//Cookie����Header��
		
		/**
		 * �����Ӧ��
		 */
		HttpResponse httpResponse=httpClient.execute(httpDelete); //�������󲢻����Ӧ
		int statusCode=httpResponse.getStatusLine().getStatusCode();
		if (statusCode==401)									//�ж���Ӧ��
			return FileResult.unAuthorized;      		
		else if (statusCode!=200)
			return FileResult.unknownError;
		
		/**
		 * ������Ӧ����
		 */
		HttpEntity responseEntity=httpResponse.getEntity();    	//���Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");
																//���Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
		String status=jsonResponse.getString("status");         //��ȡ״̬
		
		/**
		 * �رտͻ��˲����ؽ�����ϲ�
		 */
		httpClient.close();										//�رշ�����
		try{
			FileResult fileResult								//���ؽ��
				=FileResult.valueOf(status);						
			return fileResult;
		}catch (IllegalArgumentException e){
			return FileResult.unknownError;
		}				
	}

    @Override
    public DownloadFileResult downloadFile(String fileID) throws UnsupportedOperationException, IOException {
        /**
         * �����ͻ���
         */
        CloseableHttpClient httpClient=HttpClients.createDefault(); //����HttpClient
        HttpGet httpGet = new HttpGet(serverUri+"/file?fileID="+fileID);        
                                                                    //Get����
        /**
         * ���÷�������
         */
        httpGet.addHeader("Cookie", cookie);        //Cookie����Header��
        
        /**
         * �����Ӧ��
         */
        HttpResponse httpResponse=httpClient.execute(httpGet); //�������󲢻����Ӧ
        int statusCode=httpResponse.getStatusLine().getStatusCode();
        if (statusCode==401)                                    //�ж���Ӧ��
            return new DownloadFileResult(FileResult.unAuthorized);  
        else if (statusCode==403)
            return new DownloadFileResult();
        else if (statusCode!=200)
            return new DownloadFileResult(FileResult.unknownError);
        
        /**
         * ������Ӧ����
         */
        HttpEntity responseEntity=httpResponse.getEntity();     //���Entity
        byte[] content=IOUtils.toByteArray(responseEntity.getContent());
                                                                //��ȡ�ļ�����
        /**
         * �رտͻ��˲����ؽ�����ϲ�
         */
        httpClient.close();                                     //�رշ�����
        DownloadFileResult downloadFileResult                 //�����ļ�Ŀ¼
            =new DownloadFileResult(content,FileResult.OK);                     
        return downloadFileResult;     
    }
}
