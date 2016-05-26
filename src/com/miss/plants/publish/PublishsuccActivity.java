package com.miss.plants.publish;


import com.qlf.plants.MainActivity;
import com.qlf.plants.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class PublishsuccActivity extends Activity {

	Button butsee;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publishsucc);
	}

	public void backHome(View source) {
		Intent intent = new Intent(PublishsuccActivity.this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}

	public void sucessBack(View source) {
		this.onBackPressed();
	}

}