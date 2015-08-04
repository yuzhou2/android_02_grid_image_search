package com.yuzhou.viewer.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yuzhou on 2015/08/02.
 */
@EqualsAndHashCode
@ToString
public class GoogleImage
{
    @Getter @Setter private int tbWidth;
    @Getter @Setter private int tbHeight;
    @Getter @Setter private String tbUrl;
    @Getter @Setter private String titleNoFormatting;

}