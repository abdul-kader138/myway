package org.hurryapp.quickstart.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * MD5 工具类
 * 
 * @author jiangshengkun 2012-12-25
 */
public class Md5Utils {
	byte[] buffer = null ;
	/**
	 * 获取压缩文件MD5值 David 2012-10-12
	 * 
	 */
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

			// System.err.println(GetFileMD5.class.getName()+"初始化失败，MessageDigest不支持MD5Util。");
			e.printStackTrace();
		}
	}
	/**
	 * 生成文件的md5校验值
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */

	public static String getMD5String(InputStream is) {
		byte[] buffer = new byte[1024*1024];
        String result = "";
        try {
            int numRead = 0;
    		while ((numRead = is.read(buffer)) > 0) {
	    		messagedigest.update(buffer, 0, numRead);
	    	}
	    	result = bufferToHex(messagedigest.digest());
        }
        catch(IOException e){
        }

        closeStreamNE(is);
        return result;
	}
	
	public static String getMD5String(String filePath) {
		try {
            FileInputStream is = new FileInputStream(filePath);
            return getMD5String(is);
        }
        catch(FileNotFoundException e) {
            return "";
        }
	}

	private static String bufferToHex(byte[] bytes) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int start, int length) {
		StringBuffer stringbuffer = new StringBuffer(2 * length);
		for (int i = start, n = start + length; i < n; ++i) {
			appendHexPair(bytes[i], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}

	public static boolean validateZipFile(String filePath) {
		boolean isIntegrity = true;
		ZipFile zf = null;
        InputStream is = null;
		try {
			zf = new ZipFile(filePath);
			Enumeration<ZipEntry> entryies = (Enumeration<ZipEntry>) zf.entries();
			while (entryies.hasMoreElements()) {
				ZipEntry entry = entryies.nextElement();
				if (!entry.isDirectory()) {
					CRC32 crc32 = new CRC32();
					is = zf.getInputStream(entry);
					int reads = 0;
                    byte[] buf = new byte[1024];
					while ((reads = is.read(buf)) > 0) {
						crc32.update(buf, 0, reads);
					}
					closeStreamNE(is);
					if (crc32.getValue() != entry.getCrc())
						isIntegrity = false;
				}
			}
		} catch (IOException e) {
			isIntegrity = false;
		}

        closeZipFileNE(zf);
        closeStreamNE(is);
		return isIntegrity;
	}

    private static void closeStreamNE(InputStream is) {
        try {
            if (is != null) is.close();
        } catch (IOException e) {
		}
    }

    private static void closeZipFileNE(ZipFile zf) {
        try {
            if (zf != null) zf.close();
        } catch (IOException e) {
		}
    }
}
