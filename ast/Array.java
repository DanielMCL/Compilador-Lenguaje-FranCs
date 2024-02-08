package ast;

public class Array extends V {
  private V iden;
  private E index;
  private Tipo tipo;
  private boolean binding2 = false; 
  
  public Array( V iden,E index) {
   this.iden = iden;
   this.index = index;
  }
  public V array() {return iden;}
  public E index() {return index;}
  public KindV kindV() {return KindV.ARRAY;}   
  public String toString() {return iden.toString() +"["+ index.toString()+"]";}
  	public Dec getDec(){ return iden.getDec(); }  
	public boolean bind(PilaBind pila){
		if(binding2) return iden.bind(pila);
		return iden.bind(pila) & index.bind(pila);
	}
	
	public boolean bind2(PilaBind pila){
		binding2 = true;
		return index.bind(pila);
	}
	
	public BoolYTipo type(){
		BoolYTipo btIden =  iden.type(), btIndex = index.type();
		Tipo tArr = btIden.getTipo(), tInt = btIndex.getTipo();
		boolean b = btIden.getBool() && btIndex.getBool();
		
		if(b && tArr != null){
			if(tArr.tKind() == TKind.ARRAY){
				tipo = ((TArray)tArr).arrayDe();
				if(tInt != null){
				if (tInt.tKind() != TKind.NUM){
					b = false;
					System.out.println("Typing error: " + index.toString() + " not an entier, cannot be used to index");
				}
			} else {b = false;}
			}
			else{
				b = false;
				System.out.println("Typing error: " + iden.name() + " cannot be indexed (is not an array)");
			}
		}
		else{ b = false; }
		return new BoolYTipo(b, tipo);
	}
	
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder(generateCodeAsig(depth));
		ss.append("i32.load\n"); 
		return ss.toString();
	}
	
	public String generateCodeAsig(int depth){
		StringBuilder ss = new StringBuilder();
		ss.append(iden.generateCodeAsig(depth));
		ss.append("i32.const " + tipo.size() + "\n");
		if(depth > 2)	ss.append(index.generateCode(depth - 2));
		else ss.append(index.generateCode(depth));
		ss.append("i32.mul\n");
		ss.append("i32.add\n");
		return ss.toString();
	}
}
