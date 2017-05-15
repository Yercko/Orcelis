package es.orcelis.orcelis.operations.cultivos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;

import es.orcelis.orcelis.R;

public class TripsFragment extends Fragment {
    public static String TAG = "TripsFragment";
    public TripsFragment() {
    }
    public static TripsFragment newInstance() {
        TripsFragment fragment = new TripsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_trips, container, false);

        RecyclerView mCrimeRecyclerView = (RecyclerView)result.findViewById(R.id.list_crimes);

        CrimeExpandableAdapter mCrimeExpandableAdapter = new CrimeExpandableAdapter(getActivity(), generateCrimes());
        mCrimeExpandableAdapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        mCrimeExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        mCrimeExpandableAdapter.setParentAndIconExpandOnClick(true);
        mCrimeRecyclerView.setAdapter(mCrimeExpandableAdapter);

        return result;
    }


    private ArrayList<ParentObject> generateCrimes() {
        ArrayList<ParentObject> parentObjects = new ArrayList<>();

        ArrayList<Object> childcrimes = new ArrayList<>();

        childcrimes.add(new CrimeChild("fecha1",true));
        childcrimes.add(new CrimeChild("fecha1",true));
        childcrimes.add(new CrimeChild("fecha1",true));
        childcrimes.add(new CrimeChild("fecha1",true));
        childcrimes.add(new CrimeChild("fecha1",true));


        Crime padre = new Crime();
        padre.setChildObjectList(childcrimes);
        Crime padre2 = new Crime();
        padre2.setChildObjectList(childcrimes);
        parentObjects.add(padre);
        parentObjects.add(padre2);
        return parentObjects;
    }
}
