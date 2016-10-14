/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.renderer;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import mezmurcollectioneditor.data.MezmurInfo;
import mezmurcollectioneditor.helper.FontHelper;

/**
 *
 * @author Filippo
 */
public class MezmurRenderer extends DefaultListCellRenderer {
    
    private final Font amharicFont;

    public MezmurRenderer(FontHelper fontHelper) {
        amharicFont = fontHelper.getCustomFont();
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
        Component renderer = (JLabel) (super.getListCellRendererComponent(list, value, index, isSelected, hasFocus));
        if (value instanceof MezmurInfo) {
            MezmurInfo mezmurInfo = (MezmurInfo) value;
            ((JLabel)renderer).setText(mezmurInfo.getMezmurTitle());
            renderer.setFont(amharicFont);
            
//            renderer.setIcon(photo.getIcon());
//            renderer.setToolTipText(photo.getDescription());
            //Filippo.get().load(photo.getThumbnailUrl()).into(renderer);
        }
        return renderer;
    }
}
