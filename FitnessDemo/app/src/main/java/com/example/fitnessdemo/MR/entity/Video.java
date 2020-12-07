package com.example.fitnessdemo.MR.entity;

public class Video {
    private int video_id;
    private String course_name;
    private String video_name;
    private String video_path;
    public Video(int video_id, String course_name, String video_name, String video_path) {
        super();
        this.video_id = video_id;
        this.course_name = course_name;
        this.video_name = video_name;
        this.video_path = video_path;
    }
    public int getVideo_id() {
        return video_id;
    }
    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }
    public String getCourse_name() {
        return course_name;
    }
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
    public String getVideo_name() {
        return video_name;
    }
    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }
    public String getVideo_path() {
        return video_path;
    }
    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }


}
