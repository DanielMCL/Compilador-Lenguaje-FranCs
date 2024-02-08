package ast;

public abstract class E extends I {
	protected Tipo tipo;
	public KindI kindI(){return KindI.EXPRESSION;};
    public abstract KindE kind();
    public E opnd1() {throw new UnsupportedOperationException("opnd1");} 
    public E opnd2() {throw new UnsupportedOperationException("opnd2");} 
    //public String num() {throw new UnsupportedOperationException("num");}
    //public NodeKind nodeKind() {return NodeKind.EXPRESSION;}
    public String toString() {return "";}
 	public String generateCode(int depth){return "";}
   	public Tipo getTipo(){return tipo;}
   	public boolean isV(){return false;}
}
