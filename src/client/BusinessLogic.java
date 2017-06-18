package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;

import common.AddNoteResult;
import common.Authorization;
import common.AuthorizationResult;
import common.ChangePasswdResult;
import common.CloudFile;
import common.Credential;
import common.DeleteFileResult;
import common.DeleteNoteResult;
import common.DownloadFileResult;
import common.FileDirectoryResult;
import common.LoginResult;
import common.Note;
import common.NoteListResult;
import common.RegisterResult;
import common.RenameFileResult;
import common.UploadFileResult;
import common.User;
import common.FileDirectoryResult.FileDirectoryStatus;
import client.IBusinessLogic;

public class BusinessLogic implements IBusinessLogic{
    /**
     * 客户端BLL需使用的NET提供接口
     */
    private final INetworkLayer m_Network;

    private User m_User;
/*    private User currentUser;
    private ArrayList<CloudFile> currentDirectory;
    private CloudFile currentCloudFile;
    private Note currentNote;*/

    @Override
    public User getUser() {
        return m_User;
    }

    @Override
    public void setUser(User m_User) {
        this.m_User = m_User;
    }

 /*   *//**
     *
     * @return
     *//*
    @Override
    public User getCurrentUser() {
        return currentUser;
    }*/
/*
    *//**
     *
     * @param currentUser
     *//*
    @Override
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public ArrayList<CloudFile> getCurrentDirectory() {
        return currentDirectory;
    }

    @Override
    public void setCurrentDirectory(ArrayList<CloudFile> currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    @Override
    public CloudFile getCurrentCloudFile() {
        return currentCloudFile;
    }

    @Override
    public void setCurrentCloudFile(CloudFile currentCloudFile) {
        this.currentCloudFile = currentCloudFile;
    }

    @Override
    public Note getCurrentNote() {
        return currentNote;
    }

    public void setCurrentNote(Note currentNote) {
        this.currentNote = currentNote;
    }
	*/

    public BusinessLogic(INetworkLayer network){
            m_Network=network;
            m_User=new User();
 /*           currentUser=new User();
            currentDirectory=new ArrayList<CloudFile>();
            currentCloudFile=new CloudFile();
            currentNote=new Note();*/
    }

    @Override
    /**
     * 实现BLL层的login接口方法
     * @param  身份信息
     * @return 登录结果
     */
    public LoginResult login(Credential cred) throws ClientProtocolException, IOException {
            LoginResult loginResult=m_Network.login(cred);
            if (loginResult.equals(LoginResult.OK))
                    m_User=new User(cred.getUserID(),cred.getPassword());
              /*  currentUser=new User(cred.getUserID(),cred.getPassword());
                FileDirectoryResult directoryResult=m_Network.getDirectory(m_User.getUserID());
                if (directoryResult.getResult().equals(FileDirectoryStatus.OK)){
                    currentDirectory=directoryResult.getFileDirectory();
                }*/
            return loginResult;   //调用NET层的相关接口方法，直接返回结果
    }

    @Override
    /**
     * 实现BLL层的register接口方法
     * @param 身份信息
     * @param 第二次输入的密码
     * @param 注册结果
     */
    public RegisterResult register(Credential cred, String passwordAgain) 
            throws ClientProtocolException, IOException {
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
    public UploadFileResult uploadFile(String filename, String filePath) throws IOException 
                     {
            File file=new File(filePath);			//判断上传文件是否存在
            if (!file.exists())
                    return UploadFileResult.wrong;
            FileInputStream fin;
            byte[] content;
    try {
        fin = new FileInputStream(file);
        content = IOUtils.toByteArray(fin);
    } catch (FileNotFoundException e) {
        return UploadFileResult.wrong;
    } catch (IOException e) {
        return UploadFileResult.wrong;
    }

            CloudFile cloudFile=new CloudFile();		//设置文件的filename属性
            cloudFile.setFilename(filename);
            return m_Network.uploadFile(cloudFile, content);	//返回调用结果
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
            //currentDirectory=directoryResult.getFileDirectory();
                                                            //暂存文件目录
            /*if (directoryResult.getResult().equals(FileResult.OK)){
                    if (targetID.equals(m_User.getUserID()))	//暂存文件目录
                            selfDirectory=directoryResult.getFileDirectory();
                    else
                            otherDirectory=directoryResult.getFileDirectory();
            }*/
            return directoryResult;						//返回调用结果到上层
    }

    @Override
    /**
     * UI传入CloudFile对象，判断是否为自己的文件，有权限则调用底层
     * @param CloudFile
     */
    public DeleteFileResult deleteFile(CloudFile cloudFile) throws IOException {
        String targetID=cloudFile.getCreator();
        String fileID=cloudFile.getFileID();
            if (!targetID.equals(m_User.getUserID()))	//检查权限
                    return DeleteFileResult.wrong;

            return m_Network.deleteFile(fileID);		//调用接口返回结果
    }

    @Override
    public DownloadFileResult downloadFile(CloudFile cloudFile) throws UnsupportedOperationException, IOException {
       String fileID=cloudFile.getFileID();
       return m_Network.downloadFile(fileID);
    }

    @Override
    /**
     * 重命名文件
     */
    public RenameFileResult renameFile(CloudFile cloudFile,String newFilename) throws IOException {
       if (!cloudFile.getCreator().equals(m_User.getUserID()))    //非本人文件
           return RenameFileResult.wrong;
       if (cloudFile.getFilename().equals(newFilename))           //与原来文件相同
           return RenameFileResult.OK;                       
       return m_Network.renameFile(cloudFile.getFileID(), newFilename);
    }
    
    @Override
    /**
     * 调用底层接口
     */
    public AddNoteResult addNote(Note note) throws IOException {
        return m_Network.addNote(note);
    }

    @Override
    /**
     * 删除备注
     * @param note
     * 先判断权限
     */
    public DeleteNoteResult deleteNote(Note note) throws IOException {
        if (!note.getCreator().equals(m_User.getUserID()))  //检查权限
            return DeleteNoteResult.wrong;
        return m_Network.deleteNote(note);                  //调用接口
    }

    @Override
    public NoteListResult getNoteList(CloudFile file) throws IOException {
        String fileID=file.getFileID();
        return m_Network.getNoteList(fileID);
    }

    @Override
    public AuthorizationResult setAuthorization(CloudFile file,
            Authorization authorization) throws IOException {
       if (!file.getCreator().equals(m_User.getUserID())) //检查权限
           return AuthorizationResult.wrong;
       if (file.getAuthorization().equals(authorization))
           return AuthorizationResult.OK;                 //与原来的权限相同则不修改
       return m_Network.setAuthorization(file.getFileID(), authorization);
    }
}
