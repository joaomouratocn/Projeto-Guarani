package br.com.devjmcn.projetoguarani.presenter.consultClient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.devjmcn.projetoguarani.R;
import br.com.devjmcn.projetoguarani.databinding.ItemConsultClientsLayoutBinding;
import br.com.devjmcn.projetoguarani.model.models.Client;

public class ConsultClientsAdapter extends RecyclerView.Adapter<ConsultClientsAdapter.ClientsConsultViewHolder> {
    private final List<Client> clientList = new ArrayList<>();
    private final OnCliCkEvent onCliCkEvent;

    public ConsultClientsAdapter(OnCliCkEvent onCliCkEvent) {
        this.onCliCkEvent = onCliCkEvent;
    }

    @NonNull
    @Override
    public ClientsConsultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ClientsConsultViewHolder.inflate(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsConsultViewHolder holder, int position) {
        Client client = clientList.get(position);
        holder.bind(client, onCliCkEvent);
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

        public void bind(Client client, OnCliCkEvent onCliCkEvent) {
            binding.txvNameClient.setText(client.getReason());
            binding.txvFantasyName.setText(client.getFantasyName());
            binding.txvCpfCnpj.setText(context.getString(R.string.str_cnpj_cpf, client.getCnpjCpf()));
            binding.txvEmail.setText(context.getString(R.string.str_email, client.getEmail()));
            binding.txvDtRegister.setText(context.getString(R.string.str_dt_register, client.getDtRegister()));

            binding.imgOptions.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.inflate(R.menu.consult_client_menu);
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.menu_edit) {
                        onCliCkEvent.onClickEdit(client);
                    } else if (menuItem.getItemId() == R.id.menu_delete) {
                        onCliCkEvent.onClickDelete(client);
                    }
                    return true;
                });
                popupMenu.show();
            });

            binding.getRoot().setOnClickListener(view -> {
                onCliCkEvent.onDetailClick(client);
            });
        }
    }

    interface OnCliCkEvent {
        void onClickEdit(Client client);
        void onClickDelete(Client client);
        void onDetailClick(Client client);
    }
}
