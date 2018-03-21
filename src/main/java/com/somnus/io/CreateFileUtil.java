package com.somnus.io;

import java.io.File;
import java.io.IOException;

/**
 * 如何创建目录和文件
 * @Title: CreateFileUtil.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午4:56:27 
 * @version V1.0
 */
public class CreateFileUtil {
	/**
	 * 创建目录
	 * @param destDirName 目录路劲
	 * @return Boolean
	 */
	public static boolean createDir(String destDirName){
        File dir = new File(destDirName);
        //如果文件夹存在
        if (dir.exists()){
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        //创建目录
        if (dir.mkdirs()){
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } 
        else{
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }
	/**
	 * 创建文件
	 * @param destFileName 文件路劲
	 * @return Boolean
	 */
	public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if(file.exists()){
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)){
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()){
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if(!file.getParentFile().mkdirs()){
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()){
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } 
            else{
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } 
        catch (IOException e){
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }
   
   
    public static String createTempFile(String prefix, String suffix, String dirName){
        File tempFile = null;
        if (dirName == null){
            try{
                //在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                //返回临时文件的路径
                return tempFile.getCanonicalPath();
            } 
            catch (IOException e){
                e.printStackTrace();
                System.out.println("创建临时文件失败！" + e.getMessage());
                return null;
            }
        } 
        else{
            File dir = new File(dirName);
            //如果临时文件所在目录不存在，首先创建
            if (!dir.exists()){
                if (!CreateFileUtil.createDir(dirName)){
                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try{
                //在指定目录下创建临时文件
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            } 
            catch (IOException e){
                e.printStackTrace();
                System.out.println("创建临时文件失败！" + e.getMessage());
                return null;
            }
        }
    }
   
    public static void main(String[] args) {
        //创建目录
        String dirName = "D:/work/temp/temp0/temp1";
        CreateFileUtil.createDir(dirName);
        //创建文件
        String fileName = dirName + "/temp2/tempFile.txt";
        CreateFileUtil.createFile(fileName);
        //创建临时文件
        String prefix = "temp";
        String suffix = ".txt";
        for (int i = 0; i < 10; i++){
            System.out.println("创建了临时文件："+ CreateFileUtil.createTempFile(prefix, suffix, dirName));
        }
        //在默认目录下创建临时文件
        for (int i = 0; i < 10; i++){
            System.out.println("在默认目录下创建了临时文件："+ CreateFileUtil.createTempFile(prefix, suffix, null));
        }
    }

}