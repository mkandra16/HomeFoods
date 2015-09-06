package com.apps.b3bytes.homefoods.datalayer.common;

import android.net.Uri;

/**
 * Created by Pavan on 9/4/2015.
 */
public interface   FileStor {
    abstract void saveFile(byte[] fileContent, String fileName, DataLayer.SaveCallback cb);
}
