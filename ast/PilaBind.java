package ast;
import java.util.ArrayList;
import java.util.HashMap;

public class PilaBind{
	private ArrayList<HashMap<String, Dec>> pila;
	private Dec openFun, openClass; 
	private Dec lastDec;
	private int levelFun;//nivel de openFun
	
	public PilaBind(){
		pila = new ArrayList<HashMap<String, Dec>>();
	}
	
	public void add(){
		if(lastDec != null){ 
			if(lastDec.decKind() == DecKind.TYPE && ((DecType)lastDec).decTypeKind() == DecTypeKind.CLASS) openClass = lastDec;
			else if(lastDec.decKind() == DecKind.FUN){
				openFun = lastDec;
				levelFun = pila.size();
			}
		}
		pila.add(new HashMap<String, Dec>());
	}
	
	public void remove(){
		pila.remove(pila.size() - 1);
		if(pila.size() == 1) openClass = null;
		if(pila.size() == levelFun) openFun = null;
	}
	
	public void insert(String id, Dec dec){
		pila.get(pila.size() - 1).put(id, dec);
		lastDec = dec;
	}
	
	public Dec search(String id){
		Dec d = null;
		int i = pila.size() - 1;
		while(d == null && i >= 0){
			d = pila.get(i).get(id);
			i = i - 1;			
		}
		return d;
	}
	
	public Dec getOpenFun(){
		return openFun;
	}
	
	public Dec getOpenClass(){
		return openClass;
	}
	
	public int getDepth(){
		return pila.size() - 1;
	}
	
	//Para structs y clases:
	
	public HashMap<String, Dec> getTop(){
		if(pila.size() > 0)	return pila.get(pila.size() - 1);
		else return new HashMap<String, Dec>();
	}
	
	public PilaBind(HashMap<String, Dec> inicio){
		pila = new ArrayList<HashMap<String, Dec>>();
		pila.add(inicio);
	}
}
