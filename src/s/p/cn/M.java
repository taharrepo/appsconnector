package s.p.cn;

import javax.mail.PasswordAuthentication;

public class M extends javax.mail.Authenticator {
	private PasswordAuthentication authentication = null;
	private String username;
	private String password;
	
	public M() {
		if(null == S.p)S.p = new CF().l(S.p, "cfg", "e");
		this.username = S.p.getProperty("username");
		this.password = new String(S.p.getProperty("password"));
		this.authentication = new PasswordAuthentication(username,password);
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
