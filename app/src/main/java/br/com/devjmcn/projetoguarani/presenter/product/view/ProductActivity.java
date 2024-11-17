package br.com.devjmcn.projetoguarani.presenter.product.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static br.com.devjmcn.projetoguarani.presenter.product.ProductContracts.ProductPresenterInterface;
import static br.com.devjmcn.projetoguarani.presenter.product.ProductContracts.ProductViewInterface;
import static br.com.devjmcn.projetoguarani.util.Util.configureNavigationView;

import android.app.Dialog;
import android.content.Context;
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
import br.com.devjmcn.projetoguarani.databinding.DialogPriceBinding;
import br.com.devjmcn.projetoguarani.databinding.ProductActivityBinding;
import br.com.devjmcn.projetoguarani.model.models.Product;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductActivity extends AppCompatActivity implements ProductViewInterface {
    private ProductActivityBinding binding;
    private ProductAdapter productAdapter;

    @Inject
    ProductPresenterInterface productPresenterInterface;

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
        productPresenterInterface.attachView(this);
        configureToolbar();

        configureNavigationDrawer();

        configureSpinner();

        productAdapter = new ProductAdapter(product -> {
            showPriceDialog(product.getPrices());
        });

        binding.rcvProducts.setAdapter(productAdapter);

        binding.incCustomToolbar.btnSearch.setOnClickListener(view -> {
            if (binding.incCustomToolbar.edtSearch.getText().toString().isEmpty()) {
                showToast(getString(R.string.str_invalid_text_field));
                return;
            }
            String search = binding.incCustomToolbar.edtSearch.getText().toString();
            String spinnerSelected = getItemSelectedSpinner();
            productPresenterInterface.searchProduct(search, spinnerSelected);

            hideKeyBoard(view);
        });
    }

    private void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void configureSpinner() {
        productPresenterInterface.loadProductStatus();

        binding.incCustomToolbar.spnSelectTypeSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hideKeyBoard(view);
                String selected = adapterView.getItemAtPosition(i).toString();
                productPresenterInterface.searchProduct(binding.incCustomToolbar.edtSearch.getText().toString(), selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void showPriceDialog(List<String> prices) {
        DialogPriceBinding diaBinding = DialogPriceBinding.inflate(getLayoutInflater());

        Dialog dialog = new Dialog(this);
        dialog.setContentView(diaBinding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                prices
        );

        diaBinding.ltvPrices.setAdapter(adapter);

        diaBinding.btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void configureNavigationDrawer() {
        configureNavigationView(this, binding.naviProduct, binding.drawerLayout);
    }

    private void configureToolbar() {
        setSupportActionBar(binding.incCustomToolbar.tlbCustom);
        setTitle(getString(R.string.str_products));
        binding.incCustomToolbar.tlbCustom.setNavigationOnClickListener(view -> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productPresenterInterface.detachView();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public String getItemSelectedSpinner() {
        return (String) binding.incCustomToolbar.spnSelectTypeSearch.getSelectedItem();
    }

    @Override
    public void setProductsAdapter(List<Product> products) {
        productAdapter.submitList(products);
    }

    @Override
    public void showLoad(boolean isShow) {
        if (isShow) {
            binding.incLoad.getRoot().setVisibility(VISIBLE);
        } else {
            binding.incLoad.getRoot().setVisibility(GONE);
        }
    }

    @Override
    public void loadSpinner(List<String> prodStatus) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                prodStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.incCustomToolbar.spnSelectTypeSearch.setAdapter(adapter);
    }
}