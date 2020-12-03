package JZ.com.mr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JZ.com.mr.entity.Course;
import JZ.utils.DBUtil;

public class HistorySearchDao {
	private DBUtil dbUtil;
	public HistorySearchDao() {
		try {
			dbUtil=DBUtil.getInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//�����û��ֻ��Ų�ѯ��ʷ��¼�������
	public List<String> searchHistroy(String userName) {
		String sql="select * from search_history where user_name = '"+userName+"' order by id desc limit 0,5";
		ResultSet rs = null;
		List<String> his=new ArrayList<String>();
		try {
			rs = dbUtil.queryDate(sql);
			while(rs.next()) {
				String search = rs.getString("search");
				his.add(search);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return his;
	}
	//�������ʷ��������n����ʾ��Ӱ��ļ�¼����
	public int addHistory(String userName,String search) {
		String sql="insert into search_history(user_name,search)"
				+ " values ('"+userName+"','"+search+"')";
		int n = -1;
		try {
			n = dbUtil.addDataToTable(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return n;
	}
	//�����û�����ɾ����ʷ������n����ʾ��Ӱ��ļ�¼����
	public int deleteHistory(String userName) {
		String sql="delete from search_history where user_name='"+userName+"'";
		int n = -1;
		try {
			n = dbUtil.updateData(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return n;
	}
}
