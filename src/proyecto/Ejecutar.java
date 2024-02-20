package proyecto;

import java.util.List;

public class Ejecutar {
    public static void main(String[] args) {
        String filePath = "data/programaPrueba.txt"; // Reemplaza con la ruta correcta de tu archivo

        // Paso 1: Ejecutar RobotLexer para obtener los tokens
        List<String> tokens = RobotLexer.tokenizeFromFile(filePath);

        // Paso 2: Imprimir los tokens generados por RobotLexer
        for (String token : tokens) {
            System.out.println("Token: " + token);
        }

        // Paso 3: Ejecutar RobotParser para verificar la sintaxis de los tokens
        RobotParser parser = new RobotParser(tokens);
        parser.parseTokens();
    }
}
