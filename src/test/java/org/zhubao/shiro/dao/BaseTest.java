/**
 * 
 */
package org.zhubao.shiro.dao;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;

/**
 * @author jason.zhu
 * @date   2014-11-17
 * @email jasonzhu@augmentum.com.cn
 */
public class BaseTest {

	public void login(String configFile,String username,String password){
	    Factory<org.apache.shiro.mgt.SecurityManager> factory =  
	            new IniSecurityManagerFactory(configFile);  
	  
	    org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();  
	    SecurityUtils.setSecurityManager(securityManager);  
	  
	    org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();  
	    UsernamePasswordToken token = new UsernamePasswordToken(username, password);  
	    token.setRememberMe(true);
	    subject.login(token);
	}
	
	 @After
	    public void tearDown() throws Exception {
	        ThreadContext.unbindSubject();
	    }
}
