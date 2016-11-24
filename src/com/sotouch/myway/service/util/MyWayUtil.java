package com.sotouch.myway.service.util;


public class MyWayUtil {

	public static boolean isImage(String fileName) {
		String fileExtension = fileName.substring(fileName.lastIndexOf('.')+1).toUpperCase();
		if ("JPG".equals(fileExtension) || "JPEG".equals(fileExtension) || "PNG".equals(fileExtension) || "GIF".equals(fileExtension)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isVideo(String fileName) {
		String fileExtension = fileName.substring(fileName.lastIndexOf('.')+1).toUpperCase();
		if ("MOV".equals(fileExtension) || "FLV".equals(fileExtension) || "MP4".equals(fileExtension)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isFlash(String fileName) {
		String fileExtension = fileName.substring(fileName.lastIndexOf('.')+1).toUpperCase();
		if ("SWF".equals(fileExtension)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isZip(String fileName) {
		String fileExtension = fileName.substring(fileName.lastIndexOf('.')+1).toUpperCase();
		if ("ZIP".equals(fileExtension)) {
			return true;
		}
		else {
			return false;
		}
	}
}
