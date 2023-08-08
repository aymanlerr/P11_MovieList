package sg.edu.rp.c346.id22015131.p11_movielist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class editMovie extends AppCompatActivity {

    EditText etId, etTitle, etGenre, etYear;
    AutoCompleteTextView ratingMenu;
    Button btnUpdate, btnDelete, btnCancel;
    Movie movie;
    String rating = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        Intent a = getIntent();
        movie = (Movie) a.getSerializableExtra("movie");

        etId = findViewById(R.id.etId);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnBack);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, RATINGS);
        ratingMenu = (AutoCompleteTextView) findViewById(R.id.ratingMenu);
        ratingMenu.setAdapter(adapter);
        int genrePos = 0;
        for (int i = 0; i < RATINGS.length; i++) {
            if (Objects.equals(movie.getRating(), RATINGS[i])) {
                genrePos = i;
            }
        }

        ratingMenu.setText(adapter.getItem(genrePos), false);
        ratingMenu.setOnItemClickListener((parent, view, position, id) -> {
            rating = parent.getItemAtPosition(position).toString();
        });

        etId.setText(movie.getId()+"");
        etId.setEnabled(false);
        etGenre.setText(movie.getGenre());
        etTitle.setText(movie.getTitle());
        etYear.setText(movie.getYear()+"");

        btnCancel.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Danger")
                    .setMessage("Are you sure you want to delete the movie\n" + movie.getTitle())
                    .setPositiveButton("Don't discard changes", null)
                    .setNegativeButton("Discard changes", (dialog, which) -> {
                        finish();
                    }).show();
        });

        btnUpdate.setOnClickListener(v -> {
            if (!isEmpty()) {
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());

                DBHelper db = new DBHelper(editMovie.this);
                movie.setMovieDetails(title, genre, year, rating);
                db.updateMovie(movie);
                db.close();
                finish();
                Intent intent = new Intent(editMovie.this, movieList.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Movie updated", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Danger")
                    .setMessage("Are you sure you want to delete the movie\n" + movie.getTitle())
                    .setPositiveButton("Cancel", null)
                    .setNegativeButton("Delete", (dialog, which) -> {
                        DBHelper db = new DBHelper(editMovie.this);
                        db.deleteMovie(movie.getId());
                        finish();
                        Intent intent = new Intent(editMovie.this, movieList.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Movie deleted", Toast.LENGTH_SHORT).show();
                    }).show();
        });
    }

    private static final String[] RATINGS = new String[] {
            "PG", "PG13", "NC16", "M18", "R21"
    };
    private boolean isEmpty() {
        String title = etTitle.getText().toString();
        String genre = etGenre.getText().toString();
        String year = etYear.getText().toString();
        if (title.isEmpty() && genre.isEmpty() && year.isEmpty()) {
            etTitle.setError("Enter title");
            etGenre.setError("Enter genre");
            etYear.setError("Enter year");
            return true;
        } else if (title.isEmpty() && genre.isEmpty()) {
            etTitle.setError("Enter title");
            etGenre.setError("Enter genre");
            return true;
        } else if (title.isEmpty() && year.isEmpty()) {
            etTitle.setError("Enter title");
            etYear.setError("Enter year");
            return true;
        }  else if (genre.isEmpty() && year.isEmpty()) {
            etGenre.setError("Enter genre");
            etYear.setError("Enter year");
            return true;
        }  else if (title.isEmpty()) {
            etTitle.setError("Enter title");
            return true;
        }  else if (genre.isEmpty()) {
            etGenre.setError("Enter genre");
            return true;
        }  else if (year.isEmpty()) {
            etYear.setError("Enter year");
            return true;
        } return false;
    }
}