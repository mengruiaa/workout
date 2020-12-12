package com.example.fitnessdemo.LZYZYH.model;

public class Categoryr {

	private int product_id;
	private int categoryl_id;
	private String product_name;
	private String product_mainimage;
	private String product_subimage1;
	private String product_subimage2;
	private String product_subimage3;
	private String product_subimage4;
	private String product_subimage5;
	private String product_subimage6;
	private Float product_price;
	private String product_color;
	private int product_quantity;
	private String product_link;
	
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getCategoryl_id() {
		return categoryl_id;
	}
	public void setCategoryl_id(int categoryl_id) {
		this.categoryl_id = categoryl_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_mainimage() {
		return product_mainimage;
	}
	public void setProduct_mainimage(String product_mainimage) {
		this.product_mainimage = product_mainimage;
	}
	public Float getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Float product_price) {
		this.product_price = product_price;
	}
	
	public Categoryr(int product_id, int categoryl_id, String product_name, String product_mainimage,
			String product_subimage1, String product_subimage2, String product_subimage3, String product_subimage4,
			String product_subimage5, String product_subimage6, Float product_price, String product_color,
			int product_quantity, String product_link) {
		super();
		this.product_id = product_id;
		this.categoryl_id = categoryl_id;
		this.product_name = product_name;
		this.product_mainimage = product_mainimage;
		this.product_subimage1 = product_subimage1;
		this.product_subimage2 = product_subimage2;
		this.product_subimage3 = product_subimage3;
		this.product_subimage4 = product_subimage4;
		this.product_subimage5 = product_subimage5;
		this.product_subimage6 = product_subimage6;
		this.product_price = product_price;
		this.product_color = product_color;
		this.product_quantity = product_quantity;
		this.product_link = product_link;
	}
	public String getProduct_subimage1() {
		return product_subimage1;
	}
	public void setProduct_subimage1(String product_subimage1) {
		this.product_subimage1 = product_subimage1;
	}
	public String getProduct_subimage2() {
		return product_subimage2;
	}
	public void setProduct_subimage2(String product_subimage2) {
		this.product_subimage2 = product_subimage2;
	}
	public String getProduct_subimage3() {
		return product_subimage3;
	}
	public void setProduct_subimage3(String product_subimage3) {
		this.product_subimage3 = product_subimage3;
	}
	public String getProduct_subimage4() {
		return product_subimage4;
	}
	public void setProduct_subimage4(String product_subimage4) {
		this.product_subimage4 = product_subimage4;
	}
	public String getProduct_subimage5() {
		return product_subimage5;
	}
	public void setProduct_subimage5(String product_subimage5) {
		this.product_subimage5 = product_subimage5;
	}
	public String getProduct_subimage6() {
		return product_subimage6;
	}
	public void setProduct_subimage6(String product_subimage6) {
		this.product_subimage6 = product_subimage6;
	}
	public String getProduct_color() {
		return product_color;
	}
	public void setProduct_color(String product_color) {
		this.product_color = product_color;
	}
	public int getProduct_quantity() {
		return product_quantity;
	}
	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}
	public String getProduct_link() {
		return product_link;
	}
	public void setProduct_link(String product_link) {
		this.product_link = product_link;
	}
	@Override
	public String toString() {
		return "Categoryr [product_id=" + product_id + ", categoryl_id=" + categoryl_id + ", product_name="
				+ product_name + ", product_mainimage=" + product_mainimage + ", product_subimage1=" + product_subimage1
				+ ", product_subimage2=" + product_subimage2 + ", product_subimage3=" + product_subimage3
				+ ", product_subimage4=" + product_subimage4 + ", product_subimage5=" + product_subimage5
				+ ", product_subimage6=" + product_subimage6 + ", product_price=" + product_price + ", product_color="
				+ product_color + ", product_quantity=" + product_quantity + ", product_link=" + product_link + "]";
	}
	
	public Categoryr(int product_id, int categoryl_id, String product_name, String product_mainimage,Float product_price) {
		super();
		this.product_id =product_id;
		this.categoryl_id = categoryl_id;
		this.product_name = product_name;
		this.product_mainimage = product_mainimage;
		this.product_price = product_price;
	}
	public Categoryr() {
		
	}
}
