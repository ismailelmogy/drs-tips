package ocs.com.dr_tips.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alamrawy on 3/26/2018.
 */

public class Tip {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("img")
    @Expose
    private String img;

    private Bitmap bitmapImg;


    public String getImg() {

        return img;
    }

    public String getText() {
        return text;
    }
    public Bitmap getBitmapImg(){
        byte[] decodedString = Base64.decode(this.img, Base64.DEFAULT);
        this.bitmapImg = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return bitmapImg;

    }
}
