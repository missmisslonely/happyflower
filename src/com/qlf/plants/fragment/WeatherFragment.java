package com.qlf.plants.fragment;
import com.miss.plants.view.CircleImageView;
import com.miss.plants.view.TrendView;
import com.miss.plants.weather.WeatherData;
import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.bean.Weather;
import com.qlf.plants.utils.IP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherFragment extends Fragment {
	View rootView;
	private TrendView view;
	private Weather weatherData;
	private TextView day1;
	private TextView day2;
	private TextView day3;
	private TextView day4;
	private TextView wea1;
	private TextView wea2;
	private TextView wea3;
	private TextView wea4;
	Bundle bundle;
	ImageView back;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater
				.inflate(R.layout.fragment_weather, container, false);
		back = (ImageView) rootView.findViewById(R.id.action_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.mLeftMenu.toggle();
			}
		});
		initView();
		// 设置天气曲线
		int screenWidth = getActivity().getWindowManager().getDefaultDisplay()
				.getWidth(); // 屏幕宽（像素，如：480px）
		// int screenHeight =
		int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
		view = (TrendView) rootView.findViewById(R.id.trendView);
		view.setWidthHeight(screenWidth, screenHeight);
		refresh();

		return rootView;
	}

	// 更新天气
	private void refresh() {
			// 得到天气情况
		WeatherTask task = new WeatherTask();
		task.execute(0);
	}

	public void initView() {
		day1 = (TextView) rootView.findViewById(R.id.day1);
		day2 = (TextView) rootView.findViewById(R.id.day2);
		day3 = (TextView) rootView.findViewById(R.id.day3);
		day4 = (TextView) rootView.findViewById(R.id.day4);
		wea1 = (TextView) rootView.findViewById(R.id.weather1);
		wea2 = (TextView) rootView.findViewById(R.id.weather2);
		wea3 = (TextView) rootView.findViewById(R.id.weather3);
		wea4 = (TextView) rootView.findViewById(R.id.weather4);

	}

	/**
	 * 异步查询天气
	 * 
	 * @author
	 * 
	 */
	class WeatherTask extends AsyncTask<Integer, Integer, Integer> {
		@Override
		protected Integer doInBackground(Integer... i) {// 处理后台执行的任务，在后台线程执行
			WeatherData data = new WeatherData(WeatherFragment.this);
			/*
			 * weatherData = data .getData(IP.IpWeather);
			 */
			// 测试
			weatherData = data
					.getData(IP.IpNative+"mp3/10015.html");
			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {// 后台任务执行完之后被调用，在ui线程执行
			// 更新界面控件值

			day1.setText("今天");
			day2.setText(weatherData.getDate().get(1));
			day3.setText(weatherData.getDate().get(2));
			day4.setText(weatherData.getDate().get(3));

			wea1.setText(weatherData.getWeather().get(0));

			wea2.setText(weatherData.getWeather().get(1));
			wea3.setText(weatherData.getWeather().get(2));
			wea4.setText(weatherData.getWeather().get(3));

			view.setTemperature(weatherData.getMaxlist(),
					weatherData.getMinlist());
			view.setBitmap(weatherData.getTopPic(), weatherData.getLowPic());

		}
	}
}
