package ast;

public class TBool extends Tipo{
	public TBool(){
	}
	public String name(){return "boolean";}
	public String toString(){return ("tipo(boolean)");}
	public boolean bind(PilaBind pila){return true;}
	public TKind tKind(){return TKind.BOOL;}
	public boolean igual(Tipo tipo){
		return (tipo.tKind() == TKind.BOOL);
	}
}
