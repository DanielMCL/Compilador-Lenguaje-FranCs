package ast;
import java.util.ArrayList;
import java.util.HashMap;

public class Programa implements ASTNode{
	private ArrayList<Dec> pr;
	private PilaBind pila;
	private Iden main;
	public static int index =0;
	public Programa(ArrayList<Dec> dec){
		pr = dec;
	}
	public void add(Dec d){
		pr.add(d);
	}
	public void setDelta(){
		int delta= 0;
		for( Dec d: pr)
			delta = d.setDelta(delta);
	}
	public NodeKind nodeKind(){return NodeKind.PR;}
	
	public boolean bind(PilaBind p){
		boolean b = true;
		pila = new PilaBind();
		pila.add();
		for(int i = 0; i < pr.size(); i++){
			b = pr.get(i).bind(pila) && b;
			if(pr.get(i).decKind()==DecKind.VAR){
				((DecVar) pr.get(i)).setGlobal(index);
				index= index +1;
			}
		}
		main = new Iden("principale");
		b = main.bind(pila) && b;
		pila.remove();
		
		return b;
	}
	
	public void typePrev(){
		for(int i = 0; i < pr.size(); i++){
			pr.get(i).typePrev();
		}
	}
	
	public BoolYTipo type(){
		boolean b = true;
		
		for(int i = 0; i < pr.size(); i++){
			BoolYTipo bt = pr.get(i).type();
			b = b && bt.getBool();
		}
		
		return new BoolYTipo(b, null);
	}
	public String toString() {return "Programa(" + pr.toString() + ")";}
	
    public String generateCode(int depth) {
    		int sizeGlobals = 0;
    		for(Dec d: pr){
			sizeGlobals = sizeGlobals+d.globalSize();
		}
		
		StringBuilder ss = new StringBuilder("(module\n");
		ss.append("(import \"runtime\" \"print\" (func $print (type $_sig_i32)))\n");
		ss.append("(import \"runtime\" \"read\" (func $read (result i32)))\n");
		ss.append("(import \"runtime\" \"exceptionHandler\" (func $exception (type $_sig_i32)))\n");
		ss.append("(type $_sig_i32 (func (param i32)))\n");
		ss.append("(type $_sig_void (func ))\n");
		ss.append("(type $_sig_ri32 (func (result i32)))\n");
		ss.append("(memory 2000)\n");
		ss.append("(global $SP (mut i32) (i32.const "+(sizeGlobals + 4)+"))\n");
		ss.append("(global $MP (mut i32) (i32.const 0))\n");
		ss.append("(global $CP (mut i32) (i32.const 0))\n");
		ss.append("(global $NP (mut i32) (i32.const 131071996))\n");
		
		ss.append ("(start $globals)\n");
		
		for(Dec d: pr){
			ss.append(d.generateCode(0));
		}
		ss.append("(func $globals\n");
		for(int i = 0; i < index; i++){
			ss.append("call $global"+i+"\n");
		}
		ss.append("call $principale_nule\n");
		ss.append(")\n");
		
		// Funcion que reserva $size posiciones de memoria de la pila de memoria.
		// Devuelve el valor antiguo de $MP.
		ss.append("(func $reserveStack (param $size i32) (result i32)\n");
		ss.append("get_global $MP\n"); // Apilamos el resultado
		ss.append("get_global $SP\n");
		ss.append("set_global $MP\n"); // Actualizamos $MP a $SP
		ss.append("get_global $SP\n");
		ss.append("get_local $size\n");
		ss.append("i32.add\n");
		ss.append("set_global $SP\n"); // Actualizamos $SP a $SP + $size
		ss.append("get_global $SP\n");
		ss.append("get_global $NP\n");
		ss.append("i32.gt_u\n");
		ss.append("if\n"); // Si $SP > $NP salta una excepcion
		ss.append("i32.const 3\n");
		ss.append("call $exception\n");
		ss.append("end\n");
		ss.append(")\n");

		ss.append("(func $freeStack ");
		ss.append("(type $_sig_void)\n");
		
		ss.append("get_global $MP\n");// Recuperamos el CP anterior
		ss.append("i32.load offset=8\n");
		ss.append("set_global $CP\n");
		
		ss.append("get_global $MP\n");//Recuperamos  el SP anterior
		//ss.append("i32.load\n"); // Apilamos enlace dinamico = MP del marco de la funcion llamante
		ss.append("i32.load offset=4\n"); // Apilamos MP del marco de la funcion que se elimina
		ss.append("set_global $SP\n"); // Actualizamos $SP al MP del marco de la funcion que se elimina
		
		ss.append("get_global $MP\n");
		ss.append("i32.load\n"); // Apilamos enlace dinamico = MP del marco de la funcion llamante
		ss.append("set_global $MP\n"); // Actualizamos $MP al MP del marco de la funcion llamante
		ss.append(")\n");
		
		ss.append("(export \"principale\" (func $principale_nule))\n)");
		
		return ss.toString();
    }
}
