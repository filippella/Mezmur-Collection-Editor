/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.helper;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
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
        //createFont(null, 0f);
    }

    private void createFont(String fontPath, float fontSize) {
        try {
            fontPath = "/mezmurcollectioncreator/font/nyala.ttf";
            fontSize = 15f;
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(fontPath));
            font.deriveFont(fontSize);
        } catch (FontFormatException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(FontHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(FontHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Font getCustomFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/mezmurcollectioneditor/font/nyala.ttf"));
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
