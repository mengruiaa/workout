package JZ.com.mr.entity;

public class Course {
	private int course_id;
	private String name;
	private String type;
	private String body_part;
	private String level;
	private String picture;
	private String sign;
	private String introduce;
	
	
	public Course(int course_id, String name, String type, String body_part, String level, String picture, String sign,
			String introduce) {
		super();
		this.course_id = course_id;
		this.name = name;
		this.type = type;
		this.body_part = body_part;
		this.level = level;
		this.picture = picture;
		this.sign = sign;
		this.introduce = introduce;
	}
	/**
	 * @return the course_id
	 */
	public int getCourse_id() {
		return course_id;
	}
	/**
	 * @param course_id the course_id to set
	 */
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the body_part
	 */
	public String getBody_part() {
		return body_part;
	}
	/**
	 * @param body_part the body_part to set
	 */
	public void setBody_part(String body_part) {
		this.body_part = body_part;
	}
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}
	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}
	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}
	/**
	 * @return the introduce
	 */
	public String getIntroduce() {
		return introduce;
	}
	/**
	 * @param introduce the introduce to set
	 */
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	

}
