package client;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import common.ChangePasswdResult;
import common.CloudFile;
import common.Credential;
import common.DownloadFileResult;
import common.FileDirectoryResult;
import common.FileResult;
import common.LoginResult;
import common.RegisterResult;

/**
 * �ͻ���NET���ṩ�ӿ�
 *   ��¼
 *   ע��
 *   �޸�����
 */
public interface INetworkLayer {
	//Client.BLL->Client.NET
	public LoginResult login(Credential cred) 
			throws ClientProtocolException, IOException;
	public RegisterResult register(Credential cred) 
			throws ClientProtocolException, IOException;
	public ChangePasswdResult changePasswd(String password, String newPassword)
			throws ClientProtocolException, IOException;
	public FileResult uploadFile(CloudFile cloudFile,File file)
			throws ClientProtocolException, IOException;
	public FileDirectoryResult getDirectory(String targetID) throws IOException;
	public FileResult deleteFile (String fileID) throws IOException;
	public DownloadFileResult downloadFile(String fileID) 
	        throws UnsupportedOperationException, IOException;
	public addNote(String fileID,Note note);
}
