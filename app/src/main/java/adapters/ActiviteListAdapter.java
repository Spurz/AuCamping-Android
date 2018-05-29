package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.campingimerir.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import entities.Activite;

/**
 * Created by student on 04/04/2018.
 */

public class ActiviteListAdapter extends ArrayAdapter<Activite> {

    private Context context;
    private List<Activite> activities;

    public ActiviteListAdapter(Context context, List<Activite> activities){
        super(context, R.layout.activite_item, activities);
        this.context = context;
        this.activities = activities;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Declaration du layout de la cellule
        View view = layoutInflater.inflate(R.layout.activite_item, parent, false);

        //Récupération de l'activité en question
        Activite activite = activities.get(position);

        //Affichage des données dans le layout
        TextView textViewNameActivite = (TextView) view.findViewById(R.id.text_view_nameActivite);
        textViewNameActivite.setText(activite.getName());

        TextView textViewTypeActivite = (TextView) view.findViewById(R.id.text_view_typeActivite);
        textViewTypeActivite.setText(activite.getType());

        TextView textViewDateActivite = (TextView) view.findViewById(R.id.text_view_dateActivite);
        textViewDateActivite.setText(activite.getDate());

        Button participeActivite = (Button) view.findViewById(R.id.participeActivite);
        TextView participePas = (TextView) view.findViewById(R.id.participePas);

        if(activite.getActiveReserv() == 0){
            participePas.setVisibility(View.GONE);
            participeActivite.setText("Je participe");
        }else{
            participeActivite.setVisibility(View.GONE);
            participePas.setText("Vous participez à cette évenement");
        }



        return view;
    }
}
