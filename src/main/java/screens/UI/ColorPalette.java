package screens.UI;

import java.awt.Color;

public class ColorPalette {

    
    public static final Color WHITE = Color.decode("#FBF0D8");
    public static final Color DIMMED_WHITE = Color.decode("#B0A896");
    public static final Color BLACK = Color.decode("#282828");
    public static final Color GREEN = Color.decode("#5B7048");
    public static final Color BROWN = Color.decode("#5E4017");
    public static final Color RED = Color.decode("#C54E43");
    
    public static Color dim(Color c, double transparency) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)((1-transparency) * 255));
    }
    
    private ColorPalette(){}
}
