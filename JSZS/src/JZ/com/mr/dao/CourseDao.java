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
	//查询所有课程
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
	//查询所有课程名字
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
	//根据课程名查询某课程
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
	//根据课程关键字搜索某课程
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
	//根据课程类型查询课程
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
	//根据锻炼的身体部位查询课程
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
	//根据难度查询课程
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
	//根据三者查询课程
	public ResultSet searchCoursesByThree(String type,String body_part,String level) {
		String sql;
		if(body_part.equals("全部")&& !level.equals("全部")) {
			sql="select * from course where type='"+type+"' and level='"+level+"'";	
		}else if(!body_part.equals("全部")&& level.equals("全部")) {
			sql="select * from course where type='"+type+"' and body_part='"+body_part+"'";	
		}else if(!body_part.equals("全部")&& !level.equals("全部")) {
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
	//根据标志查询相关的课程
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
