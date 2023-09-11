package com.trendyol.shipment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {

    private List<Product> products;

    public ShipmentSize getShipmentSize() {
        if (products.isEmpty()){
            return null;
        }
        Map<ShipmentSize, Integer> sizes = sizeMapper(this.products);
        ShipmentSize ifThree = ifThree(sizes);
        if(ifThree != null){
            return getNextSize(ifThree);
        }
        return largest(products);
    }

    private Map<ShipmentSize, Integer> sizeMapper(List<Product> productList){
        Map<ShipmentSize, Integer> result = new HashMap<>();
        for(Product product : productList){
            result.put(product.getSize(), result.getOrDefault(product.getSize(),0)+1);
        }
        return result;
    }

    private ShipmentSize ifThree(Map<ShipmentSize, Integer> sizeMap){
        for (ShipmentSize size : sizeMap.keySet()){
            if (sizeMap.get(size)>=3){
                return size;
            }
        }
        return null;
    }

    private ShipmentSize getNextSize(ShipmentSize size){
        return switch (size) {
            case SMALL -> ShipmentSize.MEDIUM;
            case MEDIUM -> ShipmentSize.LARGE;
            default -> ShipmentSize.X_LARGE;
        };
    }

    private ShipmentSize largest(List<Product> productList){
        ShipmentSize size = ShipmentSize.SMALL;
        for (Product product: productList){
            if(sizeToInt(product.getSize()) > sizeToInt(size)){
                size = product.getSize();
            }
        }
        return size;
    }

    private int sizeToInt(ShipmentSize size){
        return switch (size) {
            case SMALL -> 1;
            case MEDIUM -> 2;
            case LARGE -> 3;
            default -> 4;
        };
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
