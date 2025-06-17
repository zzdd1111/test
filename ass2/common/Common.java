package comp1110.ass2.common;

import javafx.scene.paint.Color;

public class Common {


    /**
     * Convert the digit of decimal number
     *
     * @param number
     * @param digit
     * @return
     */
    public String ConvertDigit(int number, int digit) {
        String res = "";
        int length = String.valueOf(number).length();
        if (length <= digit) {
            for (int i = 0; i < digit - length; i++) {
                res += "0";
            }
            res += String.valueOf(number);

        }
        return res;


    }

    public Color ConvertPaintColor(char color) {

        var res = switch (color) {

            case Constants.Cyan -> Color.CYAN;
            case Constants.Red -> Color.RED;
            case Constants.Purple -> Color.PURPLE;
            case Constants.Yellow -> Color.YELLOW;
            default -> Color.ORANGE;
        };

        return res;

    }

    public String ConvertColor(char color) {

        var res = switch (color) {

            case 'c' -> "cyan";
            case 'r' -> "red";
            case 'p' -> "purple";
            case 'y' -> "yellow";
            default -> "white";
        };

        return res;

    }
}
