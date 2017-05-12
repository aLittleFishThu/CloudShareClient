package client;

import java.io.IOException;

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
import java.util.ArrayList;

/**
 * 客户端BLL层提供接口
 * 	登录
 * 	注册
 * 	修改密码
 */
public interface IBusinessLogic {
    public User getUser();
    public void setUser(User m_User);
  /*  public User getCurrentUser();
    public void setCurrentUser(User currentUser);
    public ArrayList<CloudFile> getCurrentDirectory();
    public void setCurrentDirectory(ArrayList<CloudFile> currentDirectory);
    public CloudFile getCurrentCloudFile();
    public void setCurrentCloudFile(CloudFile currentCloudFile);
    public Note getCurrentNote();*/
	public LoginResult login(Credential cred) 
			throws ClientProtocolException, IOException;
	public RegisterResult register(Credential cred, String passwordAgain) 
			throws ClientProtocolException, IOException; 
	public ChangePasswdResult changePasswd(String password, 
			String newPassword, String newPasswordAgain) 
			        throws ClientProtocolException, IOException;
	public UploadFileResult uploadFile(String filename,String filePath) 
			throws IOException;
	public FileDirectoryResult getDirectory(String targetID) 
			throws IOException;
	public DeleteFileResult deleteFile(CloudFile cloudFile) 
			throws IOException;
	public DownloadFileResult downloadFile(CloudFile cloudFile) 
	        throws UnsupportedOperationException, IOException;
	public RenameFileResult renameFile(CloudFile cloudFile,String newFilename) 
	        throws IOException;
	public AddNoteResult addNote(Note note) throws IOException;
	public DeleteNoteResult deleteNote(Note note) throws IOException;
	public NoteListResult getNoteList(CloudFile file) throws IOException;
	public AuthorizationResult setAuthorization(CloudFile file,Authorization authorization) 
	        throws IOException;
}