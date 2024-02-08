package ast;

public class Write extends I {
   private E exp;
   
   public Write(E exp) {
     this.exp = exp;
   }
   public E exp() {return exp;}
   public KindI kindI() {return KindI.WRITE;}  
   public String toString() {
   		return("write(" + exp.toString() + ")");
   }
   public boolean bind(PilaBind pila){
		return exp.bind(pila);
	}
	public BoolYTipo type(){
		BoolYTipo bt = exp.type();
		Tipo t1 = bt.getTipo();
		boolean b = bt.getBool() && (t1.tKind() == TKind.NUM || t1.tKind() == TKind.BOOL);
		return new BoolYTipo(b, t1); 
	}
	public String generateCode(int depth) {
		StringBuilder ss = new StringBuilder(exp.generateCode(depth));
		ss.append("call $print\n");
		return ss.toString();
	}
}
