package JZ.com.mr.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JZ.com.mr.dao.CourseDao;
import JZ.com.mr.entity.Course;

public class CourseService {
	private List<Course> courses;
	private CourseDao courseDao;
	public CourseService() {
		courses = new ArrayList<Course>();
		courseDao = new CourseDao();
	}
	//查询所有课程
	public List<Course> getCourses() {
		ResultSet rs = courseDao.getCourses();
		try {
			while(rs.next()) {
				int course_id=rs.getInt("course_id");
				String name = rs.getString("name");
				String type = rs.getString("type");
				String body_part=rs.getString("body_part");
				String level=rs.getString("level");
				String picture=rs.getString("picture");
				String sign=rs.getString("sign");
				String introduce=rs.getString("introduce");

				Course course=new Course(course_id, name, type, body_part, level, picture, sign, introduce);
				courses.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
	//通过课程名查询某一蛋糕信息
	public Course searchCourse(String name0) {
		ResultSet rs = courseDao.searchCourse(name0);
		try {
			rs.next();
			int course_id=rs.getInt("course_id");
			String name = rs.getString("name");
			String type = rs.getString("type");
			String body_part=rs.getString("body_part");
			String level=rs.getString("level");
			String picture=rs.getString("picture");
			String sign=rs.getString("sign");
			String introduce=rs.getString("introduce");
			Course course=new Course(course_id, name, type, body_part, level, picture, sign, introduce);
			return course;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//查询对应标签的课程列表
	public List<Course> getCoursesBySign(String sign0) {
		ResultSet rs = courseDao.searchCoursesBySign(sign0);
		List<Course> courses=new ArrayList<Course>();
		try {
			while(rs.next()) {
				int course_id=rs.getInt("course_id");
				String name = rs.getString("name");
				String type = rs.getString("type");
				String body_part=rs.getString("body_part");
				String level=rs.getString("level");
				String picture=rs.getString("picture");
				String sign=rs.getString("sign");
				String introduce=rs.getString("introduce");

				Course course=new Course(course_id, name, type, body_part, level, picture, sign, introduce);
				courses.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
	

}