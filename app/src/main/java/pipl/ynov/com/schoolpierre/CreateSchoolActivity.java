package pipl.ynov.com.schoolpierre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.List;

import pipl.ynov.com.schoolpierre.Interfaces.IVolleyCallback;
import pipl.ynov.com.schoolpierre.Managers.NetworkManager;
import pipl.ynov.com.schoolpierre.Models.School;

public class CreateSchoolActivity extends AppCompatActivity {
    public ImageButton returnButton;
    private TextView name;
    private TextView address;
    private TextView zip_code;
    private TextView city;
    private TextView openings;
    private TextView phone;
    private TextView email;
    private TextView latitude;
    private TextView longitude;
    private TextView nb_student;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_school);
        setTitle("Création");

        name = findViewById(R.id.nameEditText);
        address = findViewById(R.id.addressEditText);
        zip_code = findViewById(R.id.zipcodeEditText);
        city = findViewById(R.id.cityEditText);
        openings = findViewById(R.id.openingsEditText);
        phone = findViewById(R.id.phoneEditText);
        email = findViewById(R.id.emailEditText);
        latitude = findViewById(R.id.latitudeEditText);
        longitude = findViewById(R.id.longitudeEditText);
        nb_student = findViewById(R.id.nb_studentEditText);
        status = findViewById(R.id.statusEditText);

        Button validateButton = findViewById(R.id.validateCreateButton);
        Button cancelButton = findViewById(R.id.cancelCreateButton);

        validateButton.setOnClickListener(genericOnClickListener);
        cancelButton.setOnClickListener(genericOnClickListener);

        returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(genericOnClickListener);
    }

    final View.OnClickListener genericOnClickListener = new View.OnClickListener(){
        public void  onClick(final View v){
            switch(v.getId()){
                case R.id.validateCreateButton:
                    if(TextUtils.isEmpty(name.getText())) {
                        name.setError("Ce champs est requis");
                    }

                    if(TextUtils.isEmpty(latitude.getText())) {
                        latitude.setError("Ce champs est requis");
                    }

                    if(TextUtils.isEmpty(longitude.getText())) {
                        longitude.setError("Ce champs est requis");
                    }

                    if(TextUtils.isEmpty(status.getText())) {
                        name.setError("Ce champs est requis");
                    }

                    if(!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(longitude.getText())
                            && !TextUtils.isEmpty(longitude.getText()) && !TextUtils.isEmpty(status.getText())) {
                        createSchool();
                    }
                    break;
                case R.id.cancelCreateButton:
                    Intent returnMenu = new Intent(CreateSchoolActivity.this, MenuActivity.class);
                    startActivity(returnMenu);
                    break;
                case R.id.return_button:
                    Intent intent = new Intent(CreateSchoolActivity.this, MenuActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    private void createSchool() {
        School school = new School(name.getText().toString(), Double.parseDouble(latitude.getText().toString()),
                Double.parseDouble(longitude.getText().toString()), status.getText().toString(),
                address.getText().toString(), zip_code.getText().toString(), city.getText().toString(),
                openings.getText().toString(), phone.getText().toString(), email.getText().toString(),
                Integer.parseInt(nb_student.getText().toString()));

        NetworkManager.createSchool(school, this, new IVolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.i("Success", result);
                try {
//                    JSONObject response = new JSONObject(result);
//                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CreateSchoolActivity.this);
//                    prefs.edit()
//                            .putString("token", response.getString("auth_token"))
//                            .apply();
                    Intent intent = new Intent(CreateSchoolActivity.this, MenuActivity.class);
                    startActivity(intent);
                }catch (Exception err) {
                    Log.e("Error", err.getMessage());
                    Toast.makeText(getBaseContext(), "Un probleme est survenu, veillez réessayer", Toast.LENGTH_LONG);
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Failed authentification");
                Toast.makeText(getBaseContext(), "no data permit", Toast.LENGTH_LONG);
            }

            public void onSuccessResponseList(List<School> result) {

            }
        });
    }
}
