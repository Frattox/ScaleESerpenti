package util;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;

import java.util.function.UnaryOperator;

public class Util {
    public static void setTextFormatter(TextField t) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Regex per numeri
                return change;
            }
            return null; // Rifiuta il cambiamento
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        t.setTextFormatter(textFormatter);
    }

    public static String toHexString(Color color) {
        return String.format( "#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255)
        );
    }
}
