package es.orcelis.orcelis.operations.cultivos;

/**
 * Created by yercko on 12/05/2017.
 */

public class CrimeChild {

    private String mDate;
    private boolean mSolved;

    public CrimeChild(String date, boolean solved) {
        mDate = date;
        mSolved = solved;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }
}