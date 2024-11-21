package br.com.devjmcn.projetoguarani.presenter.product;

import static br.com.devjmcn.projetoguarani.presenter.product.ProductContracts.ProductPresenterInterface;
import static br.com.devjmcn.projetoguarani.presenter.product.ProductContracts.ProductViewInterface;

import javax.inject.Inject;

import br.com.devjmcn.projetoguarani.model.Repository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductPresentation implements ProductPresenterInterface {
    private ProductViewInterface productViewInterface;
    private final Repository repository;
    private Disposable disposable;

    @Inject
    public ProductPresentation(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void attachView(ProductViewInterface productViewInterface) {
        this.productViewInterface = productViewInterface;
    }

    @Override
    public void detachView() {
        this.productViewInterface = null;
    }

    @Override
    public void loadProductStatus() {
        if (viewIsNull()){return;}
        productViewInterface.showLoad(true);
        disposable = repository.getProductStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> productViewInterface.showLoad(false))
                .subscribe(
                        prodStatus -> {
                            productViewInterface.loadSpinner(prodStatus);
                        },
                        throwable -> {
                            productViewInterface.showToast(throwable.getMessage());
                        }
                );
    }

    @Override
    public void searchProduct(String search, String selectedStatus) {
        if (viewIsNull()){return;}
        productViewInterface.showLoad(true);
        disposable = repository.getProductsByName(selectedStatus, search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> productViewInterface.showLoad(false))
                .subscribe(
                        products -> {
                            productViewInterface.setProductsAdapter(products);
                        },
                        throwable -> {
                            productViewInterface.showToast(throwable.getMessage());
                        }
                );
    }

    private Boolean viewIsNull() {
        return productViewInterface == null;
    }
}
