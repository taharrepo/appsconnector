package s.p.cn;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class J {
	
	public J(){}
	
	@SuppressWarnings("unchecked")
	public String OutJsonData(String[] key, String[] v){
				
		JSONObject array= new JSONObject();
		
		for(int i=0; i<key.length;i++){
			if(v[i] == null)
				array.put(key[i],"");
			else
				array.put(key[i],v[i]);
		}
		
		return ""+array+"";
	}
	
	@SuppressWarnings("unchecked")
	public String ListOutJsonDataDefaultArray(List<String> l){
		
		JSONObject array= new JSONObject();
		
		for(int i=0; i<l.size();i++){
			if(l.get(i) == null)
				array.put(""+i+"","");
			else
				array.put(""+i+"",l.get(i));
		}
		
		StringWriter out = new StringWriter();
		try {
			array.writeJSONString(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String jsonText = out.toString();
		//System.out.print(jsonText);
		return jsonText;
	}
	
	@SuppressWarnings("unchecked")
	public String ListOutJsonData(List<String> key, List<String> l){
		
		JSONObject array= new JSONObject();
		
		for(int i=0; i<key.size();i++){
			if(i < l.size()){
				if(l.get(i) == null)
					array.put(key.get(i),"");
				else
					array.put(key.get(i),l.get(i));
			}else{
				array.put(key.get(i), "");
			}
		}
		
		return ""+array+"";
	}
	
	@SuppressWarnings("unchecked")
	public String ArrayListOutJsonData(String[] key, ArrayList<String> l){
		
		JSONObject array= new JSONObject();
		
		for(int i=0; i<key.length;i++){
			if(l.get(i) == null)
				array.put(key[i],"");
			else
				array.put(key[i],l.get(i));
		}
		
		return ""+array+"";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String ArrayListOutJsonData(List<String> data_a, String[] key_a, String data_parent_a){
		JSONObject array= new JSONObject();
		Map mp1 = new LinkedHashMap();
		List  l1 = new LinkedList();
		int k=0;
		
		for(int i=0; i<data_a.size();i++){
			for(int j=0;j<key_a.length; j++){
				if(data_a.get(k) == null)
					mp1.put(key_a[j],"");
				else
					mp1.put(key_a[j],data_a.get(k));
				
				if(k < data_a.size()-1)
					k++;
				
				if(j==key_a.length-1){
					l1.add(mp1);
					i+=key_a.length-1;
					mp1 = new LinkedHashMap();
				}			
			}
		}
				
		array.put(data_parent_a, l1);
		
		return ""+array+"";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String ArrayListOutJsonData(List<String> data_a, List<String> key_a, String data_parent_a){
		JSONObject array= new JSONObject();
		Map mp1 = new LinkedHashMap();
		
		for(int j=0;j<key_a.size(); j++){
			if(data_a.get(j) == null)
				mp1.put(key_a.get(j),"");
			else{
				if(!data_a.get(j).equalsIgnoreCase("false") && 
						!data_a.get(j).equalsIgnoreCase("true")){						
					if(key_a.get(j).equalsIgnoreCase("id_int")){
						BigInteger idn = new BigInteger(data_a.get(j));
						mp1.put(key_a.get(j).replace("id_int", "id"), idn);						
					}else{
						mp1.put(key_a.get(j), data_a.get(j));
					}
				}else{
					if(data_a.get(j).equalsIgnoreCase("false"))mp1.put(key_a.get(j), false);
					else if(data_a.get(j).equalsIgnoreCase("true"))mp1.put(key_a.get(j), true);
					
				}
			}
		}
				
		array.put(data_parent_a, mp1);
		
		return ""+array+"";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String ArrayListOutJsonDataComplex(String[] key, List<String> l, List<String> data_a, String[] key_a, String data_b){
		List<String> zero_data = new ArrayList<String>();
		
		JSONObject array= new JSONObject();
		if(l.size() > 0){
			for(int i=0; i<key.length;i++){
				if(l.get(i) == null)
					array.put(key[i],"");
				else
					array.put(key[i],l.get(i));
			}
		}
		
		LinkedHashMap m1 = new LinkedHashMap();
		for(int i=0; i<key_a.length;i++){
			if(data_a.size() <= 0){
				zero_data.add("");
				m1.put(key_a[i],zero_data.get(i));
			}else{
				if(data_a.get(i) == null)
					m1.put(key_a[i],"");
				else
					m1.put(key_a[i],data_a.get(i));			
			}
		}
		
		array.put(data_b, m1);
		
		return ""+array+"";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String ArrayListOutJsonDataComplex_b(String[] key, List<String> l, List<String> data_a, String[] key_a, String data_parent_a, List<String> data_b, String[] key_b, String data_parent_b){
		List<String> zero_data = new ArrayList<String>();
		
		JSONObject array= new JSONObject();
		if(l.size() > 0){
			for(int i=0; i<key.length;i++){
				if(l.get(i) == null)
					array.put(key[i],"");
				else
					array.put(key[i],l.get(i));
			}
		}
		
		Map mp1 = new LinkedHashMap();
		List  l1 = new LinkedList();
		int k=0;
		
		for(int i=0; i<data_a.size();i++){
			for(int j=0;j<key_a.length; j++){
				if(data_a.get(k) == null)
					mp1.put(key_a[j],"");
				else
					mp1.put(key_a[j],data_a.get(k));
				
				if(k < data_a.size()-1)
					k++;
				
				if(j==key_a.length-1){
					l1.add(mp1);
					i+=key_a.length-1;
					mp1 = new LinkedHashMap();
				}			
			}
		}
				
		array.put(data_parent_a, l1);
		
		if(null != key_b){
			zero_data = new ArrayList<String>();
			LinkedHashMap m1 = new LinkedHashMap();
			for(int i=0; i<key_b.length;i++){
				if(data_b.size() <= 0){
					zero_data.add("");
					m1.put(key_b[i],zero_data.get(i));
				}else{
					if(data_b.get(i) == null)
						m1.put(key_b[i],"");
					else
						m1.put(key_b[i],data_b.get(i));			
				}
			}
			
			array.put(data_parent_b, m1);
		}
		
		return ""+array+"";
	}
	
	@SuppressWarnings({ "rawtypes" })
	public String ArrayListOutJsonDataComplex_c(Map<Integer, List<String>> ms, Map<Integer, List<String>> ms1, List<String> data_a, String[] key_a, List<String> data_b, String[] key_b, String data_parent_b, int length_id){
		List<String> zero_data = new ArrayList<String>();		
		Map mp1 = new HashMap();
		Map mp2 = new HashMap();
		List  l1 = new LinkedList();
		List  l2 = new LinkedList();
		
		if(length_id > 1){
			
			for(int i=0; i<length_id;i++){
				if(i==0){
					setData_a_JsonComplex_c(ms.get(i), key_a, mp1, l1);
					setData_b_JsonComplex_c(zero_data, ms1.get(i), key_b, data_parent_b, mp2, l1, l2);
				}else{
					mp1 = new HashMap();
					l1 = new LinkedList();
					setData_a_JsonComplex_c(ms.get(i), key_a, mp1, l1);
					
					mp2 = new HashMap();
					setData_b_JsonComplex_c(zero_data, ms1.get(i), key_b, data_parent_b, mp2, l1, l2);
				}
			
			}
			
		}else{
			
			setData_a_JsonComplex_c(data_a, key_a, mp1, l1);
			setData_b_JsonComplex_c(zero_data, data_b, key_b, data_parent_b, mp2, l1, l2);
		
		}
		
		String jsonString = JSONValue.toJSONString(l2);
		
		return jsonString;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void setData_a_JsonComplex_c(List<String> data_a, String[] key_a, Map mp1, List l1){
		
		int k=0;
		
		for(int i=0; i<data_a.size();i++){
			for(int j=0;j<key_a.length; j++){
				if(data_a.get(k) == null)
					mp1.put(key_a[j],"");
				else
					mp1.put(key_a[j],data_a.get(k));
				
				if(k < data_a.size()-1)
					k++;
				
				if(j==key_a.length-1){
					l1.add(mp1);
					i+=key_a.length-1;
					mp1 = new LinkedHashMap();
				}			
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setData_b_JsonComplex_c(List<String> zero_data, List<String> data_b, String[] key_b, String data_parent_b, Map mp2, List l1, List l2){
		
		zero_data = new ArrayList<String>();
		for(int i=0; i<key_b.length;i++){
			if(data_b.size() <= 0){
				zero_data.add("");
				mp2.put(key_b[i],zero_data.get(i));
			}else{
				if(data_b.get(i) == null)
					mp2.put(key_b[i],"");
				else
					mp2.put(key_b[i],data_b.get(i));			
			}
			
			if(i==key_b.length-1)
				mp2.put(data_parent_b,l1);
		}
		
		l2.add(mp2);
	}
	
	@SuppressWarnings("rawtypes")
	public String[] parseJsonData(String s, int length){
		JSONParser parser=new JSONParser();
		String[] sa = new String[length];
		int i=0;
		try {        
	        ContainerFactory containerFactory = new ContainerFactory(){
				public List creatArrayContainer() { return new LinkedList();}
				public Map createObjectContainer() { return new LinkedHashMap();}           
	         };
	         
	         Map json = (Map)parser.parse(s, containerFactory);
	         Iterator iter = json.entrySet().iterator();
	         //System.out.println("==iterate result==");
	         
	         while(iter.hasNext()){
	           Map.Entry entry = (Map.Entry)iter.next();
	           //System.out.println(entry.getKey() + "=>" + entry.getValue());
	           sa[i] = (String) entry.getValue();
	           i++;
	         }
	        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return sa;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,String> parseMapJsonData(String s, int length){
		JSONParser parser=new JSONParser();
		Map<String,String> sa = new HashMap<String,String>();
		
		try {        
	        ContainerFactory containerFactory = new ContainerFactory(){
				public List creatArrayContainer() { return new LinkedList();}
				public Map createObjectContainer() { return new LinkedHashMap();}           
	         };
	         
	         Map json = (Map)parser.parse(s, containerFactory);
	         Iterator iter = json.entrySet().iterator();
	         //System.out.println("==iterate result==");
	         
	         while(iter.hasNext()){
	           Map.Entry entry = (Map.Entry)iter.next();
	           //System.out.println(entry.getKey() + "=>" + entry.getValue());
	           sa.put(entry.getKey().toString().toLowerCase(), (String) entry.getValue());
	         }
	        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return sa;
	}

}
