
package com.roix.testtaskgooglebooks.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Epub {

    @SerializedName("isAvailable")
    @Expose
    private Boolean isAvailable;

    /**
     * 
     * @return
     *     The isAvailable
     */
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * 
     * @param isAvailable
     *     The isAvailable
     */
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
