package com.example.kuba.itemist;

import java.io.InputStream;


/**
 * Created by Kuba on 04.11.2017.
 */

public class Subpoint {

        public final int id;
        public final String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean enabled;

        public Subpoint(int id, String name) {
            this.id = id;
            this.name = name;
            this.enabled=false;
        }


    }
