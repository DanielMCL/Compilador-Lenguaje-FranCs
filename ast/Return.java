package ast;

public class Return extends I {
   private E exp;
   private Tipo tipo;
   private DecFun fun;
   
   public Return(E exp) {
     this.exp = exp;
   }
   public E exp() {return exp;}
   public KindI kindI() {return KindI.RETURN;}  
   public String toString() {
   		return("return(" + exp.toString() + ")");
   }
   public boolean bind(PilaBind pila){
   		Dec d = pila.getOpenFun();
   		boolean b = true;
   		if(d == null || d.decKind() != DecKind.FUN){
   			b = false;
   			System.out.println("Error: retour statement outside of a function");
   		}else{
   			fun = (DecFun) d;
   		}
		return b & exp.bind(pila);
	}
	
	public BoolYTipo type(){
		BoolYTipo bt = exp.type();
		tipo = bt.getTipo();
		boolean b = bt.getBool(); 
		if(!tipo.igual(fun.getTipo())){
			b = false;
			System.out.println("Error: retour expression type doesn't match function type");
		} 		
		return new BoolYTipo(b, tipo);
	}
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder();
		ss.append(exp.generateCode(depth));
		return ss.toString();
	}
}
