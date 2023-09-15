package com.android.launcher3.settings;

import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.android.launcher3.Launcher;
import com.android.launcher3.LauncherAppState;
import com.android.launcher3.util.ItemInfoMatcher;

public class OverrideApplyHandler implements Runnable{
    private static final String TAG = "OverrideApplyHandler";
    private final Context mContext;
    private final boolean mRemoveDrawer;

    public OverrideApplyHandler(Context context, boolean removeDrawer) {
        mContext = context;
        mRemoveDrawer = removeDrawer;
    }

    @Override
    public void run() {
        // Clear the workspace and db.
        if (mContext instanceof Launcher) {
            Log.d(TAG, "is Launcher: " + (mContext instanceof Launcher));
            Launcher launcher = (Launcher) mContext;
            ItemInfoMatcher matcher = ItemInfoMatcher.ofUser(Process.myUserHandle());
            launcher.getModelWriter().deleteItemsFromDatabase(matcher);
//                if (!mRemoveDrawer) {
//                    launcher.getWorkspace().removeItemsByMatcher(matcher);
//                }
        }
        // Clear the icon cache.
        Log.i(TAG, "Clearing user data com.android.launcher3");
        LauncherAppState.getInstance(mContext).getIconCache().clear();
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            Log.e(TAG, "Error waiting", ex);
        }
        Log.i(TAG, "kill com.android.launcher3");
        Process.killProcess(Process.myPid());
    }
}
