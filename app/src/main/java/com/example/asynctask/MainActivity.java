package com.example.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ProgressBar pb;
    TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi ProgressBar dan TextView
        pb = findViewById(R.id.pb);
        tvProgress = findViewById(R.id.tvProgress);

        // Set padding sesuai sistem
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Menambahkan OnClickListener ke Button
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(view);
            }
        });
    }

    // Method untuk memulai proses AsyncTask
    public void go(View view) {
        // Set progress ke 0 setiap kali tombol ditekan
        pb.setProgress(0);
        tvProgress.setText("Memulai proses...");
        new ProgressSet().execute();
    }

    // AsyncTask untuk mengatur progres
    public class ProgressSet extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            for (int i = 0; i <= 100; i++) {
                SystemClock.sleep(50); // Memperlambat proses untuk melihat progres
                publishProgress(i); // Memperbarui progress
            }
            return "Completed";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setProgress(values[0]);
            tvProgress.setText("Progress: " + values[0] + "%"); // Update TextView dengan nilai progres
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            tvProgress.setText(result); // Menampilkan hasil akhir di TextView
        }
    }
}
