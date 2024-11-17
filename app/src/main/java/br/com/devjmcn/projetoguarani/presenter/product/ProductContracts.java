package br.com.devjmcn.projetoguarani.presenter.product;

import java.util.List;

import br.com.devjmcn.projetoguarani.model.models.Product;

public interface ProductContracts {

    interface ProductPresenterInterface {
        void attachView(ProductViewInterface productViewInterface);

        void detachView();

        void loadProductStatus();

        void searchProduct(String search, String selectedStatus);
    }

    interface ProductViewInterface {

        void showToast(String message);

        String getItemSelectedSpinner();

        void setProductsAdapter(List<Product> products);

        void showLoad(boolean isShow);

        void loadSpinner(List<String> prodStatus);
    }
}