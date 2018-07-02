package s.p.cn;

import com.mongodb.DB;
import com.mongodb.DBCollection;

public class T {	
	private static D b;
	private static DB d;
    private static DBCollection m, n, o;   
    public T(){m = bd().cb(d(), S.mcc, "MemberId");n = bd().cb(d(), S.pad, "store_key");o = bd().cb(d(), S.csf, "accountid");}
    public static D bd(){if(null == b)return b = new D();else return b;}
	public static DB d(){if(null == d)return d = b.c();else return d;}	
	public static DBCollection tm(){return m;}
	public static DBCollection tn(){return n;}
	public static DBCollection to(){return o;}
}
