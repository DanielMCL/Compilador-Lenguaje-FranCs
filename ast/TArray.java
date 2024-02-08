package ast;

public class TArray extends Tipo{
	private Tipo t;
	private Num size;
	private boolean tNull;
	public TArray(TArray ar, Num size){
		this.t = ar;
		this.size = size;
		tNull = false;
	}
	public TArray(Num size){
		this.size = size;
		tNull = true;
	}
	public void addType(Tipo tipo){
		if(tNull) t = tipo;
		else ((TArray)t).addType(tipo);
	} 
	public Tipo arrayDe(){
		return t;
	}
	
	public Tipo getTipo(){
		if(tNull){
			TArray tAr = new TArray(size);
			tAr.addType(t.getTipo());
			return tAr;
		}
		else return new TArray((TArray)t.getTipo(), size);
	}
	public int getSize(){
		return size.value();
	}
	public int size(){
		return t.size()*this.size.value();
	}
	public String name(){return (t.name() + "[" + size.toString() + "]");}
	public String toString(){
		return ("tipo(" + t.name() + "[" + size.toString() + "])");
		}
	public boolean bind(PilaBind pila){
		boolean b = t.bind(pila);
		return b;
	}
	public TKind tKind(){return TKind.ARRAY;}
	public BoolYTipo type(){
		BoolYTipo bt = size.type();
		Tipo num = bt.getTipo();
		boolean b = bt.getBool() && num.tKind() == TKind.NUM; 
		return new BoolYTipo(b, this);
	}
	public boolean igual(Tipo tipo){
		if(tipo.tKind() == TKind.ARRAY){
			TArray t1 = (TArray) tipo; 
			if(this.arrayDe().igual(t1.arrayDe())){
				if (size.value() == t1.getSize()){
					return true;
				}
			}
		}
		return false;
	}
	public DecType getDec(){
		return t.getDec();
	}
}
