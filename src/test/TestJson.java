package test;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import common.Credential;
import common.CloudFile;
import common.Note;
public class TestJson {
	public static void main(String args[]){
		/*HashSet<CloudFile> set=new HashSet<CloudFile>();
		 CloudFile cloud1=new CloudFile("yzj","123");
	     CloudFile cloud2=new CloudFile("yzj2","123");
	     set.add(cloud1);
	     set.add(cloud2);
	     JSONArray jsonArray=new JSONArray(set);
	     System.out.println(jsonArray);
	     JSONObject json1=jsonArray.optJSONObject(0);
	     System.out.println(jsonArray.length());
	     System.out.println(json1);
	     System.out.println(json1.get("openList").toString());*/
	     HashMap<String,Note> map=new HashMap<String,Note>();
	     Note note=new Note("123","456","789");
	     map.put("345", note);
	     map.put("678", note);
	     JSONArray jsonArray=new JSONArray(map.values());
	     System.out.println(jsonArray);
	}
}
