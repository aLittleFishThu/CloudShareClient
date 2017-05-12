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
	public UploadFileResult uploadFile(CloudFile cloudFile,byte[] content)
			throws ClientProtocolException, IOException;
	public FileDirectoryResult getDirectory(String targetID) throws IOException;
	public DeleteFileResult deleteFile (String fileID) throws IOException;
	public DownloadFileResult downloadFile(String fileID) 
	        throws UnsupportedOperationException, IOException;
	public RenameFileResult renameFile(String fileID,String newFilename) 
	        throws IOException;
	public AddNoteResult addNote(Note note) throws IOException;
	public DeleteNoteResult deleteNote(Note note) throws IOException;
	public NoteListResult getNoteList(String fileID) throws IOException;
	public AuthorizationResult setAuthorization(String fileID,Authorization authorization) 
	        throws IOException;
}
