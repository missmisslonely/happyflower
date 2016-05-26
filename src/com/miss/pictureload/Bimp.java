package com.miss.pictureload;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract.Contacts.Photo;

/**
 * ѹ��ͼƬ
 * 
 * @author Administrator
 * 
 */
public class Bimp {
	public static int max = 0;
	public static boolean act_bool = true;
	public static List<Bitmap> bmp = new ArrayList<Bitmap>();

	// ͼƬsd��ַ �ϴ�������ʱ��ͼƬ�������淽��ѹ���� ���浽��ʱ�ļ��� ͼƬѹ����С��100KB��ʧ��Ȳ�����
	public static List<String> drr = new ArrayList<String>();

	// TelephonyManager tm = (TelephonyManager) this
	// .getSystemService(Context.TELEPHONY_SERVICE);

	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// Bitmap btBitmap=BitmapFactory.decodeFile(path);
		// System.out.println("ԭ�ߴ�߶ȣ�"+btBitmap.getHeight());
		// System.out.println("ԭ�ߴ��ȣ�"+btBitmap.getWidth());
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 800)
					&& (options.outHeight >> i <= 800)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		// ������Ϊ����ʱͼƬ��ת
//		bitmap = Photo.photoAdapter(path, bitmap);
//		System.out.println("-----ѹ����ߴ�߶ȣ�" + bitmap.getHeight());
//		System.out.println("-----ѹ����ߴ��ȶȣ�" + bitmap.getWidth());
		return bitmap;
	}

	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis); // /����ת��ΪBitmapͼƬ

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param x
	 *            ͼ��Ŀ��
	 * @param y
	 *            ͼ��ĸ߶�
	 * @param image
	 *            ԴͼƬ
	 * @param outerRadiusRat
	 *            Բ�ǵĴ�С
	 * @return Բ��ͼƬ
	 */
	public static Bitmap createFramedPhoto(int x, int y, Bitmap image, float outerRadiusRat) {
		
		Drawable imageDrawable = new BitmapDrawable(image);

		
		Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		
		RectF outerRect = new RectF(0, 0, x, y);

		// ����һ����ɫ��Բ�Ǿ���
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

		// ��ԴͼƬ���Ƶ����Բ�Ǿ�����
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		imageDrawable.setBounds(0, 0, x, y);
		canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
		imageDrawable.draw(canvas);
		canvas.restore();

		return output;
	}
}
