package ast;

public abstract class V extends E {
	public abstract KindV kindV();
    public KindE kind() {return KindE.VAR; };
    public String name() {throw new UnsupportedOperationException("name");}
    public NodeKind nodeKind() {return NodeKind.EXPRESSION;}
    public String toString() {return "";}
    public abstract Dec getDec();
    public abstract String generateCodeAsig(int depth);
   	public boolean isV(){return true;}
   	public boolean bind2(PilaBind pila){return true;}
}
