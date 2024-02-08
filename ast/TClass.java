package ast;
import java.util.ArrayList;
public class TClass extends Tipo{
	ArrayList<Dec> atr;
	public TClass(DecClass dec, ArrayList<Dec> a){
		this.dec = dec;
		this.atr =a;
	}
	public TKind tKind(){return TKind.CLASS;}
	public boolean igual(Tipo t){
		if(t.tKind() == TKind.CLASS)
			return dec == ((TClass)t).getDec();
		else return false;
	}
	public String toString(){
		return "TClass";
	}
	public String name(){
		return this.toString();
	}
	public int size(){
		int aux= 0;
		for(Dec a: atr){
			if(a.decKind()== DecKind.VAR){
				aux+= a.getTipo().size();
			}
		}
		return aux;
	}
}
