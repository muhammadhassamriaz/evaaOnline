package com.ciesto.evaafashion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WardrobModel {



        @SerializedName("Merchant")
        @Expose
        private List<Merchant> merchant = null;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("Status")
        @Expose
        private String status;

        public List<Merchant> getMerchant() {
            return merchant;
        }

        public void setMerchant(List<Merchant> merchant) {
            this.merchant = merchant;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }




    public class Merchant {

        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;
        @SerializedName("Merchant_Name")
        @Expose
        private String merchantName;
        @SerializedName("Profile_Image")
        @Expose
        private String profileImage;
        @SerializedName("Cover_Image")
        @Expose
        private String coverImage;
        @SerializedName("Bio")
        @Expose
        private String bio;

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

    }
}
