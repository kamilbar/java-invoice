package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private final int number = Math.abs(new Random().nextInt());
	private Map<Product, Integer> products = new HashMap<Product, Integer>();
	
    public Map<Product, Integer> getProducts() {
		return products;
	}

	public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer addedQuantity) {
        if (product == null || addedQuantity <= 0) {
            throw new IllegalArgumentException();
        }
        Product productAlreadyOnTheInvoice = findProductByNameFromInvoice(product.getName());
        if (productAlreadyOnTheInvoice != null) {
        	products.replace(productAlreadyOnTheInvoice, this.getProducts().get(productAlreadyOnTheInvoice) + addedQuantity);
        } else {
        	products.put(product, addedQuantity);
        }
    }
	    private Product findProductByNameFromInvoice(String productName) {
	    	if (this.getProducts().size() == 0) {
	    		return null;
	    	}
	    	for (Map.Entry<Product, Integer> set : products.entrySet()) {
	    		if (set.getKey().getName().equals(productName)){
	    			return set.getKey();
	    		}
	    	}
	    	return null;
	    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

	public int getNumber() {
		return number;
	}

	public void printInvoice() {
		System.out.println("Faktura nr: " + this.getNumber());
		this.getProducts().forEach((product, qty) -> {
			System.out.println(product.getName() + " qty: " + qty + " price: " + product.getPrice());
		});
		System.out.println();
		System.out.println("Liczba pozycji: " + this.getProducts().size());
	}

}
