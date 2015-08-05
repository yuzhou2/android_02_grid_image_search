package com.yuzhou.viewer.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.loopj.android.http.RequestParams;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yuzhou on 2015/08/02.
 */
@EqualsAndHashCode
@ToString
public class GoogleImageSearchTaskParams implements TaskParams, Parcelable
{

    public static final Parcelable.Creator CREATOR = new Creator()
    {
        public GoogleImageSearchTaskParams createFromParcel(Parcel in)
        {
            return new GoogleImageSearchTaskParams(in);
        }

        @Override
        public Object[] newArray(int size)
        {
            return new GoogleImageSearchTaskParams[size];
        }
    };

    @Getter private final String url = "https://ajax.googleapis.com/ajax/services/search/images";
    @Getter private final String version = "1.0";

    @Getter @Setter private int quantity = 8;
    @Getter @Setter private String query;
    @Getter @Setter private String imgSize;
    @Getter @Setter private String imgColor;
    @Getter @Setter private String imgType;
    @Getter @Setter private String siteFilter;

    public GoogleImageSearchTaskParams()
    {
    }

    private GoogleImageSearchTaskParams(Parcel in)
    {
        quantity = in.readInt();
        query = in.readString();
        imgSize = in.readString();
        imgColor = in.readString();
        imgType = in.readString();
        siteFilter = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(quantity);
        dest.writeString(query);
        dest.writeString(imgSize);
        dest.writeString(imgColor);
        dest.writeString(imgType);
        dest.writeString(siteFilter);
    }

    @Override
    public RequestParams getParams()
    {
        RequestParams result = new RequestParams();
        result.put("v", version);
        result.put("rsz", quantity);
        result.put("q", query);
        result.put("imgsz", revampImgSize(imgSize));
        result.put("imgcolor", imgColor);
        result.put("imgtype", revampImgType(imgType));
        result.put("as_sitesearch", siteFilter);
        return result;
    }

    public void clear()
    {
        query = null;
        imgSize = null;
        imgColor = null;
        imgType = null;
        siteFilter = null;
    }

    private String revampImgSize(String imgSize)
    {
        if (imgSize == null) {
            return null;
        }
        switch (imgSize) {
        case "small":
        case "medium":
        case "large":
            return imgSize;
        case "extra-large":
            return "xlarge";
        default:
            return "";
        }
    }

    private String revampImgType(String imgType)
    {
        if (imgType == null) {
            return null;
        }
        switch (imgType) {
        case "face":
        case "photo":
            return imgType;
        case "clip art":
            return "clipart";
        case "line art":
            return "lineart";
        default:
            return "";
        }
    }

}