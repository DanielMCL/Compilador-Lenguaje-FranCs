package ast;

public abstract class Tipo implements ASTNode {
	protected DecType dec = null;
	
	public abstract String name();
	public abstract String toString();
	public abstract TKind tKind();
	public abstract boolean igual(Tipo t);
	
	public NodeKind nodeKind() {return NodeKind.TYPE;}
	public Tipo getTipo() {return this; }
	public void typePrev(){}
	public BoolYTipo type(){ return new BoolYTipo(true, this);}
	public DecType getDec(){return dec;}
	public boolean bind(PilaBind pila){return true;}
	public int size(){return 4;}
  public String generateCode(int depth){return "";}
}
