package com.sharpflux.shetkarimaza.model;

public class MyLanguage {

    String languageName;
    boolean selected;

    public MyLanguage( ) {

    }

    public MyLanguage(String languageName,Boolean Selected ) {
        this.languageName = languageName;
        this.selected=Selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }


}
