package com.miss.plants.weather;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
public class FlowerHttpUtils {
	private Activity activity;
	public static final String Success="Success";
	public static final String False="False";
	public FlowerHttpUtils(Activity activity){
		this.activity = activity;
	}
	
	public static String connServerForResult(String url) {
		// 获取HttpGet对象
		String strResult = null;
		HttpGet httpRequest = new HttpGet(url);
		
		try {
			// HttpClient对象
			HttpClient httpClient = new DefaultHttpClient();
			// 获得HttpResponse对象
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取得返回的数据
				strResult = EntityUtils.toString(httpResponse.getEntity(), "GBK");
			}
		} catch (ClientProtocolException e) {
			System.out.println("网络连接有误1");
		} catch (IOException e) {
			System.out.println("网络连接有误2");
			e.printStackTrace();
		}
		
		System.out.println("结果" + strResult);
		return strResult; // 返回结果
	}
	

	
	
	
	

}
