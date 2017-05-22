package es.orcelis.orcelis.operations.explotaciones.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import es.orcelis.orcelis.R;

/**
 * Created by yercko on 12/05/2017.
 */

public class ExplotacionChildViewHolder extends ChildViewHolder {

    public TextView mCrimeDateText;
    //public CheckBox mCrimeSolvedCheckBox;

    public ExplotacionChildViewHolder(View itemView) {
        super(itemView);

        mCrimeDateText = (TextView) itemView.findViewById(R.id.child_list_item_crime_date_text_view);
       // mCrimeSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.child_list_item_crime_solved_check_box);
    }
}