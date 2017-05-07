package client;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import common.AddNoteResult;
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

/**
 * 客户端BLL层提供接口
 * 	登录
 * 	注册
 * 	修改密码
 */
public interface IBusinessLogic {
	public LoginResult login(Credential cred) 
			throws ClientProtocolException, IOException;
	public RegisterResult register(Credential cred, String passwordAgain) 
			throws ClientProtocolException, IOException; 
	public ChangePasswdResult changePasswd(String password, 
			String newPassword, String newPasswordAgain) 
			        throws ClientProtocolException, IOException;
	public UploadFileResult uploadFile(String filename,String filePath) 
			throws ClientProtocolException, IOException;
	public FileDirectoryResult getDirectory(String targetID) 
			throws IOException;
	public DeleteFileResult deleteFile(CloudFile file) 
			throws IOException;
	public DownloadFileResult downloadFile(CloudFile file) 
	        throws UnsupportedOperationException, IOException;
	public RenameFileResult renameFile(CloudFile file,String newFilename) 
	        throws IOException;
	public AddNoteResult addNote(Note note) throws IOException;
	public DeleteNoteResult deleteNote(Note note) throws IOException;
	public NoteListResult getNoteList(CloudFile file) throws IOException;
}