package cn.kane.domain;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.Length;

import cn.kane.groups.ValidatorGroup4Add;

public class Department {
	@NotNull(groups={ValidatorGroup4Add.class})
	private int id ;
	@Length(min=1,max=10,message="部门名称长度1~10",groups={ValidatorGroup4Add.class,Default.class})
	private String name ;
	private int leaderId ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}
	
}
