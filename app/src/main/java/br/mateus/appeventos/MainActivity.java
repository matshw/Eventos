package br.mateus.appeventos;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

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
    private TimePicker relogio;
    private Spinner spinner;
    private ImageButton botaoSalvar;
    private ImageButton mostrarRelogio;
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
        mostrarRelogio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (relogio.getVisibility() == View.VISIBLE) {
                    relogio.setVisibility(View.GONE);
                } else {
                    relogio.setVisibility(View.VISIBLE);
                }
            }
        });
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
                String horaEvento = relogio.getHour() + ":" + relogio.getMinute();
                if (nomeEvento.isEmpty() || dataEvento.isEmpty() || horaEvento.isEmpty() || tipoEvento.equals("Selecione uma categoria")) {
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
                    e.setRelogio(horaEvento);
                    if (e.getId() == null) {
                        dao.salvar(e);
                    } else{
                        dao.editar(e);
                    }
                    atualizarItens();
                    limparCampos();
                }
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final Evento eventoSelecionado = listaEventos.get(position);

                LinearLayout itemLayout = view.findViewById(R.id.idItem);

                itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupEditarExcluir(eventoSelecionado);
                    }
                });
            }
        });
    }

    private void popupEditarExcluir(final Evento evento){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setTitle("Opções");
        dialogBuilder.setMessage("Escolha o que deseja fazer:");

        dialogBuilder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                popupEditar(evento);
            }
        });
        dialogBuilder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                popupExcluir(evento);
            }
        });
        dialogBuilder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogBuilder.create().show();
    }
    private void popupEditar(Evento evento) {
        AlertDialog.Builder editarDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        editarDialogBuilder.setTitle("Editar Evento");

        View editarView = getLayoutInflater().inflate(R.layout.dialog_editar, null);
        editarDialogBuilder.setView(editarView);

        final EditText editarNome = editarView.findViewById(R.id.idNome);
        final Spinner editarSpinner = editarView.findViewById(R.id.idSpinner);
        final DatePicker editarCalendario = editarView.findViewById(R.id.idCalendario);
        final TimePicker editarRelogio = editarView.findViewById(R.id.idRelogio);
        editarSpinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item));

        // Defina os valores iniciais dos elementos com base no evento selecionado
        editarNome.setText(evento.getNome());
        editarSpinner.setSelection(getIndex(editarSpinner, evento.getTipo()));

        // Converter a data do evento para o DatePicker
        String[] dataParts = evento.getData().split("/");
        int dia = Integer.parseInt(dataParts[0]);
        int mes = Integer.parseInt(dataParts[1]) - 1; // O DatePicker usa meses de 0 a 11
        int ano = Integer.parseInt(dataParts[2]);
        editarCalendario.updateDate(ano, mes, dia);

        String[] horaParts = evento.getRelogio().split(":");
        int hora = Integer.parseInt(horaParts[0]);
        int minuto = Integer.parseInt(horaParts[1]);
        editarRelogio.setHour(hora);
        editarRelogio.setMinute(minuto);
        editarDialogBuilder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String novoNome = editarNome.getText().toString().trim();
                String novoTipo = editarSpinner.getSelectedItem().toString().trim();
                int novoDia = editarCalendario.getDayOfMonth();
                int novoMes = editarCalendario.getMonth() + 1; // Adicionar 1 para ajustar o mês
                int novoAno = editarCalendario.getYear();
                int novaHora = editarRelogio.getHour();
                int novoMinuto = editarRelogio.getMinute();

                evento.setNome(novoNome);
                evento.setTipo(novoTipo);
                evento.setData(novoDia + "/" + novoMes + "/" + novoAno);
                evento.setRelogio(novaHora + ":" + novoMinuto);
                dao.editar(evento);

                atualizarItens();
            }
        });
        editarDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        editarDialogBuilder.create().show();
    }
    private void popupExcluir(Evento evento){
        AlertDialog.Builder confirmarExcluir = new AlertDialog.Builder(MainActivity.this);
        confirmarExcluir.setTitle("Confirmar Exclusão");
        confirmarExcluir.setMessage("Tem certeza de que deseja excluir esta tarefa?");

        confirmarExcluir.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dao.remove(evento);

                atualizarItens();
            }
        });

        confirmarExcluir.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        confirmarExcluir.create().show();
    }
    private void mapearXML(){
        nome = findViewById(R.id.idNome);
        relogio =findViewById(R.id.idRelogio);
        calendario = findViewById(R.id.idCalendario);
        spinner = findViewById(R.id.idSpinner);
        botaoSalvar = findViewById(R.id.idSalvar);
        lista = findViewById(R.id.idLista);
        mostrarCalendario = findViewById(R.id.mostrarCalendario);
        mostrarRelogio = findViewById(R.id.mostrarRelogio);
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

    private void limparCampos(){
        nome.setText(" ");
        calendario.updateDate(2023,0,1);
        spinner.setSelection(0);
    }
    private int getIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                return i;
            }
        }
        return 0;
    }
}
