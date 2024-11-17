package br.com.devjmcn.projetoguarani.presenter.consultClient.view;

import static br.com.devjmcn.projetoguarani.presenter.consultClient.ConsultClientContracts.ConsultClientPresenter;
import static br.com.devjmcn.projetoguarani.presenter.consultClient.ConsultClientContracts.ConsultClientView;
import static br.com.devjmcn.projetoguarani.presenter.consultClient.view.ConsultClientsAdapter.OnCliCkEvent;
import static br.com.devjmcn.projetoguarani.util.Util.configureNavigationView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import javax.inject.Inject;

import br.com.devjmcn.projetoguarani.R;
import br.com.devjmcn.projetoguarani.databinding.ConsultClientActivityBinding;
import br.com.devjmcn.projetoguarani.model.models.Client;
import br.com.devjmcn.projetoguarani.presenter.registerClient.RegisterClientActivity;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ConsultClientActivity extends AppCompatActivity implements ConsultClientView {
    private ConsultClientActivityBinding binding;
    private ConsultClientsAdapter consultClientsAdapter;

    @Inject
    ConsultClientPresenter consultClientPresenter;

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
        consultClientPresenter.attachView(this);

        configureToolbar();

        configureNavigationDrawer();

        configureSpinner();

        consultClientsAdapter = new ConsultClientsAdapter(new OnCliCkEvent() {
            @Override
            public void onClickEdit(Client client) {
                goToRegisterActivity(client);
            }

            @Override
            public void onClickDelete(Client client) {
                consultClientPresenter.deleteClient(client);
                execSearch();
            }
        });

        binding.rcvClients.setAdapter(consultClientsAdapter);

        binding.incToolbar.btnSearch.setOnClickListener(view -> {
            if (binding.incToolbar.edtSearch.getText().toString().isEmpty()) {
                showToast(getString(R.string.str_invalid_text_field));
                return;
            }
            execSearch();

            hideKeyBoard(view);
        });
    }

    private void goToRegisterActivity(Client client) {
        Intent intent = new Intent(this, RegisterClientActivity.class);
        intent.putExtra("CLIENT", client);
        startActivity(intent);
    }

    private void execSearch() {
        String search = binding.incToolbar.edtSearch.getText().toString();
        String spinnerSelected = getItemSelectedSpinner();
        consultClientPresenter.searchClient(search, spinnerSelected);
    }

    private void configureSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.client_type_search));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.incToolbar.spnSelectTypeSearch.setAdapter(adapter);

        binding.incToolbar.spnSelectTypeSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hideKeyBoard(view);
                String selected = adapterView.getItemAtPosition(i).toString();
                consultClientPresenter.searchClient(binding.incToolbar.edtSearch.getText().toString(), selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void configureNavigationDrawer() {
        configureNavigationView(this, binding.naviConsultClient, binding.drawerLayout);
    }

    private void configureToolbar() {
        setSupportActionBar(binding.incToolbar.tlbCustom);
        setTitle(getString(R.string.str_clients_consult));
        binding.incToolbar.tlbCustom.setNavigationOnClickListener(view -> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });
    }

    public String getItemSelectedSpinner() {
        return (String) binding.incToolbar.spnSelectTypeSearch.getSelectedItem();
    }

    private void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        consultClientPresenter.detachView();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad(boolean isShow) {
        if (isShow) {
            binding.incLoad.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.incLoad.getRoot().setVisibility(View.GONE);
        }
    }

    @Override
    public void setClientsAdapter(List<Client> clients) {
        consultClientsAdapter.submitList(clients);
    }

    @Override
    public void deleteSuccess() {
        showToast(getString(R.string.str_delete_success));
    }
}