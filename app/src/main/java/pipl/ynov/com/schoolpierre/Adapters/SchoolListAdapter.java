package pipl.ynov.com.schoolpierre.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.List;

import pipl.ynov.com.schoolpierre.Interfaces.IVolleyCallback;
import pipl.ynov.com.schoolpierre.MapsActivity;
import pipl.ynov.com.schoolpierre.MenuActivity;
import pipl.ynov.com.schoolpierre.Models.School;
import pipl.ynov.com.schoolpierre.R;
import pipl.ynov.com.schoolpierre.Services.SchoolService;

public class SchoolListAdapter extends RecyclerView.Adapter<SchoolViewHolder> {

    private List<School> schoolsList;
    private Activity activity;
    private SchoolService schoolService;
    private School school;


    public SchoolListAdapter(List<School> schoolsList, Activity activity) {
        this.schoolsList = schoolsList;
        this.activity = activity;
        schoolService = new SchoolService(activity);
    }

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_school_list, parent, false);

        return new SchoolViewHolder(itemView);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {
        school = schoolsList.get(position);

        holder.name.setText(school.getName());
        holder.address.setText(school.getAddress());
        holder.nb_student.setText(new StringBuilder().append(String.valueOf(school.getNb_student())).append(" élèves").toString());

        holder.mapButton.setOnClickListener(genericOnClickListener);
        holder.deleteButton.setOnClickListener(genericOnClickListener);

        if (school.getNb_student() <= 50) {
            holder.background.setBackground(new ColorDrawable(ContextCompat.getColor(activity, R.color.redColor)));
            holder.thumb.setImageResource(R.mipmap.koicone);
        } else if (school.getNb_student() > 50 && school.getNb_student() < 200) {
            holder.background.setBackground(new ColorDrawable( ContextCompat.getColor(activity, R.color.orangeColor)));
            holder.thumb.setImageResource(R.mipmap.okicone);
        } else {
            holder.background.setBackground(new ColorDrawable(ContextCompat.getColor(activity, R.color.greenColor)));
            holder.thumb.setImageResource(R.mipmap.okicone);
        }

    }


    @Override
    public int getItemCount() {
        return schoolsList.size();
    }

    private View.OnClickListener genericOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mapButton:
                    Intent intent = new Intent(activity, MapsActivity.class);
                    activity.startActivity(intent);
                    break;
                case R.id.deleteSchoolButton:
                    schoolService.deleteSchool(school.getId(), activity, new IVolleyCallback() {
                        @Override
                        public void onSuccessResponse(String result) {
                            Toast.makeText(activity, "Suppression réussi ! ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(activity, MenuActivity.class);
                            activity.startActivity(intent);
                        }

                        @Override
                        public void onSuccessResponseList(List<School> result) {

                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(activity, "Suppression ratée... ", Toast.LENGTH_LONG).show();
                        }
                    });
                    break;

            }
        }
    };

}
