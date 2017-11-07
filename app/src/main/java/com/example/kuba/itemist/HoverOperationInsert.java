package com.example.kuba.itemist;


import android.graphics.Rect;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class HoverOperationInsert extends AbstractHoverOperation {
    ArrayList backingList;

    public HoverOperationInsert(ArrayList backingList) {
        this.backingList = backingList;
    }

    @Override
    public void hoverEnded(DynamicListView dynamicListView, long stableID, int currentPosition, int originalPosition, Rect hoverCellBounds, Rect viewBounds) {

        if (currentPosition == DynamicListView.INVALID_POSITION) {
            return;
        }
        if (currentPosition != originalPosition) {
            if (originalPosition <= currentPosition) {
                dynamicListView.animatePositionShift(originalPosition, currentPosition-1, -1);
            } else {
                dynamicListView.animatePositionShift(currentPosition+1, originalPosition, 1);
            }

            moveElement(backingList, originalPosition, currentPosition);
            ((BaseAdapter) dynamicListView.getAdapter()).notifyDataSetChanged();
        }
    }
}
