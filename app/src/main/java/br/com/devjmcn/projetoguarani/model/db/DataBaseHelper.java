package br.com.devjmcn.projetoguarani.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "bancomovel.db";
    private static final int DB_VERSION = 1;
    private static String DB_PATH = "";
    private final Context context;
    private SQLiteDatabase DB;

    @Inject
    public DataBaseHelper(@ApplicationContext Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
    }

    public SQLiteDatabase getDatabase() {
        createDB();
        if (DB == null || !DB.isOpen()) {
            DB = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        }
        return DB;
    }

    public void createDB() {
        boolean dbExists = checkDataBase();
        if (!dbExists) {
            File databaseDir = new File(DB_PATH);
            if (!databaseDir.exists()) {
                databaseDir.mkdir();
            }

            this.getReadableDatabase();
            copyDB();
        }
    }

    private void copyDB() {
        try (InputStream input = context.getAssets().open(DB_NAME);
             OutputStream output = new FileOutputStream(DB_PATH + DB_NAME)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            Log.d("DataBaseHelper", "Banco copiado com sucesso para: " + DB_PATH + DB_NAME);

        } catch (Exception e) {
            Log.e("DataBaseHelper", "Erro ao copiar o banco: " + e.getMessage());
            throw new RuntimeException("Erro ao copiar o banco: " + e.getMessage());
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    public void closeDatabase() {
        if (DB != null && DB.isOpen()) {
            DB.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
