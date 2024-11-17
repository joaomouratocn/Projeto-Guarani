package br.com.devjmcn.projetoguarani.presenter.consultClient;

import static br.com.devjmcn.projetoguarani.presenter.consultClient.ConsultClientContracts.ConsultClientPresenter;
import static br.com.devjmcn.projetoguarani.presenter.consultClient.ConsultClientContracts.ConsultClientView;

import android.util.Log;

import javax.inject.Inject;

import br.com.devjmcn.projetoguarani.model.Repository;
import br.com.devjmcn.projetoguarani.model.models.Client;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ConsultPresenter implements ConsultClientPresenter {
    private ConsultClientView consultClientView;
    private Disposable disposable;
    private Repository repository;

    @Inject
    public ConsultPresenter(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void attachView(ConsultClientView consultClientView) {
        this.consultClientView = consultClientView;
    }

    @Override
    public void detachView() {
        consultClientView = null;
        disposable.dispose();
    }

    @Override
    public void searchClient(String searchName, String typeSearch) {
        consultClientView.showLoad(true);
        disposable = repository.searchClient(searchName, typeSearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        clients -> {
                            consultClientView.setClientsAdapter(clients);
                        },
                        throwable -> {
                            consultClientView.showToast(throwable.getMessage());
                        },
                        () -> {
                            consultClientView.showLoad(false);
                        }
                );
    }

    @Override
    public void deleteClient(Client client) {
        consultClientView.showLoad(true);
        disposable = repository.deleteClient(client)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            consultClientView.deleteSuccess();
                        },
                        throwable -> {
                            consultClientView.showToast(throwable.getMessage());
                        },
                        () -> {
                            consultClientView.showLoad(false);
                        }
                );

    }
}
