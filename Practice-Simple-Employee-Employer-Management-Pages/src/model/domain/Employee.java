package sbzy.enterpriseems.model.domain;

import java.util.List;


public class Employee {
	private Integer id;
	private Integer pid;
	private String code;
	private String name;
	private int sex;
	private Employee parent;
	private List<Employee> childern;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public Employee getParent() {
		return parent;
	}
	public void setParent(Employee parent) {
		this.parent = parent;
	}
	
	
}
