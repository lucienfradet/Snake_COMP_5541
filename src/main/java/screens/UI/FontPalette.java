package screens.UI;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontPalette {
    public static final String fontPath = "data/yoster.ttf";

    public static final Font TITLE = loadFont(48);
    public static final Font TEXT  = loadFont(20);

    private static Font loadFont(float size) {
        try {
            Font base = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            return base.deriveFont(Font.BOLD, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.BOLD, (int) size); // fallback
        }
    }

    private FontPalette() {}
}
