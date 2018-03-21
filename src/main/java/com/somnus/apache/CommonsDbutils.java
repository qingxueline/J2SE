package com.somnus.apache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

/**
 * 
 * @Title: CommonsDbutils.java 
 * @Package com.somnus.apache 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月12日 下午5:39:33 
 * @version V1.0
 */
public class CommonsDbutils {

	public static void main(String[] args) {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/demo";
		String jdbcDriver = "com.mysql.jdbc.Driver";
		String username = "root";
		String password = "password";

		DbUtils.loadDriver(jdbcDriver);
		//****************************转换成list  ************************************
		try {
			conn = DriverManager.getConnection(url, username, password);
			QueryRunner qr = new QueryRunner();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			List results = (List) qr.query(conn, "select id,name from person",new BeanListHandler(Person.class));
			for (int i = 0; i < results.size(); i++) {
				Person p = (Person) results.get(i);
				System.out.println("age:" + p.getAge() + ",name:" + p.getName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		//****************************转换成map  ************************************
		try {
			conn = DriverManager.getConnection(url, username, password);
			QueryRunner qr = new QueryRunner();
			@SuppressWarnings("rawtypes")
            List results = (List) qr.query(conn, "select age,name from person",new MapListHandler());
			for (int i = 0; i < results.size(); i++) {
				@SuppressWarnings("unchecked")
                Map<String,Object> map = (Map<String,Object>) results.get(i);
				System.out.println("age:" + map.get("age") + ",name:"+ map.get("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}
}
