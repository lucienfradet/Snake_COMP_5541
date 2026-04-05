package screens.UI;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FontPalette {
    
    public static final String FONT_RESOURCE_PATH = "/data/yoster.ttf";
    public static final String FONT_FILE_PATH = "data/yoster.ttf";

    public static final Font TITLE = loadFont(48);
    public static final Font TEXT  = loadFont(20);

    private static Font loadFont(float size) {
        try (InputStream fontStream = FontPalette.class.getResourceAsStream(FONT_RESOURCE_PATH)) {
            if (fontStream != null) {
                Font base = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                return base.deriveFont(Font.BOLD, size);
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            Font base = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_FILE_PATH));
            return base.deriveFont(Font.BOLD, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.BOLD, (int) size); // fallback
        }
    }

    private FontPalette() {}
}
