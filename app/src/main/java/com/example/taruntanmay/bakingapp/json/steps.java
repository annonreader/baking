package com.example.taruntanmay.bakingapp.json;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class steps implements Parcelable {

    private String stepId;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;


    public steps(){}

    public steps(String stepId, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
    public Boolean hasVideo() {
        return !TextUtils.isEmpty(videoURL);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stepId);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    protected steps(Parcel in) {
        this.stepId = in.readString();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<steps> CREATOR = new Parcelable.Creator<steps>() {
        @Override
        public steps createFromParcel(Parcel source) {
            return new steps(source);
        }

        @Override
        public steps[] newArray(int size) {
            return new steps[size];
        }
    };
}
