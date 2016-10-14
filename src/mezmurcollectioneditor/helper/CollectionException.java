/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.helper;

/**
 *
 * @author Filippo
 */
public class CollectionException extends Exception {
    
    private final String name;
    
    public CollectionException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
