package ast;
import java.util.ArrayList;

public class DecFun extends Dec {
	private Iden id;
	private ArrayList<DecParam> params;
	private ArrayList<Tipo> tParams;
	private ArrayList<I> ins;
	private int MaxSize;
	private String location = "_nule";
	
	public DecFun(Tipo type, Iden id, ArrayList<DecParam> params, ArrayList<I> ins){
		tipo = type;
		this.id = id;
		this.params = params;
		this.ins = ins;
		tParams = new ArrayList<Tipo>();
	}
	
	public DecKind decKind(){return DecKind.FUN; }
	public String toString(){
		String strIns = "", strParams = "";
		
		if(params.size() > 0) strParams = strParams + params.get(0).toString();
		for(int i = 1; i < params.size(); i++)
			strParams = strParams + ", " + params.get(i).toString();
			
		if(ins.size() > 0) strIns = strIns + ins.get(0).toString();
		for(int i = 1; i < ins.size(); i++)
			strIns = strIns + ", " + ins.get(i).toString();
			
		return "fun(" + tipo.toString() + ", " + id.toString() + ", (" + strParams + ")" + ", (" + strIns + "))";
	}	
	
	public boolean bind(PilaBind pila){
		depth = pila.getDepth();
		if(depth ==1){
			location = "_"+pila.getOpenClass().getName();
		}
		boolean b = tipo.bind(pila);
		
		pila.insert(id.name(), this);
		
		pila.add();
		for(int i = 0; i < params.size(); i++){
			b = params.get(i).bind(pila) && b;
		}
		for(int i = 0; i < ins.size(); i++){
			b = ins.get(i).bind(pila) && b;
		}
		pila.remove();
		
		return b;
	}
	public void typePrev(){
		tipo = tipo.getTipo();
		for(int i = 0; i < params.size(); i++){
			params.get(i).typePrev();
		}
		for(int i = 0; i < ins.size(); i++){
			ins.get(i).typePrev();
		}
	}
	public boolean match(ArrayList<Tipo> tipos, ArrayList<E> exps){
		if(tipos.size() != tParams.size())
			return false;
		boolean b = true;
		for(int i = 0; i < tipos.size(); ++i){
			if(params.get(i).isRef()){
				if(!exps.get(i).isV()){
					System.out.println("Error: in call to funcion " + id.toString() + ", argument for " 
						+ params.get(i).toString() + " isn't a variable and can't be referenced");
					b = false;
				}
			}
			if(!tipos.get(i).igual(tParams.get(i))){
				System.out.println("Error: in call to funcion " + id.toString() + ", argument for " 
					+ params.get(i).toString() + " doesn't match its type");
				b = false;
			}
		}
		return b;
	}
	
	public ArrayList<Boolean> getRefs(){
		ArrayList<Boolean> refs = new ArrayList<Boolean>();
		for(int i = 0; i< params.size(); i++){
			refs.add(params.get(i).isRef());
		}
		return refs;
	}
	
	public ArrayList<DecParam> getParams(){return params;}
	
	public BoolYTipo type(){
		boolean b = true;
		
		for(int i = 0; i < params.size(); i++){
			BoolYTipo bt = params.get(i).type();
			b = b && bt.getBool();
			tParams.add(bt.getTipo());
		}
		for(int i = 0; i < ins.size(); i++){
			BoolYTipo bt = ins.get(i).type();
			b = b && bt.getBool();
		}
		
		return new BoolYTipo(b, tipo);
	}
	
	public int setDelta(int n){
		int aux = 0;
		delta = 0;
		for(DecParam p: params){
			aux = p.setDelta(aux);
		}
		for(I in: ins){
			aux = in.setDelta(aux);
		}
		MaxSize= aux;
		return n;
	}
	public String getName(){
		return id.name()+location;
	}
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder("");
		ss.append("(func $");
		ss.append(id.name()+location);
		if(tipo.tKind()!= TKind.NULE)
			ss.append(" (result i32)\n");
		ss.append("(local $temp i32)\n");
		ss.append("(local $localStart i32)\n");
		ss.append("\n");
		if(id.name().equals("principale")){
			
			ss.append("get_global $SP\n");
			ss.append("set_global $MP\n");
			
			ss.append("get_global $SP\n");
			ss.append("i32.const " + MaxSize + "\n");
			ss.append("i32.add\n");
			ss.append("set_global $SP\n");
			
			ss.append("get_global $MP\n");
			ss.append("set_local $localStart\n");
		}
		else{
			ss.append("get_global $MP\n");
			ss.append("i32.const 12\n");
			ss.append("i32.add\n");
			ss.append("set_local $localStart\n");
		}
		for(I i: ins){
			ss.append(i.generateCode(depth + 1));
			if(i.kindI() == KindI.EXPRESSION){
				if(((E)i).getTipo().tKind() != TKind.NULE){
					ss.append("drop\n");
				}
			}
		}
		ss.append(")\n");
    		return ss.toString();
	}
	public int size(){
		return MaxSize;
	}	
}
