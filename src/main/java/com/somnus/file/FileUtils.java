package com.somnus.file;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.HashMap;


/**
 * @author lyl
 */
@Slf4j
public class FileUtils {

    /**
     * 缓存文件头信息-文件头信息
     **/
    private static final HashMap<String, String> FILE_TYPES = Maps.newHashMap();

    static {
        // images
        FILE_TYPES.put("FFD8FF", "jpg");
        FILE_TYPES.put("89504E47", "png");
        FILE_TYPES.put("47494638", "gif");
        FILE_TYPES.put("49492A00", "tif");
        FILE_TYPES.put("424D", "bmp");
        //CAD
        FILE_TYPES.put("41433130", "dwg");
        FILE_TYPES.put("38425053", "psd");
        // 日记本
        FILE_TYPES.put("7B5C727466", "rtf");
        FILE_TYPES.put("3C3F786D6C", "xml");
        FILE_TYPES.put("68746D6C3E", "html");
        // 邮件
        FILE_TYPES.put("44656C69766572792D646174653A", "eml");
        FILE_TYPES.put("D0CF11E0", "doc");
        FILE_TYPES.put("5374616E64617264204A", "mdb");
        FILE_TYPES.put("252150532D41646F6265", "ps");
        FILE_TYPES.put("255044462D312E", "pdf");
        //Excel,ppt,doc
        FILE_TYPES.put("504B0304", "docx");
        FILE_TYPES.put("52617221", "rar");
        FILE_TYPES.put("57415645", "wav");
        FILE_TYPES.put("41564920", "avi");
        FILE_TYPES.put("2E524D46", "rm");
        FILE_TYPES.put("000001BA", "mpg");
        FILE_TYPES.put("000001B3", "mpg");
        FILE_TYPES.put("6D6F6F76", "mov");
        FILE_TYPES.put("3026B2758E66CF11", "asf");
        FILE_TYPES.put("4D546864", "mid");
        FILE_TYPES.put("1F8B08", "gz");
        FILE_TYPES.put("4D5A9000", "exe/dll");
        FILE_TYPES.put("75736167", "txt");
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param is 文件流
     * @return 文件头信息
     */
    public static String getFileHeader(InputStream is) {
        /*
         * int read() 从此输入流中读取一个数据字节。 int read(byte[] b) 从此输入流中将最多 b.length
         * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
         * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
         */
        byte[] b = new byte[4];
        try {
            is.read(b, 0, b.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytesToHexString(b);
    }

    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     *
     * @param src 要读取文件头信息的文件的byte数组
     * @return 文件头信息
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    /**
     * 判断是否是合法的文件类型
     *
     * @param fileType1 被判定的文件类型（未知类型）
     * @param fileType2 合法的文件的文件类型
     * @return 。
     */
    public static boolean isValidFileType(String fileType1, String fileType2) {
        if (fileType2.toUpperCase().equals(fileType1.toUpperCase())) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是合法的文件类型
     *
     * @param fileType  被判定的文件类型（未知类型）
     * @param fileTypes 合法的文件的文件类型数组
     * @return 。
     */
    public static boolean isValidFileType(String fileType, String[] fileTypes) {
        fileType = fileType.toLowerCase();
        for (String str : fileTypes) {
            if (str.toLowerCase().equals(fileType)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 通过文件路径，提取文件夹名。如home/bae/test.txt
     *
     * @param fullFileName 文件全名，包含路径
     * @return
     */
    public static String extractFileName(String fullFileName) {
        return fullFileName.substring(fullFileName.lastIndexOf(File.separator) + 1);
    }

    /**
     * 通过输入流获取文件类型,该方法用户串改文件后缀也能获取到真实的后缀
     *
     * @param is 文件输入流
     * @return
     */
    public static String getFileType(InputStream is) {
        String key = getFileHeader(is);
        log.debug("bytesToHexString key:" + key);
        return FILE_TYPES.get(key);
    }

    /**
     * 取得文件名获取文件类型，改方法用户串改文件名后缀，获取到的不是真实后缀
     *
     * @param fileName 文件名
     * @return
     */
    public static String getFileType(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return fileName;
        }
    }


    /**
     * 判断上传的文件是否是空文件
     *
     * @param orginalFile
     * @return
     */
//    public static boolean isEmptyFile(CommonsMultipartFile orginalFile) {
//        return orginalFile == null || orginalFile.isEmpty();
//    }

    /**
     * 取得文件名
     *
     * @param orginalFile
     * @return
     */
//    public static String getFileName(CommonsMultipartFile orginalFile) {
//        return orginalFile.getOriginalFilename();
//    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public static void remove(String fileName) {
        try {
            org.apache.commons.io.FileUtils.forceDelete(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得文件大小
     *
     * @param srcFile   文件资源
     * @param isDisplay 是否拥有显示，如果为true，则会自动转为bytes,KB,MB,GB等显示带单位。默认是bytes不带单位。
     * @return
     */
    public static String getFileSize(String srcFile, boolean isDisplay) {
        // File (or directory) to be moved
        File file = new File(srcFile);
        if (!file.exists() && !file.isFile()) {
            throw new RuntimeException("src file not exits!");
        }

        if (isDisplay) {
            return org.apache.commons.io.FileUtils.byteCountToDisplaySize(file.length());
        } else {
            //返回文件长度（bytes），不带单位。
            return String.valueOf(file.length());
        }
    }


    /**
     * 移动文件
     *
     * @param srcFile
     * @param destPath
     * @param destFile
     * @return
     */
    public static boolean move(String srcFile, String destPath, String destFile) {
        // File (or directory) to be moved
        File file = new File(srcFile);
        if (!file.exists()) {
            throw new RuntimeException("src file not exits!");
        }
        // Destination directory
        File dir = new File(destPath);
        dir.mkdirs();       //目录已存在返回false，否则返回true
        log.info("目录是否存在？ = " + dir.exists());
        log.info("能否创建目录？ = " + dir.mkdirs());
        System.out.println("目录是否存在？ = " + dir.exists());
        System.out.println("能否创建目录？ = " + dir.mkdirs());

        File newFile = new File(dir, destFile);
        log.info("destFile = " + destFile);
        log.info("newFile = " + newFile.getName());
        System.out.println("newFile.getName() = " + newFile.getName());
        if (newFile.exists()) {
            throw new RuntimeException("dest file already exits!");
        }

        // Move file to new directory
        boolean success = file.renameTo(newFile);

        // new File(srcFile).delete();
        return success;
    }

    public static File copyTo(File file, String directory, String filename) throws Exception {
        if (directory == null) {
            throw new Exception("error");
        }
        return copyTo(file, new File(directory), filename);
    }

    /**
     * 拷贝文件
     *
     * @param file
     * @param directory
     * @param filename
     * @return
     * @throws Exception
     */
    public static File copyTo(File file, File directory, String filename)
            throws Exception {
        if (filename == null || "".equals(filename)) {
            throw new Exception("error");
        }

        if (directory == null) {
            throw new Exception("error");
        }

        try {
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new Exception("Can not create the directory!");
                }
            }

            if (!directory.isDirectory()) {
                throw new Exception(
                        "The given direoctry name is not a directory!");
            }
        } catch (SecurityException se) {
            throw new Exception("error");
        }

        String pathname = directory.getAbsolutePath();

        if (!pathname.endsWith(File.separator)) {
            pathname += File.separator;
        }

        while (filename.startsWith(File.separator)) {
            filename = filename.substring(1);
        }

        return copyTo(file, new File(pathname + filename));
    }


    /**
     * 复制文件到一个新目录
     *
     * @param source 源文件
     * @param desc   目的文件
     * @return
     * @throws Exception
     */
    public static File copyTo(File source, File desc) throws Exception {
        if (source == null) {
            throw new Exception("The source file is null!");
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            if (!source.exists()) {
                throw new Exception("The source file does not exists!");
            }

            fis = new FileInputStream(source);
            fos = new FileOutputStream(desc);

            byte[] bufferBytes = new byte[4 * 1024];
            int bytesRead = 0;

            while (bytesRead != -1) {
                bytesRead = fis.read(bufferBytes);

                if (bytesRead > 0) {
                    fos.write(bufferBytes, 0, bytesRead);
                }

            }

            fos.close();
            fis.close();
            return desc;
        } catch (SecurityException se) {
            log.error("SecurityException", se);
            throw new Exception(se.getMessage());
        } catch (FileNotFoundException fe) {
            log.error("FileNotFoundException", fe);
            throw new Exception(fe);
        } catch (IOException ie) {
            log.error("IOException", ie);
            throw new Exception(ie);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }

                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                log.error("error", e);
            }
        }
    }

    public static File create(String filePath, String fileName, boolean isDelete)
            throws Exception {
        File file = new File(filePath + File.separator + fileName);

        if (file.exists()) {
            if (isDelete) {
                org.apache.commons.io.FileUtils.forceDelete(file);
                file.createNewFile();
            }
        } else {
            file.createNewFile();
        }

        return file;
    }

    public static File create(String filePath, String fileName)
            throws Exception {
        File file = new File(filePath);

        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(filePath + File.separator + fileName);

        if (file.exists()) {
            org.apache.commons.io.FileUtils.forceDelete(file);
        }

        file.createNewFile();

        return file;
    }


    /**
     * 对文件名进行重命名
     *
     * @param name    文件名
     * @param append  增加的后缀
     * @param isAfter 是否放在文件名末尾(是-末尾；否-文件名头)
     * @return
     */
    public static String rename(String name, String append, boolean isAfter) {
        String ret = "";
        int i = name.lastIndexOf(".");
        if (i == -1) {
            return name;
        } else {
            if (!isAfter) {
                ret = append + "_" + name;
            } else {
                ret = name.substring(0, i) + "_" + append + name.substring(i);
            }
        }

        return ret;
    }

    /**
     * 对文件名进行重命名
     *
     * @param name
     * @param append
     * @return
     */
    public static String rename(String name, String append) {
        return rename(name, append, true);
    }

    /**
     * 根据文件格式名对文件进行重命名
     *
     * @param name
     * @param fileType
     * @return
     */
    public static String changeType(String name, String fileType) {
        String ret = "";
        int i = name.lastIndexOf(".");
        if (i == -1) {
            ret = name + "." + fileType;
        } else {
            ret = name.substring(0, i) + "." + fileType;
        }
        return ret;
    }

    /**
     * 创建目录
     *
     * @param filePath 目录绝对路径
     */
    public static void createDirectory(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            org.apache.commons.io.FileUtils.forceMkdir(file);
        }
    }

    /**
     * 递归删除这个文件夹里面的所有东西（包括文件夹自己）
     *
     * @param directory 文件目录
     */
    public static void deleteDirectory(File directory) {
        try {
            org.apache.commons.io.FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 清空文件夹下的文件，但是不删除文件夹
     *
     * @param directory 文件目录
     */
    public static void cleanDirectory(File directory) {
        try {
            org.apache.commons.io.FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws Exception {
        System.out.println("脚本之家测试结果：");
//        String path = "C:\\Users\\J.com\\Desktop\\111.docx";
        String path = "C:\\Users\\J.com\\Desktop\\11.xlsx";
        InputStream in = new FileInputStream(new File(path));
        final String fileType = getFileType(in);
        System.out.println("fileType:" + fileType);
//        FileUtils.forceDelete(new File("C:\\Users\\J.com\\Desktop\\aa\\aa.txt"));//删除文件
//        FileUtils.cleanDirectory("C:\\Users\\J.com\\Desktop\\aa);//清空目录
        System.out.println(org.apache.commons.io.FileUtils.byteCountToDisplaySize(new File(path).length()));
        System.out.println(path.substring(path.lastIndexOf(File.separator) + 1));
        System.out.println(getFileType(".text"));
    }
}
