package br.com.devjmcn.projetoguarani.util;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import br.com.devjmcn.projetoguarani.R;
import br.com.devjmcn.projetoguarani.presenter.consultClient.ConsultClientActivity;
import br.com.devjmcn.projetoguarani.presenter.product.ProductActivity;
import br.com.devjmcn.projetoguarani.presenter.registerClient.RegisterClientActivity;

public class Util {
    public static void configureNavigationView(Activity activity, NavigationView navigationView, DrawerLayout drawerLayout) {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(activity, menuItem);
            drawerLayout.closeDrawers();
            return true;
        });
    }

    private static void handleNavigationItemSelected(Activity activity, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_home) {
            Intent intent = new Intent(activity, ProductActivity.class);
            activity.startActivity(intent);
        } else if (menuItem.getItemId() == R.id.navi_client_register) {
            Intent intent = new Intent(activity, RegisterClientActivity.class);
            activity.startActivity(intent);
        } else if (menuItem.getItemId() == R.id.nav_consult_clients) {
            Intent intent = new Intent(activity, ConsultClientActivity.class);
            activity.startActivity(intent);
        }
    }
}
