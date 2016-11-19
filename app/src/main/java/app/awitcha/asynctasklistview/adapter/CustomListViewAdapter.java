package app.awitcha.asynctasklistview.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.awitcha.asynctasklistview.R;
import app.awitcha.asynctasklistview.model.ImageModel;

/**
 * Created by Song-rit Maleerat on 19/11/2559.
 */
public class CustomListViewAdapter extends BaseAdapter {

    private FragmentActivity mActivity;
    private List<ImageModel> listItem;
    private LayoutInflater mInflater;
    private ViewHolder mViewHolder;

    public CustomListViewAdapter(List<ImageModel> listItem, FragmentActivity mActivity) {
        this.listItem = listItem;
        this.mActivity = mActivity;
        mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomListViewAdapter(FragmentActivity mActivity) {
        this.listItem = listItem;
        this.mActivity = mActivity;
        mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

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
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_image, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            mViewHolder.textName = (TextView) convertView.findViewById(R.id.text_name);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    public static class ViewHolder {

        TextView textName;
        ImageView image;
    }
}
