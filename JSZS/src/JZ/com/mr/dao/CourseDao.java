package JZ.com.mr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JZ.utils.DBUtil;



public class CourseDao {
	private DBUtil dbUtil;
	public CourseDao() {
		try {
			dbUtil=DBUtil.getInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//��ѯ���пγ�
	public ResultSet getCourses() {
		String sql="select * from course";
		ResultSet rs = null;
		try {
			rs = dbUtil.queryDate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	//��ѯ���пγ�����
	public List<String> getCoursesNames() {
		String sql="select name from course";
		ResultSet rs = null;
		List<String> names=new ArrayList<String>();
		try {
			rs = dbUtil.queryDate(sql);
			while(rs.next()) {
				String name = rs.getString("name");
				names.add(name);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return names;
	}
	//���ݿγ�����ѯĳ�γ�
	public ResultSet searchCourse(String name) {
		String sql="select * from course where name = '"+name+"'";
		ResultSet rs = null;
		try {
			rs = dbUtil.queryDate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	//���ݿγ̹ؼ�������ĳ�γ�
	public ResultSet searchCoursesByName(String name) {
		String sql="select * from course where name like '%"+name+"%'";
		ResultSet rs = null;
		try {
			rs = dbUtil.queryDate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	//���ݿγ����Ͳ�ѯ�γ�
	public ResultSet searchCoursesByType(String type) {
		String sql="select * from course where type='"+type+"'";
		ResultSet rs = null;
		try {
			rs = dbUtil.queryDate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	//���ݶ��������岿λ��ѯ�γ�
	public ResultSet searchCoursesByBody(String body_part) {
		String sql="select * from course where body_part='"+body_part+"'";
		ResultSet rs = null;
		try {
			rs = dbUtil.queryDate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	//�����ѶȲ�ѯ�γ�
	public ResultSet searchCoursesByLevel(String level) {
		String sql="select * from course where level='"+level+"'";
		ResultSet rs = null;
		try {
			rs = dbUtil.queryDate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	//�������߲�ѯ�γ�
	public ResultSet searchCoursesByThree(String type,String body_part,String level) {
		String sql;
		if(body_part.equals("ȫ��")&& !level.equals("ȫ��")) {
			sql="select * from course where type='"+type+"' and level='"+level+"'";	
		}else if(!body_part.equals("ȫ��")&& level.equals("ȫ��")) {
			sql="select * from course where type='"+type+"' and body_part='"+body_part+"'";	
		}else if(!body_part.equals("ȫ��")&& !level.equals("ȫ��")) {
			sql="select * from course where type='"+type+"' and body_part='"+body_part+"' and level='"+level+"'";	
		}else {
			sql="select * from course where type='"+type+"'";	
		}
		
		ResultSet rs = null;
		try {
			rs = dbUtil.queryDate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	//���ݱ�־��ѯ��صĿγ�
	public ResultSet searchCoursesBySign(String sign) {
		String sql="select * from course where sign='"+sign+"'";
		ResultSet rs = null;
		try {
			rs = dbUtil.queryDate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
