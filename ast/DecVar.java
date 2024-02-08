package ast;

public class DecVar extends Dec {
	private Iden id;
	private Asig asig;
	private int globalNum;
	private boolean asignacion;
	public DecVar (Tipo type, Iden id){
		this.id = id;
		tipo = type;
		asignacion = false;
	}
	public DecVar (Tipo type, Iden id, E e){
		this.id = id;
		this.tipo = type;
		asig = new Asig(id, e);
		asignacion = true;
	}
	public String id() {return id.name();}
	public int globalSize() {return this.tipo.size();}
	public String toString(){
		if(asignacion) return "decVar(" + tipo.toString() + ", " + id.toString() + ", " + asig.toString() + ")"; 
		else return "decVar(" + tipo.toString() + ", " + id.toString() + ")"; 
	}
	public int setDelta(int n){
		delta = n;
		return n + tipo.size();
	}
		
	public DecKind decKind(){
		return DecKind.VAR;
	}
	
	public boolean bind(PilaBind pila){
		depth = pila.getDepth();
		boolean b = tipo.bind(pila);
		pila.insert(id.name(), this);
		id.bind(pila);
		if(asignacion) b = asig.bind(pila) & b;
		return b;
	}
	
	public void typePrev(){
		tipo = tipo.getTipo();
	}
	public void setGlobal(int i){
		globalNum = i;
	}
	public int getGlobal(){
		return globalNum;
	}
	
	public BoolYTipo type(){
		BoolYTipo bt = tipo.type();
		boolean b = bt.getBool();
		if(b && asignacion){
			BoolYTipo btT = asig.type();
			b = b && btT.getBool();
			Tipo t1 = btT.getTipo();
			if (t1 == null){
				b = false;
				System.out.println("Typing error in asignation while declaring variable "  + id.name());
			}
			b = b && (tipo.igual(t1));
		}
		return new BoolYTipo(b, tipo);
	}
	
	public String generateCode(int depth){
		this.depth = depth;
		
		if(depth ==0){
			StringBuilder ss = new StringBuilder();
			ss.append("(func $");
			ss.append("global"+globalNum+"\n");
			
			ss.append("(local $localStart i32)\n");
			ss.append("i32.const " + delta + "\n");
			ss.append("set_local $localStart\n");
			
			if(asignacion) ss.append(asig.generateCode(depth));
			ss.append(")\n");
	    		return ss.toString();
		}
		else if(asignacion) return asig.generateCode(depth);
		else return "";
	}
}
