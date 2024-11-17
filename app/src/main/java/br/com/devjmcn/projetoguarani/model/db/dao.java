package br.com.devjmcn.projetoguarani.model.db;

import static br.com.devjmcn.projetoguarani.util.Util.numberFormat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.devjmcn.projetoguarani.model.Repository;
import br.com.devjmcn.projetoguarani.model.models.Client;
import br.com.devjmcn.projetoguarani.model.models.Product;
import io.reactivex.rxjava3.core.Observable;

public class dao implements Repository {

    private final DataBaseHelper dataBaseHelper;

    @Inject
    public dao(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    public Observable<List<String>> getProductStatus() {
        return Observable.create(emitter -> {
            List<String> prodStatusList = new ArrayList<>();

            String query = "SELECT DISTINCT PRO_STATUS FROM GUA_PRODUTOS";

            try {
                SQLiteDatabase db = dataBaseHelper.getDatabase();
                Cursor cursor = db.rawQuery(query, null);

                if (cursor != null && cursor.getCount() > 0) {
                    int columnStatus = cursor.getColumnIndexOrThrow("PRO_STATUS");
                    while (cursor.moveToNext()) {
                        String prodStatus = cursor.getString(columnStatus);
                        prodStatusList.add(prodStatus);
                    }
                }
                cursor.close();

                emitter.onNext(prodStatusList);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Observable<List<Product>> getProductsByName(String selected, String name) {
        return Observable.create(emitter -> {
            List<Product> productList = new ArrayList<>();

            String query = "SELECT A.PRO_CODIGO, A.PRO_DESCRICAO, B.ESE_ESTOQUE FROM GUA_PRODUTOS as A " +
                    "INNER JOIN GUA_ESTOQUEEMPRESA as B ON A.PRO_CODIGO = B.ESE_CODIGO " +
                    "WHERE A.PRO_STATUS = ? AND A.PRO_DESCRICAO LIKE ?";

            try {
                SQLiteDatabase db = dataBaseHelper.getDatabase();
                Cursor cursor = db.rawQuery(query, new String[]{selected, "%" + name + "%"});

                if (cursor != null && cursor.getCount() > 0) {
                    int columnCod = cursor.getColumnIndex("PRO_CODIGO");
                    int columnDesc = cursor.getColumnIndex("PRO_DESCRICAO");
                    int columnStock = cursor.getColumnIndex("ESE_ESTOQUE");

                    while (cursor.moveToNext()){
                        String cod = cursor.getString(columnCod);
                        String desc = cursor.getString(columnDesc);
                        Double stock = cursor.getDouble(columnStock);

                        Product product = new Product(cod, desc, stock, Collections.emptyList());

                        productList.add(product);
                    }
                }
                cursor.close();

                List<Product> productsWithPrice = getPrices(productList);

                emitter.onNext(productsWithPrice);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    private List<Product> getPrices(List<Product> productList) {
        List<Product> productWithPrices = new ArrayList<>();

        String query = "SELECT PRP_PRECOS FROM GUA_PRECOS WHERE PRP_CODIGO = ? ORDER BY PRP_PRECOS ASC";

        try{
            SQLiteDatabase db = dataBaseHelper.getDatabase();

            for (Product product: productList) {
                List<String> listPrice = new ArrayList<>();
                Cursor cursor = db.rawQuery(query, new String[]{product.getCod()});
                int columnPrice = cursor.getColumnIndexOrThrow("PRP_PRECOS");

                if (cursor.getCount() > 0){
                    while (cursor.moveToNext()){
                        String price = cursor.getString(columnPrice);
                        price = numberFormat(price);
                        listPrice.add(price);
                    }
                }
                Product productWithPrice = product.copy(product, listPrice);
                productWithPrices.add(productWithPrice);
                cursor.close();
            }
            return productWithPrices;

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Observable<List<Client>> getAllClients(String status) {
        return Observable.create(emitter -> {
            List<Client> clientList = new ArrayList<>();

            String query = "SELECT * FROM GUA_CLIENTES WHERE CLI_RAZAOSOCIAL LIKE ?";


            try {
                SQLiteDatabase db = dataBaseHelper.getDatabase();
                Cursor cursor = db.rawQuery(query, new String[]{"%" + status + "%"});

                if (cursor != null && cursor.getCount() > 0) {
                    int columnCod = cursor.getColumnIndexOrThrow("CLI_CODIGOCLIENTE");
                    int columnRazaoSocial = cursor.getColumnIndexOrThrow("CLI_RAZAOSOCIAL");
                    int columnNomeFantasia = cursor.getColumnIndexOrThrow("CLI_NOMEFANTASIA");
                    int columnCpf = cursor.getColumnIndexOrThrow("CLI_CGCCPF");
                    int columnEndereco = cursor.getColumnIndexOrThrow("CLI_ENDERECO");
                    int columnNumero = cursor.getColumnIndexOrThrow("CLI_NUMERO");
                    int columnComplemento = cursor.getColumnIndexOrThrow("CLI_COMPLEMENTO");
                    int columnBairro = cursor.getColumnIndexOrThrow("CLI_BAIRRO");
                    int columnCep = cursor.getColumnIndexOrThrow("CLI_CEP");
                    int columnCodMunicipio = cursor.getColumnIndexOrThrow("CLI_CODIGOMUNICIPIO");
                    int columnEmail = cursor.getColumnIndexOrThrow("CLI_EMAIL");
                    int columnTel = cursor.getColumnIndexOrThrow("CLI_TELEFONE");
                    int columnDtCadastro = cursor.getColumnIndexOrThrow("CLI_DATACADASTRO");

                    while (cursor.moveToNext()) {
                        String id = cursor.getString(columnCod);
                        String razaoSocial = cursor.getString(columnRazaoSocial);
                        String nomeFantasia = cursor.getString(columnNomeFantasia);
                        String cnpjCpf = cursor.getString(columnCpf);
                        String endereco = cursor.getString(columnEndereco);
                        String numero = cursor.getString(columnNumero);
                        String complemento = cursor.getString(columnComplemento);
                        String bairro = cursor.getString(columnBairro);
                        String cep = cursor.getString(columnCep);
                        String codMunicipio = cursor.getString(columnCodMunicipio);
                        String email = cursor.getString(columnEmail);
                        String tel = cursor.getString(columnTel);
                        String dtCadastro = cursor.getString(columnDtCadastro);

                        Client client = new Client(id, razaoSocial, nomeFantasia, cnpjCpf, endereco,
                                numero, complemento, bairro, cep, codMunicipio, email, tel, dtCadastro);

                        clientList.add(client);
                    }

                }
                cursor.close();

                emitter.onNext(clientList);
                emitter.onComplete();

            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
