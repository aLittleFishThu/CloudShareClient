package test;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import common.Credential;

public class TestJson {
	public static void main(String args[]){
		JSONObject json=new JSONObject(new Credential("yzj","123"));
		System.out.println(json);
		System.out.println(json.toString().length());
		StringEntity entity=new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
		System.out.println(entity);
	}
}
