package com.emp.model;


public class EmpVO implements java.io.Serializable{
	
	private String emp_id;
	private String emp_password;
	private java.sql.Timestamp emp_add_time;
	private String emp_mail;
	private String emp_phone;
	private Integer emp_status;
	private Integer emp_auth;
	private java.sql.Timestamp emp_last_edit_time;
	private String emp_last_editor;
	
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_password() {
		return emp_password;
	}
	public void setEmp_password(String emp_password) {
		this.emp_password = emp_password;
	}
	public java.sql.Timestamp getEmp_add_time() {
		return emp_add_time;
	}
	public void setEmp_add_time(java.sql.Timestamp emp_add_time) {
		this.emp_add_time = emp_add_time;
	}
	public String getEmp_mail() {
		return emp_mail;
	}
	public void setEmp_mail(String emp_mail) {
		this.emp_mail = emp_mail;
	}
	public String getEmp_phone() {
		return emp_phone;
	}
	public void setEmp_phone(String emp_phone) {
		this.emp_phone = emp_phone;
	}
	public Integer getEmp_status() {
		return emp_status;
	}
	public void setEmp_status(Integer emp_status) {
		this.emp_status = emp_status;
	}
	public Integer getEmp_auth() {
		return emp_auth;
	}
	public void setEmp_auth(Integer emp_auth) {
		this.emp_auth = emp_auth;
	}
	public java.sql.Timestamp getEmp_last_edit_time() {
		return emp_last_edit_time;
	}
	public void setEmp_last_edit_time(java.sql.Timestamp emp_last_edit_time) {
		this.emp_last_edit_time = emp_last_edit_time;
	}
	public String getEmp_last_editor() {
		return emp_last_editor;
	}
	public void setEmp_last_editor(String emp_last_editor) {
		this.emp_last_editor = emp_last_editor;
	}
	
}
