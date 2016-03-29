package kubik.roman.moviesdb.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.widget.Toast;

import kubik.roman.moviesdb.activities.MainActivity;

/**
 * Created by roman on 3/29/2016.
 */
public class BaseFragment extends Fragment {

    protected MainActivity mainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivity = (MainActivity) activity;
    }

    protected MainActivity getBaseActivity() {
        return mainActivity;
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
