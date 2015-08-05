package com.yuzhou.viewer.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yuzhou on 2015/08/02.
 */
@EqualsAndHashCode
@ToString
public class GoogleImage implements Parcelable
{

    public static final Parcelable.Creator CREATOR = new Creator()
    {
        public GoogleImage createFromParcel(Parcel in)
        {
            return new GoogleImage(in);
        }

        @Override
        public Object[] newArray(int size)
        {
            return new GoogleImage[size];
        }
    };

    @Getter @Setter private String titleNoFormatting;
    @Getter @Setter private String tbUrl;
    @Getter @Setter private int tbWidth;
    @Getter @Setter private int tbHeight;
    @Getter @Setter private String unescapedUrl;
    @Getter @Setter private int width;
    @Getter @Setter private int height;

    public GoogleImage()
    {
    }

    private GoogleImage(Parcel in)
    {
        titleNoFormatting = in.readString();
        tbUrl = in.readString();
        tbWidth = in.readInt();
        tbHeight = in.readInt();
        unescapedUrl = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(titleNoFormatting);
        dest.writeString(tbUrl);
        dest.writeInt(tbWidth);
        dest.writeInt(tbHeight);
        dest.writeString(unescapedUrl);
        dest.writeInt(width);
        dest.writeInt(height);
    }

}