package com.kit.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * 压缩文件
 * @author PengX
 * @createDate 2016-3-2
 */
public class ZipUtil {
	private static final int BUFFER = 2048;
	
	public static void main(String[] args) throws Exception {
		String zipFileName = "E:/tmp/test.zip";
		String files = "E:/tmp/2016_03_02_04_10_50";
		zip(files, zipFileName);
	}
	
	/**
	 * 压缩指定文件
	 * @param files 需要压缩的文件路径及文件名，可以是文件夹、具体文件
	 * @param zipFileName zip文件路径及文件名
	 * @return 是否压缩成功，true：成功，false：失败
	 * @throws Exception
	 */
	public static boolean zip(String files, String zipFileName) throws Exception{
		boolean isSuccessed = true;
		BufferedInputStream origin = null;
		FileOutputStream dest = null;
		ZipOutputStream out = null;
		try {
			dest = new FileOutputStream(zipFileName);
			out = new ZipOutputStream(new BufferedOutputStream(dest));
			byte data[] = new byte[BUFFER];
			File f = new File(files);
			File[] fileList = f.listFiles();

			for (int i = 0; i < fileList.length; i++) {
//				System.out.println("Adding: " + fileList[i]);
				FileInputStream fi = new FileInputStream(fileList[i]);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(fileList[i].getName());
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (Exception e) {
			isSuccessed = false;
			throw e;
		}finally{
			try {
				origin.close();
			} catch (Exception e2) {}
			try {
				out.close();
			} catch (Exception e2) {}
			try {
				dest.close();
			} catch (Exception e2) {}
		}
		return isSuccessed;
	}
}
