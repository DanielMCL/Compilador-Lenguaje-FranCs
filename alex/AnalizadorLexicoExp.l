package alex;

import errors.GestionErroresTiny;

%%
%cup
%line
%column
%class AnalizadorLexicoExp
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

comentario = \$ [^\n]*

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
separador = [ \t\r\b\n]

foug = foug
whilee = whilee
oui = oui
non = non
structe = structe
tdef = tdef
classe = classe
retour = retour
leeg = leeg
printe = printe
this = deuis

tEntier = entier
tBoleen = boleen
boleen = (vrai|faux)
entier = {digitoPositivo}{digito}* | 0
nule = nule

opSuma = \+
opResta = \-
asterisco = \*
opDivision = \/
opModulo = \%
parentesisApertura = \(
parentesisCierre = \)
ampersand = \&
almohadilla = \#

opIgualdad = \=\=
opMenor = \<
opMayor = \>
opMenorIgual = \<\=
opMayorIgual = \>\=
opDistinto = \!\=

et = et
ou = ou

abrirCorchete = \[
cerrarCorchete = \]
abrirLlaves = \{
cerrarLlaves = \}
dosPuntos = \:
punto = \.
puntoYComa = \;

opAsignacion = \=

identificador = {letra}({letra}|{digito})*

coma  = ,
%%
{separador}               {}
{comentario}              {}
{opSuma}		{return ops.unidadSuma();}
{opResta}		{return ops.unidadResta();}
{asterisco}		{return ops.unidadMul();}
{opDivision}		{return ops.unidadDiv();}
{parentesisApertura}    {return ops.unidadPAp();}
{parentesisCierre}      {return ops.unidadPCierre();} 
{opAsignacion}		{return ops.unidadAsig();} 
{coma}                  {return ops.unidadopComa();}
{foug}			{return ops.unidadFoug();}
{whilee}			{return ops.unidadWhilee();}
{et}			{return ops.unidadEt();}
{oui}			{return ops.unidadOui();}
{ou}			{return ops.unidadOu();}
{non}			{return ops.unidadNon();}
{structe}		{return ops.unidadStructe();}
{classe}		{return ops.unidadClasse();}
{retour}		{return ops.unidadRetour();}
{leeg}			{return ops.unidadLeeg();}
{printe}			{return ops.unidadPrinte();}
{whilee}			{return ops.unidadWhilee();}
{this}			{return ops.unidadThis();}
{tBoleen}		{return ops.unidadtBoleen();}
{boleen}		{return ops.unidadBoleen();}
{tEntier}		{return ops.unidadtEntier();}
{entier}		{return ops.unidadEntier();}
{nule}			{return ops.unidadNule();}
{opModulo}		{return ops.unidadOpModulo();}
{ampersand}		{return ops.unidadAmpersand();}
{opIgualdad}		{return ops.unidadIgualdad();}
{opMenor}		{return ops.unidadMenor();}
{opMayor}		{return ops.unidadMayor();}
{opMenorIgual}		{return ops.unidadMenorIgual();}
{opMayorIgual}		{return ops.unidadMayorIgual();}
{opDistinto}		{return ops.unidadopDistinto();}
{abrirCorchete}		{return ops.unidadAbrirCorchete();}
{cerrarCorchete}	{return ops.unidadCerrarCorchete();}
{abrirLlaves}		{return ops.unidadAbrirLlaves();}
{cerrarLlaves}		{return ops.unidadCerrarLlaves();}
{dosPuntos}		{return ops.unidadDosPuntos();}
{punto}			{return ops.unidadPunto();}
{puntoYComa}		{return ops.unidadPuntoYComa();}
{almohadilla}		{return ops.unidadAlmohadilla();}
{tdef}			{return ops.unidadTypedef();}
{identificador}		{return ops.unidadId();}
[^]                       {errores.errorLexico(fila(),columna(),lexema());}  
