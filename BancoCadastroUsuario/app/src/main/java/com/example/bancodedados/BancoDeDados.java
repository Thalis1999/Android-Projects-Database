package com.example.bancodedados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados extends SQLiteOpenHelper {
    private final String criaTabela = "CREATE TABLE usuario (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(60), " +
            "email VARCHAR(80), " +
            "usuario VARCHAR(40) UNIQUE, " +
            "senha VARCHAR(70) );";

    public BancoDeDados(@Nullable Context context, int version) {
        super(context, "BD_Aplicativo", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(criaTabela);
    }

    public boolean cadastrar(String nome, String email,
                                        String usuario, String senha){
        //Abrir a conexão com o banco de dados
        SQLiteDatabase banco = getWritableDatabase();

        //Usar a classe ContentValues para indicar os valores
        //de cada coluna
        ContentValues valores = new ContentValues();
        valores.put("nome", nome); //nome_da_coluna, valor
        valores.put("email", email);
        valores.put("usuario", usuario);
        valores.put("senha", senha);

        //Executando o comando insert e testando o resultado
        //O insert retorna -1 quando não consegue cadastrar (erro)
        if(banco.insert("usuario", null, valores) == -1){
            return false;
        }else{
            return true;
        }
    }

    //Método para validar o acesso com usuário e senha
    public boolean validarAcesso(String usuario, String senha){
        //Abrir a conexão com o banco
        SQLiteDatabase conexao = getWritableDatabase();

        //Criar um objeto da classe Cursor a qual é responsável por
        //armazenar o resultado do SELECT
        Cursor resultado = conexao.query(
                "usuario", //Nome da tabela
                null, //Quais colunas serão selecionadas, null==todas (*)
                "usuario = ?", //Comando de filtro que vai depois do WHERE
                new String[]{usuario}, //Valor que será atribuído ao ?
                null, //Agrupar por uma coluna, null == sem agrupar
                null, //Filtro do agrupamento, null == sem filtro
                null //Ordenar por uma coluna, null == sem ordenar
        );

        //Testar se o "resultado" tem algum valor ou não
        if(resultado.moveToFirst()){
            //Se há ao menos 1 valor...
            if(senha.equals(resultado.getString(4))){
                //Se a senha digitada é igual a senha da tabela
                return true; //Usuário existe e senha correta
            }else {
                return false; //Usuário existe e senha incorreta
            }
        }else{
            return false; //Usuário não existe
        }
    }

    //Método para buscar todas as linhas e colunas da tabela
    //SELECT * FROM usuario;
    public List<Usuario> buscarTodos(){
        SQLiteDatabase conexao = getWritableDatabase(); //Abre a conexão
        //Criar a lista de objetos Usuario
        List<Usuario> lista = new ArrayList<Usuario>();

        Cursor resultado = conexao.query(
                "usuario", null, null,
                null, null, null, null
        );

        if(resultado.moveToFirst()){
            //Se conseguiu mover para a primeira linha, então devemos ler os valores
            do{
                //Recuperar os valores da tabela
                int id = resultado.getInt(0); //Coluna 0 == coluna _id
                String nome = resultado.getString(1);
                String email = resultado.getString(2);
                String usuario = resultado.getString(3);
                String senha = resultado.getString(4);

                //Montar o objeto da classe Usuario
                Usuario u = new Usuario(id, nome, email, usuario, senha);
                //Adicionar o usuário "u" na lista
                lista.add(u);
            }while (resultado.moveToNext()); //Avança enquanto houver um próximo
        }

        return lista;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
