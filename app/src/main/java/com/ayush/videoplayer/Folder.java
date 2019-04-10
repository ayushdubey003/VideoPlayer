package com.ayush.videoplayer;

public class Folder {
    private int mID;
    private String mFolderName;
    private int mSize;

    public Folder(int id, String folderName, int size) {
        mID = id;
        mFolderName = folderName;
        mSize = size;
    }

    public int getmID() {
        return mID;
    }

    public String getmFolderName() {
        return mFolderName;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public void setmFolderName(String mFolderName) {
        this.mFolderName = mFolderName;
    }

    public int getmSize() {
        return mSize;
    }

    public void setmSize(int mSize) {
        this.mSize = mSize;
    }
}
