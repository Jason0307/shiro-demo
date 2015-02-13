/**
 * 
 */
package org.zhubao.shiro.dao;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;

/**
 * @author jason.zhu
 * @date 2014-11-17
 * @email jasonzhu@augmentum.com.cn
 */
public class TestPasswordSecurity extends BaseTest{

	@Test
	public void testBase64() {
		String pass = "123456";
		String base64Encoded = Base64.encodeToString(pass.getBytes());
		System.out.println(base64Encoded);
		String repass = Base64.decodeToString(base64Encoded);
		System.out.println(repass);

		String hexEncoded = Hex.encodeToString(pass.getBytes());
		System.out.println("Hex :" + hexEncoded);
		String hexpass = new String(Hex.decode(hexEncoded));
		System.out.println("Hexpass : " + hexpass);
	}

	@Test
	public void testHashAlg() {
		String pass = "jason";
		String salt = "123456";
		String simpleHash = new Md5Hash(pass, salt).toString();
		System.out.println(simpleHash);
		
		
		String str = "123456";
		//String salt = "123";
		//内部使用MessageDigest
		String hash = new SimpleHash("SHA-512", str, null).toString();
		System.out.println(hash);

	}

	@Test
	public void testHashService() {
		DefaultHashService hashService = new DefaultHashService();
		hashService.setHashAlgorithmName("SHA-512");
		hashService.setPrivateSalt(new SimpleByteSource("123456"));
		hashService.setGeneratePublicSalt(true);
		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());
		hashService.setHashIterations(1);

		HashRequest request = new HashRequest.Builder().setAlgorithmName("MD5")
				.setSource(ByteSource.Util.bytes("jason"))
				.setSalt(ByteSource.Util.bytes("123456")).setIterations(1)
				.build();
		String hex = hashService.computeHash(request).toHex();
		System.out.println(hex);
	}

	@Test
	public void testHashSalt(){
		String algorithmName = "md5";
		String username = "liu";
		String password = "123";
		String salt1 = username;
		String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
		int hashIterations = 2;

		SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
		String encodedPassword = hash.toHex();
		System.out.println("encodedPassword : " + encodedPassword);
	}
	
	@Test
	public void testPass(){
		login("classpath:shiro-password.ini","jason","$shiro1$SHA-512$1$$PJkJr+wlNU1VHa4hWQuybjjVPyFzuNPcPu5MBH56scHri4UQPjvnumE7MbtcnDYhTcnxSkL9ei/bhIVrylxEwg==");
	}
}
