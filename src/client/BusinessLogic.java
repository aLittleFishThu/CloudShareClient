package client;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.apache.http.client.ClientProtocolException;

import common.ChangePasswdResult;
import common.CloudFile;
import common.Credential;
import common.FileDirectoryResult;
import common.FileResult;
import common.LoginResult;
import common.RegisterResult;
import common.User;
import client.IBusinessLogic;

public class BusinessLogic implements IBusinessLogic{
	/**
	 * �ͻ���BLL��ʹ�õ�NET�ṩ�ӿ�
	 */
	private final INetworkLayer m_Network;
	private User m_User;
	private HashSet<CloudFile> selfDirectory;
	private HashSet<CloudFile> otherDirectory;
	
	public BusinessLogic(INetworkLayer network){
		m_Network=network;
		m_User=new User();
		selfDirectory=new HashSet<CloudFile>();
		otherDirectory=new HashSet<CloudFile>();
	}

	@Override
	/**
	 * ʵ��BLL���login�ӿڷ���
	 * @param  �����Ϣ
	 * @return ��¼���
	 */
	public LoginResult login(Credential cred) throws ClientProtocolException, IOException {
		NetworkLayer network=new NetworkLayer();
		LoginResult loginResult=network.login(cred);
		if (loginResult.equals(LoginResult.OK))
			m_User=new User(cred.getUserID(),cred.getPassword());
		return loginResult;   //����NET�����ؽӿڷ�����ֱ�ӷ��ؽ��
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

	@Override
	/**
	 * ʵ��BLL���uploadFile�ӿڷ���
	 * @param filename �ļ���
	 * @param filePath �ļ�·��
	 * return ���
	 */
	public FileResult uploadFile(String filename, String filePath) 
			throws ClientProtocolException, IOException {
		File file=new File(filePath);					//�ж��ϴ��ļ��Ƿ����
		if (!file.exists())
			return FileResult.wrong;
		CloudFile cloudFile=new CloudFile();			//�����ļ���filename����
		cloudFile.setFilename(filename);
		return m_Network.uploadFile(cloudFile, file);	//���ص��ý��
	}

	@Override
	/**
	 * ʵ��NLL��������ļ�Ŀ¼����
	 * @param targetID
	 * @return ���ؽ����Ŀ¼
	 * ���ص�Ŀ¼�ݴ���BLL����
	 */
	public FileDirectoryResult getDirectory(String targetID) throws IOException {
		FileDirectoryResult directoryResult=
				m_Network.getDirectory(targetID);		//����NET��ӿڣ��õ����
		if (directoryResult.getResult().equals(FileResult.OK)){
			if (targetID.equals(m_User.getUserID()))	//�ݴ��ļ�Ŀ¼
				selfDirectory=directoryResult.getFileDirectory();
			else
				otherDirectory=directoryResult.getFileDirectory();
		}
		return directoryResult;							//���ص��ý�����ϲ�
	}

	@Override
	/**
	 * ����UI�����filename��userID�ҵ�fileID
	 * @param filename
	 * @param userID
	 */
	public FileResult deleteFile(String filename, String userID) throws IOException {
		if (!userID.equals(m_User.getUserID()))			//���Ȩ��
			return FileResult.wrong;
		String fileID=FileIDHelper.toFileID(filename, selfDirectory);
		if (fileID==null)
			return FileResult.wrong;					//��filenameתΪfileID
		else
			return m_Network.deleteFile(fileID);		//���ýӿڷ��ؽ��
	}
}
