package com.somnus;

/**
 * 
 * @Title: SystemProperty.java 
 * @Package com.somnus 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:37:38 
 * @version V1.0
 */
public class SystemProperty{

	public static void main(String[] args){
		System.out.println("Java 运行时环境供应商-java_vendor: "+ System.getProperty("java.vendor"));

		System.out.println("Java 供应商的 URL-java_vendor_url: "+ System.getProperty("java.vendor.url"));

		System.out.println("Java 安装目录-java_home: "+ System.getProperty("java.home"));

		System.out.println("Java 类格式版本号-java_class_version: "+ System.getProperty("java.class.version"));

		System.out.println("Java 类路径-java_class_path: "+ System.getProperty("java.class.path"));

		System.out.println("操作系统的名称-os_name: " + System.getProperty("os.name"));

		System.out.println("操作系统的架构-os_arch: " + System.getProperty("os.arch"));

		System.out.println("操作系统的版本-os_version: "+ System.getProperty("os.version"));

		System.out.println("用户的账户名称-user_name: "+ System.getProperty("user.name"));

		System.out.println("用户的账户名称-user_home: "+ System.getProperty("user.home"));

		System.out.println("用户的当前工作目录-user_dir: "+ System.getProperty("user.dir"));

		System.out.println("Java 虚拟机规范版本-java_vm_specification_version: "+ System.getProperty("java.vm.specification.version"));

		System.out.println("Java 虚拟机规范供应商-java_vm_specification_vendor: "+ System.getProperty("java.vm.specification.vendor"));

		System.out.println("Java 虚拟机规范名称-java_vm_specification_name: "+ System.getProperty("java.vm.specification.name"));

		System.out.println("Java 虚拟机实现版本-java_vm_version: "+ System.getProperty("java.vm.version"));

		System.out.println("Java 虚拟机实现供应商-java_vm_vendor: "+ System.getProperty("java.vm.vendor"));

		System.out.println("Java 虚拟机实现名称-java_vm_name: "+ System.getProperty("java.vm.name"));

		System.out.println("一个或多个扩展目录的路径-java_ext_dirs: "+ System.getProperty("java.ext.dirs"));

		System.out.println("文件分隔符（在 UNIX 系统中是“/”）-file_separator: "+ System.getProperty("file.separator"));

		System.out.println("路径分隔符（在 UNIX 系统中是“: ”）-path_separator: "+ System.getProperty("path.separator"));

		System.out.println("行分隔符（在 UNIX 系统中是“/n”）-line_separator: "+ System.getProperty("line.separator"));

	}
}
