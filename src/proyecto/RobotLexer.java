package proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobotLexer {

    public static List<String> tokenizeFromFile(String filePath) {
        List<String> tokens = new ArrayList<>();
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tokenize(content.toString(), tokens);
        return tokens;
    }

    private static void tokenize(String input, List<String> tokens) {
        int index = 0;
        Pattern commandPattern = Pattern.compile("\\([^()]*\\)|defvar|(if)|M|R|C|B|C|c|b|P|J(s\\.?)|can-move?|can-put?|move-dir|turn|not|blocked\\?|move|repeat|Spaces|isZero\\?|defun|put|pick\\s\\.|run-dirs\\b");
        Pattern variablePattern = Pattern.compile("^[a-zA-Z0-9]+$"); // Variables
        Pattern numberPattern = Pattern.compile("\\d+"); // numeros
        Pattern constantPattern = Pattern.compile("(Dim|myXpos|myYpos|myChips|myBalloons|balloonsHere|ChipsHere|Spaces|left|:right|:around|:north|:south|:east|:west|:balloons|:chips|:front)"); // Constantes

        Pattern combinedPattern = Pattern.compile(commandPattern.pattern() + "|" +
                variablePattern.pattern() + "|" +
                numberPattern.pattern() + "|" +
                constantPattern.pattern());

        while (index < input.length()) {
            char currentChar = input.charAt(index);

            if (currentChar == '(') {
                int endIndex = findMatchingParenthesis(input, index);
                if (endIndex != -1) {
                    tokens.add(input.substring(index, endIndex + 1));
                    index = endIndex + 1;
                } else {
                    // No se encontró el paréntesis de cierre correspondiente, se agrega el '(' como token
                    tokens.add("(");
                    index++;
                }
            } else if (Character.isWhitespace(currentChar)) {
                // Espacios en blanco, simplemente incrementa el índice
                index++;
            } else {
                // Intenta buscar números o constantes
                Matcher combinedMatcher = combinedPattern.matcher(input.substring(index));
                if (combinedMatcher.lookingAt()) {
                    tokens.add(combinedMatcher.group());
                    index += combinedMatcher.end();
                } else {
                    // Otros caracteres se agregan como tokens individuales
                    tokens.add(String.valueOf(currentChar));
                    index++;
                }
            }
        }
    }

    private static int findMatchingParenthesis(String input, int start) {
        int count = 1;
        int index = start + 1;

        while (count > 0 && index < input.length()) {
            char currentChar = input.charAt(index);
            if (currentChar == '(') {
                count++;
            } else if (currentChar == ')') {
                count--;
            }
            index++;
        }

        return (count == 0) ? index - 1 : -1;
    }


}

