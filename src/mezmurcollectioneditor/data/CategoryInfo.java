/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.data;

/**
 *
 * @author Filippo
 */
public class CategoryInfo {
    
    private String cid;
    private String name;
    private String parent;
    private String lmodified;
    private String morder;

    public CategoryInfo(String name) {
        this.name = name;
    }

    public String getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public String getLmodified() {
        return lmodified;
    }

    public String getMorder() {
        return morder;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
