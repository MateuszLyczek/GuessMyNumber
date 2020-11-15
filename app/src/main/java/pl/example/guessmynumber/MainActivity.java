package pl.example.guessmynumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static pl.example.guessmynumber.R.id.editText;

public class MainActivity extends AppCompatActivity {
    int mojNumer, numerUzytkownika, prob = 0, liczby;  //deklaracje zmiennych
    SharedPreferences sharedPreferences; // Zapis danych aplikacji
    SharedPreferences.Editor editor; //Zapis danych aplikacji
    public MediaPlayer mp1, mp2; // Odtwarzacz dzwięków
    private Switch sw1; // Przełącznik dzwieków

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random rand = new Random();   // Generator liczb
        mojNumer = rand.nextInt((100 - 0) + 1); // Generowanie liczby z zakresu 0-100
        prob = 0; //Ustawiamy licznik prób na 0
        sharedPreferences = getSharedPreferences("pl.example.guessmynumber", Context.MODE_PRIVATE); // Odpowiada za zapis danych użytkownika
        editor = sharedPreferences.edit(); //Edycja danych aplikacji
        int best = sharedPreferences.getInt("BEstScore", 100); //Pobiera najlepszy wynik z zapisanych danych
        TextView textViewy = (TextView) findViewById(R.id.textView2);
        textViewy.setText("Najlepszy wynika: " + best); //Odpowiada za wyświetlenie najlepszego wyniku
        mp1 = MediaPlayer.create(MainActivity.this, R.raw.fail); //Przypisanie dzwięku złego strzału
        mp2 = MediaPlayer.create(MainActivity.this, R.raw.complete); //Przypisanie dzwięku trafionej liczby
        sw1 = (Switch)findViewById(R.id.switch2); // Przełącznik dźwięków
    }

    public void newGame(View view) { // Początek nowej gry
        Random rand = new Random(); //Odpowiada za generowanie liczb
        mojNumer = rand.nextInt((100 - 0) + 1);  // Generujemy liczby z przedziału 0-100
        prob = 0; //Ilość prób w nowj rozgrywce ustawiamy na 0
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Prób " + prob); //Pokazuje ilość prób
        TextView textViewm = (TextView) findViewById(R.id.textView5);//Wyświetla informację o szukanej liczbie
        textViewm.setText("Szukana liczba jest większa niż --"); //Wyświetla informację o szukanej liczbie
        TextView textVieww = (TextView) findViewById(R.id.textView6);//Wyświetla informację o szukanej liczbie
        textVieww.setText("Szukana liczba jest większa niż --");//Wyświetla informację o szukanej liczbie
        TextView textViewy = (TextView) findViewById(R.id.textView2);//Wyświetla informację o szukanej liczbie
        int best = sharedPreferences.getInt("BEstScore", 100);//Pobiera najlepszy wynik
        textViewy.setText("Najlepszy wynika: " + best);//Wyświetla najlepszy wynik
        EditText editText = (EditText) findViewById(R.id.editText);//Wyszukuje pole
        editText.setEnabled(true); //Włącza możliwość edycji pola
    }

    public void pobierzLiczbe(View view) {
        prob++; //Dodajemy +1 do ilości prób
        EditText editText = (EditText) findViewById(R.id.editText);
        numerUzytkownika = Integer.parseInt(editText.getText().toString()); //Pobieramy numer jako int
        String wiadomosc = "";

        if (numerUzytkownika > 0 || numerUzytkownika < 100) { //jeli numer jest większy o 0 i mniejszy 0d 100
            if (numerUzytkownika < mojNumer) { // Jeśli numer gracza jest mniejszy niż wylosowany
                if (sw1.isChecked()) { //Sprawdzamy czy ma odtwarzać dźwięki
                    mp1.start(); //Jeśli tak - uruchamiamy dźwięk
                }
                wiadomosc = "Liczba jest większa niż podana"; // Przypisuje tekst do zmiennej wiadomosc
                TextView textVieww = (TextView) findViewById(R.id.textView5); // Szukamy pola textView5
                textVieww.setText("Szukana liczba jest większa niż " + editText.getText().toString()); //Podmieniamy wiadomość na aktualną
            } else if (numerUzytkownika > mojNumer) { // Jeśli numer jes większy niż szukany
                if (sw1.isChecked()) { //Sparwdzamy czy ma odtwarzać
                    mp1.start(); //Jesli tak, odtwarzamy
                }
                wiadomosc = "Liczba jest mniejsza niż podana"; // Wyświetla wiadomość
                TextView textViewm = (TextView) findViewById(R.id.textView6); //Wyszukuje pole textView 6
                textViewm.setText("Szukana liczba jest mniejsza niż " + editText.getText().toString()); // Ustawia aktualny tekst
            } else if (numerUzytkownika == mojNumer) { //Jeśli numer wpisany jest taki sam jak wylosowany
                if (sw1.isChecked()) { // Jeśli ma odtwarzać dźwięki
                    mp2.start(); // Odtwórz dźwięk
                }
                wiadomosc = "Gratulacje, zgadłeś :)"; // Ustawia wartość zmiennej wiadomość
                editText.setEnabled(false); // Blokuje możliwość edycji pola do wpisywania liczb
                TextView textVieww = (TextView) findViewById(R.id.textView6); // Wyszukuje pole textView6
                textVieww.setText(" ");  // Ustawia linijkę jako pustą
                TextView textViewm = (TextView) findViewById(R.id.textView5); // Wyszukuje pole textView5
                textViewm.setText("Gratulacje, szukana liczba to " + editText.getText().toString()); // Wypisuje wiadomość na ekranie
                if(prob < sharedPreferences.getInt("BEstScore", 100)){ //
                    editor.putInt("BEstScore", prob);                  // Jeśli ilość prób była mniejsza niż najmniejsza
                    editor.commit();                                   // zapisana w BEstScora - nadpisuje wartość
                }
            }

            TextView textView = (TextView) findViewById(R.id.textView); // Wyszukuje pole textView
            textView.setText("Prób: " + prob); //Wypisuje ilość prób
            editText.setText(""); // Ustawia pustą wartość
        } else {
            wiadomosc = "Proszę podać wartość z zakresu 0-100"; // Jeśli liczba nie mieści się w zakresie 0 - 100 pokazuje informację
        }
        Context context = getApplicationContext();                  //
        int duration = Toast.LENGTH_SHORT;                          // Odpowiada za wyświetlanie dodatkowych znikających wiadomości
        Toast toast = Toast.makeText(context, wiadomosc, duration); // na dole ekranu
        toast.show();                                               //
    }
}
