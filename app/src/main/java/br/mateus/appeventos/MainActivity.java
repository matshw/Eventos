package br.mateus.appeventos;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.mateus.appeventos.Model.Evento;
import br.mateus.appeventos.Persistance.DBEvento;

public class MainActivity extends AppCompatActivity {

    private EditText eventNameEditText;
    private DatePicker eventDatePicker;
    private Spinner eventTypeSpinner;
    private Button addEventButton;
    private ListView eventListView;
    private List<String> eventList;
    private ArrayAdapter<String> eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventNameEditText = findViewById(R.id.eventNameEditText);
        eventDatePicker = findViewById(R.id.eventDatePicker);
        eventTypeSpinner = findViewById(R.id.eventTypeSpinner);
        addEventButton = findViewById(R.id.addEventButton);
        eventListView = findViewById(R.id.eventListView);
        eventTypeSpinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item));

        eventList = new ArrayList<>();
        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        eventListView.setAdapter(eventAdapter);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName = eventNameEditText.getText().toString();
                String eventDate = eventDatePicker.getDayOfMonth() + "/" + (eventDatePicker.getMonth() + 1) + "/" + eventDatePicker.getYear();
                String eventType = eventTypeSpinner.getSelectedItem().toString();

                Evento event = new Evento(eventName, eventDate, eventType);

                DBEvento dbHelper = new DBEvento(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(DBEvento.COLUMN_NAME, event.getName());
                values.put(DBEvento.COLUMN_DATE, event.getDate());
                values.put(DBEvento.COLUMN_TYPE, event.getType());

                long newRowId = db.insert(DBEvento.TABLE_EVENTS, null, values);
                event.setId((int) newRowId); // Define o ID do evento após a inserção

                eventList.add(event.getName());
                eventList.add(event.getDate());
                eventList.add(event.getType());
                eventAdapter.notifyDataSetChanged();

                // Limpa os campos após adicionar o evento
                eventNameEditText.getText().clear();

                db.close();
            }
        });

    }
}
