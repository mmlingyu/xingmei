/**   
 * Copyright © 2015 公司名. All rights reserved.
 * 
 * @Title: MediaView.java 
 * @Prject: CheYiPai_AndroidUpdate
 * @Package: com.cyp.p.activity.view 
 * @Description: TODO
 * @author: SHAOS   
 * @date: 2015-8-11 上午10:29:15 
 * @version: V1.0   
 */
package com.cheyipai.ui.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


import com.cheyipai.ui.R;
import com.cheyipai.ui.bean.FlagBase;
import com.cheyipai.ui.utils.BitmapUtil;
import com.cheyipai.ui.utils.DialogUtils;
import com.cheyipai.ui.utils.PathManagerBase;
import com.cheyipai.ui.utils.UriUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MediaView
 * @Description: 多媒体处理工具类
 * @author: SHAOS
 * @date: 2015-8-11 上午10:29:15
 */
@SuppressLint("SimpleDateFormat")
public class MediaView {
	private static Context ctx = null;
	private static MediaView instance = null;
	private final static String Path = PathManagerBase.SDCARD_FJ_PATH;// 附件存放的路径

	public synchronized static MediaView getInstance(Context context) {
		ctx = context;
		if (instance == null) {
			instance = new MediaView();
		}
		return instance;
	}

	/**
	 * @Title: takePhoto
	 * @Description: 拍照
	 * @param folder
	 *            文件夹
	 * @param fileName
	 *            文件名
	 * @return: void
	 */
	public void takePhoto(String folder, String fileName) {
		File file = new File(Path + folder + "/");
		if (!file.exists()) {
			file.mkdirs();
		}

		Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Path + folder + "/" + fileName + ".jpg")));
		photoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		photoIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, false);
		((Activity) ctx).startActivityForResult(photoIntent, FlagBase.MEDIA_PHOTO);
	}

	/**
	 * @Title: selectPhoto
	 * @Description: 选照
	 * @return: void
	 */
	public void selectPhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		// intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setAction(Intent.ACTION_PICK);
		intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		((Activity) ctx).startActivityForResult(intent, FlagBase.MEDIA_SPHOTO);
	}

	/**
	 * @Title: takePhoto
	 * @Description: 拍照
	 * @param folder
	 *            文件夹名称
	 * @param fileName
	 *            文件名称
	 * @param flag
	 * @return: void
	 */
	public void takePhoto(String folder, String fileName, int flag) {
		File file = new File(Path + folder + "/");
		if (!file.exists()) {
			file.mkdirs();
		}

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Path + folder + "/" + fileName + ".jpg")));
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		intent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, false);
		((Activity) ctx).startActivityForResult(intent, flag);
	}

	/**
	 * @Title: selectPhoto
	 * @Description: 选照
	 * @return: void
	 */
	public void selectPhoto(int flag) {
		Intent intent = new Intent();
		intent.setType("image/*");
		// intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setAction(Intent.ACTION_PICK);
		intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		((Activity) ctx).startActivityForResult(intent, flag);
	}

	/**
	 * @Title: getFolderName
	 * @Description: 获取文件夹名称
	 * @return
	 * @return: String
	 */
	public String getFolderName() {
		Date mDate = new Date();
		DateFormat mDateFormat = new SimpleDateFormat("MMddHHmmss");// 时间格式
		String folderName = mDateFormat.format(mDate);
		return folderName;
	}

	/**
	 * 传的参数没用
	 * 
	 * @param telephone
	 *            没用,白传
	 */
	@SuppressLint({ "NewApi", "InflateParams" })
	public void callTelePhone(String telephone) {
		OnClickListener off = new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		};
		OnClickListener on = new OnClickListener() {
			@Override
			public void onClick(View v) {

			}

		};


	}

	/**
	 * @ClassName: OnCallTelphoneListener
	 * @Description: 确认拨打电话
	 * @author: SHAOS
	 * @date: 2015-8-11 下午5:08:34
	 */
	class OnCallTelphoneListener implements DialogInterface.OnClickListener {
		Context context;
		String phonenumber;

		OnCallTelphoneListener(Context context, String phonenumber) {
			this.context = context;
			this.phonenumber = phonenumber;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenumber));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			dialog.dismiss();
		}
	}

	/**
	 * @ClassName: OnCanceListener
	 * @Description: 取消拨打
	 * @author: SHAOS
	 * @date: 2015-8-11 下午5:08:41
	 */
	class OnCanceListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}
	}

	/**
	 * 删除一个文件
	 * 
	 * @param file
	 * @return
	 */
	public boolean delFile(File file) {
		if (file.isDirectory())
			return false;
		return file.delete();
	}

	/**
	 * @Title: setSelectPhoto
	 * @Description: 设置选照
	 * @param data
	 * @param showIv
	 * @param folderName
	 * @param delIv
	 * @return: void
	 */
	public void setSelectPhoto(Intent data, ImageView showIv, String folderName, ImageView delIv) {
		Uri uri = data.getData();// 获取照片的原始地址
		String uriPath = UriUtil.getImageAbsolutePath((Activity) ctx, uri);
		if (!uriPath.endsWith(".jpg") && !uriPath.endsWith(".jpeg") && !uriPath.endsWith(".png")) {

			return;
		}
		if (uri != null && !TextUtils.isEmpty(uriPath)) {
			ContentResolver cr = ctx.getContentResolver();
			Bitmap mBitmap;
			try {
				InputStream is = cr.openInputStream(uri);
				byte[] len = MediaView.readInstream(is);
				if (len.length > 500 * 1024) {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = false;
					options.inSampleSize = 2;
					mBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
				} else {
					mBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				}

				if (mBitmap != null) {
					saveAngleBitmapPath(mBitmap, folderName, folderName, showIv, uri);
					delIv.setVisibility(View.VISIBLE);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * @Title: setSelectPhoto
	 * @Description: 设置选照
	 * @param data
	 * @param imageView
	 * @param flag
	 * @param folderName
	 *            文件夹名
	 * @return: void
	 */
	public void setSelectPhoto(Intent data, ImageView imageView, int flag, String folderName, ImageView delIv) {
		Uri uri = data.getData();// 获取照片的原始地址
		String uriPath = UriUtil.getImageAbsolutePath((Activity) ctx, uri);
		if (!TextUtils.isEmpty(uriPath) && !uriPath.toLowerCase().endsWith(".jpg")
				&& !uriPath.toLowerCase().endsWith(".jpeg") && !uriPath.toLowerCase().endsWith(".png")) {
			DialogUtils.showToast(ctx, ctx.getString(R.string.select_image_format));
			return;
		}
		if (uri != null && !TextUtils.isEmpty(uriPath)) {
			ContentResolver cr = ctx.getContentResolver();
			Bitmap mBitmap = null;
			try {
				InputStream is = cr.openInputStream(uri);
				byte[] len = readInstream(is);
				if (len.length > 5 * 1024 * 1024) {
					DialogUtils.showToast(ctx, ctx.getString(R.string.select_image_size));
					return;
				}
				if (len.length > 500 * 1024) {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = false;
					options.inSampleSize = 2;
					mBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
				} else {
					mBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				}
				if (mBitmap != null) {
					saveAngleBitmapPath(mBitmap, folderName, folderName + flag, imageView, uri);
					delIv.setVisibility(View.VISIBLE);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * @Title: setSelectPhoto2
	 * @Description: 设置选照
	 * @param data
	 * @param imageView
	 * @param flag
	 * @param folderName
	 * @return: void
	 */
	public void setSelectPhoto2(Intent data, ImageView imageView, int flag, String folderName) {
		Uri uri = data.getData();// 获取照片的原始地址
		if (uri != null) {
			try {
				Bitmap mBitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), uri);
				if (mBitmap != null) {
					saveBitmapPath(mBitmap, folderName, folderName + flag, imageView);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: setSelectPhoto3
	 * @Description: 设置选照
	 * @param data
	 * @param imageView
	 * @param flag
	 * @param folderName
	 * @return: void
	 */
	public void setSelectPhoto3(Intent data, ImageView imageView, int flag, String folderName) {
		Uri uri = data.getData();// 获取照片的原始地址
		BitmapFactory.Options bfOptions = new BitmapFactory.Options();
		bfOptions.inDither = false;
		bfOptions.inPurgeable = true;
		bfOptions.inInputShareable = true;
		bfOptions.inTempStorage = new byte[32 * 1024];
		try {
			String[] pojo = { MediaStore.Images.Media.DATA };
			@SuppressWarnings("deprecation")
			Cursor cursor = ((Activity) ctx).managedQuery(uri, pojo, null, null, null);
			if (cursor != null) {
				int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				String path = cursor.getString(colunm_index);// 获得图片的路径
				File sFile = new File(path);
				FileInputStream fis = new FileInputStream(sFile);
				if (path.endsWith("jpg") || path.endsWith("png") || path.endsWith("jpeg")) {
					Bitmap bitmapSel = BitmapFactory.decodeFileDescriptor(fis.getFD(), null, bfOptions);
					saveBitmapPath(bitmapSel, folderName, folderName + flag, imageView);
				} else {
					DialogUtils.showToast(ctx, "请上传JPG、PNG或JPEG图片！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: readInstream
	 * @Description: 输入流转字节数组
	 * @param inputStream
	 * @return
	 * @throws Exception
	 * @return: byte[]
	 */
	public static byte[] readInstream(InputStream inputStream) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, length);
		}
		baos.close();
		inputStream.close();
		return baos.toByteArray();
	}

	/**
	 * @Title: setImageAngel
	 * @Description: 设置图片显示角度
	 * @param mImageCaptureUri
	 * @param ctx
	 * @return
	 * @return: Bitmap
	 */
	public Bitmap setImageAngel(Uri mImageCaptureUri, Context ctx) {
		// 不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值
		// 所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看
		ContentResolver cr = ctx.getContentResolver();
		Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找
		if (cursor != null) {
			cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了
			String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路
			String orientation = cursor.getString(cursor.getColumnIndex("orientation"));// 获取旋转的角度
			cursor.close();
			if (filePath != null) {
				// Bitmap bitmap = BitmapFactory.decodeFile(filePath);//
				// 根据Path读取资源图片
				Bitmap bitmap = BitmapUtil.createImageThumbnail(filePath);
				int angle = 0;
				if (orientation != null && !"".equals(orientation)) {
					angle = Integer.parseInt(orientation);
				}
				if (angle != 0) {
					// 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
					Matrix m = new Matrix();
					int width = bitmap.getWidth();
					int height = bitmap.getHeight();
					m.setRotate(angle); // 旋转angle度
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
					return bitmap;
				}
			}
		}
		return null;
	}

	/**
	 * @Title: setImageAngel
	 * @Description: 设置图片显示角度
	 * @param mImageCaptureUri
	 * @param filePath
	 * @param ctx
	 * @return
	 * @return: Bitmap
	 */
	public Bitmap setImageAngel(Uri mImageCaptureUri, String filePath, Context ctx) {
		// 不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值
		// 所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看
		ContentResolver cr = ctx.getContentResolver();
		Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找
		if (cursor != null) {
			cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了
			String orientation = cursor.getString(cursor.getColumnIndex("orientation"));// 获取旋转的角度
			cursor.close();
			if (filePath != null) {
				// 根据Path读取资源图片
				Bitmap bitmap = BitmapFactory.decodeFile(filePath);

				int angle = 0;
				if (orientation != null && !"".equals(orientation)) {
					angle = Integer.parseInt(orientation);
				}
				if (angle != 0) {
					// 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
					Matrix m = new Matrix();
					int width = bitmap.getWidth();
					int height = bitmap.getHeight();
					m.setRotate(angle); // 旋转angle度
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
					saveBitmap(bitmap, filePath);
					return bitmap;
				} else {
					return bitmap;
				}
			}
		}
		return null;
	}

	/**
	 * @Title: saveBitmap
	 * @Description: 保存Bitmap
	 * @param b
	 * @param filePath
	 * @return: void
	 */
	public void saveBitmap(Bitmap bitmap, String filePath) {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			if (bitmap == null) {
				return;
			}
			FileOutputStream fos = null;

			try {
				File f = new File(filePath);
				if (f.exists()) {
					f.delete();
				}
				fos = new FileOutputStream(f);
				if (null != fos) {
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
					fos.flush();
					fos.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: saveBitmapPath
	 * @Description: 保存并压缩图片到指定的路径下
	 * @param bitmap
	 * @param folder
	 *            文件夹
	 * @param fileName
	 *            文件名
	 * @param imageView
	 * @return: void
	 */
	public void saveBitmapPath(Bitmap bitmap, String folder, String fileName, ImageView imageView) {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {

			File dir = new File(Path + folder + "/");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(dir, fileName + ".jpg");
			FileOutputStream fileOutputStream = null;

			try {
				fileOutputStream = new FileOutputStream(file);
				if (bitmap != null) {
					// 如果不压缩是100，表示压缩率为0
					if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
						try {
							String filePath = Path + folder + "/" + fileName + ".jpg";
							Bitmap mBitmap = BitmapUtil.createImageThumbnail(filePath);
							imageView.setImageBitmap(mBitmap);
							fileOutputStream.flush();
						} catch (IOException e) {
							file.delete();
							e.printStackTrace();
						}
					}
				}
			} catch (FileNotFoundException e) {
				file.delete();
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @Title: saveAngleBitmapPath
	 * @Description: 旋转图片保存
	 * @param bitmap
	 * @param folder
	 * @param fileName
	 * @param imageView
	 * @param uri
	 * @return: void
	 */
	public void saveAngleBitmapPath(Bitmap bitmap, String folder, String fileName, ImageView imageView, Uri uri) {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {

			String filePath = Path + folder + "/" + fileName + ".jpg";

			ContentResolver cr = ctx.getContentResolver();
			Cursor cursor = cr.query(uri, null, null, null, null);// 根据Uri从数据库中找
			if (cursor != null) {
				cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了
				String orientation = cursor.getString(cursor.getColumnIndex("orientation"));// 获取旋转的角度
				cursor.close();
				if (filePath != null) {
					int angle = 0;
					if (orientation != null && !"".equals(orientation)) {
						angle = Integer.parseInt(orientation);
					}
					if (angle != 0) {
						// 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
						Matrix m = new Matrix();
						int width = bitmap.getWidth();
						int height = bitmap.getHeight();
						m.setRotate(angle); // 旋转angle度
						bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
					}

					File dir = new File(Path + folder + "/");
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File file = new File(dir, fileName + ".jpg");
					FileOutputStream fileOutputStream = null;

					try {
						fileOutputStream = new FileOutputStream(file);
						if (null != fileOutputStream) {
							bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
							Bitmap mBitmap = BitmapUtil.createImageThumbnail(filePath);
							imageView.setImageBitmap(mBitmap);
							fileOutputStream.flush();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							fileOutputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * @Title: loadImageDegree
	 * @Description: 读取图片角度
	 * @param filePath
	 *            图片绝对路径
	 * @return
	 * @return: int
	 */
	public int loadImageDegree(String filePath) {
		int degree = 0;
		try {
			ExifInterface mExifInterface = new ExifInterface(filePath);
			int orientation = mExifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * @Title: rotateBitmap
	 * @Description:旋转图片
	 * @param mBitmap
	 * @param degree
	 * @return
	 * @return: Bitmap
	 */
	public Bitmap rotateBitmap(Bitmap mBitmap, int degree) {
		if (mBitmap != null) {
			Matrix mMatrix = new Matrix();
			mMatrix.postRotate(degree);
			mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mMatrix, true);
			return mBitmap;
		}
		return mBitmap;
	}
}
