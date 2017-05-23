package es.orcelis.orcelis.operations.explotaciones.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import es.orcelis.orcelis.R;
import es.orcelis.orcelis.operations.maps.MapsActivity;

/**
 * Created by yercko on 12/05/2017.
 */


public class ExplotacionExpandableAdapter extends ExpandableRecyclerAdapter<ExplotacionParentViewHolder, ExplotacionChildViewHolder> {
    private LayoutInflater mInflater;

    public ExplotacionExpandableAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ExplotacionParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.list_item_explotacion_parent, viewGroup, false);
        return new ExplotacionParentViewHolder(view);
    }

    @Override
    public ExplotacionChildViewHolder onCreateChildViewHolder(final ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.list_item_explotacion_child, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewGroup.getContext(), MapsActivity.class);
                viewGroup.getContext().startActivity(intent);
            }
        });
        return new ExplotacionChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(ExplotacionParentViewHolder explotacionParentViewHolder, int i, Object parentObject) {
        ExplotacionItem explotacionItem = (ExplotacionItem) parentObject;
        explotacionParentViewHolder.mCrimeTitleTextView.setText("5616");
    }

    @Override
    public void onBindChildViewHolder(ExplotacionChildViewHolder explotacionChildViewHolder, int i, Object childObject) {
        ExplotacionChild explotacionChild = (ExplotacionChild) childObject;
        explotacionChildViewHolder.mCrimeDateText.setText(explotacionChild.getmDate());
    }
}
