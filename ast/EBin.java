package ast;

public class EBin extends E {
   private E opnd1;
   private E opnd2;
   private KindE op;
   
   public EBin(E opnd1, E opnd2, KindE op) {
     this.opnd1 = opnd1;
     this.opnd2 = opnd2;
     this.op = op;
   }
   public E opnd1() {return opnd1;}
   public E opnd2() {return opnd2;}  
   public KindE kind() {return op;}  
   public String toString() {
   		switch(op){
   		case AST:
   			return "mul("+opnd1.toString()+","+opnd2().toString()+")";
   		case MAS:
  			return "suma("+opnd1().toString()+","+opnd2().toString()+")";
  		case MENOS:
   			return "resta("+opnd1().toString()+","+opnd2().toString()+")";
   		case DIV:
  			return "division("+opnd1().toString()+","+opnd2().toString()+")";
  		case MOD:
  			return "modulo("+opnd1().toString()+","+opnd2().toString()+")";
  		case ET:
  			return "and("+opnd1().toString()+","+opnd2().toString()+")";
  		case OU:
  			return "or("+opnd1().toString()+","+opnd2().toString()+")";
  		case MAYOR:
  			return "mayor("+opnd1().toString()+","+opnd2().toString()+")";
  		case MENOR:
  			return "menor("+opnd1().toString()+","+opnd2().toString()+")";
  		case MAYORIG:
  			return "mayorig("+opnd1().toString()+","+opnd2().toString()+")";
  		case MENORIG:
  			return "menorig("+opnd1().toString()+","+opnd2().toString()+")";
  		case IGUAL:
  			return "igual("+opnd1().toString()+","+opnd2().toString()+")";
  		case DIST:
  			return "distinto("+opnd1().toString()+","+opnd2().toString()+")";
  		default:
  			return "Operation Type ERROR";
   		}
   	}  
   	
   	public boolean bind(PilaBind pila){
		return opnd1.bind(pila) & opnd2.bind(pila);
	}
	public BoolYTipo type(){
	
		BoolYTipo bt1 =  opnd1.type(), bt2 = opnd2.type();
		Tipo t1 = bt1.getTipo(), t2 = bt2.getTipo();
		boolean b = bt1.getBool() && bt2.getBool() && t1.igual(t2);
		if(b){
			switch(op){
   			case AST:
   			case MAS:
  			case MENOS:
   			case DIV:
  			case MOD:
  				if(!t1.igual(new TNum())){
  					b = false;
  					System.out.println("Error: non entier operands in entier-exclusive binary operation");
  				}
  				tipo = new TNum();
  				break;
  			case ET:
  			case OU:
  				if(!t1.igual(new TBool())){
  					b = false;
  					System.out.println("Error: non boleen operands in boleen-exclusive binary operation");
  				}
  				tipo = new TBool();
  				break;
  			case MAYOR:
  			case MENOR:
  			case MAYORIG:
  			case MENORIG:
  			case IGUAL:
  			case DIST:
  				if(!t1.igual(new TBool()) && !t1.igual(new TNum())){
  					b = false;
  					System.out.println("Error: operation only supported for boleen and entier expresions");
  				}
  				tipo = new TBool();
  				break;
  			default:
   			}
		}
		else{
			b = false;
			System.out.println("Error: operands in a binary operation must have matching type");
		}
		return new BoolYTipo(b, tipo);
	}
	public String generateCode(int depth) {
		StringBuilder ss = new StringBuilder();
	    	ss.append(opnd1.generateCode(depth));
	    	ss.append(opnd2.generateCode(depth));
		switch(op){
   		case AST:
   			ss.append("i32.mul\n"); break;
   		case MAS:
  			ss.append("i32.add\n"); break;
  		case MENOS:
  			ss.append("i32.sub\n"); break;
   		case DIV:
  			ss.append("i32.div_s\n"); break;
  		case MOD:
  			ss.append("i32.rem_u\n"); break;
  		case ET:
  			ss.append("i32.and\n"); break;
  		case OU:
  			ss.append("i32.or\n"); break;
  		case MAYOR:
  			ss.append("i32.gt_s\n"); break;
  		case MENOR:
  			ss.append("i32.lt_s\n"); break;
  		case MAYORIG:
  			ss.append("i32.ge_s\n"); break;
  		case MENORIG:
  			ss.append("i32.le_s\n"); break;
  		case IGUAL:
  			ss.append("i32.eq\n"); break;
  		case DIST:
  			ss.append("i32.ne\n"); break;
   		}
    	return ss.toString();
    }
}

