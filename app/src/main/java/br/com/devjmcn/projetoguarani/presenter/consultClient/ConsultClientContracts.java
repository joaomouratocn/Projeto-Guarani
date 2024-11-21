package br.com.devjmcn.projetoguarani.presenter.consultClient;

import java.util.List;

import br.com.devjmcn.projetoguarani.model.models.Client;

public interface ConsultClientContracts {
    interface ConsultClientPresenterInterface {

        void attachView(ConsultClientViewInterface consultClientViewInterface);

        void detachView();

        void searchClient(String searchName, String typeSearch);

        void deleteClient(Client client);
    }

    interface ConsultClientViewInterface {

        void showToast(String message);

        void showLoad(boolean isShow);

        void setClientsAdapter(List<Client> clients);

        void deleteSuccess();
    }
}
