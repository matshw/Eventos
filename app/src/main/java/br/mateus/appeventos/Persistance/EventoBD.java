package br.mateus.appeventos.Persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.mateus.appeventos.Model.Evento;

public class EventoBD implements EventoDAO{
    BancoDados bd;

    public EventoBD(Context context){
        this.bd=new BancoDados(context);
    }

    @Override
    public void salvar(Evento e) {
        ContentValues evento = new ContentValues();
        evento.put("nome",e.getNome());
        evento.put("tipo",e.getTipo());
        evento.put("data",e.getData());
        bd.getWritableDatabase().insertOrThrow("evento",null,evento);
        bd.close();
    }

    @Override
    public void editar(Evento e) {
        ContentValues evento = new ContentValues();
        evento.put("nome",e.getNome());
        evento.put("tipo",e.getTipo());
        evento.put("data",e.getData());
        bd.getWritableDatabase().update("evento",evento,"id=?",new String[]{String.valueOf(e.getId())});
        bd.close();
    }

    @Override
    public void remove(Evento e) {
        bd.getWritableDatabase().delete("evento","id=?",new String[]{e.getId()+""});
        bd.close();
    }

    @Override
    public List listar() {
        List eventos = new ArrayList();
        String colunas[]={"id","nome","data","tipo"};
        Cursor cursor = bd.getReadableDatabase().query("evento",colunas,null,null,null,null,"nome");

        while(cursor.moveToNext()){
            Evento e = new Evento();
            e.setId(cursor.getInt(0));
            e.setNome(cursor.getString(1));
            e.setData(cursor.getString(2));
            e.setTipo(cursor.getString(3));
            eventos.add(e);
        }
        bd.close();

        return eventos;
    }
}
