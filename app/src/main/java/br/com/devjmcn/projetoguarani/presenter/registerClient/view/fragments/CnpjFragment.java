package br.com.devjmcn.projetoguarani.presenter.registerClient.view.fragments;

import static br.com.devjmcn.projetoguarani.presenter.registerClient.RegisterContracts.RegisterPresenterInterface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import br.com.devjmcn.projetoguarani.R;
import br.com.devjmcn.projetoguarani.databinding.CnpjFragmentBinding;
import br.com.devjmcn.projetoguarani.model.models.Client;
import br.com.devjmcn.projetoguarani.presenter.registerClient.RegisterContracts;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CnpjFragment extends Fragment implements RegisterContracts.RegisterViewInterface {
    CnpjFragmentBinding binding;
    Client receivedClient;

    @Inject
    RegisterPresenterInterface registerPresenterInterface;

    private static final String KEY = "KEY";

    public static CnpjFragment newInstance(Client client) {
        CnpjFragment fragment = new CnpjFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY, client);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = CnpjFragmentBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            receivedClient = getArguments().getParcelable(KEY);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initConfig();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registerPresenterInterface.detachView();
    }

    private void initConfig() {
        registerPresenterInterface.attachView(this);

        if (receivedClient != null) {
            loadFields();
        }

        binding.btnCancel.setOnClickListener(view -> {
            if (receivedClient != null) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
            clearFields();
        });

        binding.btnSave.setOnClickListener(view -> {
            if (validateFields()) {
                if (receivedClient != null) {
                    Client editedClient = getClientFields(true);
                    registerPresenterInterface.editClient(editedClient);
                } else {
                    Client newClient = getClientFields(false);
                    registerPresenterInterface.insertClient(newClient);
                }
            }
        });
    }

    private void loadFields() {
        binding.edtCnpj.setText(receivedClient.getCnpjCpf());
        binding.edtReason.setText(receivedClient.getReason());
        binding.edtFantasyName.setText(receivedClient.getFantasyName());
        binding.edtBestEmail.setText(receivedClient.getEmail());
        binding.edtSecondaryEmail.setText(receivedClient.getSecEmail());
        binding.edtDistric.setText(receivedClient.getDistrict());
        binding.edtAddress.setText(receivedClient.getAddress());
        binding.edtNumber.setText(receivedClient.getNumber());
        binding.edtCep.setText(receivedClient.getCep());
        binding.edtComplement.setText(receivedClient.getComplement());
    }

    private void clearFields() {
        binding.edtCnpj.setText("");
        binding.edtReason.setText("");
        binding.edtFantasyName.setText("");
        binding.edtBestEmail.setText("");
        binding.edtSecondaryEmail.setText("");
        binding.edtDistric.setText("");
        binding.edtAddress.setText("");
        binding.edtNumber.setText("");
        binding.edtCep.setText("");
        binding.edtComplement.setText("");
    }

    private boolean validateFields() {
        boolean isValid = true;

        if (binding.edtCnpj.getText().toString().trim().isEmpty()) {
            binding.tilCnpj.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtReason.getText().toString().trim().isEmpty()) {
            binding.tilReason.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtFantasyName.getText().toString().trim().isEmpty()) {
            binding.tilFantasyName.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtBestEmail.getText().toString().trim().isEmpty()) {
            binding.tilBestEmail.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtSecondaryEmail.getText().toString().trim().isEmpty()) {
            binding.tilSecondaryEmail.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtDistric.getText().toString().trim().isEmpty()) {
            binding.tilDistrict.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtCep.getText().toString().trim().isEmpty()) {
            binding.tilCep.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtAddress.getText().toString().trim().isEmpty()) {
            binding.edtAddress.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtNumber.getText().toString().trim().isEmpty()) {
            binding.edtNumber.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtComplement.getText().toString().trim().isEmpty()) {
            binding.tilComplement.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        }

        return isValid;
    }

    private Client getClientFields(Boolean isEdit) {
        String cnpjCpf = binding.edtCnpj.getText().toString().trim();
        String reason = binding.edtReason.getText().toString().trim();
        String fantasyName = binding.edtFantasyName.getText().toString().trim();
        String email = binding.edtBestEmail.getText().toString().trim();
        String secEmail = binding.edtSecondaryEmail.getText().toString().trim();
        String district = binding.edtDistric.getText().toString().trim();
        String address = binding.edtAddress.getText().toString().trim();
        String number = binding.edtNumber.getText().toString().trim();
        String cep = binding.edtCep.getText().toString().trim();
        String complement = binding.edtComplement.getText().toString().trim();

        if (isEdit) {
            return new Client(
                    receivedClient.getCod(),
                    reason,
                    cnpjCpf,
                    address,
                    number,
                    complement,
                    cep,
                    district,
                    "",
                    fantasyName,
                    email,
                    secEmail
            );
        } else {
            return new Client(
                    "",
                    reason,
                    cnpjCpf,
                    address,
                    number,
                    complement,
                    cep,
                    district,
                    "",
                    fantasyName,
                    email,
                    secEmail
            );
        }
    }

    @Override
    public void resultSuccess() {
        clearFields();
        showToast(getString(R.string.str_success));
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}