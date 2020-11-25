package com.ciesto.evaafashion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetails
{


        @SerializedName("Orders")
        @Expose
        private List<Order> orders = null;
        @SerializedName("Customer")
        @Expose
        private Customer customer;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("Status")
        @Expose
        private String status;

        public List<Order> getOrders() {
            return orders;
        }

        public void setOrders(List<Order> orders) {
            this.orders = orders;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
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




    public class Customer {

        @SerializedName("Customer_Id")
        @Expose
        private String customerId;
        @SerializedName("Customer_Name")
        @Expose
        private String customerName;
        @SerializedName("Mobile_No")
        @Expose
        private String mobileNo;
        @SerializedName("Email_Id")
        @Expose
        private String emailId;
        @SerializedName("Customer_Image")
        @Expose
        private String customerImage;


        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getCustomerImage() {
            return customerImage;
        }

        public void setCustomerImage(String customerImage) {
            this.customerImage = customerImage;
        }


    }

    public class Order {

        @SerializedName("Merchant_Name")
        @Expose
        private String merchantName;
        @SerializedName("Mobile_No")
        @Expose
        private String mobileNo;
        @SerializedName("Email_Id")
        @Expose
        private String emailId;
        @SerializedName("Profile_Image")
        @Expose
        private String profileImage;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("Total")
        @Expose
        private String total;
        @SerializedName("Sub_Total")
        @Expose
        private String subTotal;
        @SerializedName("Delivery_Charge")
        @Expose
        private String deliveryCharge;
        @SerializedName("Order_No")
        @Expose
        private String orderNo;
        @SerializedName("Order_Status")
        @Expose
        private String orderStatus;
        @SerializedName("Order_Status_Text")
        @Expose
        private String orderStatusText;
        @SerializedName("Coupon_Amount")
        @Expose
        private String couponAmount;
        @SerializedName("Total_Item")
        @Expose
        private String totalItem;

        @SerializedName("Order_Data")
        @Expose
        private List<OrderDatum> orderData = null;

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
        }

        public String getDeliveryCharge() {
            return deliveryCharge;
        }

        public void setDeliveryCharge(String deliveryCharge) {
            this.deliveryCharge = deliveryCharge;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusText() {
            return orderStatusText;
        }

        public void setOrderStatusText(String orderStatusText) {
            this.orderStatusText = orderStatusText;
        }

        public String getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(String couponAmount) {
            this.couponAmount = couponAmount;
        }

        public List<OrderDatum> getOrderData() {
            return orderData;
        }

        public void setOrderData(List<OrderDatum> orderData) {
            this.orderData = orderData;
        }
        public String getTotalItem() {
            return totalItem;
        }

        public void setTotalItem(String totalItem) {
            this.totalItem = totalItem;
        }
    }


    public class OrderDatum {

        @SerializedName("Order_Id")
        @Expose
        private String orderId;
        @SerializedName("Order_No")
        @Expose
        private String orderNo;
        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;
        @SerializedName("Merchant_Acceptance")
        @Expose
        private String merchantAcceptance;
        @SerializedName("Product_Id")
        @Expose
        private String productId;
        @SerializedName("Stock_Id")
        @Expose
        private String stockId;
        @SerializedName("Price_Id")
        @Expose
        private String priceId;
        @SerializedName("M_Price_Id")
        @Expose
        private String mPriceId;
        @SerializedName("Quantity")
        @Expose
        private String quantity;
        @SerializedName("Product_Price")
        @Expose
        private String productPrice;
        @SerializedName("Total_Amount")
        @Expose
        private String totalAmount;
        @SerializedName("Product_Name")
        @Expose
        private String productName;
        @SerializedName("Product_Image")
        @Expose
        private String productImage;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getMerchantAcceptance() {
            return merchantAcceptance;
        }

        public void setMerchantAcceptance(String merchantAcceptance) {
            this.merchantAcceptance = merchantAcceptance;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getStockId() {
            return stockId;
        }

        public void setStockId(String stockId) {
            this.stockId = stockId;
        }

        public String getPriceId() {
            return priceId;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }

        public String getMPriceId() {
            return mPriceId;
        }

        public void setMPriceId(String mPriceId) {
            this.mPriceId = mPriceId;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
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

    }
}
