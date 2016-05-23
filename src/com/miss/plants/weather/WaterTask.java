package com.miss.plants.weather;

import com.qlf.plants.utils.IP;

import android.app.Activity;

import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.widget.Toast;


public class WaterTask extends AsyncTask<String, Void, String> {

	private ProgressDialog pdialog;
	private Activity context = null;
	public static String url;
	public static  String url_constant = IP.IpNative+"flowerService/flower/WaterFlowerServlet?";
    

	public WaterTask(Activity ctx) {
		this.context = ctx;
		pdialog = ProgressDialog.show(context, "正在连接浇花...", "系统正在处理您的请求");
	}

	@Override
	protected void onPostExecute(String result) {

		pdialog.dismiss();
		  if(FlowerHttpUtils.Success.equalsIgnoreCase(result)){
	        	Toast.makeText(context, "浇花成功!",Toast.LENGTH_LONG ).show();
	        }else{
	        	Toast.makeText(context, "网络有误",Toast.LENGTH_LONG ).show();
	        }

	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected String doInBackground(String... params) {
		url = url_constant + "level=" + params[0] ;
		return FlowerHttpUtils.connServerForResult(url);
	}

	@Override
	protected void onProgressUpdate(Void... values) {
	}

}
