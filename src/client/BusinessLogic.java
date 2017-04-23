package client;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import common.ChangePasswdResult;
import common.Credential;
import common.LoginResult;
import common.RegisterResult;
import client.IBusinessLogic;

public class BusinessLogic implements IBusinessLogic{
	/**
	 * 客户端BLL需使用的NET提供接口
	 */
	private final INetworkLayer m_Network;
	public BusinessLogic(INetworkLayer network){
		m_Network=network;
	}

	@Override
	/**
	 * 实现BLL层的login接口方法
	 * @param  身份信息
	 * @return 登录结果
	 */
	public LoginResult login(Credential cred) throws ClientProtocolException, IOException {
		NetworkLayer network=new NetworkLayer();
		return network.login(cred);   //调用NET层的相关接口方法，直接返回结果
	}

	@Override
	/**
	 * 实现BLL层的register接口方法
	 * @param 身份信息
	 * @param 第二次输入的密码
	 * @param 注册结果
	 */
	public RegisterResult register(Credential cred, String passwordAgain) throws ClientProtocolException, IOException {
		String userID=cred.getUserID();
		String password=cred.getPassword();
		
		if(!common.Validator.isUserIDLegal(userID))
			return RegisterResult.illegalID;         //用户名非法 
		else if (!password.equals(passwordAgain))
			return RegisterResult.notMatch;          //密码不一致
		else if(!common.Validator.isPasswdLegal(password))
			return RegisterResult.illegalPassword;   //密码非法
		else 
			return m_Network.register(cred);     //调用NET层的相关接口方法，返回结果
	}

	@Override
	/**
	 * 实现BLL层的changePasswd接口方法
	 * @param 密码
	 * @param 新密码
	 * @param 第二次新密码
	 * @param 修改结果
	 */
	public ChangePasswdResult changePasswd(String password, String newPassword,
			String newPasswordAgain) throws ClientProtocolException, IOException {
		
		if (!newPassword.equals(newPasswordAgain))
			return ChangePasswdResult.notMatch;          //密码不一致
		else if(!common.Validator.isPasswdLegal(newPassword))
			return ChangePasswdResult.illegalPassword;   //密码非法
		else 
			return m_Network.changePasswd(password,newPassword);    
														//调用NET层的相关接口方法，返回结果
	}


}
