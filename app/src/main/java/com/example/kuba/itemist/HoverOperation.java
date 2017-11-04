package com.example.kuba.itemist;

import android.graphics.Rect;
import android.view.View;

public interface HoverOperation {
    abstract void hoverEnded(DynamicListView dynamicListView, long stableID, int currentPosition, int originalPosition, Rect hoverCellBounds, Rect viewBounds);

    abstract void hoverPosition(DynamicListView dynamicListView, long stableID, int currentPosition, int originalPosition, Rect hoverCellBounds, Rect viewBounds);

    abstract void viewSwitched(DynamicListView dynamicListView, long stableID, int position, View oldView, View newView);
}
