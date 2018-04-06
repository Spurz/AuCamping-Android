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

/**
 * Created by student on 04/04/2018.
 */

public class LocationListAdapter extends ArrayAdapter<Emplacement> {

    private Context context;
    private List<Emplacement> emplacements;

    public LocationListAdapter(Context context, List<Emplacement> emplacements){
        super(context, R.layout.list_item, emplacements);
        this.context = context;
        this.emplacements = emplacements;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);

        Emplacement emplacement = emplacements.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        Picasso.with(getContext()).load(emplacement.getImageURL()).placeholder(R.mipmap.ic_launcher).into(imageView);

        TextView textView = (TextView) view.findViewById(R.id.text_view_title);
        textView.setText(emplacement.getName());
        return view;
    }
}
