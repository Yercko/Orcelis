package es.orcelis.orcelis.operations.explotaciones;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;

import es.orcelis.orcelis.R;
import es.orcelis.orcelis.operations.cultivos.TripsFragment;
import es.orcelis.orcelis.operations.explotaciones.RecyclerView.ExplotacionItem;
import es.orcelis.orcelis.operations.explotaciones.RecyclerView.ExplotacionChild;
import es.orcelis.orcelis.operations.explotaciones.RecyclerView.ExplotacionExpandableAdapter;

public class ExplotacionFragment extends Fragment {
    public static String TAG = "ExplotacionFragment";
    public ExplotacionFragment() {
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
        View result = inflater.inflate(R.layout.fragment_explotaciones, container, false);

        RecyclerView mCrimeRecyclerView = (RecyclerView)result.findViewById(R.id.list_crimes);

        ExplotacionExpandableAdapter mExplotacionExpandableAdapter = new ExplotacionExpandableAdapter(getActivity(), generateCrimes());
        mExplotacionExpandableAdapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        mExplotacionExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        mExplotacionExpandableAdapter.setParentAndIconExpandOnClick(true);
        mCrimeRecyclerView.setAdapter(mExplotacionExpandableAdapter);


        return result;
    }


    private ArrayList<ParentObject> generateCrimes() {
        ArrayList<ParentObject> parentObjects = new ArrayList<>();

        ArrayList<Object> childcrimes = new ArrayList<>();

        childcrimes.add(new ExplotacionChild("Explotacicion1",true));
        childcrimes.add(new ExplotacionChild("Explotacicion1",true));
        childcrimes.add(new ExplotacionChild("Explotacicion1",true));
        childcrimes.add(new ExplotacionChild("Explotacicion1",true));
        childcrimes.add(new ExplotacionChild("Explotacicion1",true));


        ExplotacionItem padre = new ExplotacionItem();
        padre.setChildObjectList(childcrimes);
        ExplotacionItem padre2 = new ExplotacionItem();
        padre2.setChildObjectList(childcrimes);
        ExplotacionItem padre3 = new ExplotacionItem();
        padre3.setChildObjectList(childcrimes);
        parentObjects.add(padre);
        parentObjects.add(padre2);
        return parentObjects;
    }
}
