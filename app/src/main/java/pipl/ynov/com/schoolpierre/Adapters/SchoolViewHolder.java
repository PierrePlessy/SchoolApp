package pipl.ynov.com.schoolpierre.Adapters;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import pipl.ynov.com.schoolpierre.R;

public class SchoolViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ConstraintLayout background;
    public ImageView thumb;
    public TextView address;
    public TextView nb_student;
    public TextView nbKm;
    public ImageButton mapButton;
    public ImageButton deleteButton;


    @SuppressLint("CutPasteId")
    public SchoolViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.schoolNameTextView);
        background = itemView.findViewById(R.id.background);
        thumb = itemView.findViewById(R.id.thumbTextView);
        address = itemView.findViewById(R.id.schoolAddressTextView);
        nb_student = itemView.findViewById(R.id.nbStudentTextView);
        nbKm = itemView.findViewById(R.id.kmTextView);
        mapButton = itemView.findViewById(R.id.mapButton);
        deleteButton = itemView.findViewById(R.id.deleteSchoolButton);

    }

}
