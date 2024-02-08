package ast;

public class BoolYTipo{
	private boolean bool;
	private Tipo tipo;
	
	public BoolYTipo(boolean b, Tipo t){
		bool = b;
		tipo = t;
	}
	
	public boolean getBool(){ return bool; }
	public Tipo getTipo(){ return tipo; }
}
