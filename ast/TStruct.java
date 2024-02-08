package ast;
import java.util.ArrayList;
public class TStruct extends Tipo{
	ArrayList<DecVar> atr;
	public TStruct(DecStruct dec, ArrayList<DecVar> a){
		this.dec = dec;
		this.atr = a;
	}
	public TKind tKind(){return TKind.STRUCT;}
	public boolean igual(Tipo t){
		if(t.tKind() == TKind.STRUCT)
			return dec == ((TStruct)t).getDec();
		else return false;
	}
	public String toString(){
		return "TStruct";
	}
	public String name(){
		return this.toString();
	}
	public int size(){
		int aux= 0;
		for(DecVar a: atr){
			aux+= a.getTipo().size();
		}
		return aux;
	}
}
