package br.com.devjmcn.projetoguarani.di;

import android.content.Context;

import javax.inject.Singleton;

import br.com.devjmcn.projetoguarani.model.db.DataBaseHelper;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataBaseModule {
    @Provides
    @Singleton
    public DataBaseHelper providerSQLite(@ApplicationContext Context context){
        return new DataBaseHelper(context);
    }
}
