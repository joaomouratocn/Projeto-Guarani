package br.com.devjmcn.projetoguarani.presenter.consultClient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.devjmcn.projetoguarani.databinding.ItemConsultClientsLayoutBinding;
import br.com.devjmcn.projetoguarani.model.models.ClientModel;

public class ConsultClientsAdapter extends RecyclerView.Adapter<ConsultClientsAdapter.ClientsConsultViewHolder> {
    private List<ClientModel> clientModelList = new ArrayList<>();

    @NonNull
    @Override
    public ClientsConsultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ClientsConsultViewHolder.inflate(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsConsultViewHolder holder, int position) {
        ClientModel client = clientModelList.get(position);
        holder.bind(client);
    }

    @Override
    public int getItemCount() {
        return clientModelList.size();
    }

    public static class ClientsConsultViewHolder extends RecyclerView.ViewHolder {
        private ItemConsultClientsLayoutBinding binding;
        private Context context;

        public ClientsConsultViewHolder(@NonNull ItemConsultClientsLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = binding.getRoot().getContext();
        }

        public static ClientsConsultViewHolder inflate(ViewGroup parent) {
            ItemConsultClientsLayoutBinding binding = ItemConsultClientsLayoutBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new ClientsConsultViewHolder(binding);
        }

        public void bind(ClientModel client) {

        }
    }
}
