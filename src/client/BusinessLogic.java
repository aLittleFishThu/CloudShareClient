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
	 * 客户端BLL需使用的NET提供接口
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
	 * 实现BLL层的login接口方法
	 * @param  身份信息
	 * @return 登录结果
	 */
	public LoginResult login(Credential cred) throws ClientProtocolException, IOException {
		NetworkLayer network=new NetworkLayer();
		LoginResult loginResult=network.login(cred);
		if (loginResult.equals(LoginResult.OK))
			m_User=new User(cred.getUserID(),cred.getPassword());
		return loginResult;   //调用NET层的相关接口方法，直接返回结果
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

	@Override
	/**
	 * 实现BLL层的uploadFile接口方法
	 * @param filename 文件名
	 * @param filePath 文件路径
	 * return 结果
	 */
	public UploadFileResult uploadFile(String filename, String filePath) 
			throws ClientProtocolException, IOException {
		File file=new File(filePath);					//判断上传文件是否存在
		if (!file.exists())
			return UploadFileResult.wrong;
		CloudFile cloudFile=new CloudFile();			//设置文件的filename属性
		cloudFile.setFilename(filename);
		return m_Network.uploadFile(cloudFile, file);	//返回调用结果
	}

	@Override
	/**
	 * 实现NLL层的下载文件目录方法
	 * @param targetID
	 * @return 下载结果和目录
	 * 下载的目录暂存在BLL层中
	 */
	public FileDirectoryResult getDirectory(String targetID) throws IOException {
		FileDirectoryResult directoryResult=
				m_Network.getDirectory(targetID);		//调用NET层接口，得到结果
		currentDirectory=directoryResult.getFileDirectory();
		                                                //暂存文件目录
		/*if (directoryResult.getResult().equals(FileResult.OK)){
			if (targetID.equals(m_User.getUserID()))	//暂存文件目录
				selfDirectory=directoryResult.getFileDirectory();
			else
				otherDirectory=directoryResult.getFileDirectory();
		}*/
		return directoryResult;						     //返回调用结果到上层
	}

	@Override
	/**
	 * UI传入CloudFile对象，判断是否为自己的文件，有权限则调用底层
	 * @param CloudFile
	 */
	public DeleteFileResult deleteFile(CloudFile file) throws IOException {
	    String targetID=file.getCreator();
	    String fileID=file.getFileID();
		if (!targetID.equals(m_User.getUserID()))		//检查权限
			return DeleteFileResult.wrong;
		
		return m_Network.deleteFile(fileID);		//调用接口返回结果
	}

    @Override
    public DownloadFileResult downloadFile(CloudFile file) throws UnsupportedOperationException, IOException {
       String fileID=file.getFileID();
       return m_Network.downloadFile(fileID);
      /* String targetID=file.getCreator();
       if (targetID.equals(m_User.getUserID())){
           String fileID=FileIDHelper.toFileID(filename,targetID,selfDirectory);
           if (fileID==null)
               return new DownloadFileResult();            //将filename转为fileID
           else
               return m_Network.downloadFile(fileID);        //调用接口返回结果
       }
       else
       {
           String fileID=FileIDHelper.toFileID(filename,targetID,otherDirectory);
           if (fileID==null)
               return new DownloadFileResult();            //将filename转为fileID
           else
               return m_Network.downloadFile(fileID);        //调用接口返回结果  
       }*/
    }

    @Override
    /**
     * 调用底层接口
     */
    public NoteResult addNote(Note note) throws IOException {
        return m_Network.addNote(note);
    }
}
