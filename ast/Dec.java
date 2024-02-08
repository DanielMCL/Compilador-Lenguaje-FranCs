package ast;

public abstract class Dec extends I {
	protected Iden id;
	protected Tipo tipo;
	protected int delta = 4;
	protected int depth;
	public KindI kindI() {return KindI.DEC;}
	public String id() {return id.name();}
	public abstract DecKind decKind();
	public abstract String toString();
	public Tipo getTipo(){return tipo;}
	public int getDelta() {return delta;}
	public int setDelta(int n){delta = n;return n+ this.tipo.size();};
	public int getDepth(){return depth;};
	public String generateCode(int depth){return "";}
	public int size(){return 0;}
	public String getName(){return id.name();}
	public Boolean isRef(){return false;}
	public int globalSize() {return 0;}
}
