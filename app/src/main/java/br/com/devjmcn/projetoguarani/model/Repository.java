package br.com.devjmcn.projetoguarani.model;

import java.util.List;

import br.com.devjmcn.projetoguarani.model.models.Client;
import br.com.devjmcn.projetoguarani.model.models.Product;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;


public interface Repository {

    Observable<List<String>> getProductStatus();

    Observable<List<Product>> getProductsByName(String selected, String name);

    Observable<List<Client>> searchClient(String searchName, String typeSearch);

    Observable<Boolean> deleteClient(Client client);
}
