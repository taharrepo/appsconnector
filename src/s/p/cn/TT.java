package s.p.cn;

import java.nio.charset.Charset;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.codec.Base64;

public class TT {
	
	public static void main(String... args) {	
	}
	
	private static String p(String s){
        if(s.substring(0,2).equalsIgnoreCase("00")){
            return s = s.substring(2,s.length());
        }else if(s.substring(0,1).equalsIgnoreCase("0")){
        	return s = s.substring(1,s.length());
        }
        return s;
    }
	
	private static org.apache.http.client.methods.HttpPost e(String h, String t){
		org.apache.http.client.methods.HttpPost p = new org.apache.http.client.methods.HttpPost(t);
		p.setHeader("Content-Type", "application/xml");
		p.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encode(h.getBytes(Charset.forName("ISO-8859-1")))));
		p.getRequestLine();        
        return p;
	}

}
