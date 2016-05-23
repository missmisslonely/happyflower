package com.miss.plants.weather;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v4.app.Fragment;
import com.qlf.plants.bean.Weather;
import android.content.Context;
/**
 * 解析天气数据
 * @author 
 *
 */
public class WeatherData {
	private Fragment fragment;
	public WeatherData(Fragment fragment){
		this.fragment = fragment;
	}
	public Weather getData(String strUrl){
		return parseJson(connServerForResult(strUrl));
	}
	private String connServerForResult(String strUrl) {
		// 获取HttpGet对象
		HttpGet httpRequest = new HttpGet(strUrl);
		String strResult = "";
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
		System.out.println("rresult" + strResult);
		return strResult; // 返回结果
	}
	

	private Weather parseJson(String strResult) {
		Weather weather = null;
		try {
			JSONObject jsonObj = new JSONObject(strResult).getJSONObject("data");
			weather = new Weather();
			int temp = 0;	//偏移
			weather.setCity(jsonObj.getString("city"));	//城市
			weather.setRefreshDate(getDate());	//更新日期
			weather.setRefreshTime(getTime());	//更新时间
			weather.setRefreshWeek(getWeek());	//更新星期
			weather.setPicIndex(jsonObj.getInt("img_1"));	//当天天气图片编号
			
			List<Integer> topPic = new ArrayList<Integer>();	//最高温时的图片编号
			if(temp == 1){
				topPic.add(getSavePic(fragment));
			}else{
				topPic.add(getJsonPic(jsonObj, "img_", 1+temp));
				savePic(fragment, topPic.get(0));
			}
			topPic.add(getJsonPic(jsonObj, "img_", 3-temp));
			topPic.add(getJsonPic(jsonObj, "img_", 5-temp));
			topPic.add(getJsonPic(jsonObj, "img_", 7-temp));
			weather.setTopPic(topPic);
			
			List<Integer> lowPic = new ArrayList<Integer>();	//最低温时的图片编号
			lowPic.add(getJsonPic(jsonObj, "img_", 2-temp));
			lowPic.add(getJsonPic(jsonObj, "img_", 4-temp));
			lowPic.add(getJsonPic(jsonObj, "img_", 6-temp));
			lowPic.add(getJsonPic(jsonObj, "img_", 8-temp));
			weather.setLowPic(lowPic);
			
			List<String> tempList = new ArrayList<String>();	//未来四天温度
			tempList.add(jsonObj.getString("temp_1"));
			tempList.add(jsonObj.getString("temp_2"));
			tempList.add(jsonObj.getString("temp_3"));
			tempList.add(jsonObj.getString("temp_4"));
			
			List<String> tempListMax = new ArrayList<String>();		//未来四天最高温度集合（有℃符号）
			if(temp == 1){
				tempListMax.add(getSaveTemperature(fragment));
			}else{
				tempListMax.add(getTemperatureMaxAndMin(tempList.get(0))[0+temp]);
				saveTemperature(fragment, tempListMax.get(0));
			}
			tempListMax.add(getTemperatureMaxAndMin(tempList.get(1-temp))[0+temp]);
			tempListMax.add(getTemperatureMaxAndMin(tempList.get(2-temp))[0+temp]);
			tempListMax.add(getTemperatureMaxAndMin(tempList.get(3-temp))[0+temp]);
			weather.setTemperatureMax(tempListMax);		
			
			weather.setTodayTemperature(getTemperatureMaxAndMin(tempList.get(0))[0]);	//当天温度（实时）
			weather.setTodayWeather(jsonObj.getString("weather_1"));	//当天天气描述（实时）
			
			List<String> tempListMin = new ArrayList<String>();			//未来四天最低温度集合（有℃符号）
			tempListMin.add(getTemperatureMaxAndMin(tempList.get(0))[1-temp]);
			tempListMin.add(getTemperatureMaxAndMin(tempList.get(1))[1-temp]);
			tempListMin.add(getTemperatureMaxAndMin(tempList.get(2))[1-temp]);
			tempListMin.add(getTemperatureMaxAndMin(tempList.get(3))[1-temp]);
			weather.setTemperatureMin(tempListMin);
			
			weather.setTomorrowTemperature(tempList.get(1));	//明天温度（包括最高温和最低温）
			
			List<String> weatherList = new ArrayList<String>();	//未来四天天气
			if(temp == 1){
				weatherList.add(getSaveWeather(fragment));
			}else{
				weatherList.add(jsonObj.getString("weather_"+1));
				saveWeather(fragment, weatherList.get(0));
			}
			weatherList.add(jsonObj.getString("weather_"+(2-temp)));
			weatherList.add(jsonObj.getString("weather_"+(3-temp)));
			weatherList.add(jsonObj.getString("weather_"+(4-temp)));
			weather.setWeather(weatherList);
			weather.setTomorrowWeather(weatherList.get(1));
			
			List<String> dateList = new ArrayList<String>();	//未来四天日期
			if(temp == 1){
				dateList.add(getSaveWeather(fragment));
			}else{
				dateList.add(getsubDate(jsonObj.getString("date_"+1)));
				
			}
			dateList.add(getsubDate(jsonObj.getString("date_"+2)));
			dateList.add(getsubDate(jsonObj.getString("date_"+3)));
			dateList.add(getsubDate(jsonObj.getString("date_"+4)));
			weather.setDate(dateList);
			
			weather.setMaxlist(transplate(tempListMax));	//未来四天最高温度集合（无℃符号）
			weather.setMinlist(transplate(tempListMin));	//未来四天最低温度集合（无℃符号）
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return weather;
	}
	private String getsubDate(String Sdate){
		String Ddate = Sdate.substring(5);
		
		return Ddate;
	}
	private String getDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日 EEE", Locale.CHINA);  
	    String date=sdf.format(new java.util.Date()); 
	    System.out.println(date);
		return date;
	}
	private String getTime(){
	    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm", Locale.CHINA);  
	    String time=sdf.format(new java.util.Date()) + " " + "更新"; 
	    System.out.println(time);
		return time;
	}
	private String getWeek(){
		return null;
	}
	//获取最高温度和最低温度，有℃符号
	private String[] getTemperatureMaxAndMin(String str){
		return str.split("~");
	}
	//去除最高温度和最低温度里的℃符号
	private List<Integer> transplate(List<String> strList){
		List<Integer> intList = new ArrayList<Integer>();
		for(String temp : strList){
			intList.add(Integer.valueOf(temp.split("℃")[0]));
		}
		return intList;
	}
	//获取图片编号 例如"img_" + "1"
	private int getJsonPic(JSONObject jsonObj, String str, int index) throws JSONException{
		int result = jsonObj.getInt(str + index);
		if(result == 99 && index > 1){
			index --;
			result = jsonObj.getInt(str + index);
		}
		return result;
	}
	private void saveTemperature(Fragment fragment, String value){
		//MySharedPreferences mp = new MySharedPreferences(activity);
		//mp.writeMessage("temperature", value);
	}
	//保存的温度
	private String getSaveTemperature(Fragment fragment){
		MySharedPreferences mp = new MySharedPreferences(fragment);
		return mp.readMessage("temperature", "100");
	}
	private void saveWeather(Fragment fragment, String value){
		//MySharedPreferences mp = new MySharedPreferences(activity);
		//mp.writeMessage("weather", value);
	}
	//保存的天气
	private String getSaveWeather(Fragment fragment){
		MySharedPreferences mp = new MySharedPreferences(fragment);
		return mp.readMessage("weather", "");
	}
	private void savePic(Fragment fragment, int value){
		//MySharedPreferences mp = new MySharedPreferences(activity);
		//mp.writeMessage("pic", value);
	}
	//保存的天气图片编号
	private int getSavePic(Fragment fragment){
		MySharedPreferences mp = new MySharedPreferences(fragment);
		return mp.readMessage("pic", 99);
	}
}
