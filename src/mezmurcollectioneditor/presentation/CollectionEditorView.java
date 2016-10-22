/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.presentation;

import mezmurcollectioneditor.data.CategoryInfo;
import mezmurcollectioneditor.data.MezmurInfo;

/**
 *
 * @author Filippo
 */
public interface CollectionEditorView {

    void onAppendMezmur(MezmurInfo data);

    void onError(String message);

    void onAppendCategory(CategoryInfo data);
}
