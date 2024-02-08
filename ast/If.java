package ast;
import java.util.ArrayList;

public class If extends I{
	private E cond;
	private ArrayList<I> ins;
	
	public If(E cond, ArrayList<I> ins){
		this.cond = cond;
		this.ins = ins;
	}
	
	public String toString(){
		String str = "";
		if(ins.size() > 0) str = str + ins.get(0).toString();
		for(int i = 1; i < ins.size(); i++){
			str = str + ", " + ins.get(i).toString();
		}
		return "if(" + cond.toString() + ", (" + str + "))";
	}
	
	public KindI kindI(){
		return KindI.WHILE;
	}
	
	public boolean bind(PilaBind pila){
	
		pila.add();
		boolean b = cond.bind(pila);
		
		for(int i = 0; i < ins.size(); i++){
			b = ins.get(i).bind(pila) && b;
		}
		pila.remove();
		
		return b;
	}
	
	public BoolYTipo type(){
		BoolYTipo btCond = cond.type();
		Tipo tCond = btCond.getTipo();
		boolean b = btCond.getBool();
		if(b && tCond.tKind() != TKind.BOOL){
			System.out.println("Condition in if header must be a boleen");
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
		for(I in: ins){
			aux =in.setDelta(aux); 
		}
		return aux;
	}
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder(cond.generateCode(depth));
		ss.append("if\n");
		for(I i: ins){
			ss.append(i.generateCode(depth));
			if(i.kindI() == KindI.EXPRESSION){
				if(((E)i).getTipo().tKind() != TKind.NULE){
					ss.append("drop\n");
				}
			}
		}
		ss.append("end\n");
		return ss.toString();
	}
};
