package com.apps.b3bytes.homefoods.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.apps.b3bytes.homefoods.R;

public class DishImageHelper {
    public static void setDishImage(ImageView ivPhoto, Context context) {

        ivPhoto.setImageResource(R.drawable.chicken_65_01);

/*        UserProfile profile = UserProfile.getCurrentUser();

        String uriStr = profile.getPhotoUri();
        if (uriStr == null || uriStr.isEmpty()) {
            ivPhoto.setImageResource(R.drawable.default_photo);
        } else {
            try {
                Uri photoUri = Uri.parse(uriStr);
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(
                        context.getContentResolver(), photoUri);
                // Load the selected image into a preview
                ivPhoto.setImageBitmap(selectedImage);
            } catch (Exception e) {
                ivPhoto.setImageResource(R.drawable.default_photo);
            }
        }*/

    }
}