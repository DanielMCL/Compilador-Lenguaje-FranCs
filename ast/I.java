package ast;

public abstract class I implements ASTNode {
    public abstract KindI kindI();
    public NodeKind nodeKind() {return NodeKind.INSTRUCTION;}
    public String toString() {return "ast instruction error";}
    public Tipo noTdef(){return null;}
    public void typePrev(){};
    public int setDelta(int n) {return n;}
    	public String generateCode(int depth){return "";}
}
