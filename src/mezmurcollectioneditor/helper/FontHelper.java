/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.helper;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Filippo
 */
public final class FontHelper {

    private Font font;

    public FontHelper() {
    }

    public Font getCustomFont() {
        try {
            InputStream fontInputStream = ClassLoader.getSystemResourceAsStream("mezmurcollectioneditor/font/nyala.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontInputStream);
        } catch (FontFormatException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(FontHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(FontHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return font.deriveFont(16f);
    }

    public Font getFont() {
        return this.font;
    }
}
