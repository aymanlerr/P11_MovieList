package sg.edu.rp.c346.id22015131.p11_movielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Movie> movieList;
    TextView tvTitle, tvGenre, tvYear;
    ImageView ivRating;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        movieList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layout_id, parent, false);

        tvTitle = rowView.findViewById(R.id.tvTitle);
        tvGenre = rowView.findViewById(R.id.tvGenre);
        tvYear = rowView.findViewById(R.id.tvYear);
        ivRating = rowView.findViewById(R.id.ivRating);

        Movie currentMovie = movieList.get(position);

        tvTitle.setText(currentMovie.getTitle());
        tvGenre.setText(currentMovie.getGenre());
        tvYear.setText(currentMovie.getYear() + "");

        String img;
        String stringRating = currentMovie.getRating();
        if (stringRating.equalsIgnoreCase("G")) {
            img = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16277-28797ce.jpg?quality=90&webp=true&fit=584,471";
        } else if (stringRating.equalsIgnoreCase("PG")) {
            img = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16278-28797ce.jpg?quality=90&webp=true&fit=584,471";
        } else if (stringRating.equalsIgnoreCase("PG13")) {
            img = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16280-8d5bdb7.jpg?quality=90&webp=true&fit=320,320";
        } else if (stringRating.equalsIgnoreCase("NC16")) {
            img = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16281-8d5bdb7.jpg?quality=90&webp=true&fit=490,490";
        } else if (stringRating.equalsIgnoreCase("M18")) {
            img = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16282-05127b2.jpg?quality=90&webp=true&fit=300,300";
        } else {
            img = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16283-05127b2.jpg?quality=90&webp=true&fit=515,424";
        }
        Picasso.with(parent_context).load(img).into(ivRating);

        return rowView;
    }
}