package com.jcodecraeer.xrecyclerview;

/**
 * Created by zq on 16/1/15.
 */
interface  BaseRefreshHeader {
    public void onMove(float delta) ;
    public boolean releaseAction();
    public void refreshComplate();
    public final static int STATE_NORMAL = 0;
    public final static int STATE_RELEASE_TO_REFRESH = 1;
    public final static int STATE_REFRESHING = 2;
    public final static int STATE_DONE = 3;
}
