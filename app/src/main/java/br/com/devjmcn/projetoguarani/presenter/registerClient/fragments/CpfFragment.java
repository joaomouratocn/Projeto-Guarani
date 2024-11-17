package br.com.devjmcn.projetoguarani.presenter.registerClient.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.devjmcn.projetoguarani.R;
import br.com.devjmcn.projetoguarani.databinding.FragmentCnpjBinding;
import br.com.devjmcn.projetoguarani.databinding.FragmentCpfBinding;
import br.com.devjmcn.projetoguarani.model.models.Client;

public class CpfFragment extends Fragment {
    FragmentCpfBinding binding;
    Client receivedClient;

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
        binding = FragmentCpfBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            receivedClient = getArguments().getParcelable(KEY);
        }
    }
}