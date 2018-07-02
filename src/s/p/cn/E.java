package s.p.cn;

import java.security.GeneralSecurityException;
import java.util.Properties;

import com.sun.mail.util.MailSSLSocketFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class E {
	private Pattern p;
	private Matcher m;
	private boolean b;
	private String f, t, h, c, d;
	private InternetAddress a,r;
	private Authenticator o;
	private Session s;
	private String e = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public boolean E(String i, String v, String e, String z) {
		b = true;
		if(null == S.p)S.p = new CF().l(S.p, "cfg", "e");
		f = S.p.getProperty("mailsender");
		t = S.p.getProperty("mailaccept");		
		h = S.p.getProperty("head");
		try {a= new InternetAddress(f);} catch (Exception e1) {e1.printStackTrace();}			
		c = S.p.getProperty("content")+z;		
			
		o = new M();
		s = Session.getDefaultInstance(k(),o);
		//if(i.indexOf("gmail.com") != -1)
		//	d = "<p style='white-space: pre-wrap;font-family: arial,sans-serif;font-size: 15px;line-height: 22px;'>"+e+"</p>";
		//else d = "<br/><a href="+e.replaceAll(" ", "")+" target='_blank'>"+e.replaceAll(" ", "")+"</a><br/>";
		d = "<br/><a href="+e.replaceAll(" ", "")+" target='_blank'>"+e.replaceAll(" ", "")+"</a><br/>";
		if(i.equalsIgnoreCase(""))i = t;
		try{
			Message g = new MimeMessage(s);
			g.setFrom(a);				
			String[] w = i.split(",");
			InternetAddress[] x = new InternetAddress[w.length];
			if(x.length > 0){
				int c=0;
				for (String r : w) {
					if(m(r)){
						x[c] = new InternetAddress(r.trim());
						c++;
					}  
				}
				g.addRecipients(Message.RecipientType.TO,x);
			}else{
				if(m(i)){
					r = new InternetAddress(i);
					g.addRecipient(Message.RecipientType.TO,r);
				}else{
					b=false;
				}
			}
			
			if(b){
				g.setSubject(h);
		        MimeBodyPart r = new MimeBodyPart();
		        r.setContent(c+b(d)+S.h, "text/html");
		        Multipart l = new MimeMultipart();
		        l.addBodyPart(r);	        
		        if (v != null && v.length() > 0) {
		            MimeBodyPart a = new MimeBodyPart();
		            v = "\\opt\\xxxx\\"+v;
	                a.attachFile(v);
	                l.addBodyPart(a);
	                g.setContent(l);
		        }else{
		        	g.setContent(c+b(d)+S.h,"text/html");
		        }	
		        Transport.send(g);				
			}
			
		}catch (MessagingException ex) {b = false;}catch (Exception ex) {b = false;}		
		return b;
	}
	
	private boolean m(String hex) {p = Pattern.compile(e);m = p.matcher(hex);return m.matches();}
	private String b(String s){ return "<table><tbody>"+s+"</tbody></table>";}
	private Properties k(){
		Properties k = new Properties();
		/*
		k.put("mail.smtp.submitter", S.p.getProperty("smtp_submitter"));
		k.put("mail.smtp.auth", S.p.getProperty("smtp_auth"));
		k.put("mail.smtp.host", S.p.getProperty("smtp_host"));	
		k.put("mail.smtp.user", S.p.getProperty("smtp_user"));
		k.put("mail.smtp.port", S.p.getProperty("smtp_port"));
		k.put("mail.smtp.socketFactory.port", S.p.getProperty("smtp_socketFactory_port"));
		k.put("mail.smtp.starttls.enable",S.p.getProperty("smtp_starttls_enable"));
		k.put("mail.smtp.socketFactory.fallback", S.p.getProperty("smtp_socketFactory_fallback"));
		k.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		k.put("mail.smtp.ssl.enable", "true");
		k.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		MailSSLSocketFactory sf;
		try {
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			  k.put("mail.smtp.ssl.socketFactory", sf);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		k.put("mail.smtp.submitter", S.p.getProperty("smtp_submitter"));
		k.put("mail.smtp.auth", S.p.getProperty("smtp_auth"));
		k.put("mail.smtp.host", S.p.getProperty("smtp_host"));	
		k.put("mail.smtp.user", S.p.getProperty("smtp_user"));
		k.put("mail.smtp.port", S.p.getProperty("smtp_port"));
		k.put("mail.smtp.socketFactory.port", S.p.getProperty("smtp_socketFactory_port"));
		k.put("mail.smtp.starttls.enable", S.p.getProperty("smtp_starttls_enable"));
		k.put("mail.smtp.socketFactory.fallback", S.p.getProperty("smtp_socketFactory_fallback"));
		//k.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		//k.put("mail.smtp.ssl.enable", S.p.getProperty("smtp_starttls_enable"));
		//k.put("mail.smtp.ssl.trust", S.p.getProperty("smtp_host"));
		MailSSLSocketFactory sf;
		try {
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			  k.put("mail.smtp.ssl.socketFactory", sf);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return k;
	}
	
	//public static void main(String[] args){
	//	new E().E("ilmuduni@gmail.com", null, "test new mail account", S.g);
	//}
}
