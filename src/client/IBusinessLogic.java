package client;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import common.ChangePasswdResult;
import common.Credential;
import common.LoginResult;
import common.RegisterResult;

/**
 * �ͻ���BLL���ṩ�ӿ�
 * 	��¼
 * 	ע��
 * 	�޸�����
 */
public interface IBusinessLogic {
	public LoginResult login(Credential cred) 
			throws ClientProtocolException, IOException;
	public RegisterResult register(Credential cred, String passwordAgain) 
			throws ClientProtocolException, IOException; 
	public ChangePasswdResult changePasswd(String password, 
			String newPassword, String newPasswordAgain) throws ClientProtocolException, IOException;
}