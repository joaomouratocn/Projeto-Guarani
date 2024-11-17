package br.com.devjmcn.projetoguarani.model.db;

import static br.com.devjmcn.projetoguarani.util.Util.getStringBd;
import static br.com.devjmcn.projetoguarani.util.Util.getStringRes;
import static br.com.devjmcn.projetoguarani.util.Util.getTypeSearch;
import static br.com.devjmcn.projetoguarani.util.Util.numberFormat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.devjmcn.projetoguarani.model.Repository;
import br.com.devjmcn.projetoguarani.model.models.Client;
import br.com.devjmcn.projetoguarani.model.models.Product;
import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.core.Observable;

public class dao implements Repository {

    private final DataBaseHelper dataBaseHelper;
    private final Context context;

    @Inject
    public dao(DataBaseHelper dataBaseHelper, @ApplicationContext Context context) {
        this.dataBaseHelper = dataBaseHelper;
        this.context = context;
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
                List<String> result = getStringRes(prodStatusList, context);
                emitter.onNext(result);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Observable<List<Product>> getProductsByName(String selected, String name) {
        return Observable.create(emitter -> {
            String result = getStringBd(selected, context);
            List<Product> productList = new ArrayList<>();

            String query = "SELECT A.PRO_CODIGO, A.PRO_DESCRICAO, B.ESE_ESTOQUE FROM GUA_PRODUTOS as A " +
                    "INNER JOIN GUA_ESTOQUEEMPRESA as B ON A.PRO_CODIGO = B.ESE_CODIGO " +
                    "WHERE A.PRO_STATUS = ? AND A.PRO_DESCRICAO LIKE ?";

            try {
                SQLiteDatabase db = dataBaseHelper.getDatabase();
                Cursor cursor = db.rawQuery(query, new String[]{result, "%" + name + "%"});

                if (cursor != null && cursor.getCount() > 0) {
                    int columnCod = cursor.getColumnIndex("PRO_CODIGO");
                    int columnDesc = cursor.getColumnIndex("PRO_DESCRICAO");
                    int columnStock = cursor.getColumnIndex("ESE_ESTOQUE");

                    while (cursor.moveToNext()) {
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

    @Override
    public Observable<List<Client>> searchClient(String searchName, String typeSearch) {
        return Observable.create(emitter -> {
            String typeSearchConverted = getTypeSearch(typeSearch, context);
            List<Client> clientList = new ArrayList<>();

            String query = "SELECT CLI_CODIGOCLIENTE, CLI_RAZAOSOCIAL, CLI_NOMEFANTASIA, CLI_CGCCPF,\n" +
                    "CLI_ENDERECO, CLI_NUMERO, CLI_COMPLEMENTO, CLI_CEP, CLI_BAIRRO,\n" +
                    "CLI_CODIGOMUNICIPIO, CLI_DATACADASTRO, CLI_TELEFONE, CLI_EMAIL, CLI_EMAILSECUNDARIO \n" +
                    "FROM GUA_CLIENTES WHERE " + typeSearchConverted + " LIKE ?";


            try {
                SQLiteDatabase db = dataBaseHelper.getDatabase();
                Cursor cursor = db.rawQuery(query, new String[]{"%" + searchName + "%"});

                if (cursor != null && cursor.getCount() > 0) {
                    int columnCod = cursor.getColumnIndexOrThrow("CLI_CODIGOCLIENTE");
                    int columnReason = cursor.getColumnIndexOrThrow("CLI_RAZAOSOCIAL");
                    int columnFantasyName = cursor.getColumnIndexOrThrow("CLI_NOMEFANTASIA");
                    int columnCnpjCpf = cursor.getColumnIndexOrThrow("CLI_CGCCPF");
                    int columnAddress = cursor.getColumnIndexOrThrow("CLI_ENDERECO");
                    int columnNumber = cursor.getColumnIndexOrThrow("CLI_NUMERO");
                    int columnComplement = cursor.getColumnIndexOrThrow("CLI_COMPLEMENTO");
                    int columnDistrict = cursor.getColumnIndexOrThrow("CLI_BAIRRO");
                    int columnCep = cursor.getColumnIndexOrThrow("CLI_CEP");
                    int columnCodMunicipality = cursor.getColumnIndexOrThrow("CLI_CODIGOMUNICIPIO");
                    int columnEmail = cursor.getColumnIndexOrThrow("CLI_EMAIL");
                    int columnPhone = cursor.getColumnIndexOrThrow("CLI_TELEFONE");
                    int columnDtRegister = cursor.getColumnIndexOrThrow("CLI_DATACADASTRO");
                    int columnSecEmail = cursor.getColumnIndexOrThrow("CLI_EMAILSECUNDARIO");

                    while (cursor.moveToNext()) {
                        String cod = cursor.getString(columnCod);
                        String reason = cursor.getString(columnReason);
                        String fantasyName = cursor.getString(columnFantasyName);
                        String cnpjCpf = cursor.getString(columnCnpjCpf);
                        String adress = cursor.getString(columnAddress);
                        String number = cursor.getString(columnNumber);
                        String complement = cursor.getString(columnComplement);
                        String district = cursor.getString(columnDistrict);
                        String cep = cursor.getString(columnCep);
                        String codMunicipality = cursor.getString(columnCodMunicipality);
                        String email = cursor.getString(columnEmail);
                        String phone = cursor.getString(columnPhone);
                        String dtRegister = cursor.getString(columnDtRegister);
                        String secEmail = cursor.getString(columnSecEmail);

                        Client client = new Client(cod, reason,cnpjCpf, adress,number, complement,
                                cep, district, codMunicipality, dtRegister, fantasyName, phone, email, secEmail);

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

    @Override
    public Observable<Boolean> deleteClient(Client client) {
        return Observable.create(emitter -> {
            String query = "DELETE FROM GUA_CLIENTES WHERE CLI_CODIGOCLIENTE = ?";
            try {
                SQLiteDatabase db = dataBaseHelper.getDatabase();
                db.execSQL(query, new String[]{client.getCod()});

                emitter.onNext(true);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    private List<Product> getPrices(List<Product> productList) {
        List<Product> productWithPrices = new ArrayList<>();

        String query = "SELECT PRP_PRECOS FROM GUA_PRECOS WHERE PRP_CODIGO = ? ORDER BY PRP_PRECOS ASC";

        try {
            SQLiteDatabase db = dataBaseHelper.getDatabase();

            for (Product product : productList) {
                List<String> listPrice = new ArrayList<>();
                Cursor cursor = db.rawQuery(query, new String[]{product.getCod()});
                int columnPrice = cursor.getColumnIndexOrThrow("PRP_PRECOS");

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
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
}
