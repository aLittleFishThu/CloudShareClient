package test;

import java.util.Collection;
import java.util.HashSet;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import common.Credential;
import common.CloudFile;
public class TestJson {
	public static void main(String args[]){
		HashSet<CloudFile> set=new HashSet<CloudFile>();
		 CloudFile cloud1=new CloudFile("yzj","123");
	     CloudFile cloud2=new CloudFile("yzj2","123");
	     set.add(cloud1);
	     set.add(cloud2);
	     JSONArray jsonArray=new JSONArray(set);
	     System.out.println(jsonArray);
	}
}
