package pipl.ynov.com.schoolpierre;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.VolleyError;

import java.util.List;

import pipl.ynov.com.schoolpierre.Adapters.SchoolListAdapter;
import pipl.ynov.com.schoolpierre.Interfaces.IVolleyCallback;
import pipl.ynov.com.schoolpierre.Managers.NetworkManager;
import pipl.ynov.com.schoolpierre.Models.School;

public class ListSchoolActivity extends AppCompatActivity {
    public ImageButton returnButton;
    private RecyclerView schoolListRecyclerView;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_school);
        setTitle("Liste des écoles");

        schoolListRecyclerView = findViewById(R.id.schools_list_recycleviews);

        returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(genericOnClickListener);

        NetworkManager.schoolList(this, new IVolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccessResponseList(final List<School> schoolList) {
                ListSchoolActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("LIST SCHOOL", schoolList.toString());
                        schoolListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        schoolListRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        adapter = new SchoolListAdapter(schoolList, ListSchoolActivity.this);
                        schoolListRecyclerView.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public void onSuccessResponse(String result) {

            }
        });

    }

    // OnClickListener for this view
    View.OnClickListener genericOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.return_button:
                    // Todo : drop the line not return
                    Intent intent = new Intent(ListSchoolActivity.this, MenuActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

}
