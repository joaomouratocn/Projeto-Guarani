package br.com.devjmcn.projetoguarani.model;

import java.util.List;

import br.com.devjmcn.projetoguarani.model.models.Client;
import br.com.devjmcn.projetoguarani.model.models.Product;
import io.reactivex.rxjava3.core.Observable;


public interface Repository {

    Observable<List<String>> getProductStatus();

    Observable<List<Client>> getAllClients(String status);

    Observable<List<Product>> getProductsByName(String selected, String name);
}
