package com.kit.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializTool {
	public static byte[] serializToByte(Object sClass, String path) {
		ObjectOutputStream out;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			out = new ObjectOutputStream(byteArrayOutputStream);
			// 序列化对象
			out.writeObject(sClass);
			out.flush();
			out.close();
			return byteArrayOutputStream.toByteArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Object unSerializFromByte(byte[] by) {
		// 反序列化对象
		Object obj3 = null;
		try {
			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(by));
			obj3 = in.readObject();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj3;
	}

	public static void serializ(Object sClass, String path) {
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(path));
			// 序列化对象
			out.writeObject(sClass);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Object unSerializ(String path) {
		// 反序列化对象
		Object obj3 = null;
		try {
			ObjectInputStream in = new ObjectInputStream

			(new FileInputStream(path));
			obj3 = in.readObject();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj3;
	}

}