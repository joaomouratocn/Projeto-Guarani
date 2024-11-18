package br.com.devjmcn.projetoguarani.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.devjmcn.projetoguarani.R;
import br.com.devjmcn.projetoguarani.presenter.consultClient.view.ConsultClientActivity;
import br.com.devjmcn.projetoguarani.presenter.product.view.ProductActivity;
import br.com.devjmcn.projetoguarani.presenter.registerClient.view.RegisterClientActivity;

public class Util {
    public static void configureNavigationView(Activity activity, NavigationView navigationView, DrawerLayout drawerLayout) {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(activity, menuItem);
            drawerLayout.closeDrawers();
            return true;
        });
    }

    private static void handleNavigationItemSelected(Activity activity, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_product) {
            Intent intent = new Intent(activity, ProductActivity.class);
            activity.startActivity(intent);
        } else if (menuItem.getItemId() == R.id.nav_register_client) {
            Intent intent = new Intent(activity, RegisterClientActivity.class);
            activity.startActivity(intent);
        } else if (menuItem.getItemId() == R.id.nav_consult_client) {
            Intent intent = new Intent(activity, ConsultClientActivity.class);
            activity.startActivity(intent);
        }
    }

    public static String numberFormat(String value){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double valor = Double.parseDouble(value);
        return numberFormat.format(valor);
    }

    public static String getDate() {
        LocalDate dateNow = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return dateNow.format(formatter);
    }

    public static String getTypeSearch(String typeSearch, Context context) {
        String[] items = context.getResources().getStringArray(R.array.client_type_search);

        Map<String, String> searchTypeMap = new HashMap<>();
        searchTypeMap.put(items[0], "CLI_CGCCPF");
        searchTypeMap.put(items[1], "CLI_RAZAOSOCIAL");
        searchTypeMap.put(items[2], "CLI_NOMEFANTASIA");

        return searchTypeMap.get(typeSearch);
    }

    public static List<String> getStringRes(List<String> prodStatus,Context context) {
        List<String> listRes = new ArrayList<>();

        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("E", context.getString(R.string.str_stock));
        statusMap.put("L", context.getString(R.string.str_release));
        statusMap.put("N", context.getString(R.string.str_normal));
        statusMap.put("P", context.getString(R.string.str_promotion));

        for (String status : prodStatus) {
            String result = statusMap.get(status);
            listRes.add(result);
        }
        return listRes;
    }

    public static String getStringBd(String prodStatus, Context context) {

        Map<String, String> statusMap = new HashMap<>();
        statusMap.put(context.getString(R.string.str_stock), "E");
        statusMap.put(context.getString(R.string.str_release), "L");
        statusMap.put(context.getString(R.string.str_normal), "N");
        statusMap.put(context.getString(R.string.str_promotion), "P");

        return statusMap.get(prodStatus);
    }
}
