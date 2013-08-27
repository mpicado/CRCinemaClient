package com.amazonaws.crcinema.utils;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by mpicado on 8/27/13.
 */
public class CRCinemaUtil {
    public static Drawable getDrawableFromUrl(String url)
    {
        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            System.out.println("Exception while getting the image from the url: " + e);
            return null;
        }
    }

}
