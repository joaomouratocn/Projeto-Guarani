package br.com.devjmcn.projetoguarani.presenter.registerClient;

import static br.com.devjmcn.projetoguarani.presenter.registerClient.RegisterContracts.RegisterViewInterface;

import javax.inject.Inject;

import br.com.devjmcn.projetoguarani.model.Repository;
import br.com.devjmcn.projetoguarani.model.models.Client;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterPresenter implements RegisterContracts.RegisterPresenterInterface {
    private final Repository repository;
    Disposable disposable;
    private RegisterViewInterface registerViewInterface;

    @Inject
    public RegisterPresenter(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void editClient(Client editedClient) {
        if (viewIsNull()){return;}
        disposable = repository.updateClient(editedClient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            registerViewInterface.resultSuccess();
                        },
                        throwable -> {
                            registerViewInterface.showToast(throwable.getMessage());
                        });
    }

    @Override
    public void attachView(RegisterViewInterface registerViewInterface) {
        this.registerViewInterface = registerViewInterface;
    }

    @Override
    public void detachView() {
        registerViewInterface = null;
    }

    @Override
    public void insertClient(Client newClient) {
        if (viewIsNull()){return;}
        disposable = repository.insertClient(newClient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            registerViewInterface.resultSuccess();
                        },
                        throwable -> {
                            registerViewInterface.showToast(throwable.getMessage());
                        });
    }

    private Boolean viewIsNull(){
        return registerViewInterface == null;
    }
}
