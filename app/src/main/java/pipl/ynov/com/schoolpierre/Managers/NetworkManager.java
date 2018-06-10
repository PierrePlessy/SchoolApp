package pipl.ynov.com.schoolpierre.Managers;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pipl.ynov.com.schoolpierre.Interfaces.IVolleyCallback;
import pipl.ynov.com.schoolpierre.Models.School;
import pipl.ynov.com.schoolpierre.Models.User;

public class NetworkManager {

    public static void authentication(final User user, Context context, final IVolleyCallback volleyCallback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://schoolz-api.herokuapp.com/api/v1/users/sign_in";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        volleyCallback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        volleyCallback.onErrorResponse(error);
                    }
                })
                    {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("email", user.getLogin());
                            params.put("password", user.getPassword());

                            return params;
                        }
                    };

        queue.add(stringRequest);
    }

    public static void createSchool(final School school, final Context context, final IVolleyCallback volleyCallback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String status = context.getSharedPreferences("pipl.ynov.com.schoolpierre", Context.MODE_PRIVATE).getString("status", "");

        String url = "";
        if (status.equals("")) {
            url = "http://schoolz-api.herokuapp.com/api/v1/schools";
        }
        else {
            url = "http://schoolz-api.herokuapp.com/api/v1/schools?status=" + status;
        }


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        volleyCallback.onSuccessResponse(response);
                        Log.i("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        volleyCallback.onErrorResponse(error);
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String>  params = new HashMap<>();
                String token = context.getSharedPreferences("pipl.ynov.com.schoolpierre", Context.MODE_PRIVATE).getString("auth_token", "");
                Log.i("TOKEN", token);
                params.put("AUTHORIZATION", token);

                return params;
            }

            @Override
            protected Map<String, String> getParams()
            {
//                HashMap<String, String>  params = new HashMap<String, String>();

                JSONObject params = new JSONObject();

                try {
                    params.put("name", school.getName());
                    params.put("latitude", String.valueOf(school.getLatitude()));
                    params.put("longitude", String.valueOf(school.getLongitude()));

                    if( !TextUtils.isEmpty(school.getAddress()))
                        params.put("address", school.getAddress());

                    if( !TextUtils.isEmpty(school.getZip_code()))
                        params.put("zip_code", school.getZip_code());

                    if( !TextUtils.isEmpty(school.getCity()))
                        params.put("city", school.getCity());

                    if( !TextUtils.isEmpty(school.getOpenings()))
                        params.put("openings", school.getOpenings());

                    if( !TextUtils.isEmpty(school.getPhone()))
                        params.put("phone", school.getPhone());

                    if( !TextUtils.isEmpty(school.getEmail()))
                        params.put("email", school.getEmail());

                    if( !TextUtils.isEmpty(String.valueOf(school.getNb_student())))
                        params.put("nb_student", String.valueOf(school.getNb_student()));

                    if( !TextUtils.isEmpty(school.getStatus()))
                        params.put("status", school.getStatus());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String, String>  paramsMap = new HashMap<>();
                paramsMap.put("school", params.toString());
                Log.i("SCHOOL MAP", paramsMap.toString());
                return paramsMap;
            }
        };


        queue.add(stringRequest);
    }

    public static void schoolList(final Context context, final IVolleyCallback volleyCallback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://schoolz-api.herokuapp.com/api/v1/schools";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE SCHOOL LIST", response);
                        List<School> schoolList = new ArrayList<>();
                        try {
                            JSONObject schools = new JSONObject(response);
                            schoolList = convertJSONtoListSchool(schools.getJSONArray("schools"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyCallback.onSuccessResponseList(schoolList);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        volleyCallback.onErrorResponse(error);
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String>  params = new HashMap<>();
                String token = context.getSharedPreferences("pipl.ynov.com.schoolpierre", Context.MODE_PRIVATE).getString("auth_token", "");
                Log.i("TOKEN", token);
                params.put("AUTHORIZATION", token);

                return params;
            }

            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String>  params = new HashMap<String, String>();
                params.put("status", context.getSharedPreferences("pipl.ynov.com.schoolpierre", Context.MODE_PRIVATE).getString("status", ""));
                return params;
            }
        };


        queue.add(stringRequest);
    }

    public static void schoolDetails(final Context context, final IVolleyCallback volleyCallback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://schoolz-api.herokuapp.com/api/v1/school/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE SCHOOL LIST", response);
                        List<School> schoolList = new ArrayList<>();
                        try {
                            JSONObject schools = new JSONObject(response);
                            schoolList = convertJSONtoListSchool(schools.getJSONArray("schools"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyCallback.onSuccessResponseList(schoolList);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        volleyCallback.onErrorResponse(error);
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String>  params = new HashMap<>();
                String token = context.getSharedPreferences("pipl.ynov.com.schoolpierre", Context.MODE_PRIVATE).getString("auth_token", "");
                Log.i("TOKEN", token);
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

                School school = new School(
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
                );
                school.setId(schoolJSON.getInt("id"));

                schoolList.add(school);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return schoolList;
    }


}
