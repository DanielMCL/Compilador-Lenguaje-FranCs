package alex;
import constructorast.ClaseLexica;

public class ALexOperations {
  private AnalizadorLexicoExp alex;
  public ALexOperations(AnalizadorLexicoExp alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadSuma() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAS); 
  }

  public UnidadLexica unidadAlmohadilla() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ALM); 
  }
  public UnidadLexica unidadTypedef() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.TDEF); 
  } 
  public UnidadLexica unidadopDistinto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DIST); 
  } 
  public UnidadLexica unidadResta() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MENOS); 
  } 
  public UnidadLexica unidadMul() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.AST);
  } 
  public UnidadLexica unidadDiv() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DIV); 
  }
  public UnidadLexica unidadOpModulo() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MOD); 
  }
  public UnidadLexica unidadAsig(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ASIG); 
  }
  public UnidadLexica unidadIgualdad(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IGUAL); 
  }
  public UnidadLexica unidadMayor() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAYOR); 
  }
  public UnidadLexica unidadMayorIgual(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAYORIG); 
  }
   public UnidadLexica unidadMenor() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MENOR); 
  }
   public UnidadLexica unidadMenorIgual(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MENORIG); 
  }
  public UnidadLexica unidadPAp() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.APAREN); 
  } 
  public UnidadLexica unidadPCierre() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CPAREN); 
  }
   public UnidadLexica unidadAbrirCorchete() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ACORCH); 
  }
  public UnidadLexica unidadCerrarCorchete() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CCORCH); 
  } 
    public UnidadLexica unidadAbrirLlaves() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ALLAVE); 
  }
   public UnidadLexica unidadCerrarLlaves() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CLLAVE); 
  } 

  public UnidadLexica unidadFoug(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FOUG); 
  }
  public UnidadLexica unidadAmpersand(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.REF); 
  }
  public UnidadLexica unidadEt(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ET); 
  }
  public UnidadLexica unidadOu(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.OU); 
  }
  public UnidadLexica unidadOui(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IF); 
  }
  public UnidadLexica unidadNon(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ELSE); 
  }
  public UnidadLexica unidadStructe(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.STRUCT,
                                         alex.lexema()); 
  }
  public UnidadLexica unidadClasse(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CLASS,
                                         alex.lexema()); 
  }
   public UnidadLexica unidadRetour(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.RETOUR); 
  }
  public UnidadLexica unidadEntier(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.NUM, alex.lexema()); 
  }
  public UnidadLexica unidadtEntier() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.TINT); 
  }
  public UnidadLexica unidadtBoleen() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.TBOOL); 
  }
  public UnidadLexica unidadBoleen(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.BOOL, alex.lexema()); 
  }
  public UnidadLexica unidadNule() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.NULE); 
  }

   
  public UnidadLexica unidadDosPuntos() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DPUNTOS); 
  }
   public UnidadLexica unidadPunto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PUNTO); 
  }
   public UnidadLexica unidadPuntoYComa(){
 	return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PUNTOCOMA);
  }
   public UnidadLexica unidadopComa() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.COMA); 
  }
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EOF); 
  }
  public UnidadLexica unidadWhilee() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.WHILE); 
  }
  public UnidadLexica unidadLeeg() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.READ); 
  }
  public UnidadLexica unidadPrinte() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.WRITE); 
  }
  public UnidadLexica unidadThis() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.THIS); 
  }
  public UnidadLexica unidadId() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IDEN,
                                         alex.lexema()); 
  } 
  public void error() {
    System.err.println("***"+alex.fila()+", "+alex.columna()+" Caracter inesperado: "+alex.lexema());
  }
}
