package com.somnus.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	private static final String url = "jdbc:mysql://localhost:3306/demo";
	private static final String user = "root";
	private static final String password = "password";
	
	/*private static final String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
	private static final String user = "scott";
	private static final String password = "passw0rd";*/
	
	// 定义一个用于放置数据库连接的局部线程变量（使每个线程都拥有自己的连接）
    private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			/*Class.forName("oracle.jdbc.driver.OracleDriver");*/
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn() {
		Connection conn = connContainer.get();
		try {
		    if(conn == null){
		        conn = DriverManager.getConnection(url, user, password);
		        connContainer.set(conn);
		    }
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return conn;
	}
	
	public static void closeConnection() {
        Connection conn = connContainer.get();
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            connContainer.remove();
        }
    }

	public static void main(String[] args) {

		System.out.println(getConn());
	}

	private DbConnection() {
		super();
	}

}
