package ocs.com.dr_tips.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alamrawy on 4/4/2018.
 */

public class TipsPackage {
    @SerializedName("description")
    @Expose
    private  String description;


    public String getDescription() {
        return description;
    }
}
