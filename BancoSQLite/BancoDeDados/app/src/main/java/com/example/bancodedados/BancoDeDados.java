package com.example.bancodedados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados extends SQLiteOpenHelper {
    //Comando SQL para criar a tabela de usuários
    private final String criaTabelaUsuario = "CREATE TABLE usuario (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(60), " +
            "email VARCHAR(60), " +
            "usuario VARCHAR(40) UNIQUE, " +
            "senha VARCHAR (70) );";

    public BancoDeDados(@Nullable Context context, int version) {
        super(context, "BANCO_APP", null, version);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        //O método do onConfigure é executado antes do onCreate
        //Usamos o onConfigure para habilitar o uso de chave estrangeira
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN){
            //Se a versão do Android do dispositivo for a 4.1 ou anterior
            db.setForeignKeyConstraintsEnabled(true);
        }else{
            //Se for versões após a 4.1
            db.execSQL("PRAGMA foreign_keys=ON");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(criaTabelaUsuario);
    }

    //Método para cadastrar um novo usuário na tabela (INSERT)
    public boolean cadastrar(String nome, String email,
                                        String usuario, String senha){
        //Abrir a conexão com o banco de dados
        SQLiteDatabase conexao = getWritableDatabase();

        //Utilizar a classe ContentValues para passar o valor de cada coluna
        ContentValues valores = new ContentValues();
        valores.put("nome", nome); //nome_da_coluna, valor_a_inserir
        valores.put("email", email);
        valores.put("usuario", usuario);
        valores.put("senha", senha);

        //Executar o comando INSERT já verificando com IF o retorno
        if(conexao.insert("usuario", null, valores) != -1){
            //O método insert() retorna -1 se caso ocorreu algum erro, se
            //estive diferente de -1 então o INSERT executou corretamente
            return true;
        }else{
            return false;
        }
    }

    //Método para validar o acesso (login) com usuário e senha
    public boolean validarAcesso(String usuario, String senha){
        //Abrir a conexão
        SQLiteDatabase conexao = getWritableDatabase();

        //SELECT * FROM usuario WHERE usuario = ?
        //Criar um objeto da classe Cursor a qual irá armazenar
        //o resultado do comando SELECT
        Cursor resultado = conexao.query(
                "usuario", //Nome da tabela
                null, //Quais colunas queremos buscar, null == todas (*)
                "usuario = ?", //Filtro do comando WHERE
                new String[]{usuario}, //Valor que irá preencher o ?
                null, //Agrupar os resultados, null == não agrupar
                null, //Filtro do agrupamento, null == sem filtro
                null //Ordernar os resultados, null == não ordenar
        );

        //Testar (IF) a quantidade de linhas que foram buscadas
        if(resultado.moveToFirst()){
            //Se conseguir mover até a primeira linha, então há
            //ao menos 1 valor a ser lido
            if(senha.equals(resultado.getString(4))){
                //Comparando se a senha digitada é igual a senha
                //que está na tabela, no índice 4 (coluna senha)
                return true; //Usuário existe e senha correta
            }else{
                return false; //Usuário existe e senha incorreta
            }
        }else {
            return false; //Usuário não existe
        }
    }

    //Método para buscar todos os valores da tabela usuario
    public List<Usuario> buscarTodos(){
        SQLiteDatabase conexao = getWritableDatabase(); //Abre a conexão
        //Cursor para armazenar o resultado do SELECT * FROM usuario
        Cursor resultado = conexao.query(
                "usuario", //Nome da tabela
                null, //Todas as colunas
                null, //Sem filtro (sem where)
                null, //Sem valores para o where
                null, //Sem agrupar
                null, //Sem filtro do agrupar
                null //Sem ordenação
        );

        //Criar uma lista em branco da classe Usuario
        List<Usuario> lista = new ArrayList<Usuario>();

        //Testar se há valores no "resultado"
        if(resultado.moveToFirst()){
            //Como está posicionado na primeira linha, devemos ler esse valor
            do{
                //Recuperar os valores da tabela
                int _id = resultado.getInt(0); //Índice 0 == coluna _id
                String nome = resultado.getString(1);
                String email = resultado.getString(2);
                String usuario = resultado.getString(3);
                String senha = resultado.getString(4);

                //Montar um objeto da classe Usuario
                Usuario u = new Usuario(_id, nome, email, usuario, senha);

                //Adicionar o objeto "u" na lista de usuários
                lista.add(u);

            }while (resultado.moveToNext());//Move para o próximo, se houver
        }
        //Após terminar a repetição, a lista está preenchida
        return lista;
    }

    //Método para atualizar nome, email e senha de um usuário
    public boolean atualiza(String nome, String email, String senha, String usuario){
        SQLiteDatabase conexao = getWritableDatabase(); //Abre a conexão

        //Passagem dos valores que serão atualizados
        ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("email", email);
        valores.put("senha", senha);

        //Executar o comando UPDATE e passar o "usuario" como condição WHERE
        if(conexao.update(
                "usuario", //Nome da tabela a ser atualizada
                valores, //Valores que serão atualizados
                "usuario = ?", //Condição para atualizar (só de um usuário)
                new String[]{usuario} // Valor que será preenchido no lugar de ?
                ) != 0){ //Se o resultado do UPDATE não for zero...
            return true;
        }else{ //Se o resultado do UPDATE for igual a zero...
            return false;
        }
    }

    //Método para remover (DELETE) um usuário da tabela
    public boolean remover(String usuario){
        SQLiteDatabase conexao = getWritableDatabase();

        //Como o comando DELETE não precisa de valores, só a condição, já podemos
        //chamar a sua execução
        if(conexao.delete("usuario", "usuario = ?",
                                                        new String[]{usuario}) != 0){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
