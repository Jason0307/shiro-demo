/**
 * 
 */
package org.zhubao.shiro.dao;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author jason.zhu
 * @date 2014-11-13
 * @email jasonzhu@augmentum.com.cn
 */
public class TestShiroLogin extends BaseTest{

	@Test
	public void testLoginByIni() {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro.ini");
		org.apache.shiro.mgt.SecurityManager securityManager = factory
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("jason",
				"123456");
		subject.login(token);
		Assert.assertEquals(true, subject.isAuthenticated());
	}
	
	
	@Test
	public void testLoginByRoleIni() {
		login("classpath:shiro-role.ini","jason","123456");
		boolean result = SecurityUtils.getSubject().hasRole("developer");
		boolean[] results = SecurityUtils.getSubject().hasRoles(Arrays.asList("admin","developer"));
		Assert.assertTrue(result);
		Assert.assertTrue(results[0]);
		Assert.assertTrue(results[1]);
		
	}
	
	@Test
	public void testLoginPermission(){
		//login("classpath:shiro-permission.ini","jason","123456");
		login("classpath:shiro-permission.ini","marco","123789");
		Assert.assertTrue(SecurityUtils.getSubject().isPermitted("user:delete"));
	}
	
	
	@Test
	public void testLoginByRealmIni() {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro-realm.ini");
		org.apache.shiro.mgt.SecurityManager securityManager = factory
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("jason",
				"123456");
		subject.login(token);
		Assert.assertEquals(true, subject.isAuthenticated());
	}
	
	@Test
	public void testLoginByJdbcRealm() {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro-jdbc-realm.ini");
		org.apache.shiro.mgt.SecurityManager securityManager = factory
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("jason",
				"123456");
		subject.login(token);
		Assert.assertEquals(true, subject.isAuthenticated());
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testLoginByStrategy(){
		login("classpath:shiro-authenticator-all-success.ini","jason","123456");
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		PrincipalCollection principalCollection = subject.getPrincipals();
		List list = principalCollection.asList();
		System.out.println(list);
		Assert.assertEquals(2, list.size());  
	}
	
}
