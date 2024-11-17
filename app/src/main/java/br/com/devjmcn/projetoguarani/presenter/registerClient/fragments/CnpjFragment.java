package br.com.devjmcn.projetoguarani.presenter.registerClient.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.devjmcn.projetoguarani.databinding.FragmentCnpjBinding;
import br.com.devjmcn.projetoguarani.model.models.Client;

public class CnpjFragment extends Fragment {
    FragmentCnpjBinding binding;

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
        binding = FragmentCnpjBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Client receivedClient = getArguments().getParcelable(KEY);

            loadFields(receivedClient);
        }
    }

    private void loadFields(Client receivedClient) {
        binding.edtCnpj.setText(receivedClient.getCnpjCpf());
        binding.edtReason.setText(receivedClient.getReason());
        binding.edtFantasyName.setText(receivedClient.getFantasyName());
        binding.edtBestEmail.setText(receivedClient.getEmail());
        binding.edtSecondaryEmail.setText(receivedClient.getSecEmail());
        binding.edtEdtPhone.setText(receivedClient.getPhone());
        binding.edtAddress.setText(receivedClient.getAdress());
        binding.edtNumber.setText(receivedClient.getNumber());
        binding.edtCep.setText(receivedClient.getCep());
        binding.edtComplement.setText(receivedClient.getComplement());
    }
}