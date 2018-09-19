/**
 *
 */
package com.cheyipai.ui.utils;

import android.os.Environment;

/**
 * @ClassName: PathManagerBase
 * @Description: TODO
 * @author: SHAOS
 * @date: 2016-2-26 上午9:46:02
 */
public class PathManagerBase {

	/**
	 * 移动端存放头像的本地路径，即attachment
	 */
	public static final String SDCARD_FJ_PATH = Environment
			.getExternalStorageDirectory() + "/.attachment/";

	/**
	 * 移动端缓存图片的本地路径
	 */
	public static final String SDCARD_IMAGE_PATH = SDCARD_FJ_PATH
			+ "cypImage/";

	/**
	 * 移动端批量下载图片路径
	 */
	public static final String SDCARD_BATCH_IMAGE_PATH = SDCARD_FJ_PATH
			+ "batch/";
}
