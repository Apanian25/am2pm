package com.dawson.jonat.stockers.Hints;

public class Hints {
    private String hint;
    private String url;

    public Hints(){

    }

    public Hints(String hint, String url){
        this.hint = hint;
        this.url = url;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
