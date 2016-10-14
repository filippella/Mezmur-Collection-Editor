/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.process;

import com.google.gson.Gson;
import mezmurcollectioneditor.data.Callback;
import mezmurcollectioneditor.utilities.FileUtility;

/**
 *
 * @author Filippo
 */
public abstract class BaseProcessor implements FileProcessor {
    
    private static final boolean DEFAULT_VALUE = true;
    
    protected FileUtility fileUtility = new FileUtility();
    protected Gson gson = new Gson();
    
    private boolean finished = DEFAULT_VALUE;
    private String path;
    private Callback callback;
    
    protected void createTask(String path, Callback callback) {
        this.path = path;
        this.callback = callback;
        finished = false;
        new Thread(this).start();
    }


    @Override
    public boolean hasFinished() {
        return finished;
    }

    @Override
    public void run() {
        onDoInbackground(path, callback);
        finished = DEFAULT_VALUE;
    }

    protected abstract void onDoInbackground(String path, Callback callback);
}
