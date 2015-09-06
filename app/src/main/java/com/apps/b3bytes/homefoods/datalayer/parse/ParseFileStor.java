package com.apps.b3bytes.homefoods.datalayer.parse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.datalayer.common.FileStor;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

/**
 * Created by Pavan on 9/4/2015.
 */
public class ParseFileStor implements FileStor {
    @Override
    public void saveFile(byte[] file, String fileName, final DataLayer.SaveCallback cb) {
        final ParseFile imgFile = new ParseFile(fileName, file);
        imgFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    cb.done(imgFile.getUrl(), e);
                } else {
                    cb.done(null, e);
                }
            }
        });
    }
}
