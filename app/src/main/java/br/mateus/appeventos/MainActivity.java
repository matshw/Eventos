package br.mateus.appeventos;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.mateus.appeventos.Model.Evento;
import br.mateus.appeventos.Model.ListaEventoAdapter;
import br.mateus.appeventos.Persistance.BancoDados;
import br.mateus.appeventos.Persistance.EventoBD;
import br.mateus.appeventos.Persistance.EventoDAO;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private DatePicker calendario;
    private Spinner spinner;
    private ImageButton botaoSalvar;
    private ImageButton mostrarCalendario;
    private ListView lista;
    private List<Evento> listaEventos;
    private Evento e;
    private EventoDAO dao;
    private ListaEventoAdapter arrayEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapearXML();
        verificar();
        click();
        arrayEvento = new ListaEventoAdapter(getApplicationContext(),listaEventos);
        lista.setAdapter(arrayEvento);


    }

    private void click(){
        mostrarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendario.getVisibility() == View.VISIBLE) {
                    calendario.setVisibility(View.GONE);
                } else {
                    calendario.setVisibility(View.VISIBLE);
                }
            }
        });
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeEvento = nome.getText().toString().trim();
                String dataEvento = calendario.getDayOfMonth() + "/" + (calendario.getMonth() + 1) + "/" + calendario.getYear();
                String tipoEvento = spinner.getSelectedItem().toString().trim();
                if (nomeEvento.isEmpty() || dataEvento.isEmpty() || tipoEvento.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Campos em branco")
                            .setMessage("Por favor, preencha todos os campos!")
                            .setPositiveButton("OK", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    if(e == null){
                        e = new Evento();
                    }
                    e.setNome(nomeEvento);
                    e.setTipo(tipoEvento);
                    e.setData(dataEvento);
                    if (e.getId() == null) {
                        dao.salvar(e);
                    } else{
                        dao.editar(e);
                    }
                    atualizarItens();
                }
            }
        });
    }
    private void mapearXML(){
        nome = findViewById(R.id.idNome);
        calendario = findViewById(R.id.idCalendario);
        spinner = findViewById(R.id.idSpinner);
        botaoSalvar = findViewById(R.id.idSalvar);
        lista = findViewById(R.id.idLista);
        mostrarCalendario = findViewById(R.id.mostrarCalendario);
        spinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item));

    }
    private void verificar() {
        if (dao == null) {
            dao = new EventoBD(this);
        }
        listaEventos = dao.listar();
    }
    private void atualizarItens() {
        listaEventos.clear();
        listaEventos.addAll(dao.listar());
        arrayEvento.notifyDataSetChanged();
    }
}
