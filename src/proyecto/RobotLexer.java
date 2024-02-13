package proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobotLexer {
    private final List<String> tokens;
    private int currentTokenIndex;

    public RobotLexer(String filePath) {
    	
    	// Este metodo lee el archivo que esta en la carpeta data
        this.tokens = new ArrayList<>();
        this.currentTokenIndex = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	line = line.replaceAll("\\s+","");
                tokenize(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tokenize(String line) {
        // la expresiones regulares para simplificar la sintaxis
        Pattern commandPattern = Pattern.compile("\\(|\\)|defvar|if|can-move\\?|move-dir|turn|not|blocked\\?|move|repeat|Spaces|isZero\\?|defun|put|pick\\s.|run-dirs ");
        Pattern variablePattern = Pattern.compile("([a-zA-Z]+)"); // Variables
        Pattern numberPattern = Pattern.compile("\\d+"); // numeros
        Pattern constantPattern = Pattern.compile("(Dim|myXpos|myYpos|myChips|myBalloons|balloonsHere|ChipsHere|Spaces|left|:right|:around|:north|:south|:east|:west|:balloons|:chips|:front)"); // Constantes
        
        Pattern combinedPattern = Pattern.compile(commandPattern.pattern() + "|" +
                variablePattern.pattern() + "|" + 
                numberPattern.pattern() + "|" +
                constantPattern.pattern());
                
        Matcher matcher = combinedPattern.matcher(line);
        
        while (matcher.find()) {
            String token = matcher.group(); // Convert to lowercase            
            tokens.add(token);
        }
    }   

    
    public String getNextToken() {
        if (currentTokenIndex < tokens.size()) {
            return tokens.get(currentTokenIndex++);
        }
        return null;
    }

    public static void main(String[] args) {
        //el main
        RobotLexer lexer = new RobotLexer("data/programaPrueba.txt");
        String token;
        while ((token = lexer.getNextToken()) != null) {
            System.out.println("Token: " + token);
        }
    }
}