package cn.kane.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.kane.domain.Department;
import cn.kane.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:valid-context.xml")
public class AnnoValidTest {
	
	@Autowired
	private DomainService domainService ;
	
    @Test
    public void testSave() {
    	User user = new User() ;
    	user.setId(1);
    	user.setIsmem(true);
    	user.setMobilePhoneNo("13333333333");
    	user.setEmail("123333@163.com");
    	int res = domainService.save(user);
        Assert.assertEquals("pass", res , 1);
    }
    
    @Test
    public void testUpdateParams(){
    	int id = -1 ;
    	String name = "aaa" ;
    	String email = "1111" ;
    	int res = domainService.update(id, name, email);
    	Assert.assertEquals("pass", res , 1);
    }
    
    @Test
    public void testSaveCascade(){
    	User user = new User() ;
    	user.setId(1);
    	user.setIsmem(true);
    	user.setMobilePhoneNo("13333333333");
    	user.setEmail("123333@163.com");
    	Department dept = new Department() ;
    	dept.setName("111111111");
    	int res = domainService.saveCascade(user, dept);
    	Assert.assertEquals("pass", res , 1);
    }
    @Test
    public void testUpdateCascade(){
    	User user = new User() ;
    	user.setId(1);
    	user.setName("111111111");//POINT:not-null
    	user.setMobilePhoneNo("13333333333");
    	user.setEmail("123333@163.com");
    	Department dept = new Department() ;
    	dept.setName("11111111");
    	int res = domainService.upMulti(null,user, dept,1) ;
    	Assert.assertEquals("pass", res , 1);
    }
}
    