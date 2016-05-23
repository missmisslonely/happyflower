package com.miss.plants.weather;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

public class MySharedPreferences {
	private Fragment fragment;
	
	public MySharedPreferences(Fragment fragment){
		this.fragment = fragment;
	}
	/**
	 * 读取对应的键值
	 * @param key
	 * @return String
	 */
	public int readMessage(String key, int value) {
		//获得当前的SharedPreferences对象
		SharedPreferences message = fragment.getActivity().getPreferences(Activity.MODE_PRIVATE);
		//获取消息
		int tmp = message.getInt(key, value);
		return tmp;
	}
	/**
	 * 将键值对写入配置文件
	 * @param key
	 * @param value
	 */
	public void writeMessage(String key, int value) {
		//创建一个SharedPreferences对象
		SharedPreferences message = fragment.getActivity().getPreferences(0);
		//编辑SharedPreferences对象
		SharedPreferences.Editor editor = message.edit();
		//插入一个数据
		editor.putInt(key, value);
		//提交数据
		editor.commit();
	}
	public String readMessage(String key, String value) {
		SharedPreferences message = fragment.getActivity().getPreferences(Activity.MODE_PRIVATE);
		String text = message.getString(key, value);
		return text;
	}
	public void writeMessage(String key, String value) {
		SharedPreferences message = fragment.getActivity().getPreferences(0);
		SharedPreferences.Editor editor = message.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
}

