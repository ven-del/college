package br.project.apprun.controller;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;

        import br.project.apprun.database.Connection;
        import br.project.apprun.model.User;

public class UserService {

    private Connection connection;
    private SQLiteDatabase banco;

    public UserService (Context context) {
        connection = new Connection(context);

        //Iniciar o banco de dados, obter um banco de dados para escrita.
        banco = connection.getWritableDatabase();
    }

    public long inserir(User user){
        //Valores que será inserido
        ContentValues values = new ContentValues();
        values.put("nome", user.getNome());
        values.put("email", user.getEmail());
        values.put("senha", user.getSenha());
        values.put("telefone", user.getTelefone());
        values.put("disciplina", user.getDisciplina());
        values.put("turma", user.getTurma());
        return banco.insert("users", null, values);

    }


}
