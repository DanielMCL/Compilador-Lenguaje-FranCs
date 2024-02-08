package ast;

public abstract class DecType extends Dec{
	
	public String name(){return id.name();}
	public DecKind decKind(){return DecKind.TYPE;}
	
	public abstract DecTypeKind decTypeKind();
	public PilaBind getPila(){ return null;}
}

