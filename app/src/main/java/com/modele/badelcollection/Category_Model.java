package com.modele.badelcollection;

public class Category_Model {

private String CategoryIconlink;
private String categoryName;

    public Category_Model(String categoryIconlink, String categoryName) {
        CategoryIconlink = categoryIconlink;
        this.categoryName = categoryName;
    }

    public String getCategoryIconlink() {
        return CategoryIconlink;
    }

    public void setCategoryIconlink(String categoryIconlink) {
        CategoryIconlink = categoryIconlink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
