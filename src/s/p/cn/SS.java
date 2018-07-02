package s.p.cn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SS {
	
    public static ServerSocket s;
    private int t;
    public SS(int t) {
        this.t = t;
        S.p = new CF().l(S.p, "cfg", "c");
    }
    
    public void start() throws IOException {		
    	new T();
    	s = new ServerSocket(t);
        while (true) {
            try {        		
            	Socket c = s.accept();               
            	new C(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        
    }   
    public static void exit(){
    	System.exit(1);
    }   
    private static String execute(String c, int r) {
		Socket s = null;
		ObjectOutputStream os = null;
		ObjectInputStream ois  = null;
		try {
			InetAddress h = InetAddress.getByName(S.p.getProperty("sn").toString());
            s = new Socket(h.getHostName(), r);
            os = new ObjectOutputStream(s.getOutputStream());
            os.writeObject(c);
            ois = new ObjectInputStream(s.getInputStream());
            return (String) ois.readObject();
        } catch (IOException e) {
        	return "E01";
        } catch (ClassNotFoundException e) {
        	return e.getMessage();
		} finally {
        	if (ois != null) {
        		try { ois.close(); } catch (IOException e) {}
        	}
        	if (os != null) {
        		try { os.close(); } catch (IOException e) {}
        	}
        	if (s != null) {
        		try { s.close(); } catch (IOException e) {}
        	}
        }
	}
    public static void main(String[] a) {
    	if(null == S.p)S.p = new CF().l(S.p, "cfg", "c");
		int n = Integer.parseInt(S.p.getProperty("so"));
        try { 
        	String c=a[0]; 
        	String s = execute(c, n);
        	if("c".equals(c)){
				if ("1".equals(s)) {
					return;
				} else if ("E01".equalsIgnoreCase(s)) {
					return;
				}
        	}else if("s".equals(c)){
    			if ("1".equals(s)) {
    				return;
    			} else if ("E01".equalsIgnoreCase(s)) {
					return;
				} 
            }				
        	SS k = new SS(n);
            k.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
