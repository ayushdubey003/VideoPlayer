package com.ayush.videoplayer;

public class Video {
    int mImgId;
    String mVideoName;
    String mAddress;

    public Video(int imgId, String videoname, String address) {
        mImgId = imgId;
        mVideoName = videoname;
        mAddress = address;
    }

    public int getmImgId() {
        return mImgId;
    }

    public String getmVideoName() {
        return mVideoName;
    }

    public void setmImgId(int mImgId) {
        this.mImgId = mImgId;
    }

    public void setmVideoName(String mVideoName) {
        this.mVideoName = mVideoName;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmAddress() {
        return mAddress;
    }
}
