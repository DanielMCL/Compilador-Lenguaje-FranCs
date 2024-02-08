package ast;
import java.util.ArrayList;

public class For extends I{
	private DecVar decVar;
	private E cond;
	private Asig asig;
	private ArrayList<I> ins;
	
	public For(DecVar dec, E cond, Asig a, ArrayList<I> ins){
		decVar = dec;
		this.cond = cond;
		asig = a;
		this.ins = ins;
	}
	
	public String toString(){
		String str = "";
		if(ins.size() > 0) str = str + ins.get(0).toString();
		for(int i = 1; i < ins.size(); i++){
			str = str + ", " + ins.get(i).toString();
		}
		return "for(" + cond.toString() + ", (" + str + "))";
	}
	
	public KindI kindI(){
		return KindI.FOR;
	}
	
	public boolean bind(PilaBind pila){
	
		pila.add();
		boolean b = decVar.bind(pila) & cond.bind(pila) & asig.bind(pila);
		
		for(int i = 0; i < ins.size(); i++){
			b = ins.get(i).bind(pila) && b;
		}
		pila.remove();
		
		return b;
	}
	
	public BoolYTipo type(){
		BoolYTipo btDec =  decVar.type(), btCond = cond.type(), btAsig = asig.type();
		Tipo tCond = btCond.getTipo();
		boolean b = btDec.getBool() && btCond.getBool() && btAsig.getBool(); 
		if(b && tCond.tKind() != TKind.BOOL){
			System.out.println("Condition in foug header must be a boleen");
			b = false;
		}
		for(int i = 0; i < ins.size(); i++){
			BoolYTipo bt = ins.get(i).type();
			b = b && bt.getBool();
		}
		return new BoolYTipo(b, null);
	}
	public int setDelta(int n){
		int aux = n;
		aux = decVar.setDelta(aux);
		for(I in: ins){
			aux =in.setDelta(aux); 
		}
		return aux;
	}
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder();
		ss.append("block\n");
		ss.append(decVar.generateCode(depth));
		ss.append("loop\n");
		ss.append(cond.generateCode(depth));
		ss.append("i32.eqz\n");
		ss.append("br_if 1\n");
		for(I i: ins)
			ss.append(i.generateCode(depth));
		ss.append(asig.generateCode(depth));
		ss.append("br 0\n");
		ss.append("end\n");
		ss.append("end\n");
		return ss.toString();
	}
};
