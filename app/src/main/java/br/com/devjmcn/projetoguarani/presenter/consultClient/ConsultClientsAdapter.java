package br.com.devjmcn.projetoguarani.presenter.consultClient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.devjmcn.projetoguarani.databinding.ItemConsultClientsLayoutBinding;
import br.com.devjmcn.projetoguarani.model.models.Client;

public class ConsultClientsAdapter extends RecyclerView.Adapter<ConsultClientsAdapter.ClientsConsultViewHolder> {
    private final List<Client> clientList = new ArrayList<>();

    @NonNull
    @Override
    public ClientsConsultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ClientsConsultViewHolder.inflate(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsConsultViewHolder holder, int position) {
        Client client = clientList.get(position);
        holder.bind(client);
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public void submitList(List<Client> newList){
        clientList.clear();
        clientList.addAll(newList);
        notifyDataSetChanged();
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

        public void bind(Client client) {

        }
    }
}
