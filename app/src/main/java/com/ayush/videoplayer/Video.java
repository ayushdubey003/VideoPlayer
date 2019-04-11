package com.ayush.videoplayer;

public class Video {
    int mImgId;
    String mVideoName;
    public Video(int imgId,String videoname)
    {
        mImgId=imgId;
        mVideoName=videoname;
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
}
