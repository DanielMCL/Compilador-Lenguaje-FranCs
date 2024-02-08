package ast;

public class Read extends I {
   private V var;
   
   public Read(V var) {
     this.var = var;
   }
   public V var() {return var;}
   public KindI kindI() {return KindI.READ;}  
   public String toString() {
   		return("read(" + var.toString() + ")");
   }
   public boolean bind(PilaBind pila){
		return var.bind(pila);
	}
	public BoolYTipo type(){
		BoolYTipo bt = var.type();
		Tipo t1 = bt.getTipo();
		boolean b = bt.getBool() && (t1.tKind() == TKind.NUM || t1.tKind() == TKind.BOOL);
		return new BoolYTipo(b, t1); 
	}
	public String generateCode(int depth) {
   	StringBuilder ss = new StringBuilder(var.generateCodeAsig(depth));
		ss.append("call $read\n");
		ss.append("i32.store\n");
		return ss.toString();
   }
}
