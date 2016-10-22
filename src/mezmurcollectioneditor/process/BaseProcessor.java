/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.process;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import mezmurcollectioneditor.data.Callback;
import mezmurcollectioneditor.utilities.FileUtility;

/**
 *
 * @author Filippo
 * @param <D>
 */
public abstract class BaseProcessor<D> implements FileProcessor {
    
    private static final boolean DEFAULT_VALUE = true;
    
    protected FileUtility fileUtility = new FileUtility();
    protected Gson gson = new Gson();
    
    private boolean finished = DEFAULT_VALUE;
    private String path;
    private Callback callback;
    private final List<Thread> taskList = new ArrayList<>();
    
    protected void createTask(String path, Callback callback) {
        this.path = path;
        this.callback = callback;
        this.finished = false;
        Thread task = new Thread(this);
        this.taskList.add(task);
        task.start();
    }
    
    @Override
    public boolean hasFinished() {
        return finished;
    }

    @Override
    public void run() {
        onDoInbackground(path, callback);
        this.finished = DEFAULT_VALUE;
    }
    
    protected void stopTasks() {
        for(int i = 0; i < taskList.size(); i++) {
            Thread task = taskList.get(i);
            task.stop();
        }
    }

    protected abstract void onDoInbackground(String path, Callback<D> callback);
}
