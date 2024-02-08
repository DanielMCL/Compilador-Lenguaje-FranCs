package ast;
import java.util.ArrayList;

public class IfElse extends I{
	private E cond;
	private ArrayList<I> ins1;
	private ArrayList<I> ins2;
	
	public IfElse(E cond, ArrayList<I> ins1, ArrayList<I> ins2){
		this.cond = cond;
		this.ins1 = ins1;
		this.ins2 = ins2;
	}
	
	public String toString(){
		String str1 = "", str2 = "";
		if(ins1.size() > 0) str1 = str1 + ins1.get(0).toString();
		for(int i = 1; i < ins1.size(); i++){
			str1 = str1 + ", " + ins1.get(i).toString();
		}
		if(ins2.size() > 0) str2 = str2 + ins2.get(0).toString();
		for(int i = 1; i < ins2.size(); i++){
			str2 = str2 + ", " + ins2.get(i).toString();
		}
		return "ifelse(if(" + cond.toString() + ", (" + str1 + ")), else((" + str1 + ")))";
	}
	
	public KindI kindI(){
		return KindI.WHILE;
	}
	
	public boolean bind(PilaBind pila){
	
		pila.add();
		boolean b = cond.bind(pila);
		
		for(int i = 0; i < ins1.size(); i++){
			b = ins1.get(i).bind(pila) && b;
		}
		pila.remove();
		pila.add();
		for(int i = 0; i < ins2.size(); i++){
			b = ins2.get(i).bind(pila) && b;
		}
		pila.remove();
		
		return b;
	}
	public int setDelta(int n){
		int aux = n;
		for(I in: ins1){
			aux =in.setDelta(aux); 
		}
		for(I in: ins2){
			aux =in.setDelta(aux); 
		}
		return aux;
	}
	public BoolYTipo type(){
		BoolYTipo btCond = cond.type();
		Tipo tCond = btCond.getTipo();
		boolean b = btCond.getBool();
		if(b && tCond.tKind() != TKind.BOOL){
			System.out.println("Condition in if header must be a boleen");
			b = false;
		}
		for(int i = 0; i < ins1.size(); i++){
			BoolYTipo bt = ins1.get(i).type();
			b = b && bt.getBool();
		}
		for(int i = 0; i < ins2.size(); i++){
			BoolYTipo bt = ins2.get(i).type();
			b = b && bt.getBool();
		}
		
		return new BoolYTipo(b, null);
	}
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder(cond.generateCode(depth));
		ss.append("if\n");
		for(I i: ins1){
			ss.append(i.generateCode(depth));
			if(i.kindI() == KindI.EXPRESSION){
				if(((E)i).getTipo().tKind() != TKind.NULE){
					ss.append("drop\n");
				}
			}
		}
		ss.append("else\n");
		for(I i: ins2){
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
