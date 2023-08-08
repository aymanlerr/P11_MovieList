package sg.edu.rp.c346.id22015131.p11_movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    TextInputEditText inputTitle, inputGenre, inputYear;
    AutoCompleteTextView ratingMenu;
    Button btnInsert, btnShowList;

    String rating = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTitle = findViewById(R.id.inputTitle);
        inputGenre = findViewById(R.id.inputGenre);
        inputYear = findViewById(R.id.inputyear);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, RATINGS);
        ratingMenu = (AutoCompleteTextView) findViewById(R.id.ratingMenu);
        ratingMenu.setAdapter(adapter);
        ratingMenu.setOnItemClickListener((parent, view, position, id) -> {
            rating = parent.getItemAtPosition(position).toString();
        });

        btnInsert.setOnClickListener(v -> {
            if (!isEmpty()) {
                if (!Objects.equals(rating, "")) {
                    String title = Objects.requireNonNull(inputTitle.getText()).toString();
                    String genre = Objects.requireNonNull(inputGenre.getText()).toString();
                    int year = Integer.parseInt(Objects.requireNonNull(inputYear.getText()).toString());

                    DBHelper db = new DBHelper(MainActivity.this);
                    db.insertMovie(title, genre, year, rating);
                    inputTitle.getText().clear();
                    inputGenre.getText().clear();
                    inputYear.getText().clear();
                    ratingMenu.setText("" ,false);
                    Toast.makeText(MainActivity.this, "Movie inserted", Toast.LENGTH_SHORT).show();
                } else {
                    ratingMenu.setError("Enter rating");
                }
            }
        });

        btnShowList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, movieList.class);
            startActivity(intent);
        });

    }
    private static final String[] RATINGS = new String[] {
         "PG", "PG13", "NC16", "M18", "R21"
    };

    private boolean isEmpty() {
        String title = inputTitle.getText().toString();
        String genre = inputGenre.getText().toString();
        String year = inputYear.getText().toString();
        if (title.isEmpty() && genre.isEmpty() && year.isEmpty()) {
            inputTitle.setError("Enter title");
            inputGenre.setError("Enter genre");
            inputYear.setError("Enter year");
            return true;
        } else if (title.isEmpty() && genre.isEmpty()) {
            inputTitle.setError("Enter title");
            inputGenre.setError("Enter genre");
            return true;
        } else if (title.isEmpty() && year.isEmpty()) {
            inputTitle.setError("Enter title");
            inputYear.setError("Enter year");
            return true;
        }  else if (genre.isEmpty() && year.isEmpty()) {
            inputGenre.setError("Enter genre");
            inputYear.setError("Enter year");
            return true;
        }  else if (title.isEmpty()) {
            inputTitle.setError("Enter title");
            return true;
        }  else if (genre.isEmpty()) {
            inputGenre.setError("Enter genre");
            return true;
        }  else if (year.isEmpty()) {
            inputYear.setError("Enter year");
            return true;
        } return false;
    }

}
