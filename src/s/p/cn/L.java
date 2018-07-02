package s.p.cn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L {
	@SuppressWarnings("rawtypes")
	public Map<String, Map> c(int f){
		Map<String, Map> m = new HashMap<String, Map>();
		Map<String, String> o = new HashMap<String, String>();
		List<String> l = new ArrayList<String>();
		String[] k = new String[]{"Member","MemberId","Lvl","FullName","JoiningDate","BirthDate","FixedPhone","MobilePhone","Email"};
		if(null == S.p)S.p = new CF().l(S.p, "cfg", "c");
		ResultSet rs = null;
		Statement s = null;
		Connection c = null;
	    try {
	         Class.forName(S.p.getProperty("dd").toString());
	         c = DriverManager.getConnection(
	        		 S.p.getProperty("du").toString(),
	        		 S.p.getProperty("ds").toString(), 
	        		 S.p.getProperty("dp").toString());
	         s = c.createStatement();
	         rs = s.executeQuery("SELECT Member,MemberNewCode,Lvl,FullName,JoiningDate,BirthDate,FixedPhone,MobilePhone,Email FROM Members ORDER BY MemberNewCode OFFSET "+f+" ROWS FETCH NEXT 10000 ROWS ONLY");
	         int x=0;
	         while (rs.next()) {
	        	 o = new HashMap<String, String>();
	        	 o.put(k[0], rs.getString(1)==null?"":rs.getString(1));
	        	 o.put(k[1], rs.getString(2)==null?"":rs.getString(2));
	        	 o.put(k[2], rs.getString(3)==null?"":rs.getString(3));
	        	 o.put(k[3], rs.getString(4)==null?"":rs.getString(4));
	        	 o.put(k[4], rs.getString(5)==null?"":rs.getString(5));
	        	 o.put(k[5], rs.getString(6)==null?"":rs.getString(6));
	        	 o.put(k[6], rs.getString(7)==null?"":rs.getString(7));
	        	 o.put(k[7], rs.getString(8)==null?"":rs.getString(8));
	        	 o.put(k[8], rs.getString(9)==null?"":rs.getString(9));
	        	 m.put(String.valueOf(x), o);
	        	 x++;
	        	 
	         }
	    } catch (Exception e) {e.printStackTrace();
	    } finally{
	    	if(null != rs)
				try {
					s.close();
					rs.close();
					c.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
	    
	    return m;
	}

}
