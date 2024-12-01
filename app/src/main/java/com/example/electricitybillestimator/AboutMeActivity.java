package com.example.electricitybillestimator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // GitHub Button
        Button btnGithub = findViewById(R.id.btnGithub);
        btnGithub.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://github.com/rossara"));
            startActivity(intent);
        });

        // Redirect to Calculate Page Button
        Button btnToCalculate = findViewById(R.id.btnToCalculate);
        btnToCalculate.setOnClickListener(v -> {
            Intent intent = new Intent(AboutMeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
