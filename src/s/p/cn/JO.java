package s.p.cn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JO {
	private J j;
	public JO(){}
	
	public String callListJsonDataDefaultString(List<String> l){
		
		j = new J();
		
		return j.ListOutJsonDataDefaultArray(l);
		
	}
	
	public String callJsonData(String[] key, String[] v){
		
		j = new J();
		
		return j.OutJsonData(key, v);
		
	}
	
	public String callListJsonData(List<String> key, List<String> l){
		
		j = new J();
		
		return j.ListOutJsonData(key, l);
		
	}
	
	public String callArrayListJsonData(List<String> key, ArrayList<String> l){
		
		j = new J();
		
		return j.ListOutJsonData(key, l);
		
	}
	
	public String callArrayListJsonDataComplex(String[] key, List<String> l, List<String> data_a, String[] key_a, String data_b){
		
		j = new J();
		
		return j.ArrayListOutJsonDataComplex(key, l, data_a, key_a, data_b);
		
	}
	
	public String callArrayListJsonDataComplexMini(List<String> data_a, String[] key_a, String data_parent_a){
		
		j = new J();
		
		return j.ArrayListOutJsonData(data_a, key_a, data_parent_a);
		
	}
	
	public String callArrayListJsonDataComplexMiniTwo(List<String> data_a, List<String> key_a, String data_parent_a){
		
		j = new J();
		
		return j.ArrayListOutJsonData(data_a, key_a, data_parent_a);
		
	}
	
	public String callArrayListJsonDataComplex_b(String[] key, List<String> l, List<String> data_a, String[] key_a, String data_parent_a, List<String> data_b, String[] key_b, String data_parent_b){
		
		j = new J();
		
		return j.ArrayListOutJsonDataComplex_b(key, l, data_a, key_a, data_parent_a, data_b, key_b, data_parent_b);
		
	}
	
	public String callArrayListJsonDataComplex_c(Map<Integer, List<String>> ms, Map<Integer, List<String>> ms1, List<String> data_a, String[] key_a, List<String> data_b, String[] key_b, String data_parent_b, int length_id){
		
		j = new J();
		
		return j.ArrayListOutJsonDataComplex_c(ms, ms1, data_a, key_a, data_b, key_b, data_parent_b, length_id);
		
	}
	
	public String[] parseArrayJsonData(String s, int length){
		
		j = new J();
		
		return j.parseJsonData(s, length);
	}
	
	public Map<String,String> parseArrayMapJsonData(String s, int length){
		
		j = new J();
		
		return j.parseMapJsonData(s, length);
	}

}
