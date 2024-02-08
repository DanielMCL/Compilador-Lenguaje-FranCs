package constructorast;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.AnalizadorLexicoExp;
import ast.Programa;
import java.nio.file.*;
import java.io.IOException;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 AnalizadorLexicoExp alex = new AnalizadorLexicoExp(input);
	 ConstructorASTExp constructorast = new ConstructorASTExp(alex);
	 //System.out.println(constructorast.parse().value);
	 Programa programa = (Programa)constructorast.parse().value;
	 //System.out.println(programa);
	 if(!programa.bind(null)) System.out.println("Error, la vinculacion no ha tenido exito");
	 else {
	 	programa.typePrev();
	 	if(!programa.type().getBool()) System.out.println("Error, el tipado no ha tenido exito");
	 	else{
	 		programa.setDelta();
    	try {
    	    Files.write(Paths.get("main.wat"), (programa.generateCode(0)).getBytes());
    	}catch (IOException e) {
            System.out.println("An error occurred while writing code to the file.");
            e.printStackTrace();
    	}
	 	}
	 }
 }
}   
   
