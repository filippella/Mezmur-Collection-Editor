/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.presenter;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mezmurcollectioneditor.data.Callback;
import mezmurcollectioneditor.data.MezmurInfo;
import mezmurcollectioneditor.helper.CollectionException;
import mezmurcollectioneditor.presentation.CollectionEditorView;
import mezmurcollectioneditor.process.FileProcessor;
import mezmurcollectioneditor.process.JsonProcessor;

/**
 *
 * @author Filippo
 */
public class CollectionEditorPresenter extends BasePresenter {
    
    private final CollectionEditorView view;

    
    public CollectionEditorPresenter(CollectionEditorView view) {
        this.view = view;
    }
    
    public void loadMezmur(String path) {
        FileProcessor processor = JsonProcessor.newInstance();
        processor.open(path, new Callback<MezmurInfo>() {

            @Override
            public void onSuccess(MezmurInfo data) {
                try {
                    Thread.sleep(30L);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    Logger.getLogger(CollectionEditorPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
                view.onAppendMezmur(data);
            }

            @Override
            public void onFailure(CollectionException exception) {
                view.onError(exception.getMessage());
            }
        });
    }
}
