/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.process;

import mezmurcollectioneditor.data.Callback;

/**
 *
 * @author Filippo
 */
public interface FileProcessor extends Runnable {
    
    void open(String path, Callback callback);
    
    boolean hasFinished();
}
