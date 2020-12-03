package JZ.com.ws.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JZ.com.ws.entity.User;
import JZ.utils.DBUtil;


public class Userservice {
	private List<User> users;
	private DBUtil dbUtil;
	
	public Userservice() throws ClassNotFoundException, SQLException{
		//初始化用户集合
		users = new ArrayList<User>();
	}
	
	/*
	 * 创建用户对象
	 */
	private User createUser(String phone, String pwd, String name, int weight, int height,int age) {
		User user = new User();
		user.setPhone(phone);
		user.setPwd(pwd);
		user.setName(name);
		user.setWeight(weight);
		user.setHeight(height);
		user.setAge(age);
		return user;
	}

	/*
	 * 获取所有用户信息
	 */
	public List<User> getUsers(String sql) {
		try {
			// 查询所有用户
			DBUtil dbUtil = DBUtil.getInstance();
			ResultSet rs = dbUtil.queryDate("select * from userinfo");
			while (rs.next()) {
				String phone = rs.getString("phone");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				int weight = rs.getInt("weight");
				int height = rs.getInt("height");
				int age = rs.getInt("age");
				// 根据获取到的用户信息构造用户对象
				User user = createUser(phone, pwd, name, weight, height, age);
				users.add(user);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * 判断用户是否存在
	 * 
	 * @param user 待判断的用户
	 * @return 存在则返回true，否则返回false
	 */
	public boolean isExistUser(User user) {
		// 获取待判断的用户信息
		String phone = user.getPhone();
		// 根据用户信息拼接sql语句
		String sql = "select * from userinfo where phone = '" + phone + "'";
		System.out.println(sql);
		boolean b = false;
		try {
			DBUtil dbUtil = DBUtil.getInstance();
			b = dbUtil.isExist(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	/**
	 * 注册用户
	 * 
	 * @param user 待注册的用户
	 * @return 注册是否成功，成功返回true，否则返回false
	 */
	public boolean registerUser(User user) {
		// 获取用户信息
		String phone = user.getPhone();
		String pwd = user.getPwd();
		// 拼接插入用户的sql语句
		String sql = "insert into userinfo(phone, pwd) values('" + phone + "', '" + pwd + "')";
		// 将用户的信息插入用户表中
		int n = -1;// 存储插入的记录数
		try {
			DBUtil dbUtil = DBUtil.getInstance();
			n = dbUtil.addDataToTable(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return n > 0 ? true : false;
	}
	
	/*
	 * 注销用户
	 * @param uPhone 用户号码
	 * 
	 * @return 是否删除用户，成功删除返回true，否则返回false
	 */
	public boolean deleteuserByPhone(String phone) {
		// 拼接插入用户的sql语句
		String sql = "delete from userinfo where phone = '" + phone + "'";
		// 将用户的信息从用户表中删除
		int n = -1;// 存储插入的记录数
		try {
			DBUtil dbUtil = DBUtil.getInstance();
			n = dbUtil.updateData(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return n > 0 ? true : false;
	}
	
	
	/*
	 * 获取用户信息
	 */
	public List<User> getSearchUser(String phone) {
		// 拼接插入用户的sql语句
		String sql = "select * from userinfo where phone = '" + phone + "'";
		try {
			// 查询用户
			DBUtil dbUtil = DBUtil.getInstance();
			ResultSet rs = dbUtil.queryDate(sql);
			while (rs.next()) {
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				int weight = rs.getInt("weight");
				int height = rs.getInt("height");
				int age = rs.getInt("age");
				// 根据获取到的用户信息构造用户对象
				User user = createUser(phone, pwd, name, weight, height, age);
				users.add(user);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}
