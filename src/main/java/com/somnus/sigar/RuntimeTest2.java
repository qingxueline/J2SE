package com.somnus.sigar;

import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author lyl
 * @version V1.0
 * @project J2SE
 * @package com.somnus.sigar
 * @date 2018/05/21 10:19
 * @description
 */
@Slf4j
public class RuntimeTest2 {

    /**
     * 加载Sigar环境变量
     **/
    public static void initSigarLibraryPath() {
        String sigarLibPath = null;
        //此处只为得到依赖库文件的目录，可根据实际项目自定义
        String configPath = Resources.getResource("sigar/.sigar_shellrc").getFile();
        try {
            configPath = java.net.URLDecoder.decode(configPath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("编码异常", e);
            return;
        }

        File classPath = new File(configPath).getParentFile();
        String path = System.getProperty("java.library.path");
        try {
            sigarLibPath = classPath.getCanonicalPath();
        } catch (Exception e) {
            log.error("获取Sigar系统监控目录路径异常", e);
            return;
        }
        //为防止java.library.path重复加，此处判断了一下
        if (!path.contains(sigarLibPath)) {
            //将Sigar变量并接到环境变量中
            if (isOSWin()) {
                path = String.format("%s;%s", path, sigarLibPath);
            } else {
                path = String.format("%s:%s", path, sigarLibPath);
            }
            log.info("java.library.path：" + path);
            System.setProperty("java.library.path", path);
        }

    }

    /**
     * 操作系统判断
     **/
    private static boolean isOSWin() {//OS 版本判断
        String os = System.getProperty("os.name").toLowerCase();
        log.info("operating system：" + os);
        return os.contains("win");
    }

    public static void main(String[] args) {
        try {
            initSigarLibraryPath();
            property();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void property() throws UnknownHostException, SigarException {

//      设备ip、设备类型（CPU/硬盘/内存）、告警信息描述（如：硬盘sdb使用比率已经92%，或者CPU使用率已达98等）、告警时间
        Runtime r = Runtime.getRuntime();
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        CpuPerc cpu = sigar.getCpuPerc();

        InetAddress addr;
        addr = InetAddress.getLocalHost();
        Map<String, String> map = System.getenv();

        String computerName = map.get("COMPUTERNAME");// 获取计算机名
        String userDomain = map.get("USERDOMAIN");// 获取计算机域名
        System.out.println("计算机名:    " + computerName);
        System.out.println("计算机域名:    " + userDomain);
        System.out.println("本地ip地址:    " + addr.getHostAddress());
        System.out.println("本地主机名:    " + addr.getHostName());

        //jvm内存告警
        System.out.println("JVM可以使用的总内存:    " + r.totalMemory() / 1048576 + " MB");
        System.out.println("JVM可以使用的剩余内存:    " + r.freeMemory() / 1048576 + " MB");


        // 系统内存告警
        System.out.println("内存总量:    " + mem.getTotal() / 1048576 + " MB av");
        System.out.println("当前内存使用量:    " + mem.getUsed() / 1048576 + " MB used");
        System.out.println("当前内存剩余量:    " + mem.getFree() / 1048576 + " MB free");

        //cpu告警
        System.out.println("CPU用户使用率:    " + CpuPerc.format(cpu.getUser()));// 用户使用率
        System.out.println("CPU系统使用率:    " + CpuPerc.format(cpu.getSys()));// 系统使用率
        System.out.println("CPU当前等待率:    " + CpuPerc.format(cpu.getWait()));// 当前等待率
        System.out.println("CPU当前错误率:    " + CpuPerc.format(cpu.getNice()));//
        System.out.println("CPU当前空闲率:    " + CpuPerc.format(cpu.getIdle()));// 当前空闲率
        System.out.println("CPU总的使用率:    " + CpuPerc.format(cpu.getCombined()));// 总的使用率


        FileSystem[] fsList = sigar.getFileSystemList();
        for (int i = 0; i < fsList.length; i++) {
            System.out.println("第" + i + "块磁盘");
            FileSystem fs = fsList[i];
            if (fs.getType() == FileSystem.TYPE_LOCAL_DISK) {
                // 分区的盘符名称
                System.out.println("盘符名称:    " + fs.getDevName());
                // 分区的盘符名称
                System.out.println("盘符路径:    " + fs.getDirName());

                FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
                System.out.println("总大小:    " + usage.getTotal() / 1048576 + "KB");
                System.out.println("剩余大小:    " + usage.getFree() / 1048576 + "KB");
                System.out.println("可用大小:    " + usage.getAvail() / 1048576 + "KB");
                System.out.println("已经使用量:    " + usage.getUsed() / 1048576 + "KB");
                double usePercent = usage.getUsePercent() * 100D;
                System.out.println("资源的利用率:    " + usePercent + "%");

            }
        }
    }
}
