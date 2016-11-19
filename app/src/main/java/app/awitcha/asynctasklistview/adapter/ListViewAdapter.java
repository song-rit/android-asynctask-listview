package app.awitcha.asynctasklistview.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Song-rit Maleerat on 19/11/2559.
 */
public class ListViewAdapter extends BaseAdapter {

    private FragmentActivity mActivity;
    private List<String> listItem;
    private LayoutInflater mInflater;
    private ViewHolder mViewHolder;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    private static class ViewHolder {

    }
}
