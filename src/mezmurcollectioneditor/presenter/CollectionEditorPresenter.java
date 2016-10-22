/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.presenter;

import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mezmurcollectioneditor.data.Callback;
import mezmurcollectioneditor.data.CategoryInfo;
import mezmurcollectioneditor.data.MezmurInfo;
import mezmurcollectioneditor.helper.CollectionException;
import mezmurcollectioneditor.presentation.CollectionEditorView;
import mezmurcollectioneditor.process.JsonProcessor;

/**
 *
 * @author Filippo
 */
public class CollectionEditorPresenter extends BasePresenter {

    private final CollectionEditorView view;
    private final JsonProcessor processor;

    public CollectionEditorPresenter(CollectionEditorView view) {
        this.view = view;
        this.processor = JsonProcessor.newInstance();
    }

    /**
     * This method is used to load mezmurs
     *
     * @param path
     */
    public void loadMezmur(String path) {
        this.processor.open(path, new Callback<MezmurInfo>() {

            @Override
            public void onFailure(CollectionException exception) {
                view.onError(exception.getMessage());
            }

            @Override
            public Type getType() {
                return new TypeToken<List<MezmurInfo>>() {
                }.getType();
            }

            @Override
            public void onSuccess(MezmurInfo data) {
                try {
                    Thread.sleep(0L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CollectionEditorPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
                view.onAppendMezmur(data);
            }
        });
    }

    /**
     * This method is used to load mezmur categories
     *
     * @param path
     */
    public void loadCategories(String path) {
        this.processor.open(path, new Callback<CategoryInfo>() {

            @Override
            public void onFailure(CollectionException exception) {
                view.onError(exception.getMessage());
            }

            @Override
            public Type getType() {
                return new TypeToken<List<CategoryInfo>>() {
                }.getType();
            }

            @Override
            public void onSuccess(CategoryInfo data) {
                view.onAppendCategory(data);
            }
        });
    }

    public void clearAll() {
        this.processor.stopAll();
        this.view.clearEverything();
    }

    public void saveAsJson(File file, List<MezmurInfo> mezmurInfos) {
        this.processor.saveFile(file, mezmurInfos);
    }
}
