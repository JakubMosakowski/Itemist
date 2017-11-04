package com.example.kuba.itemist;

/**
 * Created by Kuba on 15.10.2017.
 */

public class Model {
    String name;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    boolean enabled; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */

    Model(String name, boolean value) {
        this.name = name;
        this.enabled = value;
    }


    public String getName() {
        return this.name;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

}
