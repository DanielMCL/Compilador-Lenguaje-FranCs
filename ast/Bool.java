package ast;

public class Bool extends E {
  private String v;
  public Bool(String v) {
   this.v = v;   
  }
  public int value() {if(v.equals("vrai")) return 1;return 0;}
  public KindE kind() {return KindE.BOOL;}   
  public String toString() {return v;}  
	public boolean bind(PilaBind pila){return true;}
	public Tipo decATipo(){return null;}
	public BoolYTipo type(){
		tipo = new TBool(); 
		return new BoolYTipo(true, tipo);
	}
	public String generateCode(int depth){
		return "i32.const " + this.value() + "\n";
	}
}

