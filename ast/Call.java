package ast;
import java.util.ArrayList;

public class Call extends E {
	private V fun;
	private ArrayList<E> params;
	private ArrayList<Tipo> tParams;
	private ArrayList<Boolean> refs;
	private ArrayList<DecParam> decParams;
	
	public Call(V fun, ArrayList<E> params){
		this.fun = fun;
		this.params = params;
		tParams = new ArrayList<Tipo>();
	}
	
	public KindE kind(){return KindE.LLAMADA;}
	public ArrayList<E> params(){return params;}
	public V fun(){return fun;} 
	
	public String toString(){
		String str = "(";
		if(params.size() > 0)
			str = str + params.get(0);
		for(int i = 1; i < params.size(); ++i)
			str = str + ", " + params.get(i);		
		str = str + ")";
		return "call(" + fun.toString() + ", " + str + ")";
	}
	
	public boolean bind(PilaBind pila){
		boolean b = fun.bind(pila);
		
		for(int i = 0; i < params.size(); i++){
			b = params.get(i).bind(pila) && b;
		}
		return b;
	}
	
	public BoolYTipo type(){
		BoolYTipo bt = fun.type();
		boolean b = bt.getBool();
		tipo = bt.getTipo();
		for(int i = 0; i < params.size(); i++){
			BoolYTipo btP = params.get(i).type();
			b = b && btP.getBool();
			tParams.add(btP.getTipo());
		}
		if(!b) return new BoolYTipo(b, tipo);
		if(fun.kindV() == KindV.IDEN){
			Dec dec = fun.getDec();
			if(dec.decKind() == DecKind.FUN){
				b = ((DecFun)dec).match(tParams, params);
				refs = ((DecFun)dec).getRefs();
				decParams = ((DecFun)dec).getParams();
			}
			else{
				System.out.println("Error: " + fun.toString() + " is not a function");
			}
		}
		else if(fun.kindV() == KindV.STRUCT){
			Dec dec = ((Struct)fun).campo().getDec();
			//System.out.println(dec.toString());
			if(dec.decKind() == DecKind.FUN){
				b = ((DecFun)dec).match(tParams, params);
				refs = ((DecFun)dec).getRefs();
				decParams = ((DecFun)dec).getParams();
			}
			else if(dec.decKind() == DecKind.CONSTR){
				b = ((DecConstr)dec).match(tParams, params);
				refs = ((DecConstr)dec).getRefs();
				decParams = ((DecConstr)dec).getParams();
			}
			else{
				System.out.println("Error: " + fun.toString() + " is not a function");
			}
		}
		return new BoolYTipo(b, tipo);
	}
	
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder();
		ss.append("\n");
		
		for(int i = 0; i < params.size(); ++i){
			
			
			if(refs.get(i)){
				ss.append("get_global $SP\n");
				ss.append("i32.const " + (decParams.get(i).getDelta() + 12) + "\n");
				ss.append("i32.add\n");
				ss.append(((V)params.get(i)).generateCodeAsig(depth));
				ss.append("i32.store\n"); 
			}
			else{
				
				if(decParams.get(i).size()>4)
					for(int j = 0; j < decParams.get(i).size(); j= j+4){
						ss.append("get_global $SP\n");
						ss.append("i32.const " + (decParams.get(i).getDelta()+ j + 12) + "\n");
						ss.append("i32.add\n");
						ss.append(((V)params.get(i)).generateCodeAsig(depth));
						ss.append("i32.const "+ j + "\n");
						ss.append("i32.add\n");
						ss.append("i32.load\n");
						ss.append("i32.store\n"); 
					}
				else {
					ss.append("get_global $SP\n");
					ss.append("i32.const " + (decParams.get(i).getDelta() + 12) + "\n");
					ss.append("i32.add\n");
					ss.append(params.get(i).generateCode(depth));
					ss.append("i32.store\n"); 
				}
				
			}
			
		}
		
		ss.append("i32.const " + (fun.getDec().size() + 12) + "\n");
		ss.append("call $reserveStack\n");//Cambia MP y SP pero devuelve el MP antiguo
		ss.append("set_local $temp\n");
		
		ss.append("get_global $MP\n");//Guardamos el MP antiguo (enlace dinamico)
		ss.append("get_local $temp\n");
		ss.append("i32.store\n");
   
		ss.append("get_global $MP\n");//Guardamos el SP 
		ss.append("get_global $SP\n");
		ss.append("i32.store offset=4\n");
		
		ss.append("get_global $MP\n");//Guardamos el CP antiguo
		ss.append("get_global $CP\n");
		ss.append("i32.store offset=8\n");
		
		if(fun.kindV() == KindV.STRUCT){//Actualizamos el CP (Class Pointer)
			ss.append(((Struct)fun).struct().generateCodeAsig(depth));
			ss.append("set_global $CP\n");
		}
		
		ss.append("\ncall $" + (fun.getDec()).getName()+ "\n");
	
		ss.append("call $freeStack\n\n");
 
		return ss.toString();
	}
}
