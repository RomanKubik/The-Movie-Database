package kubik.roman.moviesdb.fragments;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import kubik.roman.moviesdb.activities.MainActivity;

/**
 * Created by roman on 3/29/2016.
 */
public class BaseFragment extends Fragment {

    protected MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }

    protected MainActivity getBaseActivity() {
        return mainActivity;
    }

    protected void navigateTo(Fragment frg, boolean isAddToBackStack) {
        getBaseActivity().loadFragment(frg);
    }

    protected void navigateTo(Fragment frg) {
        getBaseActivity().loadFragment(frg);
    }

    protected void showToast(String msg) {
        buildToast(msg);
    }

    protected void showToast(int msgID) {
        buildToast(getResString(msgID));
    }

    protected String getResString(int id) {
        return mainActivity.getResources().getString(id);
    }

    private void buildToast(String msg) {
        Toast.makeText(mainActivity, msg, Toast.LENGTH_SHORT).show();
    }

}
