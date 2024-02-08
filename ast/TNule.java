package ast;

public class TNule extends Tipo{
	public TNule(){
	}
	public String name(){return "nule";}
	public String toString(){return ("tipo(nule)");}
	public boolean bind(PilaBind pila){return true;}
	public TKind tKind(){return TKind.NULE;}
	public boolean igual(Tipo tipo){
		return (tipo.tKind() == TKind.NULE);
	}
	public int size(){return 0;}
}
