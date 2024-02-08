package ast;

public class DecParam extends Dec {
	private Iden id;
	private boolean ref;
	public DecParam (Tipo type, Iden id, boolean ref){
		this.id = id;
		this.ref = ref;
		tipo = type;
	}
	public String id() {return id.name();}
	public String toString(){
		if(ref) return "decParam(" + tipo.toString() + ", &, " + id.toString() + ")"; 
		else return "decParam(" + tipo.toString() + ", " + id.toString() + ")"; 
	}
	
	public DecKind decKind(){
		return DecKind.PARAM;
	}
	
	public boolean bind(PilaBind pila){
		depth = pila.getDepth();
		Boolean b = tipo.bind(pila);
		
		pila.insert(id.name(), this);
		
		return b;
	}
	public void typePrev(){
		tipo = tipo.getTipo();
	}
	
	public Boolean isRef(){return ref;}
	
	public BoolYTipo type(){
		BoolYTipo bt = tipo.type();
		boolean b = bt.getBool();	
		return new BoolYTipo(b, tipo);
	}
	public int setDelta(int n){delta = n;return n+tipo.size();}
	
	public int size(){
		if(ref) return 4;
		else return tipo.size();
	}
}
