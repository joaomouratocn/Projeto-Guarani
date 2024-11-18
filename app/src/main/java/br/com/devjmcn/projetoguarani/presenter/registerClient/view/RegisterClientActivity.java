package br.com.devjmcn.projetoguarani.presenter.registerClient.view;

import static br.com.devjmcn.projetoguarani.util.Util.configureNavigationView;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayoutMediator;

import br.com.devjmcn.projetoguarani.R;
import br.com.devjmcn.projetoguarani.databinding.RegisterClientActivityBinding;
import br.com.devjmcn.projetoguarani.model.models.Client;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterClientActivity extends AppCompatActivity {
    RegisterClientActivityBinding binding;
    Client receivedClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = RegisterClientActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        receivedClient = getIntent().getParcelableExtra("CLIENT");

        initConfig();
    }

    private void initConfig() {
        configureToolbar();

        configureViewPager();

        configureNavigationDrawer();
    }

    private void configureToolbar() {
        setSupportActionBar(binding.tlbClients);
        binding.tlbClients.setNavigationOnClickListener(v -> binding.drawerLayout.openDrawer(GravityCompat.START));
    }

    private void configureViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, receivedClient);
        binding.vpClients.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(binding.tbClients, binding.vpClients,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText(getString(R.string.str_cnpj));
                            break;
                        case 1:
                            tab.setText(getString(R.string.str_cpf));
                            break;
                    }
                }
        ).attach();

        if (receivedClient != null && receivedClient.getFantasyName().isEmpty()) {
            binding.tbClients.selectTab(binding.tbClients.getTabAt(1));
        }
    }

    private void configureNavigationDrawer() {
        configureNavigationView(this, binding.naviClientRegister, binding.drawerLayout);
    }
}