package pipl.ynov.com.schoolpierre.Services;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pipl.ynov.com.schoolpierre.Interfaces.IVolleyCallback;
import pipl.ynov.com.schoolpierre.Models.School;

public class SchoolService {

    public List<School> SchoolsList = new ArrayList<>();

    private WeakReference<Activity> activity;

    public SchoolService (Activity activity)  {
        this.activity = new WeakReference<Activity>(activity);
    }

    public void retrieveSchool (final Context context, final IVolleyCallback callback) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this.activity.get());

        StringRequest postRequest = new StringRequest(Request.Method.GET, "https://schoolz-api.herokuapp.com/api/v1/schools",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            SchoolsList = convertJSONtoListSchool(jsonResponse.getJSONArray("schools"));

                            callback.onSuccessResponse(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity.get(), "La récupération des écolese échoué", Toast.LENGTH_LONG).show();
                        callback.onErrorResponse(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String>  params = new HashMap<String, String>();
                String token = context.getSharedPreferences("pipl.ynov.com.schoolpierre", Context.MODE_PRIVATE).getString("auth_token", "");
                params.put("AUTHORIZATION",token);
                return params;
            }
        };
        queue.add(postRequest);

    }

    public void deleteSchool (int schoolId, final Context context, final IVolleyCallback callback) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this.activity.get());

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, "https://schoolz-api.herokuapp.com/api/v1/schools/" + Integer.toString(schoolId),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onErrorResponse(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                String token = context.getSharedPreferences("pipl.ynov.com.schoolpierre", Context.MODE_PRIVATE).getString("auth_token", "");
                params.put("AUTHORIZATION", token);
                return params;
            }
        };

        queue.add(stringRequest);
    }


    private static List<School> convertJSONtoListSchool(JSONArray arrayJSON) {
        List<School> schoolList = new ArrayList<>();

        for (int i = 0; i < arrayJSON.length(); i++) {
            try {
                JSONObject schoolJSON = (JSONObject) arrayJSON.get(i);

                schoolList.add(new School(
                        schoolJSON.getString("name"),
                        schoolJSON.getDouble("latitude"),
                        schoolJSON.getDouble("longitude"),
                        schoolJSON.getString("status"),
                        schoolJSON.getString("address"),
                        schoolJSON.getString("zip_code"),
                        schoolJSON.getString("city"),
                        schoolJSON.getString("opening_hours"),   //openings with my api
                        schoolJSON.getString("phone"),
                        schoolJSON.getString("email"),
                        schoolJSON.getInt("students_count")          //nb_student
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return schoolList;
    }

}
