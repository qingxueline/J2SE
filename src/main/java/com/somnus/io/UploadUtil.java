package com.somnus.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


/**
 *上传文件帮助类
 * @Title: UploadUtil.java
 * @Package com.somnus.io
 * @Description: TODO
 * @author lyl
 * @date 2017年5月28日 下午4:59:48
 * @version V1.0
 */
public final class UploadUtil {

    /**
     * 取得真实文件名称（即：不含路径）
     * @param realFileName
     * @return
     * @throws Exception
     */
	public static String getRealFileName(String realFileName) throws Exception {
		if (realFileName == null) {
			throw new Exception();
		}
        // 返回指定子字符串在此字符串中最右边出现处的索引
		int index = realFileName.lastIndexOf("\\");
		if (index >= 0) {
            // 返回一个新的字符串，它是此字符串的一个子字符串。该子字符串从指定索引处的字符开始，直到此字符串末尾(起始索引（包括）。
			realFileName = realFileName.substring(index + 1);
		}
		return realFileName;
	}

    /**
     * 保存上传的文件到服务端指定的目录下
     * @param inputStream
     * @param uploadPath
     * @param realFileName
     * @throws Exception
     */
	public static void save(InputStream inputStream, String uploadPath,
			String realFileName) throws Exception {

        // 取得上传文件的流
		OutputStream outputStream = null;
		try {
            //取得新的真实真实文件路径
			String uuidRealFileName = makeUuidRealFileName(realFileName);
            //创建多级文件目录
			String makeUuidUploadPath = makeUuidUploadPath(uploadPath, realFileName);
			outputStream = new FileOutputStream(makeUuidUploadPath + "/" + uuidRealFileName);
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				outputStream.close();
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception();
			}
		}
	}


    /**
     * 通过UUID产生真实文件名
     * @param realFileName
     * @return
     * @throws Exception
     */
	public static String makeUuidRealFileName(String realFileName) throws Exception{
		if (realFileName == null) {
			throw new Exception();
		}
        //通过产生UUID后，与原来的真实文件路径做拼接，产生新的文件真实路径
		return UUID.randomUUID().toString()+"_"+realFileName;
	}

    //自动创建多级目录
	public static String makeUuidUploadPath(String uploadPath,String uuidRealFileName){
        //取得uuidRealFileName的整形值
		int code= uuidRealFileName.hashCode();
        //取得第一级子目录
		int dir1= code & 0XF;
        //取得第二级子目录
		int dir2 = code >> 4 & 0XF;
        //拼接成字符串（目录路径）
		String uuidUploadPath = uploadPath+"/"+dir1+"/"+dir2;
        //封装成File
		File file = new File(uuidUploadPath);
        //判断改目录是否存在
		if (!file.exists()) {
            //一次性创建目录
			file.mkdirs();
		}
        //将抽象路径转换为字符串返回
		return file.getPath();
	}
}
