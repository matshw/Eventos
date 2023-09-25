package br.mateus.appeventos.Persistance;

import java.util.List;

import br.mateus.appeventos.Model.Evento;

public interface EventoDAO {
    public void salvar (Evento e);
    public void editar(Evento e);
    public void remove(Evento e);
    public List listar();
}
