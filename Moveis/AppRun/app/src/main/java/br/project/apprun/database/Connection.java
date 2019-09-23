package br.project.apprun.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Connection extends SQLiteOpenHelper {

    private static final String name = "banco_run.db";
    private static final int version = 1;

    public Connection(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("" +
                "create table users" +
                "(id integer primary key autoincrement," +
                "foto blob," +
                "nome varchar(50)," +
                "sexo varchar(50),"+
                "email varchar(50)," +
                "senha varchar(6)," +
                "telefone varchar(20)," +
                "disciplina varchar(50)," +
                "turma integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists user");
    }

    //Checar email e senha
    public Boolean emailsenha(String email, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email=? and senha=?",
                new String[] {email, senha});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    //Checar email existente
    public Boolean emailExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email=?", new String[]{email});
        if(cursor.getCount() >0) return true;
        else return false;
    }
}
