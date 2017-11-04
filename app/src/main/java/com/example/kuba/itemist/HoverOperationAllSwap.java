package com.example.kuba.itemist;
import android.graphics.Rect;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public class HoverOperationAllSwap extends AbstractHoverOperation {
    ArrayList backingList;

    public HoverOperationAllSwap(ArrayList backingList) {
        this.backingList = backingList;
    }

    @Override
    public void hoverPosition(DynamicListView dynamicListView, long stableID, int currentPosition, int originalPosition, Rect hoverCellBounds, Rect viewBounds) {
        if (currentPosition == DynamicListView.INVALID_POSITION) {
            return;
        }
        if (currentPosition != originalPosition) {
            dynamicListView.animatePositionMove(originalPosition, currentPosition);
            swapElements(backingList, currentPosition, originalPosition);
            ((BaseAdapter) dynamicListView.getAdapter()).notifyDataSetChanged();
        }
    }


}
