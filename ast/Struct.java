package ast;

public class Struct extends V {
  private V address;
  private V iden;
  private boolean th; //th = true implica que el nodo es de la forma this.algo
  private DecClass dec; //Solo se usa cuando th = true
  
  public Struct(V address, V iden) {
   this.address = address;
   this.iden = iden;
   th = false;
  }
  public Struct(V iden) {
   this.iden = iden;
   th = true;
  }
  public boolean isThis(){return th;} 
  public V struct() {return address;}
  public V campo() {return iden;}
  public Dec getDec(){
  	if(th)return dec;
  	else return iden.getDec();
  }
  public KindV kindV() {return KindV.STRUCT;}   
  public String toString() {
  	if(th)return "this" +".("+ iden.toString()+")";
  	return address.toString() +".("+ iden.toString()+")";
  }  
  	public boolean bind(PilaBind pila){
		if(!th){
			boolean b = address.bind(pila) & iden.bind2(pila);
			return b;
		}
		Dec d = pila.getOpenClass();
		if(d == null || !(d.decKind() == DecKind.TYPE && ((DecType)d).decTypeKind() == DecTypeKind.CLASS)){
			System.out.println("Error: deuis call out of place");
			return false;
		} else{
			dec = (DecClass)d;
			return true;
		}
	}
	
	public BoolYTipo type(){
		boolean b = true;
		if(!th){
			BoolYTipo bt = address.type();
			Tipo tAdress = bt.getTipo();
			b = bt.getBool(); 
			if(b && tAdress.tKind() == TKind.STRUCT || tAdress.tKind() == TKind.CLASS 
					&& (tAdress.getDec().decTypeKind() == DecTypeKind.STRUCT || tAdress.getDec().decTypeKind() == DecTypeKind.CLASS)){
				b = b && iden.bind(tAdress.getDec().getPila());
				if(b){
					BoolYTipo btIden = iden.type();
					b = b && btIden.getBool(); 
					tipo = btIden.getTipo();
				}
			}
			else b = false;
		}
		else{
			b = b & iden.bind(dec.getPila());
			if(b){
				BoolYTipo btIden = iden.type();
				tipo = btIden.getTipo();
				b = b && btIden.getBool(); 
			}
		}
		return new BoolYTipo(b, tipo);
	}
	
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder(generateCodeAsig(depth));
		ss.append("i32.load\n"); 
		return ss.toString();
	}
	
	public String generateCodeAsig(int depth){
		StringBuilder ss = new StringBuilder();
		if(!th){
			ss.append(address.generateCodeAsig(depth));
			ss.append(iden.generateCodeAsig(depth + 2));	
		}
		else{
			ss.append("i32.const " + dec.getDelta() + "\n");
			ss.append(iden.generateCodeAsig(depth));	
		}
		ss.append("i32.add\n");
		return ss.toString();
	}
}
