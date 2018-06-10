package pipl.ynov.com.schoolpierre;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ConfigActivity extends AppCompatActivity {
    public ImageButton returnButton;
    public ImageButton privateButton;
    public ImageButton publicButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(genericOnClickListener);

        privateButton = findViewById(R.id.privateButton);
        privateButton.setOnClickListener(genericOnClickListener);

        publicButton = findViewById(R.id.publicButton);
        publicButton.setOnClickListener(genericOnClickListener);

        String status = getSharedPreferences("pipl.ynov.com.schoolpierre", ConfigActivity.MODE_PRIVATE).getString("status", "");

        if(status == "private"){
            publicButton.setBackground(new ColorDrawable( ContextCompat.getColor(ConfigActivity.this, R.color.whiteColor)));
        }

        if(status == "public"){
            privateButton.setBackground(new ColorDrawable( ContextCompat.getColor(ConfigActivity.this, R.color.whiteColor)));
        }
    }

    View.OnClickListener genericOnClickListener = new View.OnClickListener() {
        @SuppressLint("NewApi")
        @Override
        public void onClick(View view) {
            SharedPreferences prefs = getSharedPreferences("pipl.ynov.com.schoolpierre", ConfigActivity.MODE_PRIVATE);
            switch (view.getId()) {
                case R.id.return_button:
                    Intent intent = new Intent(ConfigActivity.this, MenuActivity.class);
                    startActivity(intent);
                    break;
                case R.id.privateButton:
                    if(prefs.getString("status", "").equals("public")) {
                        privateButton.setBackground(new ColorDrawable( ContextCompat.getColor(ConfigActivity.this, R.color.orangeColor)));
                        prefs.edit()
                                .putString("status", "")
                                .apply();
                        return;
                    }

                    if(prefs.getString("status", "").equals("")) {
                        privateButton.setBackground(new ColorDrawable( ContextCompat.getColor(ConfigActivity.this, R.color.whiteColor)));
                        prefs.edit()
                                .putString("status", "public")
                                .apply();
                        return;
                    }

                    Toast.makeText(getBaseContext(), "Un filtre doit minimum doit être actif.", Toast.LENGTH_LONG);

                    break;
                case R.id.publicButton:
                    if(prefs.getString("status", "").equals("private")) {
                        publicButton.setBackground(new ColorDrawable( ContextCompat.getColor(ConfigActivity.this, R.color.orangeColor)));
                        prefs.edit()
                                .putString("status", "")
                                .apply();
                        return;
                    }

                    if(prefs.getString("status", "").equals("")) {
                        publicButton.setBackground(new ColorDrawable( ContextCompat.getColor(ConfigActivity.this, R.color.whiteColor)));
                        prefs.edit()
                                .putString("status", "private")
                                .apply();
                        return;
                    }

                    Toast.makeText(getBaseContext(), "Un filtre doit minimum doit être actif.", Toast.LENGTH_LONG);
                    break;
            }
        }
    };
}
