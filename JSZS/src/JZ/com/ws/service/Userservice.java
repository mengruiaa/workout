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
		//��ʼ���û�����
		users = new ArrayList<User>();
	}
	
	/*
	 * �����û�����
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
	 * ��ȡ�����û���Ϣ
	 */
	public List<User> getUsers(String sql) {
		try {
			// ��ѯ�����û�
			DBUtil dbUtil = DBUtil.getInstance();
			ResultSet rs = dbUtil.queryDate("select * from userinfo");
			while (rs.next()) {
				String phone = rs.getString("phone");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				int weight = rs.getInt("weight");
				int height = rs.getInt("height");
				int age = rs.getInt("age");
				// ���ݻ�ȡ�����û���Ϣ�����û�����
				User user = createUser(phone, pwd, name, weight, height, age);
				users.add(user);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * �ж��û��Ƿ����
	 * 
	 * @param user ���жϵ��û�
	 * @return �����򷵻�true�����򷵻�false
	 */
	public boolean isExistUser(User user) {
		// ��ȡ���жϵ��û���Ϣ
		String phone = user.getPhone();
		// �����û���Ϣƴ��sql���
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
	 * ע���û�
	 * 
	 * @param user ��ע����û�
	 * @return ע���Ƿ�ɹ����ɹ�����true�����򷵻�false
	 */
	public boolean registerUser(User user) {
		// ��ȡ�û���Ϣ
		String phone = user.getPhone();
		String pwd = user.getPwd();
		// ƴ�Ӳ����û���sql���
		String sql = "insert into userinfo(phone, pwd) values('" + phone + "', '" + pwd + "')";
		// ���û�����Ϣ�����û�����
		int n = -1;// �洢����ļ�¼��
		try {
			DBUtil dbUtil = DBUtil.getInstance();
			n = dbUtil.addDataToTable(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return n > 0 ? true : false;
	}
	
	/*
	 * ע���û�
	 * @param uPhone �û�����
	 * 
	 * @return �Ƿ�ɾ���û����ɹ�ɾ������true�����򷵻�false
	 */
	public boolean deleteuserByPhone(String phone) {
		// ƴ�Ӳ����û���sql���
		String sql = "delete from userinfo where phone = '" + phone + "'";
		// ���û�����Ϣ���û�����ɾ��
		int n = -1;// �洢����ļ�¼��
		try {
			DBUtil dbUtil = DBUtil.getInstance();
			n = dbUtil.updateData(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return n > 0 ? true : false;
	}
	
	
	/*
	 * ��ȡ�û���Ϣ
	 */
	public List<User> getSearchUser(String phone) {
		// ƴ�Ӳ����û���sql���
		String sql = "select * from userinfo where phone = '" + phone + "'";
		try {
			// ��ѯ�û�
			DBUtil dbUtil = DBUtil.getInstance();
			ResultSet rs = dbUtil.queryDate(sql);
			while (rs.next()) {
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				int weight = rs.getInt("weight");
				int height = rs.getInt("height");
				int age = rs.getInt("age");
				// ���ݻ�ȡ�����û���Ϣ�����û�����
				User user = createUser(phone, pwd, name, weight, height, age);
				users.add(user);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}
