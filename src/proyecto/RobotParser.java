package proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RobotParser {
    private final List<String> tokens;
    private int currentTokenIndex;
    private List<String> nombresList;
    //private final List<String> arrayNomFuncinesParametros;

    public RobotParser(List<String> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
        this.nombresList = new ArrayList<>();
    }

    public void parseTokens() {
        boolean analisisCorrecto = true;
        try {
            while (currentTokenIndex < tokens.size()) {
                String currentToken = tokens.get(currentTokenIndex);
                currentToken = currentToken.replaceAll("\\s", "");

                if (isInstructionKeyword(currentToken) || isConditionalKeyword(currentToken) || isParenthesesKeyword(currentToken)) {
                    // Verificar si el siguiente token es "("
                    if ("(".equals(currentToken)) {
                        throw new SyntaxError("Error sintáctico en " + currentToken);
                    } else if (")".equals(currentToken)) {
                        throw new SyntaxError("Error sintáctico en " + currentToken);
                    }
                    analisisCorrecto = false;
                } else {
                    List<String> bloquesFunciones = obtenerBloquesFunciones(currentToken);
                    for (String bloque : bloquesFunciones) {
                    	//System.out.println("BLOQUE EN EL ARRAY BLOQUES "+bloque);
                    	if(bloque.startsWith("((")) {
                    		continue;
                    	}
                    	
                    	else if (bloque.startsWith("(defun")) {
                    		int indexOpenParentesis=bloque.indexOf("(",5);
                    		String variableName=bloque.substring(6,indexOpenParentesis);
                    		nombresList.add(variableName);
                    		//nombresList.add(variableName);
                    		int indexCloseParentesis=bloque.indexOf(")",indexOpenParentesis);
                    		String argumentNames=bloque.substring(indexOpenParentesis+1,indexCloseParentesis);
                    		
                    		if (variableName!="" && argumentNames!="") {
                    			
                        		nombresList.add(argumentNames);
                    		}
                    		

                    		
                    	}
                    	else {
                            if (!verificarReglas(bloque, nombresList)) {
                                System.out.println("Error de análisis sintáctico en la línea: " + bloque);
                                analisisCorrecto = false;
                            }
                    	}
                    }
                    //
                    //System.out.println("Array de bloques para " + currentToken + ": " + bloquesFunciones);

         
                }
                currentTokenIndex++;
            }
            if (analisisCorrecto) {
            	//System.out.println(nombresList);
                System.out.println("Análisis sintáctico exitoso. El código es correcto.");
            }
        } catch (SyntaxError e) {
            System.out.println("Error de análisis sintáctico: " + e.getMessage());
        }
    }

    private boolean isInstructionKeyword(String token) {
        return token.equals("defvar") || token.equals("move") || token.equals("turn") || token.equals("repeat") || token.equals("run-dirs");
    }

    private boolean isConditionalKeyword(String token) {
        return token.equals("if") || token.equals("loop") || token.equals("repeat") || token.equals("not");
    }

    private boolean isParenthesesKeyword(String token) {
        return token.equals("(") || token.equals(")");
    }

    private List<String> obtenerBloquesFunciones(String token) {
        List<String> bloquesList = new ArrayList<>();
        obtenerBloquesRecursivo(token, bloquesList);
        
        return bloquesList;
    }

    private static void obtenerBloquesRecursivo(String token, List<String> bloquesList) {
        int nivel = 0;
        int inicioBloque = 0;

        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);

            if ((c == '(') ) {
                nivel++;
                if (nivel == 1) {
                    inicioBloque = i;
                }
            } else if (c == ')') {
                nivel--;

                if (nivel == 0) {
                    String bloque = token.substring(inicioBloque, i + 1);
                    bloquesList.add(bloque);

                    // Llamada recursiva
                    obtenerBloquesRecursivo(bloque.substring(1, bloque.length() - 1), bloquesList);

                    inicioBloque = i + 1;
                }
            }
        }

        if (inicioBloque < token.length()) {
            String bloque = token.substring(inicioBloque);
            if (bloque.startsWith("(") && bloque.endsWith(")") || bloque.startsWith("(defun")) {
            	bloquesList.add(bloque);
            }
            else if (bloque.startsWith("if(") || bloque.startsWith("loop(") || bloque.startsWith("(defun("))  {
            	bloquesList.add(bloque);
            }
            
        }
    }

    private static boolean verificarReglas(String bloque, List<String> nombresList) {
        // Expresión regular para el patrón del condicional
        //String condicionalPattern = "\\(if\\s[^()]+\\s[^()]+\\s[^()]+\\)";
        Pattern condicionalPattern = Pattern.compile("\\(if\\s[^()]+\\s[^()]+\\s[^()]+\\)");
        
        //Expresion regular las las funciones defun
        //String funcionPattern = "\\(defun\\s+([\\w\\d]+)\\s*\\((.*?)\\)\\s*(.*?)\\)";
        Pattern funcionPattern = Pattern.compile("\\(defun\\s+([\\w\\d]+)\\s*\\((.*?)\\)\\s*(.*?)\\)");
        
        // Expresions regulares para las instrucciones
        //String instruccionPattern = "^\\((defvar[\\w]+(\\d+|\\w+))|" +
    	//        "(=[\\w]+(\\d+|\\w+))|" +
    	//        "(move(\\d+|\\w+))|" +
    	//        "(skip(\\d+|\\w+))|" +
    	//        "(turn:[left|right|around])|" +
    	//        "(face:[north|south|east|west])|" +
    	//        "(put:[balloons|chips]\\s*(\\d+|\\w+))|" +
    	//        "(pick:[balloons|chips]\\s*(\\d+|\\w+))|" +
    	//        "(move-dir\\s*(\\d+|\\w+)\\s*:[front|right|left|back])|" +
    	//        "(run-dirs:[front|right|left|back]+)|" +
    	//        "(move-face\\s*(\\d+|\\w+)\\s*:[north|south|west|east])|" +
    	//        "(null)\\)$";
        
        Pattern instruccionPattern = Pattern.compile("^\\((defvar[\\w]+(\\d+|\\w+))|" +
    	        "(=[\\w]+(\\d+|\\w+))|" +
    	        "(move(\\d+|\\w+))|" +
    	        "(skip(\\d+|\\w+))|" +
    	        "(turn:[left|right|around])|" +
    	        "(face:[north|south|east|west])|" +
    	        "(put:[balloons|chips]\\s*(\\d+|\\w+))|" +
    	        "(pick:(balloons|chips)\\s*(\\d+|\\w+))|" +
    	        "(move-dir\\s*(\\d+|\\w+)\\s*:[front|right|left|back])|" +
    	        "(run-dirs:(front|right|left|back)+)|" +
    	        "(move-face\\s*(\\d+|\\w+)\\s*:[north|south|west|east])|" +
    	        "(null)\\)$");
        
        // Expresion regular para el bloque vacio
        //String emptyBloquePattern = "\\(\\s*\\)";
        Pattern emptyBloquePattern = Pattern.compile("\\(\\s*\\)");
        
        
        //Expresiones regulares para los condicionales esxeptuando if y loop
        //String condicionPattern = "\\((facing\\?[NSEW])|" +
        //        "(blocked\\?)|" +
        //        "(can-(put|pick)\\?:(chips|balloons)\\s*(\\d+|\\w+))|" +
        //        "(can-move\\?(:north|:south|:west|:east))|" +
        //        "(isZero\\?(\\d+|\\w+))|" +
        //        "(not.+?)\\)";
        
        Pattern condicionPattern = Pattern.compile("\\((facing\\?[NSEW])|" +
                        "(blocked\\?)|" +
                        "(can-(put|pick)\\?:(chips|balloons)\\s*(\\d+|\\w+))|" +
                        "(can-move\\?(:north|:south|:west|:east))|" +
                        "(isZero\\?(\\d+|\\w+))|" +
                        "(not.+?)\\)");

        // Expresión regular para el patrón de can-move
        //String canMovePattern = "\\(can-move\\?[\\w]+\\)";

        // Verificar si el bloque cumple con alguna de las reglas
        Pattern combinedPattern = Pattern.compile(condicionalPattern.pattern() + "|" + funcionPattern.pattern()  + "|" + instruccionPattern.pattern() + "|" + condicionPattern.pattern() + "|" +emptyBloquePattern.pattern());
        //return bloque.matches(condicionalPattern) || bloque.matches(canMovePattern) || bloque.matches(emptyBloquePattern) ||bloque.matches(condicionPattern) ||bloque.matches(instruccionPattern) ||bloque.matches(funcionPattern);
        Matcher matcher = combinedPattern.matcher(bloque);
        
        boolean boolNombre=false;
        for(String nombres: nombresList) {
        	//System.out.println(nombres+" bloque: "+bloque);
        	if (bloque.indexOf(nombres)!=-1) {
        		//System.out.println(bloque+" "+nombres);
        		boolNombre=true;
        	}
        }
        
        if (matcher.find()||boolNombre) {
            // Iterar sobre los grupos de captura
            
            return true; // Coincide con algún patrón
        } else {
            return false; 
        }
        
    
    }

    private static class SyntaxError extends RuntimeException {
        public SyntaxError(String message) {
            super(message);
        }
    }
}