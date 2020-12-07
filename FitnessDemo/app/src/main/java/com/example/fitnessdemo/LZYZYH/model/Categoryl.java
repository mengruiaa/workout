package com.example.fitnessdemo.LZYZYH.model;

public class Categoryl {
	
	private int categoryl_id;
	private int type_id;
	private String categoryl_name;
	
	public int getCategoryl_id() {
		return categoryl_id;
	}
	public void setCategoryl_id(int categoryl_id) {
		this.categoryl_id = categoryl_id;
	}
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public String getCategoryl_name() {
		return categoryl_name;
	}
	public void setCategoryl_name(String categoryl_name) {
		this.categoryl_name = categoryl_name;
	}
	
	@Override
	public String toString() {
		return "Categoryl[categoryl_id="+ categoryl_id +",type_id="+ type_id +",categoryl_name="+categoryl_name+"]";
	}
	public Categoryl(int categoryl_id,int type_id,String categoryl_name) {
		super();
		this.categoryl_id = categoryl_id;
		this.type_id = type_id;
		this.categoryl_name = categoryl_name;
	}
	public Categoryl() {
		
	}
}
