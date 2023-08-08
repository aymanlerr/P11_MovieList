package sg.edu.rp.c346.id22015131.p11_movielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
            img = "https://imdaonline.imda.gov.sg/classification/images/Rating_G.png";
        } else if (stringRating.equalsIgnoreCase("PG")) {
            img = "https://imdaonline.imda.gov.sg/classification/images/Rating_PG.png";
        } else if (stringRating.equalsIgnoreCase("PG13")) {
            img = "https://imdaonline.imda.gov.sg/classification/images/Rating_PG13.png";
        } else if (stringRating.equalsIgnoreCase("NC16")) {
            img = "https://imdaonline.imda.gov.sg/classification/images/Rating_NC16.png";
        } else if (stringRating.equalsIgnoreCase("M18")) {
            img = "https://imdaonline.imda.gov.sg/classification/images/Rating_M18.png";
        } else {
            img = "https://imdaonline.imda.gov.sg/classification/images/Rating_R21.png";
        }
        Glide.with(parent_context).load(img).fitCenter().into(ivRating);

        return rowView;
    }
}