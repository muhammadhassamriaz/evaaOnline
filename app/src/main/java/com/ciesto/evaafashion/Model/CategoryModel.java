package com.ciesto.evaafashion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryModel
{
    public class Category {

        @SerializedName("Category_Id")
        @Expose
        private String categoryId;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("Sub_Category")
        @Expose
        private List<SubCategory> subCategory = null;
        @SerializedName("Slider")
        @Expose
        private List<Slider> slider = null;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<SubCategory> getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(List<SubCategory> subCategory) {
            this.subCategory = subCategory;
        }

        public List<Slider> getSlider() {
            return slider;
        }

        public void setSlider(List<Slider> slider) {
            this.slider = slider;
        }

    }




        @SerializedName("Category")
        @Expose
        private List<Category> category = null;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("Status")
        @Expose
        private String status;

        public List<Category> getCategory() {
            return category;
        }

        public void setCategory(List<Category> category) {
            this.category = category;
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



    public class Slider {

        @SerializedName("Slider_Id")
        @Expose
        private String sliderId;
        @SerializedName("Slider_Image")
        @Expose
        private String sliderImage;

        public String getSliderId() {
            return sliderId;
        }

        public void setSliderId(String sliderId) {
            this.sliderId = sliderId;
        }

        public String getSliderImage() {
            return sliderImage;
        }

        public void setSliderImage(String sliderImage) {
            this.sliderImage = sliderImage;
        }

    }

    public class SubCategory {

        @SerializedName("Sub_Category_Id")
        @Expose
        private String subCategoryId;
        @SerializedName("Sub_Category")
        @Expose
        private String subCategory;
        @SerializedName("Image")
        @Expose
        private String image;

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }
}
