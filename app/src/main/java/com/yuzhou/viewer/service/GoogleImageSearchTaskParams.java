package com.yuzhou.viewer.service;

import com.loopj.android.http.RequestParams;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yuzhou on 2015/08/02.
 */
@EqualsAndHashCode
@ToString
public class GoogleImageSearchTaskParams implements TaskParams, Serializable
{
    @Getter private final String url = "https://ajax.googleapis.com/ajax/services/search/images";

    private String version = "1.0";

    @Getter @Setter private int qunatity = 8;
    @Getter @Setter private String query;
    @Getter @Setter private String imgSize;
    @Getter @Setter private String imgColor;
    @Getter @Setter private String imgType;
    @Getter @Setter private String siteFilter;

    @Override
    public RequestParams getParams()
    {
        RequestParams result = new RequestParams();
        result.put("v", version);
        result.put("rsz", qunatity);
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