package com.amazonaws.crcinema.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mpicado (TOME!) on 8/27/13.
 */
public class CRCinemaUtil {

    public static void getDrawableFromUrl(ImageView imageView)
    {
        new DownloadImageTask().execute(imageView);
    }

    public static class DownloadImageTask extends AsyncTask<ImageView, Void, Bitmap> {

        ImageView imageView = null;

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            return download_Image((String)imageView.getTag());
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }


        private Bitmap download_Image(String url) {

            Bitmap bmp =null;

            try
            {
                URL ulrn = new URL(url);
                HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
                InputStream is = con.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
                if (null != bmp)
                    return bmp;
            }catch (Exception e) {
                System.out.println("Exception while getting the image from the url: " + url + e );
                return null;
            }

            return bmp;
        }
    }

}
