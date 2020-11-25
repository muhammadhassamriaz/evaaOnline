package com.ciesto.evaafashion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterModel {



        @SerializedName("Filters")
        @Expose
        private List<Filter> filters = null;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("Status")
        @Expose
        private String status;

        public List<Filter> getFilters() {
            return filters;
        }

        public void setFilters(List<Filter> filters) {
            this.filters = filters;
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




    public class Datum {

        @SerializedName("Id")
        @Expose
        private String id;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Type")
        @Expose
        private String type;
        @SerializedName("IsSelected")
        @Expose
        private boolean IsSelected;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
        public boolean isSelected() {
            return IsSelected;
        }

        public void setSelected(boolean selected) {
            IsSelected = selected;
        }

    }


    public class Filter {

        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Data")
        @Expose
        private List<Datum> data = null;
        @SerializedName("Type")
        @Expose
        private String type;
        @SerializedName("IsSelected")
        @Expose
        private boolean IsSelected;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
        public boolean isSelected() {
            return IsSelected;
        }

        public void setSelected(boolean selected) {
            IsSelected = selected;
        }

    }







}
