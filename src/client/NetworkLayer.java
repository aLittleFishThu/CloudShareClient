package client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import common.AddNoteResult;
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
import common.UploadFileResult;

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
		if (!(httpResponse.getStatusLine().getStatusCode()==200)){
		    httpClient.close();
		    return LoginResult.unknownError;                      //�ж���Ӧ��
		}
			
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
		if (!(httpResponse.getStatusLine().getStatusCode()==200)){
		    httpClient.close();
		    return RegisterResult.unknownError;               //�ж���Ӧ��
		}
			
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
		if (statusCode==401){                                  //�ж���Ӧ��
		    httpClient.close();
		    return ChangePasswdResult.unAuthorized;          
		}	
		else if (statusCode!=200){
		    httpClient.close();
		    return ChangePasswdResult.unknownError;
		}
			
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
	
	public UploadFileResult uploadFile(CloudFile cloudFile,File file) 
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
		if (statusCode==401){
		    httpClient.close();
		    return UploadFileResult.unAuthorized;             //�ж���Ӧ��
		}	
		else if (statusCode!=200){
		    httpClient.close();
		    return UploadFileResult.unknownError;
		}
			
		HttpEntity responseEntity=httpResponse.getEntity();    	//���Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");//���Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
		String status=jsonResponse.getString("status");         //��ȡ״̬
		/**
		 * �رտͻ��˲����ؽ�����ϲ�
		 */
		httpClient.close();										//�رշ�����
		try{                                                    //�����ϴ��ļ����
			UploadFileResult fileResult=UploadFileResult.valueOf(status);	
			return fileResult;
		}catch (IllegalArgumentException e){
			return UploadFileResult.unknownError;
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
		if (statusCode==401){                                 //�ж���Ӧ��
		    httpClient.close();
		    return new FileDirectoryResult(FileDirectoryStatus.unAuthorized);   
		}
		else if (statusCode!=200){
		    httpClient.close();
		    return new FileDirectoryResult(FileDirectoryStatus.unknownError);
		}
			
		
		/**
		 * ������Ӧ����
		 */
		HttpEntity responseEntity=httpResponse.getEntity();    	//���Entity
		String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");
																//���Body
		
		JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
		String status=jsonResponse.getString("status");         //��ȡ״̬
		
		try{                                                    //������OK����ֱ�ӷ��ؽ��
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
		
		/**
		 * �رտͻ��˲����ؽ�����ϲ�
		 */
		httpClient.close();										//�رշ�����
		return new FileDirectoryResult(directory,FileDirectoryStatus.OK);		
	}

	@Override
	public DeleteFileResult deleteFile(String fileID) throws IOException {
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
		if (statusCode==401){									//�ж���Ӧ��
			httpClient.close();
		    return DeleteFileResult.unAuthorized;
		}
		else if (statusCode!=200){
		    httpClient.close();
		    return DeleteFileResult.unknownError;
		}
			
		
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
		httpClient.close();
		try{
		    DeleteFileResult fileResult							//���ؽ��
				=DeleteFileResult.valueOf(status);						
			return fileResult;
		}catch (IllegalArgumentException e){
			return DeleteFileResult.unknownError;
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
        if (statusCode==401){                                   //�ж���Ӧ��
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
            =new DownloadFileResult(content,DownloadFileStatus.OK);                     
        return downloadFileResult;     
    }

    @Override
    public AddNoteResult addNote(Note note) throws IOException{
        /**
         * �����ͻ���
         */
        CloseableHttpClient httpClient=HttpClients.createDefault(); //����HttpClient
        HttpPost httpPost = new HttpPost(serverUri+"/note");   //Post����
        /**
         * ���÷�������
         */
        httpPost.addHeader("Cookie", cookie);           //Cookie����Header��
        JSONObject jsonRequest=new JSONObject();        //��װΪJSON����
        jsonRequest.put("content", note.getContent());
        jsonRequest.put("fileID", note.getFileID());
        HttpEntity requestBody=
                new StringEntity(jsonRequest.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestBody);              //��������Body��ContentType�Զ�����
        
        /**
         * �����Ӧ������
         */
        HttpResponse httpResponse=httpClient.execute(httpPost); //�������󲢻����Ӧ
        int statusCode=httpResponse.getStatusLine().getStatusCode();
        if (statusCode==401){
            httpClient.close();
            return AddNoteResult.unAuthorized;                     //�ж���Ӧ��
        }
            
        else if (statusCode!=200){
            httpClient.close();
            return AddNoteResult.unknownError;
        }
         
        HttpEntity responseEntity=httpResponse.getEntity();     //���Entity
        String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");//���Body
        
        JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
        String status=jsonResponse.getString("status");         //��ȡ״̬
        /**
         * �رտͻ��˲����ؽ�����ϲ�
         */
        httpClient.close();
        try{
            AddNoteResult result=AddNoteResult.valueOf(status); 
            return result;                                      //�������ӱ�ע���
        }catch (IllegalArgumentException e){
            return AddNoteResult.unknownError;
        }
    }

    @Override
    public DeleteNoteResult deleteNote(Note note) throws IOException {
        /**
         * �����ͻ���
         */
        CloseableHttpClient httpClient=HttpClients.createDefault(); //����HttpClient
        String noteID=note.getNoteID();
        String fileID=note.getFileID();
        HttpDelete httpDelete = 
                new HttpDelete(serverUri+"/note?noteID="+noteID+"&fileID="+fileID);           
                                                                    //Delete����
        /**
         * ���÷�������
         */
        httpDelete.addHeader("Cookie", cookie);         //Cookie����Header��
        
        /**
         * �����Ӧ��
         */
        HttpResponse httpResponse=httpClient.execute(httpDelete); //�������󲢻����Ӧ
        int statusCode=httpResponse.getStatusLine().getStatusCode();
        if (statusCode==401){                                    //�ж���Ӧ��
            httpClient.close();
            return DeleteNoteResult.unAuthorized;  
        }
                       
        else if (statusCode!=200){
            httpClient.close();
            return DeleteNoteResult.unknownError;
        }
        
        /**
         * ������Ӧ����
         */
        HttpEntity responseEntity=httpResponse.getEntity();     //���Entity
        String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");
                                                                //���Body
        
        JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
        String status=jsonResponse.getString("status");         //��ȡ״̬
        
        /**
         * �رտͻ��˲����ؽ�����ϲ�
         */
        httpClient.close();                                     //�رշ�����
        try{
            DeleteNoteResult noteResult                         //���ؽ��
                =DeleteNoteResult.valueOf(status);                        
            return noteResult;
        }catch (IllegalArgumentException e){
            return DeleteNoteResult.unknownError;
        }                       
    }

    @Override
    public NoteListResult getNoteList(String fileID) throws IOException {
        /**
         * �����ͻ���
         */
        CloseableHttpClient httpClient=HttpClients.createDefault(); //����HttpClient
        HttpGet httpGet = new HttpGet(serverUri+"/getNoteList?fileID="+fileID);        
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
        if (statusCode==401){
            httpClient.close();                             //�ж���Ӧ��
            return new NoteListResult(NoteListStatus.unAuthorized);
        }
        else if (statusCode!=200){
            httpClient.close();
            return new NoteListResult(NoteListStatus.unknownError);
        }
            
        
        /**
         * ������Ӧ����
         */
        HttpEntity responseEntity=httpResponse.getEntity();     //���Entity
        String responseBody=IOUtils.toString(responseEntity.getContent(),"UTF-8");
                                                                //���Body
        
        JSONObject jsonResponse=new JSONObject(responseBody);   //תΪJSON����
        String status=jsonResponse.getString("status");         //��ȡ״̬
        try{                                                    //������OK����ֱ�ӷ��ؽ��
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
         * �رտͻ��˲����ؽ�����ϲ�
         */
        httpClient.close();                                     //�رշ�����
        return new NoteListResult(noteList,NoteListStatus.OK);    
    }
}
