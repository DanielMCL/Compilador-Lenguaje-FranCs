package ast;
import java.util.ArrayList;

public class DecConstr extends Dec {
	private DecClass clase;
	private ArrayList<DecParam> params;
	private ArrayList<Tipo> tParams;
	private ArrayList<I> ins;
	private int MaxSize;
	private String location = "_nule";
	private int constructorInd = 0;
	
	public DecConstr(Iden id, ArrayList<DecParam> params, ArrayList<I> ins){
		this.id = id;
		this.params = params;
		this.ins = ins;
		tipo = new TNule();
	}
	
	public DecKind decKind(){return DecKind.CONSTR; }
	
	public String toString(){
		String strIns = "", strParams = "";
		
		if(params.size() > 0) strParams = strParams + params.get(0).toString();
		for(int i = 1; i < params.size(); i++)
			strParams = strParams + ", " + params.get(i).toString();
			
		if(ins.size() > 0) strIns = strIns + ins.get(0).toString();
		for(int i = 1; i < ins.size(); i++)
			strIns = strIns + ", " + ins.get(i).toString();
			
		return "constr(" + id.toString() + ", (" + strParams + ")" + ", (" + strIns + "))";
	}	
	
	public boolean bind(PilaBind pila){
		depth = pila.getDepth();
		Dec d = pila.getOpenClass();
		
		location = "_"+pila.getOpenClass().getName();
		boolean b = true;
		if(d == null ||  d.decKind() != DecKind.TYPE || ((DecType)d).decTypeKind() != DecTypeKind.CLASS || !d.id().equals(id.name())){
			b = false;
			System.out.println("Error: function " + id.name() + " missing type or constructor outside of its class");
		}
		else clase = (DecClass) d;
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
				if(!((TPuntero)tParams.get(i)).punteroA().igual(tipos.get(i))){
					System.out.println("Error: in call to funcion " + id.toString() + ", argument for " 
						+ params.get(i).toString() + " doesn't match its type");
					b = false;
				}
				if(!exps.get(i).isV()){
					System.out.println("Error: in call to funcion " + id.toString() + ", argument for " 
						+ params.get(i).toString() + " isn't a variable and can't be referenced");
					b = false;
				}
			}
			else if(!tipos.get(i).igual(tParams.get(i))){
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
	public int setInd(int n){
		constructorInd = n;
		return n +1;
	}
	public BoolYTipo type(){
		boolean b = true;
		
		tParams = new ArrayList<Tipo>();
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
		return id.name()+location+"_"+constructorInd;
	}
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder("");
		ss.append("(func $");
		ss.append(id.name()+location+"_"+constructorInd);
		
		ss.append("(local $temp i32)\n");
		ss.append("(local $localStart i32)\n");
		ss.append("\n");
		if(id.name().equals("principale")){
			ss.append("i32.const 0\n");
			ss.append("set_local $localStart\n");
			
			ss.append("i32.const " + MaxSize + "\n");
			ss.append("set_global $SP\n");
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
