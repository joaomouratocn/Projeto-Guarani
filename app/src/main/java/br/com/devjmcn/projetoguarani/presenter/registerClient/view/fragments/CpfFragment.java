package br.com.devjmcn.projetoguarani.presenter.registerClient.view.fragments;

import static br.com.devjmcn.projetoguarani.presenter.registerClient.RegisterContracts.RegisterPresenterInterface;
import static br.com.devjmcn.projetoguarani.presenter.registerClient.RegisterContracts.RegisterViewInterface;

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
import br.com.devjmcn.projetoguarani.databinding.CpfFragmentBinding;
import br.com.devjmcn.projetoguarani.model.models.Client;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CpfFragment extends Fragment implements RegisterViewInterface {
    CpfFragmentBinding binding;
    Client receivedClient;

    @Inject
    RegisterPresenterInterface registerPresenterInterface;

    private static final String KEY = "KEY";

    public static CpfFragment newInstance(Client client) {
        CpfFragment fragment = new CpfFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY, client);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = CpfFragmentBinding.inflate(getLayoutInflater());
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

    private Client getClientFields(Boolean isEdit) {
        String cnpjCpf = binding.edtCpf.getText().toString().trim();
        String reason = binding.edtName.getText().toString().trim();
        String email = binding.edtBestEmail.getText().toString().trim();
        String secEmail = binding.edtSecondaryEmail.getText().toString().trim();
        String district = binding.edtEdtDistric.getText().toString().trim();
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
                    "",
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
                    "",
                    email,
                    secEmail
            );
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        if (binding.edtCep.getText().toString().trim().isEmpty()) {
            binding.tilCep.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtName.getText().toString().trim().isEmpty()) {
            binding.tilName.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtBestEmail.getText().toString().trim().isEmpty()) {
            binding.tilBestEmail.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtSecondaryEmail.getText().toString().trim().isEmpty()) {
            binding.tilSecondaryEmail.setError(getString(R.string.str_invalid_text_field));
            isValid = false;
        } else if (binding.edtEdtDistric.getText().toString().trim().isEmpty()) {
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

    private void loadFields() {
        binding.edtCpf.setText(receivedClient.getCnpjCpf());
        binding.edtName.setText(receivedClient.getReason());
        binding.edtBestEmail.setText(receivedClient.getEmail());
        binding.edtSecondaryEmail.setText(receivedClient.getSecEmail());
        binding.edtEdtDistric.setText(receivedClient.getDistrict());
        binding.edtAddress.setText(receivedClient.getAddress());
        binding.edtNumber.setText(receivedClient.getNumber());
        binding.edtCep.setText(receivedClient.getCep());
        binding.edtComplement.setText(receivedClient.getComplement());
    }

    private void clearFields() {
        binding.edtCpf.setText("");
        binding.edtName.setText("");
        binding.edtBestEmail.setText("");
        binding.edtSecondaryEmail.setText("");
        binding.edtEdtDistric.setText("");
        binding.edtAddress.setText("");
        binding.edtNumber.setText("");
        binding.edtCep.setText("");
        binding.edtComplement.setText("");
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