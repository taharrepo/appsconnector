package s.p.cn;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.security.AccessControlException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HC {
	
	private int connectionTimeout = 20000; // 20 s
    private int readTimeout = 120000; // 120 s
    
    private String proxyHost;
    private int proxyPort;
    private String proxyUser;
    private String proxyPassword;
    
    private HttpURLConnection httpURLConnection = null;
    
    private Map<String, String> requestHeaders = new HashMap<String, String>();

    
    private static boolean isJDK14orEarlier = false;
    
    static {
        try {
            String versionStr = System.getProperty("java.specification.version");
            if (null != versionStr) {
                isJDK14orEarlier = 1.5d > Double.parseDouble(versionStr);
            }
        } catch (AccessControlException ace) {
            isJDK14orEarlier = true;
        }
    }
    
    public HC() { }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeoutInMillis) {
        this.connectionTimeout = connectionTimeoutInMillis;

    }
    
    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeoutInMillis) {
        this.readTimeout = readTimeoutInMillis;
    }

    
    /*
     * GET
     */
    public HR get(String url, byte[] data) throws Exception {
        return send(url, "GET", data);
    }
    
    public HR get(String url, RP[] requestParameters) throws Exception {
        return send(url, "GET", requestParameters);
    }

    public HR get(String url) throws Exception {
        return get(url, (byte[])null);
    }
        
    
    /*
     * POST
     */
    public HR post(String url, byte[] data) throws Exception {
    	return send(url, "POST", data);
    }
    
    public HR post(String url, RP[] requestParameters) throws Exception {
        return send(url, "POST", requestParameters);
    }   

    public HR post(String url) throws Exception {
        return post(url, (byte[])null);
    }
        
    
    /*
     * PUT
     */
    public HR put(String url, byte[] data) throws Exception {
        return send(url, "PUT", data);
    }
    
    public HR put(String url, RP[] requestParameters) throws Exception {
        return send(url, "PUT", requestParameters);
    }

    public HR put(String url) throws Exception {
        return put(url, (byte[])null);
    }
        
    
    /*
     * DELETE
     */
    public HR delete(String url, byte[] data) throws Exception {
        return send(url, "DELETE", data);
    }
    
    public HR delete(String url, RP[] requestParameters) throws Exception {
        return send(url, "DELETE", requestParameters);
    }

    public HR delete(String url) throws Exception {
        return delete(url, (byte[])null);
    }    

    
    /*
     * REQUEST HEADER
     */
    public void setRequestHeader(String name, String value) {
        requestHeaders.put(name, value);
    }

    public String getRequestHeader(String name) {
        return requestHeaders.get(name);
    }
    
    public Set<String> getRequestHeaderNames() {
        return requestHeaders.keySet();
    }
    
    
    /*
     * ENCODE PARAMS
     */
    public static String encodeParameters(RP[] requestParams) {
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < requestParams.length; j++) {
            if (j != 0) {
                buf.append("&");
            }
            try {
                buf.append(URLEncoder.encode(requestParams[j].getName(), "UTF-8")).append("=").append(URLEncoder.encode(requestParams[j].getValue(), "UTF-8"));
            } catch (java.io.UnsupportedEncodingException neverHappen) {
            }
        }
        return buf.toString();

    }
    
    
    /*
     * CLOSE CONNECTION
     */
    public void close(){
        if (httpURLConnection != null) {
        	httpURLConnection.disconnect();
        }
    }
    
    
    /*
     * SEND REQUEST 
     */
    public HR send(String url, String mtd, byte[] bytes) throws Exception {
    	OutputStream osw = null;
        try {
        	if (httpURLConnection == null) {
        		httpURLConnection = getConnection(url);
        	}
        	
        	httpURLConnection.setDoInput(true);
            for (String key : requestHeaders.keySet()) {
            	httpURLConnection.addRequestProperty(key, requestHeaders.get(key));
            }
            
            httpURLConnection.setRequestMethod(mtd);
            if (null != bytes) {
            	httpURLConnection.setDoOutput(true);
            	httpURLConnection.setRequestProperty("Content-Length", Integer.toString(bytes.length));
                osw = httpURLConnection.getOutputStream();
                osw.write(bytes);
                osw.flush();
            }
            HR res = new HR(httpURLConnection);
            return res;
        } catch (IOException ioe) {
        	throw ioe;
        } finally {
        	if (osw != null) {
        		osw.close();
        	}
        }
    }
    
    public HR send(String url, String mtd, RP[] requestParams) throws Exception {
    	String str = null;
    	if (requestParams != null) {
    		str = encodeParameters(requestParams);
    	}
    	return send(url, mtd, str != null ? str.getBytes() : null);
    }

    
    /*
     * GET CONNECTION
     */
    private HttpURLConnection getConnection(String url) throws Exception {
    	URL u = new URL(url);
    	boolean isSecure = "https".equals(u.getProtocol().toLowerCase());
    	if (isSecure) {
    		 SSLContext ctx = SSLContext.getInstance("TLS");
    	     ctx.init(new KeyManager[0], new TrustManager[] {new X509TrustManager(){
    	    	 public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
    	         public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
    	         public X509Certificate[] getAcceptedIssuers() { return null; }
    	     }}, new SecureRandom());
    	     SSLContext.setDefault(ctx);
    	}
    	
    	HttpURLConnection con = null;
        if (proxyHost != null && !"".equals(proxyHost)) {
            if (proxyUser != null && !proxyUser.equals("")) {
                Authenticator.setDefault(new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        if (getRequestorType().equals(RequestorType.PROXY)) {
                            return new PasswordAuthentication(proxyUser, proxyPassword.toCharArray());
                        } else {
                            return null;
                        }
                    }
                });
            }
            Proxy proxy = new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(proxyHost, proxyPort));
            if (isSecure) {
            	con = (HttpsURLConnection) u.openConnection(proxy);
            	((HttpsURLConnection)con).setHostnameVerifier(new HostnameVerifier() {
		            public boolean verify(String arg0, SSLSession arg1) {
		                return true;
		            }
		        });
            } else {
            	con = (HttpURLConnection) u.openConnection(proxy);
            }
        } else {
        	if (isSecure) {
            	con = (HttpsURLConnection) u.openConnection();
            	((HttpsURLConnection)con).setHostnameVerifier(new HostnameVerifier() {
		            public boolean verify(String arg0, SSLSession arg1) {
		                return true;
		            }
		        });
            } else {
            	con = (HttpURLConnection) u.openConnection();
            }            
        }
        
        if (connectionTimeout > 0 && !isJDK14orEarlier) {
            con.setConnectTimeout(connectionTimeout);
        }
        if (readTimeout > 0 && !isJDK14orEarlier) {
            con.setReadTimeout(readTimeout);
        }
        return con;
    }
}
