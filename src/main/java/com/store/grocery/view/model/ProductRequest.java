package com.store.grocery.view.model;

public class ProductRequest {
    
        private String name;
    
        private Integer quantity;
    
        private Double value;
    
        private String obs;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getValue() {
            return value;
        }
        public void setValue(Double value) {
            this.value = value;
        }

        public String getObs() {
            return obs;
        }
        public void setObs(String obs) {
            this.obs = obs;
        } 
}
