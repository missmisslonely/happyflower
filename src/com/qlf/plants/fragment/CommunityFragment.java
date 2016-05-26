package com.qlf.plants.fragment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.miss.plants.adapter.CommunityInforAdapter;
import com.miss.plants.publish.PublishActivity;
import com.miss.plants.view.CustomProgressDialog;
import com.miss.plants.view.XListView;
import com.miss.plants.view.XListView.IXListViewListener;
import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.bean.IndexInfor;
import com.qlf.plants.utils.HttpUtils;
import com.qlf.plants.utils.IP;
import com.qlf.plants.utils.JSONUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class CommunityFragment extends Fragment implements IXListViewListener{
	View rootView;
	private CustomProgressDialog progressDialog = null;
	private XListView listView;
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	CommunityInforAdapter adapter;
	private Handler mHandler;
	private int pageNum = 1;
	ImageView back;
	Button edit;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater
				.inflate(R.layout.fragment_community, container, false);
		back = (ImageView) rootView.findViewById(R.id.action_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.mLeftMenu.toggle();
			}
		});
		edit = (Button) rootView.findViewById(R.id.edittext);
		edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						PublishActivity.class);
				startActivity(intent);
			}
		});
		listView = (XListView)rootView.findViewById(R.id.activity_index_listView);
		findView();
		mHandler = new Handler();
		refresh();
		return rootView;
	}

	

	public void refresh() {
		IndexInforTask indexInforTask = new IndexInforTask(getActivity());
		indexInforTask.execute("1");
	}

	private void startProgressDialog() {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(getActivity());
			
		}

		progressDialog.show();
	}

	private void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	class IndexInforTask extends AsyncTask<String, Void, String> {
		Context context;
	
		public IndexInforTask(Activity ctx) {
			this.context = ctx;
			startProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {// 处理后台执行的任务，在后台线程执行
			String url;
			String url_constant = IP.IpNative + "XuptMarket/viewlatestcl?";
			url = url_constant + "page=" + params[0];
			String result = HttpUtils.queryStringForGet(url);

			return result;
		}

		@Override
		protected void onPostExecute(String result) {// 后台任务执行完之后被调用，在ui线程执行

			List<IndexInfor> listIndexInfor = JSONUtil
					.parseJsonToIndexInfo(result);
			list = getList(listIndexInfor);
			adapter = new CommunityInforAdapter(getActivity(), list);

			listView.setAdapter(adapter);
			stopProgressDialog();

		}

	}

	public ArrayList<HashMap<String, String>> getList(
			List<IndexInfor> listIndexInfor) {
		ArrayList<HashMap<String, String>> list;

		list = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> map;

		for (int i = 0; i < listIndexInfor.size(); i++) {
			map = new HashMap<String, String>();
			String uri0 = null;
			String uri1 = "";
			String uri2 = "";
			if (listIndexInfor.get(i).list[0] != null) {

				uri0 = listIndexInfor.get(i).list[0];
				if (listIndexInfor.get(i).list.length > 1) {
					uri1 = listIndexInfor.get(i).list[1];
					System.out.println("第二张照片地址" + uri1);
					uri2 = listIndexInfor.get(i).list[2];
				}

				map.put("icon", uri0);
				map.put("icon1", uri1);
				map.put("icon2", uri2);
				map.put("bookName", listIndexInfor.get(i).getGoodsName());
				map.put("bookIntroduce", listIndexInfor.get(i)
						.getGoodsDescribe());
				map.put("bookMoney", "￥"
						+ listIndexInfor.get(i).getGoodsPrice()+"元");

				map.put("goodsConnect", listIndexInfor.get(i).getGoodsConnect());
				list.add(map);
			}
		}
		return list;
	}

	// 下拉刷新需要再次请求最新一页
	@Override
	public void onRefresh() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				refresh();
				onLoad();
			}
		});
	}

	@Override
	public void onLoadMore() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				geneItems();

				adapter = new CommunityInforAdapter

				(getActivity(), list);
				listView.setAdapter(adapter);
				onLoad();
			}
		});

	}

	// 分页加载数据
	public class MyThread extends Thread {
		public void run() {
			String url;
			String url_constant = IP.IpNative +

			"XuptMarket/viewlatestcl?";
			url = url_constant + "page=" + pageNum++;
			String result = HttpUtils.queryStringForGet(url);

			List<IndexInfor> listIndexInfor = JSONUtil
					.parseJsonToIndexInfo(result);
			ArrayList<HashMap<String, String>> list1 = getList(listIndexInfor);
			if (list != null) {
				list.addAll(list1);
			}

		}
	}

	private void geneItems() {
		new MyThread().start();

	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日    HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		listView.setRefreshTime(str);
	}
	private void findView() {
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setOnItemClickListener(new ListViewListener());

	}
	class ListViewListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			System.out.println("test");
		}

	}

	

	
}
