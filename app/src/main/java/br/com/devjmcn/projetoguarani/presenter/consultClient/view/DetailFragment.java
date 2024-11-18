package br.com.devjmcn.projetoguarani.presenter.consultClient.view;

import static android.provider.Contacts.SettingsColumns.KEY;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.devjmcn.projetoguarani.R;
import br.com.devjmcn.projetoguarani.databinding.DetailFragmentBinding;
import br.com.devjmcn.projetoguarani.model.models.Client;


public class DetailFragment extends Fragment {
    DetailFragmentBinding binding;
    Client receivedClient;

    private static final String CLIENT = "CLIENT";

    public static DetailFragment newInstance(Client item) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(CLIENT, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DetailFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            receivedClient = getArguments().getParcelable(CLIENT);
        }
        loadFields();
    }

    private void loadFields() {
        binding.txvCod.setText(getString(R.string.str_cod_1s, receivedClient.getCod()));
        binding.txvReason.setText(getString(R.string.str_reason_1s, receivedClient.getReason()));
        binding.txvCnpjCpf.setText(getString(R.string.str_cnpj_cpf_1, receivedClient.getCnpjCpf()));
        binding.txvAddress.setText(getString(R.string.str_address_1, receivedClient.getAddress()));
        binding.txvNumber.setText(getString(R.string.str_number_1, receivedClient.getNumber()));
        binding.txvComplement.setText(getString(R.string.str_complement_1, receivedClient.getComplement()));
        binding.txvCep.setText(getString(R.string.str_cep_1, receivedClient.getCep()));
        binding.txvDistrict.setText(getString(R.string.str_district, receivedClient.getDistrict()));
        binding.txvDtRegister.setText(getString(R.string.str_dt_register, receivedClient.getDtRegister()));
        binding.txvFantasyName.setText(getString(R.string.str_fantasy, receivedClient.getFantasyName()));
        binding.txvEmail.setText(getString(R.string.str_email_1, receivedClient.getEmail()));
        binding.txvSecEmail.setText(getString(R.string.str_email_sec, receivedClient.getSecEmail()));
    }
}