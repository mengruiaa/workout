package JZ.com.mr.entity;

public class Video {
	private int video_id;
	private int course_id;
	private String video_name;
	private String video_path;
	public Video(int video_id, int course_id, String video_name, String video_path) {
		super();
		this.video_id = video_id;
		this.course_id = course_id;
		this.video_name = video_name;
		this.video_path = video_path;
	}
	/**
	 * @return the video_id
	 */
	public int getVideo_id() {
		return video_id;
	}
	/**
	 * @param video_id the video_id to set
	 */
	public void setVideo_id(int video_id) {
		this.video_id = video_id;
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
	 * @return the video_name
	 */
	public String getVideo_name() {
		return video_name;
	}
	/**
	 * @param video_name the video_name to set
	 */
	public void setVideo_name(String video_name) {
		this.video_name = video_name;
	}
	/**
	 * @return the video_path
	 */
	public String getVideo_path() {
		return video_path;
	}
	/**
	 * @param video_path the video_path to set
	 */
	public void setVideo_path(String video_path) {
		this.video_path = video_path;
	}
	

}
