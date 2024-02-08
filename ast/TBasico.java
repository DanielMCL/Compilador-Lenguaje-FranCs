package ast;

public class TBasico extends Tipo{
	private String s;
	private DecType dec;
	
	public TBasico(String s){
		this.s = s;
	}
	public String name(){return s;}
	public String toString(){return ("tipo(" + s + ")");}
	
	public boolean bind(PilaBind pila){
		Dec d = pila.search(s);
		
		if(d == null){
			System.out.println("Error: type " + s + " not declared");
			return false;
		}
		else if (d.decKind() != DecKind.TYPE){
			System.out.println("Error: " + s + " not a type");
			return false;
		}
		else dec = (DecType) d;
		
		return true;
	}
	public TKind tKind(){return TKind.BASICO;}
	public Tipo getTipo(){
		return dec.getTipo();
	}
	public DecType getDec(){
		return dec;
	}
	public BoolYTipo type(){ 
		return new BoolYTipo(true, this.getTipo());
	}
	
	public boolean igual(Tipo t){//No se debería llamar con esta clase
		return false;
	}
}
