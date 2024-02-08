package ast;

public class TNum extends Tipo{
	public TNum(){
	}
	public String name(){return "entier";}
	public String toString(){return ("tipo(entier)");}
	public boolean bind(PilaBind pila){return true;}
	public TKind tKind(){return TKind.NUM;}
	public boolean igual(Tipo tipo){
		return (tipo.tKind() == TKind.NUM);
	}
}
