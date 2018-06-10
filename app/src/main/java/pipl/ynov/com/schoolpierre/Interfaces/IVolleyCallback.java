package pipl.ynov.com.schoolpierre.Interfaces;

import com.android.volley.VolleyError;

import java.util.List;

import pipl.ynov.com.schoolpierre.Models.School;

public interface IVolleyCallback {
    void onSuccessResponse(String result);
    void onSuccessResponseList(List<School> result);
    void onErrorResponse(VolleyError error);
}
