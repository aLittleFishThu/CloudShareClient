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
		String responseBody=Convert.toString(responseEntity.getContent()); 
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
		return Convert.toLoginResult(status);					//���ص�¼���
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
		String responseBody=Convert.toString(responseEntity.getContent()); 
																//���Body
		JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
		String status=jsonResponse.getString("status");         //��ȡ״̬
		/**
		 * �رտͻ��˲����ؽ�����ϲ�
		 */
		httpClient.close();										//�رշ�����
		return Convert.toRegisterResult(status);				//���ص�¼���
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
		if (statusCode==403)
			return ChangePasswdResult.unAuthorized;      		//�ж���Ӧ��
		else if (statusCode!=200)
			return ChangePasswdResult.unknownError;
		HttpEntity responseEntity=httpResponse.getEntity();    	//���Entity
		String responseBody=Convert.toString(responseEntity.getContent());//���Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
		String status=jsonResponse.getString("status");         //��ȡ״̬
		/**
		 * �رտͻ��˲����ؽ�����ϲ�
		 */
		httpClient.close();										//�رշ�����
		return Convert.toChangePasswdResult(status);			//���ص�¼���	
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
		if (statusCode==403)
			return FileResult.unAuthorized;      		//�ж���Ӧ��
		else if (statusCode!=200)
			return FileResult.unknownError;
		HttpEntity responseEntity=httpResponse.getEntity();    	//���Entity
		String responseBody=Convert.toString(responseEntity.getContent());//���Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
		String status=jsonResponse.getString("status");         //��ȡ״̬
		/**
		 * �رտͻ��˲����ؽ�����ϲ�
		 */
		httpClient.close();										//�رշ�����
		return Convert.toFileResult(status);			//���ص�¼���			
	}
}
