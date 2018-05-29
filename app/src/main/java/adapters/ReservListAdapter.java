package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.campingimerir.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import entities.Emplacement;
import entities.Reservation;

/**
 * Created by student on 04/04/2018.
 */

public class ReservListAdapter extends ArrayAdapter<Reservation> {

    private Context context;
    private List<Reservation> reservations;

    public ReservListAdapter(Context context, List<Reservation> reservations){
        super(context, R.layout.list_item, reservations);
        this.context = context;
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Declaration du layout de la cellule
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);

        //Récupération de la réservation en question
        Reservation reservation = reservations.get(position);

        //Affichage des données dans le layout
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        Picasso.with(getContext()).load(reservation.getImageURLReserv()).placeholder(R.mipmap.ic_launcher).into(imageView);

        TextView textView = (TextView) view.findViewById(R.id.text_view_title);
        textView.setText(reservation.getNameReserv());
        return view;
    }
}
