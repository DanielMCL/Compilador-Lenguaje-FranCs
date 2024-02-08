package ast;

public class Asig extends I {
   private E exp;
   private V iden;
   
   public Asig(V iden, E exp) {
     this.iden = iden;
     this.exp = exp;
   }
   public E exp() {return exp;}
   public V iden() {return iden;}  
   public KindI kindI() {return KindI.ASIG;}  
   public String toString() {
   		return("asig(" + iden.toString() + ", " + exp.toString() + ")");
   }
	public boolean bind(PilaBind pila){
		return exp.bind(pila) & iden.bind(pila);
	}
	
	public BoolYTipo type(){
		BoolYTipo btIden =  iden.type(), btExp = exp.type();
		Tipo tIden = btIden.getTipo(), tExp = btExp.getTipo();
		boolean b = btIden.getBool() && btExp.getBool();
		
		if(tIden != null && tExp != null){
			if(tIden.tKind() != TKind.NUM && tIden.tKind() != TKind.BOOL && tIden.tKind() != TKind.PUNTERO){
				b = false;
				System.out.println("Typing error: trying to asign to " + iden.toString() + ", but only entier, boleen and pointer types can be asigned to");
			}
			else if(!tIden.igual(tExp)){
				b = false;
				System.out.println("Typing error: trying to asign to " + iden.toString() + " an expression of non-matching type");
			}
		}
		else {
			System.out.println("Typing error in asignation");
			b = false;
		}
		return new BoolYTipo(b, tIden);
	}
	
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder();
		ss.append(iden.generateCodeAsig(depth));
		ss.append(exp.generateCode(depth));
		ss.append("i32.store\n");
		return ss.toString();
	}
}
