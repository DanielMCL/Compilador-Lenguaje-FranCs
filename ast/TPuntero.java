package ast;

public class TPuntero extends Tipo{
	private Tipo t;
	public TPuntero(Tipo t){
		this.t = t;
	}
	public String name(){return (t.name() + "*");}
	public String toString(){return ("tipo(" + t.name() + "*)");}
	public boolean bind(PilaBind pila){return t.bind(pila);}
	public TKind tKind(){return TKind.PUNTERO;}
	
	public Tipo punteroA(){return t;}
	
	public Tipo getTipo(){return new TPuntero(t.getTipo());}
	public boolean igual(Tipo tipo){
		if(tipo.tKind() == TKind.PUNTERO){
			TPuntero t1 = (TPuntero) tipo;
			return t.igual(t1.punteroA());
		}
		else return false;
	}
	public DecType getDec(){
		return t.getDec();
	}
}
