package ast;
import java.util.ArrayList;
import java.util.HashMap;

public class DecStruct extends DecType{
	private ArrayList<DecVar> atr;
	private PilaBind pilaPropia;
	
	public DecStruct(Iden i, ArrayList<DecVar> a){
		id = i;
		atr = a;
		tipo = new TStruct(this,a);
	}

	public DecTypeKind decTypeKind(){return DecTypeKind.STRUCT;}
	public String toString(){
		return "struct(" + id.toString() + ", " + atr.toString() + ")";
	}	
	public int setDelta(int n){
		int aux = 0;
		delta = 0;
		for(DecVar a: atr){
			aux = a.setDelta(aux);	
		}
		return n;
	}
	public boolean bind(PilaBind pila){
		depth = pila.getDepth();
		pila.insert(id.name(), this);
		
		pila.add();
		boolean b = true;
		for(int i = 0; i < atr.size(); i++){
			b = atr.get(i).bind(pila) && b;
		}
		HashMap<String, Dec> bloque = pila.getTop();
		pilaPropia = new PilaBind(bloque);
		
		pila.remove();
		
		return b;
	}
	public void typePrev(){
		for(int i = 0; i < atr.size(); i++){
			atr.get(i).typePrev();
		}
	}
	
	public BoolYTipo type(){
		boolean b = true;
		for(int i = 0; i < atr.size(); i++){
			BoolYTipo bt = atr.get(i).type();
			b = b && bt.getBool();
		}
		return new BoolYTipo(b, tipo);
	}
	
	public PilaBind getPila(){
		return pilaPropia;
	}
}
