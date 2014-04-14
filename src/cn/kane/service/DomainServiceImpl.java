package cn.kane.service;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import cn.kane.domain.Department;
import cn.kane.domain.User;
import cn.kane.groups.ValidatorGroup4Update;

public class DomainServiceImpl implements DomainService {

	public int save(User bean) {
		System.out.println("save validate pass");
		return 1;
	}

	public int saveCascade(User bean1, Department bean2) {
		System.out.println("saveCascade validate pass");
		return 1;
	}

	public int update(User bean) {
		System.out.println("update validate pass");
		return 1;
	}

	public int updateCascade(User bean1, Department bean2) {
		System.out.println("updateCascade validate pass");
		return 1;
	}

	public int update(@Range(min=1,groups=ValidatorGroup4Update.class)int id, 
			@NotBlank(groups=ValidatorGroup4Update.class)String name, 
			@Email(groups=ValidatorGroup4Update.class) String email) {
		return 1;
	}

	public int upMulti(String desc, User user, Department dept, int res) {
		System.out.println("updateMulti validate pass");
		return 1;
	}

}
