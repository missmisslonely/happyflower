package com.qlf.plants.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.achartengine.GraphicalView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.qlf.plants.R;
import com.qlf.plants.bean.PosBean;
import com.qlf.plants.fragment.MyPlantsFragment;
import com.qlf.plants.graph.GraphUtils;
import com.qlf.plants.graph.StudentGradeMessage;
import com.qlf.plants.thread.BUGTerminalThread;
import com.qlf.plants.thread.DeleteTerminalThread;
import com.qlf.plants.thread.GetTerminalHistoryInfo;
import com.qlf.plants.thread.GetTerminalInfoThread;
import com.qlf.plants.thread.GetTerminalThread;
import com.qlf.plants.userdata.UserData;
import com.qlf.plants.view.DefinedScrollView;
import com.qlf.plants.view.SelectPicPopupWindow;
import com.qlf.plants.view.VerticalPager;

/**
 * 利用自定义的ScrollView加载视图来实现翻页效果，下面用页码和总页数标识当前的视图是第几屏
 * 
 * @author WANGXIAOHONG
 * 
 */
public class PlantInfoActivity extends Activity implements OnClickListener {

	public static RelativeLayout up;
	private static LinearLayout down_info;
	private RadioGroup rg_main_menu;
	private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
	private ImageView back;
	// 上下两个布局
	public View addview1 = null, addview2 = null;
	// 补光、浇水、加湿、通风
	private ImageView buguang, jiaoshui, jiashi, tongfeng;
	private Button change;

	SelectPicPopupWindow menuWindow;

	private static StudentGradeMessage sgm;
	private static Map<String, StudentGradeMessage> stuGradeMap;
	private static List<HashMap<String, StudentGradeMessage>> studentGradeList = new ArrayList<HashMap<String, StudentGradeMessage>>();
	private static GraphicalView charView;
	private static Map<String, Object> data;
	private static TextView temperature;
	private static TextView airhumidity;
	private static TextView soilmoisture;
	private static TextView illumination;
	private static TextView waterlevel;
	private TextView title;
	private ImageView update, delete;
	public static Context context;
	public static int type = 0;
	public static int time = 0;
	static ProgressDialog dialog;
	static int terId;
	static String certificate;
	static UserData userDate;

	static List<Map<String, Object>> historyList;

	public static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == GetTerminalInfoThread.SUCCESS) {
				data = (Map<String, Object>) msg.obj;
				if (Integer.parseInt(data.get("code").toString()) == 1) {
					if (dialog != null)
						dialog.dismiss();
					if (((Map<String, Object>) data.get("data"))
							.get("temperature") != null) {
						String temp = ((Map<String, Object>) data.get("data"))
								.get("temperature").toString();
						String air = ((Map<String, Object>) data.get("data"))
								.get("airhumidity").toString();
						String soil = ((Map<String, Object>) data.get("data"))
								.get("soilmoisture").toString();
						String ill = ((Map<String, Object>) data.get("data"))
								.get("illumination").toString();
						String water = ((Map<String, Object>) data.get("data"))
								.get("waterlevel").toString();
						System.out.println("OK------------------");
						temperature.setText(temp);
						airhumidity.setText(air);
						soilmoisture.setText(soil);
						illumination.setText(ill);
						waterlevel.setText(water);

						new GetTerminalHistoryInfo(handler, terId, userDate
								.getUserInfo().get("certificate").toString())
								.start();
					} else {
						Toast.makeText(PlantInfoActivity.context, "连接异常...",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(PlantInfoActivity.context,
							data.get("data").toString(), Toast.LENGTH_SHORT)
							.show();
				}
			} else if (msg.what == 0x123) {
				Toast.makeText(PlantInfoActivity.context, "请检查网络",
						Toast.LENGTH_LONG).show();
			} else if (msg.what == DeleteTerminalThread.DELETE_SUCCESS) {
				data = (Map<String, Object>) msg.obj;
				Toast.makeText(PlantInfoActivity.context,
						data.get("data").toString(), Toast.LENGTH_SHORT).show();
				MyPlantsFragment.my.update();
			} else if (msg.what == GetTerminalHistoryInfo.SUCCESS) {
				data = (Map<String, Object>) msg.obj;
				historyList = (List<Map<String, Object>>) data.get("list");
				initData();
				Toast.makeText(PlantInfoActivity.context, "获取历史信息成功",
						Toast.LENGTH_SHORT).show();
			}
			else if (msg.what == BUGTerminalThread.SUCCESS) {
				data = (Map<String, Object>) msg.obj;
				Toast.makeText(PlantInfoActivity.context, data.get("data").toString(),
						Toast.LENGTH_SHORT).show();
			}
			if (time != 0 && type != 0) {

			}
		};
	};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.plants_info_main);
		// setupView();
		context = this;
		Intent intent = getIntent();
		terId = intent.getIntExtra("id", 0);
		System.out.println("i=" + terId);

		initView1();
		initRadio();
		// LayoutParams params = new LayoutParams(dip2px(context,
		// 300), dip2px(context, 280));
		// params.gravity = Gravity.CENTER_HORIZONTAL;
		// params.topMargin = dip2px(context, 20);
		// charView = (GraphicalView) GraphUtils.getInstance().getLineChartView(
		// context, studentGradeList, "B");
		// down_info.addView(charView, params);

	}

	public void initRadio() {
		rg_main_menu = (RadioGroup) findViewById(R.id.rg_main_menu);
		radioButton1 = (RadioButton) findViewById(R.id.rb_main_tab_menu1);
		radioButton2 = (RadioButton) findViewById(R.id.rb_main_tab_menu2);
		radioButton3 = (RadioButton) findViewById(R.id.rb_main_tab_menu3);
		radioButton4 = (RadioButton) findViewById(R.id.rb_main_tab_menu4);
		rg_main_menu.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				System.out.println(checkedId);
				System.out.println(R.id.rb_main_tab_menu1);
				switch (checkedId) {
				case R.id.rb_main_tab_menu1:
					for(int i=0;i<historyList.size();i++){
						stuGradeMap = new HashMap<String, StudentGradeMessage>();
						sgm = new StudentGradeMessage();
						sgm.setName("1."+i);
						System.out.println("Temp--------------"+i+"--"+Integer.parseInt(historyList.get(i).get("temperature").toString()));
						sgm.setMath(Integer.parseInt(historyList.get(i).get("temperature").toString()));
						sgm.setChinese(89);
						sgm.setEnglish(80);
						sgm.setTotal(236);
						sgm.setNumChinese(5);
						sgm.setNumEnglish(21);
						sgm.setNumMath(23);
						sgm.setNumTotal(16);
						stuGradeMap.put("name", sgm);
						studentGradeList
							.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
						
					}
					break;
				case R.id.rb_main_tab_menu2:
					for(int i=0;i<historyList.size();i++){
						stuGradeMap = new HashMap<String, StudentGradeMessage>();
						sgm = new StudentGradeMessage();
						sgm.setName("1."+i);
						System.out.println("Temp--------------"+i+"--"+Integer.parseInt(historyList.get(i).get("temperature").toString()));
						sgm.setMath(Integer.parseInt(historyList.get(i).get("soilmoisture").toString()));
						sgm.setChinese(89);
						sgm.setEnglish(80);
						sgm.setTotal(236);
						sgm.setNumChinese(5);
						sgm.setNumEnglish(21);
						sgm.setNumMath(23);
						sgm.setNumTotal(16);
						stuGradeMap.put("name", sgm);
						studentGradeList
							.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
						
					}
					break;
				case R.id.rb_main_tab_menu3:
					for(int i=0;i<historyList.size();i++){
						stuGradeMap = new HashMap<String, StudentGradeMessage>();
						sgm = new StudentGradeMessage();
						sgm.setName("1."+i);
						System.out.println("Temp--------------"+i+"--"+Integer.parseInt(historyList.get(i).get("temperature").toString()));
						sgm.setMath(Integer.parseInt(historyList.get(i).get("airhumidity").toString()));
						sgm.setChinese(89);
						sgm.setEnglish(80);
						sgm.setTotal(236);
						sgm.setNumChinese(5);
						sgm.setNumEnglish(21);
						sgm.setNumMath(23);
						sgm.setNumTotal(16);
						stuGradeMap.put("name", sgm);
						studentGradeList
							.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
						
					}
					break;
				case R.id.rb_main_tab_menu4:
					for(int i=0;i<historyList.size();i++){
						stuGradeMap = new HashMap<String, StudentGradeMessage>();
						sgm = new StudentGradeMessage();
						sgm.setName("1."+i);
						System.out.println("Temp--------------"+i+"--"+Integer.parseInt(historyList.get(i).get("temperature").toString()));
						sgm.setMath(Integer.parseInt(historyList.get(i).get("illumination").toString()));
						sgm.setChinese(89);
						sgm.setEnglish(80);
						sgm.setTotal(236);
						sgm.setNumChinese(5);
						sgm.setNumEnglish(21);
						sgm.setNumMath(23);
						sgm.setNumTotal(16);
						stuGradeMap.put("name", sgm);
						studentGradeList
							.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
						
					}
					break;
				}
			}
		});
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	@SuppressLint("NewApi")
	private static void initData() {
		// TODO Auto-generated method stub
		for (int i = 0; i < historyList.size(); i++) {
			stuGradeMap = new HashMap<String, StudentGradeMessage>();
			sgm = new StudentGradeMessage();
			sgm.setName("1." + i);
			System.out.println("Temp--------------"
					+ i
					+ "--"
					+ Integer.parseInt(historyList.get(i).get("temperature")
							.toString()));
			sgm.setMath(Integer.parseInt(historyList.get(i).get("temperature")
					.toString()));
			sgm.setChinese(89);
			sgm.setEnglish(80);
			sgm.setTotal(236);
			sgm.setNumChinese(5);
			sgm.setNumEnglish(21);
			sgm.setNumMath(23);
			sgm.setNumTotal(16);
			stuGradeMap.put("name", sgm);
			studentGradeList
					.add((HashMap<String, StudentGradeMessage>) stuGradeMap);

		}
		LayoutParams params = new LayoutParams(dip2px(context, 300), dip2px(
				context, 280));
		params.gravity = Gravity.CENTER_HORIZONTAL;
		params.topMargin = dip2px(context, 20);
		charView = (GraphicalView) GraphUtils.getInstance().getLineChartView(
				context, studentGradeList, "B");
		down_info.addView(charView, params);

		RelativeLayout relativeLayout = new RelativeLayout(context);
		TextView textView = new TextView(context);
		textView.setText("今日趋势");
		textView.setTextColor(Color.WHITE);
		android.widget.RelativeLayout.LayoutParams layoutParams = new android.widget.RelativeLayout.LayoutParams(
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
				RelativeLayout.TRUE);
		relativeLayout.addView(textView, layoutParams);
		Button button = new Button(context);
		button.setBackground(context.getResources().getDrawable(
				R.drawable.history));
		android.widget.RelativeLayout.LayoutParams layoutParams1 = new android.widget.RelativeLayout.LayoutParams(
				dip2px(context, 40), dip2px(context, 25));
		layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				RelativeLayout.TRUE);
		relativeLayout.addView(button, layoutParams1);

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, HistoryInfoActivity.class);
				context.startActivity(intent);
			}
		});

		LayoutParams pParams = new LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		pParams.gravity = Gravity.CENTER_HORIZONTAL;
		pParams.topMargin = dip2px(context, 5);
		down_info.addView(relativeLayout, pParams);

		// stuGradeMap = new HashMap<String, StudentGradeMessage>();
		// sgm = new StudentGradeMessage();
		// sgm.setMath(67);
		// stuGradeMap.put("name", sgm);
		// studentGradeList
		// .add((HashMap<String, StudentGradeMessage>) stuGradeMap);
		// stuGradeMap = new HashMap<String, StudentGradeMessage>();
		// sgm = new StudentGradeMessage();
		// sgm.setMath(50);
		// stuGradeMap.put("name", sgm);
		// studentGradeList
		// .add((HashMap<String, StudentGradeMessage>) stuGradeMap);
		// stuGradeMap = new HashMap<String, StudentGradeMessage>();
		// sgm = new StudentGradeMessage();
		// sgm.setMath(60);
		// stuGradeMap.put("name", sgm);
		// studentGradeList
		// .add((HashMap<String, StudentGradeMessage>) stuGradeMap);
		// stuGradeMap = new HashMap<String, StudentGradeMessage>();
		// sgm = new StudentGradeMessage();
		// sgm.setMath(80);
		// stuGradeMap.put("name", sgm);
		// studentGradeList
		// .add((HashMap<String, StudentGradeMessage>) stuGradeMap);
		// stuGradeMap = new HashMap<String, StudentGradeMessage>();
		// sgm = new StudentGradeMessage();
		// sgm.setMath(90);
		// stuGradeMap.put("name", sgm);
		// studentGradeList
		// .add((HashMap<String, StudentGradeMessage>) stuGradeMap);
	}

	private void initView1() {
		up = (RelativeLayout) findViewById(R.id.up_info);
		down_info = (LinearLayout) findViewById(R.id.down_info);
		rg_main_menu = (RadioGroup) findViewById(R.id.rg_main_menu);
		temperature = (TextView) findViewById(R.id.temperature);
		airhumidity = (TextView) findViewById(R.id.airhumidity);
		soilmoisture = (TextView) findViewById(R.id.soilmoisture);
		illumination = (TextView) findViewById(R.id.illumination);
		waterlevel = (TextView) findViewById(R.id.waterlevel);
		title = (TextView) findViewById(R.id.actionbar_title);
		title.setText(MyPlantsFragment.getName(terId));
		userDate = new UserData(getApplicationContext());
		certificate = userDate.getUserInfo().get("certificate").toString();
		new GetTerminalInfoThread(handler, terId, userDate.getUserInfo()
				.get("certificate").toString()).start();

		// addAchart();

		back = (ImageView) findViewById(R.id.action_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		buguang = (ImageView) findViewById(R.id.buguang);
		jiaoshui = (ImageView) findViewById(R.id.jiaoshui);
		jiashi = (ImageView) findViewById(R.id.jiashi);
		tongfeng = (ImageView) findViewById(R.id.tongfeng);
		change = (Button) findViewById(R.id.button_change);
		update = (ImageView) findViewById(R.id.update);
		delete = (ImageView) findViewById(R.id.action_delete);

		update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = new ProgressDialog(PlantInfoActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("刷新中...");
				dialog.show();
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							new GetTerminalInfoThread(handler, terId, userDate
									.getUserInfo().get("certificate")
									.toString()).start();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						super.run();
					}
				}.start();
			}
		});

		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog();
			}
		});

		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Intent intent2 = new Intent(getApplicationContext(),
				// PlantMoreInfoActivity.class);
				// startActivity(intent2);
				// overridePendingTransition(R.anim.push_up_in,
				// R.anim.push_up_out);
			}
		});
		buguang.setOnClickListener(this);
		jiaoshui.setOnClickListener(this);
		jiashi.setOnClickListener(this);
		tongfeng.setOnClickListener(this);
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(PlantInfoActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				new DeleteTerminalThread(handler, terId, userDate.getUserInfo()
						.get("certificate").toString()).start();
				PlantInfoActivity.this.finish();
			}
		});
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getApplicationContext(), Pop.class);
		switch (v.getId()) {
		case R.id.buguang:
			type = 2;
			intent.putExtra("id", 1);
			startActivity(intent);
			break;
		case R.id.jiaoshui:
			type = 3;
			intent.putExtra("id", 2);
			startActivity(intent);
			break;
		case R.id.jiashi:
			type = 5;
			intent.putExtra("id", 3);
			startActivity(intent);
			break;
		case R.id.tongfeng:
			type = 4;
			intent.putExtra("id", 4);
			startActivity(intent);
			break;
		}
	}

	public void getPhoto() {
		// new GetTerminalInfo();
	}
}