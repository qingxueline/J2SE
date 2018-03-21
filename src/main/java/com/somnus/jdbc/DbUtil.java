package com.somnus.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;

public class DbUtil{

	// 获取connection连接
	public Connection getConnection(){
		Connection conn = DbConnection.getConn();
		return conn;
	}

	// 获取statement连接
	public Statement getStatement(){
		Connection conn = getConnection();
		
		Statement stat = null;
		try{
			stat = conn.createStatement();
		}
		catch (SQLException e){
		    throw new RuntimeException(e.getMessage(), e);
		}
		return stat;
	}

	// 获取preparedStatment连接
	public PreparedStatement getPreparedStatement(String sql,Object[] parameters){
		Connection conn = getConnection();
		
		PreparedStatement pstat = null;
		try{
			pstat = conn.prepareStatement(sql);
			if (parameters != null && parameters.length > 0){
				pstat = conn.prepareStatement(sql);
				for (int i = 0; i < parameters.length; i++){
					pstat.setObject(i + 1, parameters[i]);
				}
			}
		}
		catch (SQLException e){
		    throw new RuntimeException(e.getMessage(), e);
		}
		return pstat;
	}


	/**
	 * 获取符合查询条件的信息
	 * @param stat
	 * 			
	 * @param sql
	 * 			执行语句
	 * @return
	 */
	public ResultSet getResultSet(Statement stat, String sql){
		ResultSet rset = null;
		if (stat instanceof PreparedStatement){
			PreparedStatement pstat = (PreparedStatement) stat;
			try{
				rset = pstat.executeQuery();
			}
			catch (SQLException e){
				throw new RuntimeException(e.getMessage(),e);
			}
		}
		else{
			try{
				rset = stat.executeQuery(sql);
			}
			catch (SQLException e){
			    throw new RuntimeException(e.getMessage(), e);
			}
		}
		return rset;
	}
	

	/**
	 * 获取符合查询条件的信息的记录数
	 * 
	 * @param sqlStr
	 *            查询语句
	 * @param tempparameters
	 *            参数
	 * @return 返回信息总数
	 */
	public int getTotalCount(String sqlStr, Object[] temp){
		int count = 0;
		sqlStr = sqlStr.toUpperCase();
		sqlStr = sqlStr.replace(sqlStr.substring(6, sqlStr.indexOf("FROM")),
				" COUNT(*) ");
		String sql = sqlStr.substring(0, sqlStr.lastIndexOf("WHERE"));
		Object[] parameters = null;
		if (temp != null && temp.length > 2){
			parameters = new Object[temp.length - 2];
			for (int i = 0; i < parameters.length; i++){
				parameters[i] = temp[i];
			}
		}
		//采取PreparedStatement预编译的 SQL 语句的对象
		PreparedStatement pstat = getPreparedStatement(sql, parameters);
		
		ResultSet rs = getResultSet(pstat, sql);
		
		try{
			if (rs.next()){
				count = rs.getInt(1);
			}
		}
		catch (SQLException e){
		    throw new RuntimeException(e.getMessage(), e);
		}
		finally{
		    DbConnection.closeConnection();
			close(pstat,rs);
		}
		return count;
	}

	/**
	 * 执行插入 更新 删除语句
	 * 
	 * @param stat
	 *            
	 * @param sql
	 *            执行语句
	 * @return
	 */
	public boolean doInsertOrUpdateOrDelete(Statement stat,String sql){
		boolean flag = false;
		
		int rowCount = 0;
		
		if (stat instanceof PreparedStatement){
			PreparedStatement pstat = (PreparedStatement) stat;
			try{
				rowCount = pstat.executeUpdate();
			}
			catch (SQLException e){
			    throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
			try{
				rowCount = stat.executeUpdate(sql);
			}
			catch (SQLException e){
			    throw new RuntimeException(e.getMessage(), e);
			}
		}
		if (rowCount > 0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 调用存储过程
	 * 
	 * @param sql
	 * 				{call.queryempinfo(?,?,?,?)}
	 * @param inparamerters
	 * @param outparamerters
	 * @return
	 */
	public CallableStatement gerCall(String sql,Object[] inparamerters,Object[] outparamerters){
		Connection conn = getConnection();
		CallableStatement call = null;
		try{
			call = conn.prepareCall(sql);
			
			if(inparamerters!=null){
				for (int i = 0; i < inparamerters.length; i++){
					call.setObject(i + 1, inparamerters[i]);
				}
			}
			if(outparamerters!=null){
				for (int i = 0; i < outparamerters.length; i++){
					if(outparamerters[i] instanceof String){
						call.registerOutParameter(inparamerters.length+1+i, Types.VARCHAR);
					} else if(outparamerters[i] instanceof Integer){
						call.registerOutParameter(inparamerters.length+1+i, Types.INTEGER);
					}  else if(outparamerters[i] instanceof Double){
						call.registerOutParameter(inparamerters.length+1+i, Types.NUMERIC);
					} else if(outparamerters[i] instanceof Date){
						call.registerOutParameter(inparamerters.length+1+i, Types.DATE);
					}
				}
			}
		}
		catch (SQLException e){
		    throw new RuntimeException(e.getMessage(), e);
		}
		return call;
	}
	
	public void crudCall(String sql,Object[] paramerters){
		Connection conn = getConnection();
		try{
			CallableStatement call = conn.prepareCall(sql);
			
			if(paramerters!=null){
				for (int i = 0; i < paramerters.length; i++){
					call.setObject(i + 1, paramerters[i]);
				}
			}
			
			call.execute();
		}
		catch (SQLException e){
		    throw new RuntimeException(e.getMessage(), e);
		}
	}
	/**
	 * 关闭相关对象
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public void close(Statement st,ResultSet rs) {
		try{
			if(rs!=null)
				rs.close();
		}catch(Exception e){
		    throw new RuntimeException(e.getMessage(), e);
		}
		try{
			if(st!=null)
				st.close();
		}catch(Exception e){
		    throw new RuntimeException(e.getMessage(), e);
		}
	}
}
