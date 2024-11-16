package br.com.devjmcn.projetoguarani.presenter.product;

import static br.com.devjmcn.projetoguarani.util.Util.configureNavigationView;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.devjmcn.projetoguarani.R;
import br.com.devjmcn.projetoguarani.databinding.ProductActivityBinding;

public class ProductActivity extends AppCompatActivity {
    private ProductActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ProductActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initConfig();
    }

    private void initConfig() {
        configureToolbar();
        configureNavigationDrawer();
    }

    private void configureNavigationDrawer() {
        configureNavigationView(this, binding.naviProduct, binding.drawerLayout);
    }

    private void configureToolbar() {
        setSupportActionBar(binding.incCustomToolbar.tlbCustom);
        setTitle(getString(R.string.str_products));
        binding.incCustomToolbar.tlbCustom.setNavigationOnClickListener(view -> {binding.drawerLayout.openDrawer(GravityCompat.START);});
    }
}