package br.mateus.appeventos;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import br.mateus.appeventos.Persistance.DBEvento;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private DatePicker calendario;
    private Spinner spinner;
    private ImageButton botaoSalvar;
    private ImageButton mostrarCalendario;
    private ListView lista;
    private List<String> listaEventos;
    private ArrayAdapter<String> eventoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.idNome);
        calendario = findViewById(R.id.idCalendario);
        spinner = findViewById(R.id.idSpinner);
        botaoSalvar = findViewById(R.id.idSalvar);
        lista = findViewById(R.id.idLista);
        mostrarCalendario = findViewById(R.id.mostrarCalendario);
        spinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item));
        listaEventos = new ArrayList<>();
        eventoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaEventos);
        lista.setAdapter(eventoAdapter);

        mostrarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendario.getVisibility() == View.VISIBLE) {
                    calendario.setVisibility(View.GONE); // Torna o DatePicker invisível
                } else {
                    calendario.setVisibility(View.VISIBLE); // Torna o DatePicker visível
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
                    Evento evento = new Evento(nomeEvento, dataEvento, tipoEvento);

                    DBEvento dbHelper = new DBEvento(MainActivity.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("nome", evento.getNome());
                    values.put("data", evento.getData());
                    values.put("tipo", evento.getTipo());

                    long newRowId = db.insert("evento", null, values);
                    evento.setId((int) newRowId); // Define o ID do evento após a inserção

                    listaEventos.add(evento.getNome());
                    listaEventos.add(evento.getData());
                    listaEventos.add(evento.getTipo());
                    eventoAdapter.notifyDataSetChanged();

                    // Limpa os campos após adicionar o evento
                    nome.getText().clear();

                    db.close();
                }
            }
        });

    }
}
