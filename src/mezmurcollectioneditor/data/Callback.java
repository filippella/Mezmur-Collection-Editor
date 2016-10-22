/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.data;

import java.lang.reflect.Type;
import mezmurcollectioneditor.helper.CollectionException;

/**
 *
 * @author Filippo
 * @param <D>
 */
public interface Callback<D> {
    
    void onSuccess(D data);
    
    void onFailure(CollectionException exception);
    
    Type getType();
}
