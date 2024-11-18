package br.com.devjmcn.projetoguarani.presenter.registerClient;

import br.com.devjmcn.projetoguarani.model.models.Client;

public interface RegisterContracts {
    interface RegisterViewInterface {
        void resultSuccess();

        void showToast(String message);
    }

    interface RegisterPresenterInterface {
        void editClient(Client editedClient);

        void attachView(RegisterViewInterface registerViewInterface);

        void detachView();

        void insertClient(Client newClient);
    }
}
