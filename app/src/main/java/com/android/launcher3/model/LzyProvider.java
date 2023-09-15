package com.android.launcher3.model;

import android.util.Pair;


import com.android.launcher3.model.data.AppInfo;
import com.android.launcher3.model.data.ItemInfo;
import com.android.launcher3.model.data.WorkspaceItemInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LzyProvider implements Supplier<List<Pair<ItemInfo, Object>>> {

    private ArrayList<AppInfo> mItems;
    public LzyProvider(ArrayList<AppInfo> items) {
        mItems = items;
    }

    @Override
    public ArrayList<Pair<ItemInfo, Object>> get() {
        ArrayList<Pair<ItemInfo, Object>> installQueue = new ArrayList<>();
        for (AppInfo info : mItems ) {
            WorkspaceItemInfo si = info.makeWorkspaceItem();
            installQueue.add(Pair.create((ItemInfo) si, null));
        }
        return installQueue;
    }
}
