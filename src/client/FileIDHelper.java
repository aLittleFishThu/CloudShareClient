package client;

import java.util.HashSet;
import java.util.Iterator;

import common.CloudFile;

public class FileIDHelper {
	public static String toFileID(String filename,String creator,HashSet<CloudFile> directory){
		Iterator<CloudFile> iter=directory.iterator();
		while (iter.hasNext()){
			CloudFile aFile=iter.next();
			if ((aFile.getFilename().equals(filename))&&(aFile.getCreator().equals(filename)))
				return aFile.getFileID();
		}
		return null;
	}
}
