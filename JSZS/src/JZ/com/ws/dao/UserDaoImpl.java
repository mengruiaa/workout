package JZ.com.ws.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import JZ.utils.DBUtil;

public class UserDaoImpl {
	private DBUtil dbUtil;

	public UserDaoImpl() {
		try {
			dbUtil = DBUtil.getInstance();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// 查询所有用户
	public ResultSet getUsers() {
		String sql = "select * from userinfo";
		ResultSet rs = null;
		try {
			rs = dbUtil.queryDate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 根据用户账号删除此用户，返回n，表示受影响的记录条数
	public int deleteUser(String phone) {
		String sql = "delete from userinfo where phone = '" + phone + "'";
		int n = -1;
		try {
			n = dbUtil.updateData(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return n;

	}

	// 根据用户账号搜索用户
	public ResultSet searchUser(String phone) {
		// 拼接插入用户的sql语句
		String sql = "select * from userinfo where phone = '" + phone + "'";
		ResultSet rs = null;
		try {
			rs = dbUtil.queryDate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;

	}

	// 判断用户是否存在
	public boolean isExistUser(String phone, String pwd) {
		// 根据用户信息拼接sql语句
		String sql = "select * from userinfo where phone = '" + phone + "' and pwd = '" + pwd + "'";
		boolean b = false;
		try {
			b = dbUtil.isExist(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 注册
	public int regestUser(String phone, String pwd) {
		// 拼接插入用户的sql语句
		String sql = "insert into userinfo(phone, pwd) values('" + phone + "', '" + pwd + "')";
		// 将用户的信息插入用户表中
		int n = -1;// 存储插入的记录数
		try {
			n = dbUtil.addDataToTable(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return n;
	}

}
