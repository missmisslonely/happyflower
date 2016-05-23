package com.qlf.plants.utils;

import com.qlf.plants.R;

import android.content.Context;




public class Constants {
	public static int picSize;
	public Constants(Context c){
		picSize = (int) c.getResources().getDimension(R.dimen.picSize);
		System.out.println(picSize);
	}
  
}
