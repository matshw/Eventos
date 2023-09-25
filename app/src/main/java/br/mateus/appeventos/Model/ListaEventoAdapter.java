package br.mateus.appeventos.Model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.mateus.appeventos.R;


public class ListaEventoAdapter extends BaseAdapter {
    private Context context;
    private List<Evento> listaEvento;

    public ListaEventoAdapter(Context context, List<Evento> ListaEvento) {
        this.context = context;
        listaEvento = ListaEvento;
    }

    @Override
    public int getCount() {
        return listaEvento.size();
    }

    @Override
    public Object getItem(int position) {
        return listaEvento.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v =View.inflate(context, R.layout.linha,null);
        TextView textNome = (TextView)v.findViewById(R.id.idNomeE);
        TextView textCategoria = (TextView) v.findViewById(R.id.idCategoriaE);
        TextView textData = (TextView) v.findViewById(R.id.idDataE);

        textNome.setText(listaEvento.get(position).getNome());
        textCategoria.setText(listaEvento.get(position).getTipo());
        textData.setText(listaEvento.get(position).getData());
        v.setTag(listaEvento.get(position).getId());
        return v;
    }
}
