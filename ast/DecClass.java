package ast;
import java.util.ArrayList;
import java.util.HashMap;

public class DecClass extends DecType{
	private ArrayList<Dec> decs;
	private PilaBind pilaPropia;
	
	public DecClass(Iden i, ArrayList<Dec> d){
		id = i;
		decs = d;
		tipo = new TClass(this,decs);
	}

	public DecTypeKind decTypeKind(){return DecTypeKind.CLASS;}
	public String toString(){
		return "class(" + id.toString() + ", " + decs.toString() + ")";
	}	
	public boolean bind(PilaBind pila){
		depth = pila.getDepth();
		pila.insert(id.name(), this);
		pila.add();
		boolean b = true;
		int ind = 0;
		for(int i = 0; i < decs.size(); i++){
			b = decs.get(i).bind(pila) && b;
			if(decs.get(i).decKind() == DecKind.CONSTR){
				ind = ((DecConstr) decs.get(i)).setInd(ind);
			}
		}
		
		HashMap<String, Dec> bloque = pila.getTop();
		pilaPropia = new PilaBind(bloque);
		
		pila.remove();
		
		return b;
	}
	public void typePrev(){
		for(int i = 0; i < decs.size(); i++){
			decs.get(i).typePrev();
		}
	}
	
	public BoolYTipo type(){
		boolean b = true;
		
		for(int i = 0; i < decs.size(); i++){
			BoolYTipo bt = decs.get(i).type();
			b = b && bt.getBool();
		}
		
		return new BoolYTipo(b, null);
	}
	
	public PilaBind getPila(){
		return pilaPropia;
	}
	public int setDelta(int n){
		int aux = 0;
		delta = 0;
		for(Dec d : decs){
			aux = d.setDelta(aux);
		}
		return n;
	}
	
	public String generateCode(int depth) {
		StringBuilder ss = new StringBuilder("");

		for(Dec d:decs){
			ss.append(d.generateCode(depth + 1));
		}
		
		return ss.toString();
	}
}
