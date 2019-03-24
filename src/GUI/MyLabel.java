package GUI;

import Products.Product;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;

public class MyLabel extends Label implements Serializable {
    private Product myProduct;

    public MyLabel(Product myProduct) {
        this.myProduct = myProduct;
    }

    public MyLabel(String text, Product myProduct) {
        super(text);
        this.myProduct = myProduct;
    }

    public MyLabel(String text, Node graphic, Product myProduct) {
        super(text, graphic);
        this.myProduct = myProduct;
    }

    public Product getMyProduct() {
        return myProduct;
    }

    public void setMyProduct(Product myProduct) {
        this.myProduct = myProduct;
    }
}
