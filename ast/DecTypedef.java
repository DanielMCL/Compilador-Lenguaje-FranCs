package ast;
import java.util.ArrayList;

public class DecTypedef extends DecType{
	
	public DecTypedef(Tipo t, Iden i){
		id = i;
		tipo = t;
	}

	public DecTypeKind decTypeKind(){return DecTypeKind.STRUCT;}
	public String toString(){
		return "typedef(" + tipo.toString() + ", " + id.toString() + ")";
	}	
	public boolean bind(PilaBind pila){
		depth = pila.getDepth();
		boolean b = tipo.bind(pila);
		if(b)
			pila.insert(id.name(), this);
		return b;
	}
	public Tipo getTipo(){
		return tipo.getTipo();
	}
	public BoolYTipo type(){
		return tipo.type();
	}
}
