package com.miss.plants.weather;


import com.qlf.plants.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class WeatherPic {
	public static Bitmap getPic(Context c, int index, int type) {
		Bitmap bmp = null;
		switch (index) {
		case 0:
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.a);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable._99);
			}
			break;
		case 1:
			if(type == 0){
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.b);
			}else{
				bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable._99);
			}
			break;

			
		case 2:

			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.c);
			break;
		case 3:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.c);
			break;
		case 4:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.d);
			break;
		case 5:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.e);
			break;
		case 6:

			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.b);

			break;
		case 7:

			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.b);

			break;
		case 8:

			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.b);

			break;
		case 9:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.f);
			break;
		case 10:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.g);
			break;
		case 11:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.o);
			break;
		case 12:

			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.p);

			break;
		case 13:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.q);
			break;
		case 14:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.r);
			break;
		case 15:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.e);
			break;
		case 16:

			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.j);

			break;
		case 17:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.o);

			break;
		case 18:

			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.p);

			break;
		case 19:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.q);
			break;
		case 20:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.r);
			break;
		case 21:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.s);
			break;
		case 22:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.t);
			break;
		case 23:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.u);
			break;
		case 24:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.v);
			break;
		case 25:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.w);
			break;
		case 26:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.x);
			break;
		case 27:
			bmp = BitmapFactory
					.decodeResource(c.getResources(), R.drawable._27);
			break;

		case 28:
			bmp = BitmapFactory
					.decodeResource(c.getResources(), R.drawable._28);
			break;
		case 29:
			bmp = BitmapFactory
					.decodeResource(c.getResources(), R.drawable._29);
			break;
		case 30:
			bmp = BitmapFactory
					.decodeResource(c.getResources(), R.drawable._30);
			break;
		case 31:
			bmp = BitmapFactory
					.decodeResource(c.getResources(), R.drawable._31);
			break;

		default:
			bmp = BitmapFactory.decodeResource(c.getResources(), R.drawable.c);
			break;
		}
		return bmp;
	}

	/*public static Bitmap getSmallPic(Context c, int index, int type) {
		return Bitmap.createScaledBitmap(getPic(c, index, type),
				Constants.picSize, Constants.picSize, true);
	}*/
	public static Bitmap getSmallPic(Context c, int index, int type) {
		return Bitmap.createScaledBitmap(getPic(c, index, type),
				100, 100, true);
	}
}
