package br.com.devjmcn.projetoguarani.presenter.consultClient;

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
import br.com.devjmcn.projetoguarani.databinding.ConsultClientActivityBinding;

public class ConsultClientActivity extends AppCompatActivity {
    private ConsultClientActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ConsultClientActivityBinding.inflate(getLayoutInflater());
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
        configureNavigationView(this, binding.naviConsultClient, binding.drawerLayout);
    }

    private void configureToolbar() {
        setSupportActionBar(binding.incToolbar.tlbCustom);
        setTitle(getString(R.string.str_clients_consult));
        binding.incToolbar.tlbCustom.setNavigationOnClickListener(view -> {binding.drawerLayout.openDrawer(GravityCompat.START);});
    }
}