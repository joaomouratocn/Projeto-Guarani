package br.com.devjmcn.projetoguarani.presenter.registerClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayoutMediator;

import br.com.devjmcn.projetoguarani.R;
import br.com.devjmcn.projetoguarani.databinding.RegisterClientActivityBinding;
import br.com.devjmcn.projetoguarani.presenter.consult.ConsultClientActivity;
import br.com.devjmcn.projetoguarani.presenter.product.ProductActivity;

public class RegisterClientActivity extends AppCompatActivity {
    RegisterClientActivityBinding binding;

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

        initConfig();
    }

    private void initConfig() {
        configureToolbar();

        configureViewPager();

        configureNavigationView();
    }

    private void configureToolbar() {
        setSupportActionBar(binding.tlbClients);
        binding.tlbClients.setNavigationIcon(R.drawable.menu_line);
        binding.tlbClients.setNavigationOnClickListener(v -> binding.drawerLayout.openDrawer(GravityCompat.START));
    }

    private void configureViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
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
    }

    private void configureNavigationView() {
        binding.naviClientRegister.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.nav_home) {
                Intent intent = new Intent(RegisterClientActivity.this, ProductActivity.class);
                startActivity(intent);
            } else if (menuItem.getItemId() == R.id.nav_clients_consult) {
                Intent intent = new Intent(RegisterClientActivity.this, ConsultClientActivity.class);
                startActivity(intent);
            } else {
                showToast(getString(R.string.str_invalid_request));
            }
            binding.drawerLayout.closeDrawers();
            return true;
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}