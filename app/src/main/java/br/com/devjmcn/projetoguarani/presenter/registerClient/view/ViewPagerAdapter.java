package br.com.devjmcn.projetoguarani.presenter.registerClient.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import br.com.devjmcn.projetoguarani.model.models.Client;
import br.com.devjmcn.projetoguarani.presenter.registerClient.view.fragments.CnpjFragment;
import br.com.devjmcn.projetoguarani.presenter.registerClient.view.fragments.CpfFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final Client receivedClient;

    public ViewPagerAdapter(AppCompatActivity activity, Client receivedClient) {
        super(activity);
        this.receivedClient = receivedClient;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                if (receivedClient != null && !receivedClient.getFantasyName().isEmpty()) {
                    return CnpjFragment.newInstance(receivedClient);
                } else {
                    return new CnpjFragment();
                }
            }
            case 1: {
                if (receivedClient != null && receivedClient.getFantasyName().isEmpty()) {
                    return CpfFragment.newInstance(receivedClient);
                } else {
                    return new CpfFragment();
                }
            }
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}