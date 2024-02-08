package ast;
import java.util.ArrayList;

interface ASTNode {
    public boolean bind(PilaBind pila);
    public void typePrev(); //Quita los typedef entre otras cosas
    public BoolYTipo type();
    public String generateCode(int depth);
    public NodeKind nodeKind();
    public String toString();
}
