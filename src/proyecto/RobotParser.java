package proyecto;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobotParser {
    private final List<String> tokens;
    private int currentTokenIndex;

    public RobotParser(List<String> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    public void parseTokens() {
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
                }
                else {
                    validateConditionalSyntax(currentToken);
                }
              

                currentTokenIndex++;
            }
            System.out.println("Análisis sintáctico exitoso. El código es correcto.");
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
    
    private void validateCommandSyntax(String currentToken) {
        if (currentToken.contains("(")) {
            // Verificar si es un comando con paréntesis
        	validateConditionalSyntax(currentToken);
        } else {
            // Es un comando sin paréntesis
            System.out.println("Comando simple: " + currentToken);
        }
    }

    private void validateConditionalSyntax(String currentToken) {
        if (currentToken.contains("(if")&& currentToken.indexOf("(if")==0 ) {
            Pattern commandPattern = Pattern.compile("\\(([^()]+)\\)(.*)");
            Matcher matcher = commandPattern.matcher(currentToken);

            if (matcher.matches()) {
                String commandName = matcher.group(1);
                String remaining = matcher.group(2);

                System.out.println("Comando con paréntesis - Nombre: " + commandName);

                // Verificar el contenido dentro de los paréntesis recursivamente
                if (!remaining.isEmpty()) {
                    validateCommandSyntax(remaining);
                }
            } else {
                throw new SyntaxError("Error sintáctico en el comando: " + currentToken);
            }
        }
        else {
        	
        }
    }

    private int encontrarParCerrado(String token, int indiceInicio) {
        int count = 1;
        int index = indiceInicio + 1;

        while (count > 0 && index < token.length()) {
            char currentChar = token.charAt(index);
            if (currentChar == '(') {
                count++;
            } else if (currentChar == ')') {
                count--;
            }
            index++;
        }

        return (count == 0) ? index - 1 : -1;
    }

    private static class SyntaxError extends RuntimeException {
        public SyntaxError(String message) {
            super(message);
        }
    }
}
