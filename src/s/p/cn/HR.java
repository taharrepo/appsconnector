package s.p.cn;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class HR {
	
	public static final int OK 						= 200;// OK: Success!
	public static final int NOT_MODIFIED 			= 304;// Not Modified: There was no new data to return.
	public static final int TEMPORARY_REDIRECT 		= 307;// Response redirect to other resource 
	public static final int BAD_REQUEST 			= 400;// Bad Request: The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.
	public static final int NOT_AUTHORIZED 			= 401;// Not Authorized: Authentication credentials were missing or incorrect.
	public static final int FORBIDDEN 				= 403;// Forbidden: The request is understood, but it has been refused.  An accompanying error message will explain why.
	public static final int NOT_FOUND 				= 404;// Not Found: The URI requested is invalid or the resource requested, such as a user, does not exists.
	public static final int NOT_ACCEPTABLE 			= 406;// Not Acceptable: Returned by the Search API when an invalid format is specified in the request.
	public static final int INTERNAL_SERVER_ERROR 	= 500;// Internal Server Error: Something is broken.  Please post to the group so the Twitter team can investigate.
	public static final int BAD_GATEWAY 			= 502;// Bad Gateway: Twitter is down or being upgraded.
	public static final int SERVICE_UNAVAILABLE 	= 503;// Service Unavailable: The Twitter servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.
    
	
	private static ThreadLocal<DocumentBuilder> builders =
            new ThreadLocal<DocumentBuilder>() {
                protected DocumentBuilder initialValue() {
                    try {
                        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    } catch (ParserConfigurationException ex) {
                        throw new ExceptionInInitializerError(ex);
                    }
                }
            };

    private int statusCode;
    private Document responseAsDocument = null;
    private String responseAsString = null;
    private InputStream is;
    private HttpURLConnection con;
    private boolean streamConsumed = false;


    public HR(HttpURLConnection con) throws IOException {
        this.con = con;
        this.statusCode = con.getResponseCode();
        if(null == (is = con.getErrorStream())){
            is = con.getInputStream();
        }
        if (null != is && "gzip".equals(con.getContentEncoding())) {
            is = new GZIPInputStream(is);
        }
    }

    public HR(String content) {
        this.responseAsString = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseHeader(String name) {
        return con.getHeaderField(name);
    }
    
    public Set<String> getResponseHeaderKeys() {
    	return con.getHeaderFields().keySet();
    }

    public InputStream asStream() {
        if(streamConsumed){
            throw new IllegalStateException("Stream has already been consumed.");
        }
        return is;
    }

    public String asString() throws Exception {
        if(null == responseAsString){
            BufferedReader br;
            InputStream stream = null;
            try {
                stream = asStream();
                if (null == stream) {
                    return null;
                }
                br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                StringBuffer buf = new StringBuffer();
                String line;
                while (null != (line = br.readLine())) {
                    buf.append(line).append("\n");
                }
                this.responseAsString = buf.toString();
                //stream.close();
                //con.disconnect();
                streamConsumed = true;
            } catch (NullPointerException npe) {
                throw new Exception(npe.getMessage(), npe);
            } catch (IOException ioe) {
                throw new Exception(ioe.getMessage(), ioe);
            } finally {
            	if (stream != null) {
            		try {
						stream.close();
					} catch (IOException e) { }
            	}
            }
        }
        return responseAsString;
    }

    public Document asDocument() throws Exception {
        if (null == responseAsDocument) {
            try {
                this.responseAsDocument = builders.get().parse(new ByteArrayInputStream(asString().getBytes("UTF-8")));
            } catch (SAXException saxe) {
                throw new Exception("The response body was not well-formed:\n" + responseAsString, saxe);
            } catch (IOException ioe) {
                throw new Exception("There's something with the connection.", ioe);
            }
        }
        return responseAsDocument;
    }

    public InputStreamReader asReader() {
        try {
            return new InputStreamReader(is, "UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            return new InputStreamReader(is);
        }
    }

    /*public void disconnect(){
        if (con != null) {
        	con.disconnect();
        }
    }*/

    public String toString() {
        if(null != responseAsString){
            return responseAsString;
        }
        return "Response{" +
                "statusCode=" + statusCode +
                ", response=" + responseAsDocument +
                ", responseString='" + responseAsString + '\'' +
                ", is=" + is +
                ", con=" + con +
                '}';
    }
    
}
