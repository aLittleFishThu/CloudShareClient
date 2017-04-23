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
	 * �ͻ���BLL��ʹ�õ�NET�ṩ�ӿ�
	 */
	private final INetworkLayer m_Network;
	public BusinessLogic(INetworkLayer network){
		m_Network=network;
	}

	@Override
	/**
	 * ʵ��BLL���login�ӿڷ���
	 * @param  �����Ϣ
	 * @return ��¼���
	 */
	public LoginResult login(Credential cred) throws ClientProtocolException, IOException {
		NetworkLayer network=new NetworkLayer();
		return network.login(cred);   //����NET�����ؽӿڷ�����ֱ�ӷ��ؽ��
	}

	@Override
	/**
	 * ʵ��BLL���register�ӿڷ���
	 * @param �����Ϣ
	 * @param �ڶ������������
	 * @param ע����
	 */
	public RegisterResult register(Credential cred, String passwordAgain) throws ClientProtocolException, IOException {
		String userID=cred.getUserID();
		String password=cred.getPassword();
		
		if(!common.Validator.isUserIDLegal(userID))
			return RegisterResult.illegalID;         //�û����Ƿ� 
		else if (!password.equals(passwordAgain))
			return RegisterResult.notMatch;          //���벻һ��
		else if(!common.Validator.isPasswdLegal(password))
			return RegisterResult.illegalPassword;   //����Ƿ�
		else 
			return m_Network.register(cred);     //����NET�����ؽӿڷ��������ؽ��
	}

	@Override
	/**
	 * ʵ��BLL���changePasswd�ӿڷ���
	 * @param ����
	 * @param ������
	 * @param �ڶ���������
	 * @param �޸Ľ��
	 */
	public ChangePasswdResult changePasswd(String password, String newPassword,
			String newPasswordAgain) throws ClientProtocolException, IOException {
		
		if (!newPassword.equals(newPasswordAgain))
			return ChangePasswdResult.notMatch;          //���벻һ��
		else if(!common.Validator.isPasswdLegal(newPassword))
			return ChangePasswdResult.illegalPassword;   //����Ƿ�
		else 
			return m_Network.changePasswd(password,newPassword);    
														//����NET�����ؽӿڷ��������ؽ��
	}


}
