package br.com.devjmcn.projetoguarani.di;

import br.com.devjmcn.projetoguarani.presenter.consultClient.ConsultClientContracts;
import br.com.devjmcn.projetoguarani.presenter.consultClient.ConsultPresenter;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class ConsultClientModule {
    @Binds
    public abstract ConsultClientContracts.ConsultClientPresenterInterface ConsultClientPresenter(ConsultPresenter consultPresenter);
}
