package ast;

public class Iden extends V {
	private Dec dec;
  	private String v;
  	public Iden(String v) {
   	this.v = v;   
  	}
  	public String name() {return v;}
  	public KindV kindV() {return KindV.IDEN;}   
  	public String toString() {return "("+v+")";} 
  public boolean bind(PilaBind pila){
		dec = pila.search(v);
		if(dec == null) {
			System.out.println("Error: " + v + " not declared");
			return false;
		}
		else return true;
	}
	public BoolYTipo type(){
		tipo = dec.getTipo();
		Boolean b = tipo != null;
		if(tipo == null) System.out.println("Error typing identifier " + v);
		return new BoolYTipo(b, tipo);
	}
	public Dec getDec(){ return dec; }  
	
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder(generateCodeAsig(depth));
		ss.append("i32.load\n"); 
		return ss.toString();
	}
	
	public String generateCodeAsig(int depth){
		StringBuilder ss = new StringBuilder();
		ss.append("i32.const " + dec.getDelta() + "\n");
		if(dec.getDepth() != 0 && dec.getDepth() >= depth){
			ss.append("get_local $localStart\n");
			ss.append("i32.add\n");
		}
		else if(dec.getDepth() == 1 && depth == 2){
			ss.append("get_global $CP\n");
			ss.append("i32.add\n");
		}
		//En el else la variable es global asi que no hay que sumarle nada al delta
		
		if(dec.isRef()){ss.append("i32.load\n");}
		return ss.toString();
	}
}
