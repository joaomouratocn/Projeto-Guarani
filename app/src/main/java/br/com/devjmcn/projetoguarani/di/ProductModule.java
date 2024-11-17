package br.com.devjmcn.projetoguarani.di;

import static br.com.devjmcn.projetoguarani.presenter.product.ProductContracts.ProductPresenterInterface;

import br.com.devjmcn.projetoguarani.presenter.product.ProductPresentation;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class ProductModule {
    @Binds
    public abstract ProductPresenterInterface productPresenterInterface(ProductPresentation productPresentation);
}
