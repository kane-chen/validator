package cn.kane.service;

import cn.kane.domain.Department;
import cn.kane.domain.User;

public interface DomainService {

	
	int save(User user);
	
	int saveCascade(User user,Department dept) ;
	
	int update(int id,String name,String email);

	int update(User user);
	
	int updateCascade(User user,Department dept) ;
	
	int upMulti(String desc,User user,Department dept, int res);
	
}
