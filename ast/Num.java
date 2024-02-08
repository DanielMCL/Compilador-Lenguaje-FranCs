package ast;

public class Num extends E {
  private String v;
  public Num(String v) {
   this.v = v;   
  }
  public int value() {return Integer.parseInt(this.v);}
  public KindE kind() {return KindE.NUM;}   
  public String toString() {return v;}  
	public boolean bind(PilaBind pila){return true;}
	public BoolYTipo type(){
		tipo = new TNum();;
		return new BoolYTipo(true, tipo);
	}
	public String generateCode(int depth){
		return "i32.const " + this.value() + "\n";
	}
}
