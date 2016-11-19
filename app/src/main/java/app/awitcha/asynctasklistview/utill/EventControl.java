package app.awitcha.asynctasklistview.utill;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by Nirvana on 20/11/2559.
 */
public class EventControl {

    FragmentActivity mActivity;

    public EventControl(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void ToastShort(final String msg) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
