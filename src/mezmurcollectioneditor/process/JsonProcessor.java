/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.process;

import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.util.List;
import mezmurcollectioneditor.data.Callback;
import mezmurcollectioneditor.data.MezmurInfo;
/**
 *
 * @author Filippo
 * @param <D>
 */
public class JsonProcessor<D> extends BaseProcessor<D> {
    
    private static JsonProcessor sInstance = new JsonProcessor();
    
    private JsonProcessor() {
        //
    }
    
    public static JsonProcessor newInstance() {
        if(sInstance == null) {
            sInstance = new JsonProcessor();
        }
        return sInstance;
    }
    
    @Override
    public void open(String path, Callback callback) {
        createTask(path, callback);
    }

    @Override
    protected void onDoInbackground(String path, Callback<D> callback) {
        String dataJson = fileUtility.readFile(path);
        List<D> dataList = gson.fromJson(dataJson, callback.getType());
        for(int i = 0; i < dataList.size(); i++) {
            D data = dataList.get(i);
            callback.onSuccess(data);
        }
    }

    public void stopAll() {
        stopTasks();
    }

    public void saveFile(File file, String json) {
        this.fileUtility.writeJsonFile(file, json);
    }

    public void saveFile(File file, List<MezmurInfo> mezmurInfos) {
        String json = gson.toJson(mezmurInfos, new TypeToken<List<MezmurInfo>>() {}.getType());
        this.fileUtility.writeJsonFile(file, json);
    }
}
