package s.p.cn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.codec.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.google.gson.Gson;

public class C implements Runnable {		
	Thread t;
    Socket s;
    String m;
    ObjectInputStream ois;
    ObjectOutputStream os;
    BufferedReader ic = null;
	DataOutputStream oc = null;
	Pattern p;
	Matcher hi;
	String pu = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
    public C(Socket s) {
        this.s = s;
        t = new Thread(this);
        t.start();
        synchronized (this) {
            try {
				this.wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}catch (Exception e) {
				e.getMessage();
			}
        }
    }

	@Override
	public void run() {
		synchronized (C.this){C.this.notify();}
		try{            
	        ois = new ObjectInputStream(s.getInputStream());
	        m = (String) ois.readObject();
	        os = new ObjectOutputStream(s.getOutputStream());	            	         
	        if ("c".equalsIgnoreCase(m)) {if(s != null)os.writeObject("1");return;} else if ("s".equalsIgnoreCase(m)) {os.writeObject("1");System.exit(1);return;}else{os.writeObject(g(u(m),m.split("_")[1],(m.split("_")[2]).split("#")[0]));}	                        		        	
	        ois.close();
	        os.close();
	        s.close();            
	     } catch (IOException e) {	    	 
	    	try {
	    		os.writeObject("Status 404");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
	     } catch (ClassNotFoundException e) {
	    	try {
	    		os.writeObject("Status 404");
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
	     }catch (Exception e) {
	    	try {
	    		os.writeObject("Status 404");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	     }finally{
	    	 try {				
				if(ois != null){ois.close();}
	    		if(os != null){os.close();}
	    		s.close();
	    	 } catch (IOException e) {
	    		 e.getMessage();
	    	 }
	     }
	}
	private String u(String x){if(null == S.p)S.p = new CF().l(S.p, "cfg", "c");if(x.indexOf("#") != -1)x = x.split("#")[1];else x="";return S.p.getProperty("u").toString()+x;}
	private enum e{Members, is, r, u, p, t, cm;}
	private String g(String u, String z, String y) {
		String t=S.s;
    	if(u.indexOf("http") != -1){ 
    		switch(e.valueOf(u.split("\\/")[3])){
	    		case Members:
	    			try{	    		
	    				int f = Integer.parseInt(m.split("#")[2]);
	    				L l = new L();
	    				Map m = l.c(f);
	    				Iterator iter = m.entrySet().iterator();
	    		        while(iter.hasNext()){
	    		        	Map.Entry r = (Map.Entry)iter.next();
	    		        	Map p = (Map) r.getValue();
	    		        	Map<Object, Object> a = new HashMap<Object, Object>();
	    		        	a.put("MemberId", p.get("MemberId").toString());
    		        		a.put("FullName", p.get("FullName").toString());
		    	        	a.put("FixedPhone", p.get("FixedPhone").toString());
		    	        	a.put("MobilePhone", p.get("MobilePhone").toString());
		    	        	a.put("Email", p.get("Email").toString());
		    	        	a.put("dob", p.get("BirthDate").toString().split("T")[0]);
		    	        	t = T.bd().i1(T.tm(), a) == 1 ? t=S.s:S.r;
	    		        }
	    	    	}catch(Exception e){
	    				t=S.r;
	    			}
	    			break;
	    		case is:
	    			String d = T.bd().h(T.tm(), new String[]{"MemberId", m.split("#")[2]},new String[]{"dob", m.split("#")[3]});
	    			Map<?, ?> l = new Gson().fromJson(d, Map.class);
	    			t = a(m.split("#")[3],l.get("dob").toString()) == true ?S.s:S.r;
	    			break;
	    		case r:
	    			String ri = m.split("#")[2];String ng = m.split("#")[3];String st = m.split("#")[4];
	    			String in = T.bd().h(T.to(), new String[]{"memberid", ri},new String[]{"dob", ng});
	    			boolean et = false;
	    			if(null != in){
	    				if(!in.equalsIgnoreCase("")){
	    					et=true;	    					
	    					Map<?, ?> ap = new Gson().fromJson(in, Map.class);
	    					
	    					List<String> f = new ArrayList<String>();
			        		f.add(ap.get("accountid").toString());
			        		f.add(st);
			        		f.add(S.j);
			        		List<String> g = new ArrayList<String>();
			        		g.add("id_int");
			        		g.add("email");
			        		g.add("note");	    		
			        		String b = new JO().callArrayListJsonDataComplexMiniTwo(f, g, "customer");
			        		String c = ap.get("accountid").toString()+".json";
			        		org.apache.http.impl.client.DefaultHttpClient n = new org.apache.http.impl.client.DefaultHttpClient();
			    			org.apache.http.client.methods.HttpPut q = t(S.m+":"+S.n, S.a+c, "application/json");	        							
			    			try {
								q.setEntity(new org.apache.http.entity.StringEntity(b));
								org.apache.http.HttpResponse j = n.execute(q);
								String v = EntityUtils.toString(j.getEntity(), "UTF-8");
								Map<?, ?> i = new Gson().fromJson(v, Map.class);
			    	    		Map<?, ?> a = (Map<?, ?>) i.get("customer");	    	    		
			    	    		t = null == a ? S.r:S.s;
			    	    		if(t.equalsIgnoreCase(S.s)){
			    	    			org.apache.http.client.methods.HttpPost tp = e(S.m+":"+S.n, S.a+ap.get("accountid").toString()+S.f, "application/json");
			    					j = new org.apache.http.impl.client.DefaultHttpClient().execute(tp);
									v = EntityUtils.toString(j.getEntity(), "UTF-8");
									i = new Gson().fromJson(v, Map.class);
				    	    		String s = i.get("account_activation_url").toString();
				    	    		if(null != s){
				    	    			t = new E().E(st, null, s, S.g.replace("__n", ap.get("name").toString())) == true ? S.s:S.r;
				    	    			if(t.equalsIgnoreCase(S.s)){
				    	    				Map<String, String> m = new HashMap<String, String>();
						    	        	m.put("email", st);
				    	    				t = T.bd().u(T.to(), m, new String[]{"accountid", ap.get("accountid").toString()}, null, "") == 1 ? t=S.s:S.r;
				    	    			}
				    	    		}else{
				    	    			t = S.r;
				    	    		}
			    	    		}
							} catch (Exception e1) {
								t=S.r;
							}
	    				}
	    			}
	    			if(!et){
		    			String e = T.bd().h(T.tm(), new String[]{"MemberId", ri},new String[]{"dob", ng});
		    			Map<?, ?> o = new Gson().fromJson(e, Map.class);		
		    			List<String> f = new ArrayList<String>();
		        		f.add(o.get("FullName").toString());
		        		f.add("");
		        		f.add(m.split("#")[4]);
		        		String x = o.get("MobilePhone").toString().equalsIgnoreCase("") ? o.get("FixedPhone").toString(): o.get("MobilePhone").toString();
		        		if(x.equalsIgnoreCase(""))f.add("+639882222222");
		        		else f.add("+63"+p(x));
		        		f.add(o.get("MemberId").toString());
		        		f.add("true");		
		        		List<String> g = new ArrayList<String>();
		        		g.add("first_name");
		        		g.add("last_name");
		        		g.add("email");
		        		g.add("phone");
		        		g.add("tags");
		        		g.add("verified_email");	    		
		        		String b = new JO().callArrayListJsonDataComplexMiniTwo(f, g, "customer");	        		
		        		org.apache.http.impl.client.DefaultHttpClient n = new org.apache.http.impl.client.DefaultHttpClient();
		    			org.apache.http.client.methods.HttpPost q = e(S.m+":"+S.n, S.t, "application/json");	        							
		        		try {
							q.setEntity(new org.apache.http.entity.StringEntity(b));
							org.apache.http.HttpResponse j = n.execute(q);
							String v = EntityUtils.toString(j.getEntity(), "UTF-8");
							Map<?, ?> i = new Gson().fromJson(v, Map.class);
		    	    		Map<?, ?> a = (Map<?, ?>) i.get("customer");	    	    		
		    	    		t = null == a ? S.r:S.s;
		    	    		if(t.equalsIgnoreCase(S.s)){
		    	    			Double k = (Double)a.get("id");
		    	    			String c = String.format("%.0f", k);
			    	    		q = e(S.m+":"+S.n, S.a+c+S.f, "application/json");
			    	    		j = n.execute(q);
			    	    		v = EntityUtils.toString(j.getEntity(), "UTF-8");
			    	    		i = new Gson().fromJson(v, Map.class);
			    	    		String s = i.get("account_activation_url").toString();
			    	    		if(null != s){
			    	    			t = new E().E(st, null, s, S.g.replace("__n", a.get("first_name").toString())) == true ? S.s:S.r;
			    	    			if(t.equalsIgnoreCase(S.s)){
			    	    				Map<String, String> m = new HashMap<String, String>();
				    		        	m.put("accountid", c);
			    		        		m.put("memberid", ri);
					    	        	m.put("email", st);
					    	        	m.put("name", a.get("first_name").toString());
					    	        	m.put("dob", ng);
			    	    				t = T.bd().b(T.to(), m) == 1 ? t=S.s:S.r;
			    	    			}
			    	    		}else{
			    	    			t = S.r;
			    	    		}
		    	    		}
						} catch (Exception e1) {
							t=S.r;
						}
	    			}
	    			break;
	    		case p:
	    			List<String> e = T.bd().k(T.tn());
	    			String[] ma = new String[]{"store_key", "store_name", 
	    										"province_name","city_muni_name",
	    										"address"};
	    			List<String> li = new ArrayList<String>();
	    			for(String s: e){
	    				Map<?, ?> o = new Gson().fromJson(s, Map.class);
	    				li.add(o.get("store_key").toString());
	    				li.add(o.get("storename").toString());
	    				li.add(o.get("province_name").toString());
	    				li.add(o.get("city_muni_name").toString());
	    				li.add(o.get("address").toString()+"#"+o.get("island").toString());
	    			}
	    			String x= "list";
	    			t = new JO().callArrayListJsonDataComplexMini(li, ma, x);			    			
	    			break;
	    		case u:
	    			String p = System.getProperty("user.dir")+S.o;       	
	    			try {
	    				Workbook w = new XSSFWorkbook(new FileInputStream(new File(p)));	    				
	    				Sheet k = w.getSheetAt(1);
	    	            Iterator<Row> r = k.iterator();
	    	            Map<String, Map> m = new HashMap<String, Map>();
	    	            int tr=0, ap = 0;
	    	            while (r.hasNext()) {
	    	                Row c = r.next();
	    	                if(ap > 0){
		    	                Iterator<Cell> s = c.iterator();
		    	                Map<Object, Object> v = new HashMap<Object, Object>();
		    	                while (s.hasNext()) {
		    	                    Cell j = s.next();
		    	                    if (j.getCellTypeEnum() == CellType.STRING) {
		    	                    	s(j, v, j.getStringCellValue());
		    	                    } else if (j.getCellTypeEnum() == CellType.NUMERIC) {
		    	                    	s(j, v, String.valueOf(j.getNumericCellValue()).replace(".0", ""));
		    	                    }else if (j.getCellTypeEnum() == CellType.FORMULA) {
		    	                    	switch(j.getCachedFormulaResultType()) {
			    	                        case Cell.CELL_TYPE_NUMERIC:
			    	                        	break;
			    	                        case Cell.CELL_TYPE_STRING:
			    	                        	s(j, v, j.getRichStringCellValue().toString());
			    	                            break;
		    	                    	}
		    	                    }else if (j.getCellTypeEnum() == CellType._NONE) {
		    	                    }else if (j.getCellTypeEnum() == CellType.BLANK) {
		    	                    }else if (j.getCellTypeEnum() == CellType.ERROR) {}
	
		    	                }
		    	                m.put(""+tr, v);
		    	                tr++;
	    	                }
	    	                ap++;
	    	            }
	    	            Iterator er = m.entrySet().iterator();
	    		        while(er.hasNext()){
	    		        	Map.Entry en = (Map.Entry)er.next();
	    		        	Map h = (Map) en.getValue();
	    		        	Map<Object, Object> a = new HashMap<Object, Object>();
	    		        	a.put("store_key", h.get("store_key").toString());	    		        	
	    		        	a.put("storename", h.get("storename").toString());    		        		
    		        		a.put("address", h.get("address").toString());		    	        	
		    	        	a.put("telno", h.get("telno").toString());		    	        	
		    	        	a.put("region_name", h.get("region_name").toString());		    	        	
		    	        	a.put("province_name", h.get("province_name").toString());		    	        	
		    	        	a.put("city_muni_name", h.get("city_muni_name").toString());		    	        	
		    	        	a.put("latitude", h.get("latitude").toString());	    	        	
		    	        	a.put("longitude", h.get("longitude").toString());	    	        	
		    	        	a.put("island", h.get("island").toString());
		    	        	t = T.bd().a(T.tn(), a) == 1 ? t=S.s:S.r;		    	        	
	    		        }	    		        	    		    
	    				
	    			} catch (FileNotFoundException ex) {
	    				// TODO Auto-generated catch block
	    				ex.printStackTrace();
	    			} catch (IOException ex) {
	    				// TODO Auto-generated catch block
	    				ex.printStackTrace();
	    			}  
	    			break;
	    		case t:
	    			t = new E().E("", null, "", S.g) == true ? S.s:S.r;
	    			break;
	    		case cm:
	    			if(m(m.split("#")[2])){
	    				String n = T.bd().h(T.to(), new String[]{"email", m.split("#")[2]}, null);
	    				t = n.equalsIgnoreCase("") ? t=S.r:S.s;
	    			}else t = S.r;
	    			
	    			break;
    		}
	    	
    	}
    	return t;
	}
	
	private void s(Cell j, Map<Object, Object> m, String d){
		switch(j.getColumnIndex()){
        	case 0:
	        	m.put("store_key", d);
	        	break;
        	case 1:
	        	m.put("storename", d);
	        	break;
        	case 2:
	        	m.put("address", d);
	        	break;
        	case 3:
	        	m.put("telno", d);
	        	break;
        	case 4:
	        	m.put("region_name", d);
	        	break;
        	case 5:
	        	m.put("province_name", d);
	        	break;
        	case 6:
	        	m.put("city_muni_name", d);
	        	break;
        	case 7:
	        	m.put("latitude", d);
	        	break;
        	case 8:
	        	m.put("longitude", d);
	        	break;
        	case 9:
	        	m.put("island", d);
	        	break;
		}
	}
	
	private String p(String s){
        if(s.substring(0,2).equalsIgnoreCase("00"))return s = s.substring(2,s.length());else if(s.substring(0,1).equalsIgnoreCase("0"))return s = s.substring(1,s.length());return s;
    }
	
	@SuppressWarnings("unused")
	private void d(HC c, String h, String clength){
		c.setRequestHeader("accept", "application/xml");
		c.setRequestHeader("Content-Length", "1564");
		c.setRequestHeader(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encode(h.getBytes(Charset.forName("ISO-8859-1")))));
	}
	private org.apache.http.client.methods.HttpPost e(String h, String t, String y){
		org.apache.http.client.methods.HttpPost p = new org.apache.http.client.methods.HttpPost(t);
		p.setHeader("Content-Type", y);
		p.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encode(h.getBytes(Charset.forName("ISO-8859-1")))));
		p.getRequestLine();        
        return p;
	}
	private org.apache.http.client.methods.HttpPut t(String h, String t, String y){
		org.apache.http.client.methods.HttpPut p = new org.apache.http.client.methods.HttpPut(t);
		p.setHeader("Content-Type", y);
		p.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encode(h.getBytes(Charset.forName("ISO-8859-1")))));
		p.getRequestLine();        
        return p;
	}
	private boolean a(String x, String y){
		DateTimeFormatter f = DateTimeFormatter.ofPattern( "yyyy/MM/dd" );
        LocalDate r = LocalDate.parse(c(x) , f );
        LocalDate s = LocalDate.parse(c(y) , f );
        return r.isEqual(s);
	}
	private String c(String v){
	    SimpleDateFormat i = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat o= new SimpleDateFormat("yyyy/MM/dd");
	    try {String f = o.format(i.parse(v));return f;} catch (ParseException e) {e.printStackTrace();}return "";
	}
	private boolean m(String hex) {p = Pattern.compile(pu);hi = p.matcher(hex);return hi.matches();}
	
}
