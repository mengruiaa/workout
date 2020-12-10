package com.example.fitnessdemo.MR.entity;

public class CoursePlan {
    private String user_name;
    private String course_name;
    public CoursePlan(String user_name, String course_name) {
        super();
        this.user_name = user_name;
        this.course_name = course_name;
    }
    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }
    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    /**
     * @return the course_name
     */
    public String getCourse_name() {
        return course_name;
    }
    /**
     * @param course_name the course_name to set
     */
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }


}