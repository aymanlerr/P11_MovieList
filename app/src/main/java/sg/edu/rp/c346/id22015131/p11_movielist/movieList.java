package sg.edu.rp.c346.id22015131.p11_movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class movieList extends AppCompatActivity {

    Button btnAll, btnPg13, btnBack;
    ListView lv;
    ArrayList<Movie> movieList, filteredMovieList;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        btnAll = findViewById(R.id.btnAll);
        btnPg13 = findViewById(R.id.btnPg13);
        lv = findViewById(R.id.lv);
        movieList = new ArrayList<>();
        btnBack = findViewById(R.id.btnBack);

        filteredMovieList = new ArrayList<>();

        DBHelper db = new DBHelper(movieList.this);
        movieList = db.getMovies();
        db.close();

        adapter = new CustomAdapter(movieList.this, R.layout.row, movieList);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnBack.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(movieList.this, MainActivity.class);
            startActivity(intent);
        });

        btnAll.setOnClickListener(v -> {
            movieList = db.getMovies();
            adapter = new CustomAdapter(this, R.layout.row, movieList);
            db.close();
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });

        btnPg13.setOnClickListener(v -> {
            filteredMovieList = db.getFilteredMovies();
            adapter = new CustomAdapter(this, R.layout.row, filteredMovieList);
            db.close();
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });

        lv.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = movieList.get(position);
            Intent i = new Intent(movieList.this, editMovie.class);
            i.putExtra("movie", movie);
            startActivity(i);
        });

    }
}