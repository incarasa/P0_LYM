package proyecto;

import java.util.List;

public class RobotParser {
    private List<String> tokens;
    private int currentTokenIndex;

    public RobotParser(List<String> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    public void parseTokens() {
        // Inicia el análisis sintáctico
        // Implementa las reglas gramaticales aquí
        program();
    }

    // Resto del código...

    private void program() {
        // Regla gramatical para el programa
        // Por ejemplo, esperamos una secuencia de comandos
        while (currentTokenIndex < tokens.size()) {
            //command();
        }
    }

    // Resto del código...

    //private boolean isValidCommand(String token) {
        // Implementa la lógica para verificar si el token es un comando válido
        // Puedes usar expresiones regulares, comparaciones de cadenas, etc.
        // Retorna true si es válido, false si no lo es
        //return /* lógica de validación */;
    //}
}

