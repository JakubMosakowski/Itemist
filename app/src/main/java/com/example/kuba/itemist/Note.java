package com.example.kuba.itemist;

import java.io.InputStream;

/**
 * Created by Kuba on 04.11.2017.
 */

public class Note {
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Subpoint[] getSubpoints() {
        return subpoints;
    }

    public void setSubpoints(Subpoint[] subpoints) {
        this.subpoints = subpoints;
    }

    public final int id;
    public final String name;
    public Subpoint[] subpoints;

    public Note(int id, String name, Subpoint[] sub) {
        this.id = id;
        this.name = name;
        this.subpoints = sub;
    }

    public Note(int id, String name) {
        this.id = id;
        this.name = name;
    }


}
