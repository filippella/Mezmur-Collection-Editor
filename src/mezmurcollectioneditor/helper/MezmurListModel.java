/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.helper;

import javax.swing.DefaultListModel;
import mezmurcollectioneditor.data.MezmurInfo;

/**
 *
 * @author Filippo
 */
public class MezmurListModel extends DefaultListModel<MezmurInfo> {
    
    @Override
    public void addElement(MezmurInfo obj) {
        super.addElement(obj);
        int size = this.getSize();
        this.fireIntervalAdded(this, size-1, size);
    }
}
