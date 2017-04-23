package client;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import common.ChangePasswdResult;
import common.Credential;
import common.LoginResult;
import common.RegisterResult;

/**
 * 客户端NET层提供接口
 *   登录
 *   注册
 *   修改密码
 */
public interface INetworkLayer {
	//Client.BLL->Client.NET
	public LoginResult login(Credential cred) 
			throws ClientProtocolException, IOException;
	public RegisterResult register(Credential cred) 
			throws ClientProtocolException, IOException;
	public ChangePasswdResult changePasswd(String password, String newPassword)
			throws ClientProtocolException, IOException;
}
