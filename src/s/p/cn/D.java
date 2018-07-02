package s.p.cn;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoOptions;

public class D {
	
	public static D ins;
	private MongoClient mc = null;
	private DB d = null;
	private DBCollection c = null;
	private MongoOptions o;
	
	public DB c(){
		if(null == d){
			try {				
				if(null == o){
					o = new MongoOptions();
					o.connectionsPerHost = 40;
				}
				mc = new MongoClient("localhost", 27017);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	    	d = mc.getDB("connector_apps_ph");
		}
		return d;
	}
	
	public DBCollection cb(DB d, String t, String i){	
		c = d.getCollection(t);
		c.ensureIndex(i);	
		return c;
	}
	
	public int i1(DBCollection t, Map<Object, Object> d){
		
		int i = 0;
		try{
			BasicDBObject document = new BasicDBObject();
			document.put("MemberId", d.get("MemberId").toString());
			document.put("FullName", d.get("FullName").toString());
			document.put("FixedPhone", d.get("FixedPhone").toString());
			document.put("MobilePhone", d.get("MobilePhone").toString());
			document.put("Email", d.get("Email").toString());
			document.put("dob", d.get("dob"));
			t.insert(document);
			i=1;
		}catch(Exception e){
			e.getMessage();			
		}
		
		return i;
	
	}
	
	public int a(DBCollection t, Map<Object, Object> d){
		
		int i = 0;
		try{
			BasicDBObject document = new BasicDBObject();
			document.put("store_key", d.get("store_key").toString());
			document.put("storename", d.get("storename").toString());
			document.put("address", d.get("address").toString());
			document.put("telno", d.get("telno").toString());
			document.put("region_name", d.get("region_name").toString());
			document.put("province_name", d.get("province_name").toString());
			document.put("city_muni_name", d.get("city_muni_name").toString());
			document.put("latitude", d.get("latitude").toString());
			document.put("longitude", d.get("longitude").toString());
			document.put("island", d.get("island").toString());
			t.insert(document);
			i=1;
		}catch(Exception e){
			e.getMessage();			
		}
		
		return i;
	
	}
	
	public int b(DBCollection t, Map<String, String> d){
		
		int i = 0;
		try{
			BasicDBObject document = new BasicDBObject();
			document.put("accountid", d.get("accountid"));
			document.put("memberid", d.get("memberid"));
			document.put("email", d.get("email"));
			document.put("name", d.get("name"));
			document.put("dob", d.get("dob"));
			t.insert(document);
			i=1;
		}catch(Exception e){
			e.getMessage();		
		}
		
		return i;
	
	}
	
	public String g(DBCollection t, String[] r) {
		String s = "";
	    BasicDBObject a = new BasicDBObject();
	    a.put(r[0], r[1]);
	    DBCursor cursor = t.find(a);
	    try {while(cursor.hasNext()) {s=String.valueOf(cursor.next());}}catch(Exception e){e.getMessage();} finally {cursor.close();}return s;
	}
	
	public String h(DBCollection t, String[] r, String[] u) {
		String s = "";
	    BasicDBObject a = new BasicDBObject();  
	    if(null != u){
		    List<BasicDBObject> o = new ArrayList<BasicDBObject>();
			o.add(new BasicDBObject(r[0], r[1]));
			o.add(new BasicDBObject(u[0], u[1]));
			a.put("$and", o);	
	    }else{
	    	a.put(r[0], r[1]);
	    }
	    DBCursor cursor = t.find(a);
	    try {while(cursor.hasNext()) {s=String.valueOf(cursor.next());}}catch(Exception e){e.getMessage();} finally {cursor.close();}return s;
	}

	public void i(DBCollection t, String[] r){
		List<String> l = new ArrayList<String>();
		BasicDBObject a = new BasicDBObject();
	    a.put(r[0], r[1]);
		DBCursor c = t.find(a);
		try {while(c.hasNext()) {l.add(String.valueOf(c.next()));}}catch(Exception e){e.getMessage();} finally {c.close();}
	}
	
	public List<String> j(DBCollection tableName, String[] param) {
		List<String> l = new ArrayList<String>();
	    BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put(param[0], param[1]);
	    DBCursor cursor = tableName.find(whereQuery);
	    try {while(cursor.hasNext()) {l.add(String.valueOf(cursor.next()));}}catch(Exception e){e.getMessage();} finally {cursor.close();}
	    
	    return l;
	}
	
	public List<String> k(DBCollection tableName){
		List<String> l = new ArrayList<String>();
	    DBCursor cursor = tableName.find();
	    try {while(cursor.hasNext()) {l.add(String.valueOf(cursor.next()));}}catch(Exception e){e.getMessage();} finally {cursor.close();}
	    return l;
	}
	
	public int u(DBCollection tableName, Map<String, String> mdata, String[] param1, String[] param2, String tag) {
		int update = 0;
		try{			
			BasicDBObject newDocument = new BasicDBObject();
			DBObject history = new BasicDBObject();
			history = new BasicDBObject().append("email", mdata.get("email").toString());
	        newDocument = new BasicDBObject("$set", history);
			
			BasicDBObject searchQuery;
			if(null == param2)
				searchQuery = new BasicDBObject().append(param1[0], param1[1]);
			else{
				searchQuery = new BasicDBObject();    
			    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
				obj.add(new BasicDBObject(param1[0], param1[1]));
				obj.add(new BasicDBObject(param2[0], param2[1]));
				searchQuery.put("$and", obj);
			}
			
			tableName.updateMulti(searchQuery, newDocument);
			update = 1;
		    
		}catch(Exception e){
			e.getMessage();
		}
	    return update;
	}
	
	public void d(DB d, String t){DBCollection c = d.getCollection(t);c.drop();}

}
