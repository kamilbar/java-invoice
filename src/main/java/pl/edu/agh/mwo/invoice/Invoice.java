package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new LinkedHashMap<>();
 
    public void addProduct(Product product) {
        products.put(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
    	if(quantity <= 0) {
    		throw new IllegalArgumentException();
    	}
    	products.put(product, quantity);
    }

    public BigDecimal getSubtotal() {
    	BigDecimal sum = new BigDecimal("0");
    	Set<Product> keys = products.keySet();
    	for(Product product : keys) {
    		Integer quantity = this.products.get(product);
    		sum = sum.add(product.getPrice().multiply(new BigDecimal(quantity)));
    	}
        return sum;
    }

    public BigDecimal getTax() {
    	BigDecimal sum = new BigDecimal("0");
    	Set<Product> keys = products.keySet();
    	for(Product product : keys) {
    		Integer quantity = this.products.get(product);
    		sum = sum.add(product.getPriceWithTax().subtract(product.getPrice()).multiply(new BigDecimal(quantity)));
    	}
        return sum;
    }

    public BigDecimal getTotal() {
    	BigDecimal sum = new BigDecimal("0");
    	Set<Product> keys = products.keySet();
    	for(Product product : keys) {
    		Integer quantity = this.products.get(product);
    		sum = sum.add(product.getPriceWithTax().multiply(new BigDecimal(quantity)));
    	}
        return sum;
    }
}
