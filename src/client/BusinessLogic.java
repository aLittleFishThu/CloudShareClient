package client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import common.ChangePasswdResult;
import common.CloudFile;
import common.Credential;
import common.DeleteFileResult;
import common.DownloadFileResult;
import common.FileDirectoryResult;
import common.LoginResult;
import common.Note;
import common.NoteResult;
import common.RegisterResult;
import common.UploadFileResult;
import common.User;
import client.IBusinessLogic;

public class BusinessLogic implements IBusinessLogic{
	/**
	 * �ͻ���BLL��ʹ�õ�NET�ṩ�ӿ�
	 */
	private final INetworkLayer m_Network;
	private User m_User;

	private ArrayList<CloudFile> currentDirectory;
	/*private ArrayList<CloudFile> selfDirectory;
	private ArrayList<CloudFile> otherDirectory;*/
	
	public BusinessLogic(INetworkLayer network){
		m_Network=network;
		m_User=new User();
		currentDirectory=new ArrayList<CloudFile>();
		/*selfDirectory=new ArrayList<CloudFile>();
		otherDirectory=new ArrayList<CloudFile>();*/
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
	public UploadFileResult uploadFile(String filename, String filePath) 
			throws ClientProtocolException, IOException {
		File file=new File(filePath);					//�ж��ϴ��ļ��Ƿ����
		if (!file.exists())
			return UploadFileResult.wrong;
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
		currentDirectory=directoryResult.getFileDirectory();
		                                                //�ݴ��ļ�Ŀ¼
		/*if (directoryResult.getResult().equals(FileResult.OK)){
			if (targetID.equals(m_User.getUserID()))	//�ݴ��ļ�Ŀ¼
				selfDirectory=directoryResult.getFileDirectory();
			else
				otherDirectory=directoryResult.getFileDirectory();
		}*/
		return directoryResult;						     //���ص��ý�����ϲ�
	}

	@Override
	/**
	 * UI����CloudFile�����ж��Ƿ�Ϊ�Լ����ļ�����Ȩ������õײ�
	 * @param CloudFile
	 */
	public DeleteFileResult deleteFile(CloudFile file) throws IOException {
	    String targetID=file.getCreator();
	    String fileID=file.getFileID();
		if (!targetID.equals(m_User.getUserID()))		//���Ȩ��
			return DeleteFileResult.wrong;
		
		return m_Network.deleteFile(fileID);		//���ýӿڷ��ؽ��
	}

    @Override
    public DownloadFileResult downloadFile(CloudFile file) throws UnsupportedOperationException, IOException {
       String fileID=file.getFileID();
       return m_Network.downloadFile(fileID);
      /* String targetID=file.getCreator();
       if (targetID.equals(m_User.getUserID())){
           String fileID=FileIDHelper.toFileID(filename,targetID,selfDirectory);
           if (fileID==null)
               return new DownloadFileResult();            //��filenameתΪfileID
           else
               return m_Network.downloadFile(fileID);        //���ýӿڷ��ؽ��
       }
       else
       {
           String fileID=FileIDHelper.toFileID(filename,targetID,otherDirectory);
           if (fileID==null)
               return new DownloadFileResult();            //��filenameתΪfileID
           else
               return m_Network.downloadFile(fileID);        //���ýӿڷ��ؽ��  
       }*/
    }

    @Override
    /**
     * ���õײ�ӿ�
     */
    public NoteResult addNote(Note note) throws IOException {
        return m_Network.addNote(note);
    }
}
