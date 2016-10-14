/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.process;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import mezmurcollectioneditor.data.Callback;
import mezmurcollectioneditor.data.MezmurInfo;

/**
 *
 * @author Filippo
 */
public class JsonProcessor extends BaseProcessor {
    
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
    protected void onDoInbackground(String path, Callback callback) {
        String mezmurJson = fileUtility.readFile(getClass().getResource(path).getPath());
        List<MezmurInfo> mezmurInfos = gson.fromJson(mezmurJson, new TypeToken<List<MezmurInfo>>() {}.getType());
        for(int i = 0; i < mezmurInfos.size(); i++) {
            MezmurInfo mezmurInfo = mezmurInfos.get(i);
            callback.onSuccess(mezmurInfo);
        }
    }
}
