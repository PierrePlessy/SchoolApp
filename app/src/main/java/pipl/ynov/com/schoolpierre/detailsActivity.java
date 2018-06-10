package pipl.ynov.com.schoolpierre;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.VolleyError;

import java.util.List;

import pipl.ynov.com.schoolpierre.Interfaces.IVolleyCallback;
import pipl.ynov.com.schoolpierre.Managers.NetworkManager;
import pipl.ynov.com.schoolpierre.Models.School;

public class detailsActivity extends AppCompatActivity {
    public ImageButton returnButton;
    public ImageButton listButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(genericOnClickListener);
        listButton = findViewById(R.id.list_button);
        listButton.setOnClickListener(genericOnClickListener);

        NetworkManager.schoolDetails(this, new IVolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccessResponseList(final List<School> schoolList) {

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public void onSuccessResponse(String result) {

            }
        });
    }

    View.OnClickListener genericOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.return_button:
                    // Go to Menu
                    Intent intent = new Intent(detailsActivity.this, MenuActivity.class);
                    startActivity(intent);
                    break;
                case R.id.list_button:
                    // Go to list School
                    intent = new Intent(detailsActivity.this, ListSchoolActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
