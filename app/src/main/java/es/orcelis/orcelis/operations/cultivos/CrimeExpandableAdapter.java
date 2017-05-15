package es.orcelis.orcelis.operations.cultivos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import es.orcelis.orcelis.R;

/**
 * Created by yercko on 12/05/2017.
 */


public class CrimeExpandableAdapter extends ExpandableRecyclerAdapter<CrimeParentViewHolder, CrimeChildViewHolder> {
    private LayoutInflater mInflater;

    public CrimeExpandableAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CrimeParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.list_item_crime_parent, viewGroup, false);
        return new CrimeParentViewHolder(view);
    }

    @Override
    public CrimeChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.list_item_crime_child, viewGroup, false);
        Animation animationUp = AnimationUtils.loadAnimation(viewGroup.getContext(), R.anim.slide_down);
        view.setAnimation(animationUp);
        return new CrimeChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(CrimeParentViewHolder crimeParentViewHolder, int i, Object parentObject) {
        Crime crime = (Crime) parentObject;
        crimeParentViewHolder.mCrimeTitleTextView.setText(crime.getTitle());
    }

    @Override
    public void onBindChildViewHolder(CrimeChildViewHolder crimeChildViewHolder, int i, Object childObject) {
        CrimeChild crimeChild = (CrimeChild) childObject;
        crimeChildViewHolder.mCrimeDateText.setText(crimeChild.getmDate().toString());
    }
}
