package ast;

public class EUni extends E {
   private E opnd1;
   private KindE op;
   
   public EUni(E opnd1, KindE op) {
     this.opnd1 = opnd1;
     this.op = op;
   }
   public NodeKind nodeKind() {return NodeKind.EXPRESSION;}
   public E opnd1() {return opnd1;}
   public KindE kind() {return op;}  
   public String toString() {
   		switch(op){
   		case MAS:
  			return "mas("+opnd1().toString()+")";
  		case MENOS:
   			return "menos("+opnd1().toString()+")";
   		case REF: 
   			return "direccion("+opnd1().toString()+")";
   		case AST:
   			return "valorDePuntero("+opnd1().toString()+")";
  		default:
  			return "Operation Type ERROR";
   		}
   	}  
   	public Tipo getTipo(){return tipo;}
   	
   	public boolean bind(PilaBind pila){
		return opnd1.bind(pila);
	}
	
	public BoolYTipo type(){
		BoolYTipo bt = opnd1.type();
		Tipo t1 = bt.getTipo();
		boolean b = bt.getBool(); 
		if(t1 != null && b){
			switch(op){
   			case MAS:
  			case MENOS:
   				b = b && t1.tKind() == TKind.NUM;
   				tipo = t1;
   				break;
   			case AST:
   				b = b && t1.tKind() == TKind.PUNTERO;
   				if(t1.tKind() != TKind.PUNTERO) 
   					System.out.println("Typing error: trying to access non pointer " + opnd1.toString() + " as if it was a pointer");
   				tipo = ((TPuntero)t1).punteroA();
   				break;
   			case REF: 
   				tipo = new TPuntero(t1);
   				break;
  			default:
  				b = false;
   			}
   		}
		return new BoolYTipo(b, tipo);
	}
	
	public String generateCode(int depth){
		StringBuilder ss = new StringBuilder();
		switch(op){
 		case MAS:
			ss.append(opnd1.generateCode(depth));
  			break;
  		case MENOS:
  			ss.append("i32.const 0\n");
			ss.append(opnd1.generateCode(depth));
  			ss.append("i32.sub\n");
 			break;
 		case AST:
			ss.append(opnd1.generateCode(depth));
			ss.append("i32.load\n");
 			break;
 		case REF: 
			ss.append(((V)opnd1).generateCodeAsig(depth));
 			break;
 		default:
 		}
		return ss.toString();
	}
}
