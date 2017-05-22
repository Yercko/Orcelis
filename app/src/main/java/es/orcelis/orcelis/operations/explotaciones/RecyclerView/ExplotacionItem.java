package es.orcelis.orcelis.operations.explotaciones.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by yercko on 12/05/2017.
 */

public class ExplotacionItem implements ParentObject {

    private String title;
    private List<Object> mChildrenList;

    public ExplotacionItem(){
    }

    public ExplotacionItem(String title, List<Object> mChildrenList) {
        this.title = title;
        this.mChildrenList = mChildrenList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Object> getmChildrenList() {
        return mChildrenList;
    }

    public void setmChildrenList(List<Object> mChildrenList) {
        this.mChildrenList = mChildrenList;
    }

    @Override
    public List<Object> getChildObjectList() {
        return mChildrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mChildrenList = list;
    }
}