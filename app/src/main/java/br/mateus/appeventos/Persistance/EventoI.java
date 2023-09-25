package br.mateus.appeventos.Persistance;

import java.util.ArrayList;
import java.util.List;

import br.mateus.appeventos.Model.Evento;

public class EventoI implements EventoDAO{
    private List eventos;

    public EventoI() {
        eventos = new ArrayList();
    }

    @Override
    public void salvar(Evento e) {
        eventos.add(e);
    }

    @Override
    public void editar(Evento e) {
        if(eventos.contains(e)){
            eventos.add(eventos.indexOf(e),e);
        }
    }

    @Override
    public void remove(Evento e) {
        eventos.remove(e);
    }

    @Override
    public List listar() {
        return eventos;
    }
}
