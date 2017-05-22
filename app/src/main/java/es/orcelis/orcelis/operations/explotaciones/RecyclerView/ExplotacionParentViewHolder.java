package es.orcelis.orcelis.operations.explotaciones.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import es.orcelis.orcelis.R;

/**
 * Created by yercko on 12/05/2017.
 */

public class ExplotacionParentViewHolder extends ParentViewHolder {

    public TextView mCrimeTitleTextView;
    public ImageView mParentDropDownArrow;

    public ExplotacionParentViewHolder(View itemView) {
        super(itemView);

        mCrimeTitleTextView = (TextView) itemView.findViewById(R.id.parent_list_item_crime_title_text_view);
        mParentDropDownArrow = (ImageView) itemView.findViewById(R.id.parent_list_item_expand_arrow);
    }
}