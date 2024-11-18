package br.com.devjmcn.projetoguarani.di;

import br.com.devjmcn.projetoguarani.presenter.registerClient.RegisterContracts;
import br.com.devjmcn.projetoguarani.presenter.registerClient.view.RegisterPresenter;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RegisterModule {
    @Binds
    public abstract RegisterContracts.RegisterPresenterInterface registerPresenterInterface(RegisterPresenter registerPresenter);
}
