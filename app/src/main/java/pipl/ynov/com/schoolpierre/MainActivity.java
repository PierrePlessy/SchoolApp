package pipl.ynov.com.schoolpierre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.List;

import pipl.ynov.com.schoolpierre.Interfaces.IVolleyCallback;
import pipl.ynov.com.schoolpierre.Managers.NetworkManager;
import pipl.ynov.com.schoolpierre.Models.School;
import pipl.ynov.com.schoolpierre.Models.User;

public class MainActivity extends AppCompatActivity {
    TextView login;
    TextView pwd;
    CheckBox box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Identification");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        login = findViewById(R.id.textName);
        pwd = findViewById(R.id.textPassword);
        box = findViewById(R.id.checkBoxRemember);

        if (!TextUtils.isEmpty(prefs.getString("login", "default")) && !TextUtils.isEmpty(prefs.getString("password", "default"))) {
            login.setText(prefs.getString("login", ""));
            pwd.setText(prefs.getString("password", ""));
        }

        Button send = findViewById(R.id.buttonSend);
        Button empty = findViewById(R.id.buttonEmpty);

        send.setOnClickListener(genericOnClickListener);
        empty.setOnClickListener(genericOnClickListener);

    }

    final View.OnClickListener genericOnClickListener = new View.OnClickListener(){
        public void  onClick(final View v){
            switch(v.getId()){
                case R.id.buttonSend:
                    if(TextUtils.isEmpty(login.getText())) {
                        login.setError("Ce champs est requis");
                    }

                    if(TextUtils.isEmpty(pwd.getText())) {
                        pwd.setError("Ce champs est requis");
                    }

                    if(!TextUtils.isEmpty(login.getText()) && !TextUtils.isEmpty(pwd.getText())) {
                        Boolean checkBox = box.isChecked();
                        //regarde si le boutton est cocher et créer une ref le cas échéant
                        if(checkBox) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            prefs.edit()
                                    .putString("login", login.getText().toString())
                                    .putString("password", pwd.getText().toString())
                                    .apply();
                        }
                        else {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            prefs.edit()
                                    .remove("login")
                                    .remove("password")
                                    .apply();
                        }

                        login();
                    }
                    break;
                case R.id.buttonEmpty:
                    login.setText(null);
                    pwd.setText(null);
                    break;
                default:
                    break;
            }
        }
    };

    private void login() {
        User user = new User(login.getText().toString(), pwd.getText().toString());
        NetworkManager.authentication(user, this, new IVolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
//                Log.i("Success", result);
                try {
                    JSONObject response = new JSONObject(result);
                    SharedPreferences prefs = getSharedPreferences("pipl.ynov.com.schoolpierre", MODE_PRIVATE);
                    prefs.edit()
                            .putString("auth_token", response.getString("auth_token"))
                            .putString("status", "")
                            .apply();
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                }catch (Exception err) {
                    Log.e("Error", err.getMessage());
                    Toast.makeText(getBaseContext(), "Un probleme est survenu, veillez réessayer", Toast.LENGTH_LONG);
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(getBaseContext(), "Utilisateur non authorisé", Toast.LENGTH_LONG);
            }

            public void onSuccessResponseList(List<School> result) {

            }
        });
    }
}
