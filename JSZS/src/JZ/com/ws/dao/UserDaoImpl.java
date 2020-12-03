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

	// ��ѯ�����û�
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

	// �����û��˺�ɾ�����û�������n����ʾ��Ӱ��ļ�¼����
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

	// �����û��˺������û�
	public ResultSet searchUser(String phone) {
		// ƴ�Ӳ����û���sql���
		String sql = "select * from userinfo where phone = '" + phone + "'";
		ResultSet rs = null;
		try {
			rs = dbUtil.queryDate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;

	}

	// �ж��û��Ƿ����
	public boolean isExistUser(String phone, String pwd) {
		// �����û���Ϣƴ��sql���
		String sql = "select * from userinfo where phone = '" + phone + "' and pwd = '" + pwd + "'";
		boolean b = false;
		try {
			b = dbUtil.isExist(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// ע��
	public int regestUser(String phone, String pwd) {
		// ƴ�Ӳ����û���sql���
		String sql = "insert into userinfo(phone, pwd) values('" + phone + "', '" + pwd + "')";
		// ���û�����Ϣ�����û�����
		int n = -1;// �洢����ļ�¼��
		try {
			n = dbUtil.addDataToTable(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return n;
	}

}
