package pipl.ynov.com.schoolpierre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {
    public ImageButton mapButton;
    public ImageButton listSchoolButton;
    public ImageButton createSchoolButton;
    public ImageButton configButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Menu");

        mapButton = findViewById(R.id.mapButton);
        listSchoolButton = findViewById(R.id.listSchoolButton);
        createSchoolButton = findViewById(R.id.createSchoolButton);
        configButton = findViewById(R.id.configButton);

        mapButton.setOnClickListener(genericOnClickListener);
        listSchoolButton.setOnClickListener(genericOnClickListener);
        createSchoolButton.setOnClickListener(genericOnClickListener);
        configButton.setOnClickListener(genericOnClickListener);
    }

    final View.OnClickListener genericOnClickListener = new View.OnClickListener(){
        public void  onClick(final View v){
            switch(v.getId()){
                case R.id.mapButton:
                    Intent intentMap = new Intent(MenuActivity.this, MapsActivity.class);
                    startActivity(intentMap);
                    break;
                case R.id.listSchoolButton:
                    Intent intentList = new Intent(MenuActivity.this, ListSchoolActivity.class);
                    startActivity(intentList);
                    break;
                case R.id.createSchoolButton:
                    Intent intentCreate = new Intent(MenuActivity.this, CreateSchoolActivity.class);
                    startActivity(intentCreate);
                    break;
                case R.id.configButton:
                    Intent intentConfig = new Intent(MenuActivity.this, ConfigActivity.class);
                    startActivity(intentConfig);
                    break;
                default:
                    break;
            }
        }
    };
}
