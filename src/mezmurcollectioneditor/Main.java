/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor;

import com.jtattoo.plaf.smart.SmartLookAndFeel;
import de.muntjak.tinylookandfeel.TinyLookAndFeel;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import mezmurcollectioneditor.ui.CollectionEditorScreen;

/**
 *
 * @author Filippo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Toolkit.getDefaultToolkit().setDynamicLayout(true);
            SmartLookAndFeel.setTheme("Green", "LICENSE KEY HERE", "Dalol Technology Services");
            UIManager.setLookAndFeel(new TinyLookAndFeel());
            CollectionEditorScreen screen = new CollectionEditorScreen();
            screen.setLocationRelativeTo(null);
            screen.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
}
