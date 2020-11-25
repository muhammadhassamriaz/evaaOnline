package com.ciesto.evaafashion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailsModel {

    public class AttributeDatum {

        @SerializedName("Attribute_Id")
        @Expose
        private String attributeId;
        @SerializedName("Attribute_Name")
        @Expose
        private String attributeName;
        @SerializedName("Units")
        @Expose
        private List<Unit> units = null;

        public String getAttributeId() {
            return attributeId;
        }

        public void setAttributeId(String attributeId) {
            this.attributeId = attributeId;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

        public List<Unit> getUnits() {
            return units;
        }

        public void setUnits(List<Unit> units) {
            this.units = units;
        }

    }

    @SerializedName("Products")
    @Expose
    private Products products;
    @SerializedName("Similar_Produts")
    @Expose
    private List<SimilarProdut> similarProduts = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private String status;

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public List<SimilarProdut> getSimilarProduts() {
        return similarProduts;
    }

    public void setSimilarProduts(List<SimilarProdut> similarProduts) {
        this.similarProduts = similarProduts;
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


    public class GalleryImage {

        @SerializedName("Image_Id")
        @Expose
        private String imageId;
        @SerializedName("Gallery_Image")
        @Expose
        private String galleryImage;

        public String getImageId() {
            return imageId;
        }

        public void setImageId(String imageId) {
            this.imageId = imageId;
        }

        public String getGalleryImage() {
            return galleryImage;
        }

        public void setGalleryImage(String galleryImage) {
            this.galleryImage = galleryImage;
        }

    }


    public class PriceDatum {

        @SerializedName("M_Price_Id")
        @Expose
        private String mPriceId;
        @SerializedName("MRP")
        @Expose
        private String mRP;
        @SerializedName("Sell_Price")
        @Expose
        private String sellPrice;
        @SerializedName("Attributes")
        @Expose
        private String attributes;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("Stock")
        @Expose
        private String stock;
        @SerializedName("Price_Id")
        @Expose
        private String priceId;
        @SerializedName("Availability")
        @Expose
        private String availability;
        @SerializedName("In_Stock")
        @Expose
        private String inStock;
        @SerializedName("SKU")
        @Expose
        private String sKU;
        @SerializedName("Discount_Percent")
        @Expose
        private String discountPercent;
        @SerializedName("Discounted_Price")
        @Expose
        private String discountedPrice;
        @SerializedName("In_Cart")
        @Expose
        private String inCart;

        public String getMPriceId() {
            return mPriceId;
        }

        public void setMPriceId(String mPriceId) {
            this.mPriceId = mPriceId;
        }

        public String getMRP() {
            return mRP;
        }

        public void setMRP(String mRP) {
            this.mRP = mRP;
        }

        public String getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(String sellPrice) {
            this.sellPrice = sellPrice;
        }

        public String getAttributes() {
            return attributes;
        }

        public void setAttributes(String attributes) {
            this.attributes = attributes;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getPriceId() {
            return priceId;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public String getInStock() {
            return inStock;
        }

        public void setInStock(String inStock) {
            this.inStock = inStock;
        }

        public String getSKU() {
            return sKU;
        }

        public void setSKU(String sKU) {
            this.sKU = sKU;
        }

        public String getDiscountPercent() {
            return discountPercent;
        }

        public void setDiscountPercent(String discountPercent) {
            this.discountPercent = discountPercent;
        }

        public String getDiscountedPrice() {
            return discountedPrice;
        }

        public void setDiscountedPrice(String discountedPrice) {
            this.discountedPrice = discountedPrice;
        }

        public String getInCart() {
            return inCart;
        }

        public void setInCart(String inCart) {
            this.inCart = inCart;
        }

    }


    public class PriceDatum_ {

        @SerializedName("Price_Id")
        @Expose
        private String priceId;
        @SerializedName("MRP")
        @Expose
        private String mRP;
        @SerializedName("Sell_Price")
        @Expose
        private String sellPrice;
        @SerializedName("In_Stock")
        @Expose
        private String inStock;
        @SerializedName("Discount_Percent")
        @Expose
        private String discountPercent;
        @SerializedName("Discounted_Price")
        @Expose
        private String discountedPrice;

        public String getPriceId() {
            return priceId;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }

        public String getMRP() {
            return mRP;
        }

        public void setMRP(String mRP) {
            this.mRP = mRP;
        }

        public String getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(String sellPrice) {
            this.sellPrice = sellPrice;
        }

        public String getInStock() {
            return inStock;
        }

        public void setInStock(String inStock) {
            this.inStock = inStock;
        }

        public String getDiscountPercent() {
            return discountPercent;
        }

        public void setDiscountPercent(String discountPercent) {
            this.discountPercent = discountPercent;
        }

        public String getDiscountedPrice() {
            return discountedPrice;
        }

        public void setDiscountedPrice(String discountedPrice) {
            this.discountedPrice = discountedPrice;
        }

    }

    public class Products {

        @SerializedName("Product_Id")
        @Expose
        private String productId;
        @SerializedName("Product_Name")
        @Expose
        private String productName;
        @SerializedName("Product_Image")
        @Expose
        private String productImage;
        @SerializedName("About_Product")
        @Expose
        private String aboutProduct;
        @SerializedName("Short_Description")
        @Expose
        private String shortDescription;
        @SerializedName("Wishlist")
        @Expose
        private String wishlist;
        @SerializedName("Cart")
        @Expose
        private String cart;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("Category_Id")
        @Expose
        private String categoryId;
        @SerializedName("Sub_Category")
        @Expose
        private String subCategory;
        @SerializedName("Sub_Category_Id")
        @Expose
        private String subCategoryId;
        @SerializedName("Cart_Price_Id")
        @Expose
        private String cartPriceId;
        @SerializedName("Shopping_List")
        @Expose
        private String shoppingList;
        @SerializedName("Product_Status")
        @Expose
        private String productStatus;
        @SerializedName("Product_Type")
        @Expose
        private String Product_Type;
        @SerializedName("Rating")
        @Expose
        private String rating;
        @SerializedName("Review_Count")
        @Expose
        private String reviewCount;
        @SerializedName("Reviews")
        @Expose
        private List<Review> reviews = null;
        @SerializedName("Price_Data")
        @Expose
        private List<PriceDatum> priceData = null;
        @SerializedName("Gallery_Image")
        @Expose
        private List<GalleryImage> galleryImage = null;
        @SerializedName("Attribute_Data")
        @Expose
        private List<AttributeDatum> attributeData = null;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getAboutProduct() {
            return aboutProduct;
        }

        public void setAboutProduct(String aboutProduct) {
            this.aboutProduct = aboutProduct;
        }

        public String getWishlist() {
            return wishlist;
        }

        public void setWishlist(String wishlist) {
            this.wishlist = wishlist;
        }

        public String getCart() {
            return cart;
        }

        public void setCart(String cart) {
            this.cart = cart;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getCartPriceId() {
            return cartPriceId;
        }

        public void setCartPriceId(String cartPriceId) {
            this.cartPriceId = cartPriceId;
        }

        public String getShoppingList() {
            return shoppingList;
        }

        public void setShoppingList(String shoppingList) {
            this.shoppingList = shoppingList;
        }

        public String getProductStatus() {
            return productStatus;
        }

        public void setProductStatus(String productStatus) {
            this.productStatus = productStatus;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getReviewCount() {
            return reviewCount;
        }

        public void setReviewCount(String reviewCount) {
            this.reviewCount = reviewCount;
        }

        public List<Review> getReviews() {
            return reviews;
        }

        public void setReviews(List<Review> reviews) {
            this.reviews = reviews;
        }

        public List<PriceDatum> getPriceData() {
            return priceData;
        }

        public void setPriceData(List<PriceDatum> priceData) {
            this.priceData = priceData;
        }

        public List<GalleryImage> getGalleryImage() {
            return galleryImage;
        }

        public void setGalleryImage(List<GalleryImage> galleryImage) {
            this.galleryImage = galleryImage;
        }

        public List<AttributeDatum> getAttributeData() {
            return attributeData;
        }

        public void setAttributeData(List<AttributeDatum> attributeData) {
            this.attributeData = attributeData;
        }

        public String getProduct_Type() {
            return Product_Type;
        }

        public void setProduct_Type(String product_Type) {
            Product_Type = product_Type;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }
    }


    public class Review {

        @SerializedName("Review_Id")
        @Expose
        private String reviewId;
        @SerializedName("Rating")
        @Expose
        private String rating;
        @SerializedName("Review")
        @Expose
        private String review;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("Created")
        @Expose
        private String created;

        public String getReviewId() {
            return reviewId;
        }

        public void setReviewId(String reviewId) {
            this.reviewId = reviewId;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

    }


    public class SimilarProdut {

        @SerializedName("Product_Id")
        @Expose
        private String productId;

        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;

        @SerializedName("Product_Name")
        @Expose
        private String productName;

        @SerializedName("Product_Image")
        @Expose
        private String productImage;

        @SerializedName("About_Product")
        @Expose
        private String aboutProduct;

        @SerializedName("Short_Description")
        @Expose
        private String shortDescription;

        @SerializedName("Category")
        @Expose
        private String category;

        @SerializedName("Category_Id")
        @Expose
        private String categoryId;
        @SerializedName("Price_Data")
        @Expose
        private List<PriceDatum_> priceData = null;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getAboutProduct() {
            return aboutProduct;
        }

        public void setAboutProduct(String aboutProduct) {
            this.aboutProduct = aboutProduct;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public List<PriceDatum_> getPriceData() {
            return priceData;
        }

        public void setPriceData(List<PriceDatum_> priceData) {
            this.priceData = priceData;
        }



    }


    public class Unit {

        @SerializedName("Unit_Id")
        @Expose
        private String unitId;
        @SerializedName("Unit_Name")
        @Expose
        private String unitName;
        @SerializedName("Color_Code")
        @Expose
        private String colorCode;

        @SerializedName("IsSelected")
        @Expose
        private boolean IsSelected;


        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getColorCode() {
            return colorCode;
        }

        public void setColorCode(String colorCode) {
            this.colorCode = colorCode;
        }

        public boolean isSelected() {
            return IsSelected;
        }

        public void setSelected(boolean selected) {
            IsSelected = selected;
        }
    }

}
