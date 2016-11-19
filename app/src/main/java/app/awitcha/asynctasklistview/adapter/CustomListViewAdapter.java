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

import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;

import app.awitcha.asynctasklistview.R;
import app.awitcha.asynctasklistview.model.ImageModel;

/**
 * Created by Song-rit Maleerat on 19/11/2559.
 */
public class CustomListViewAdapter extends ArrayAdapter<ImageModel> {

    private FragmentActivity mActivity;
    private List<ImageModel> mImageModels;
    private LayoutInflater mInflater;
    private ViewHolder mViewHolder;

//    public CustomListViewAdapter(List<ImageModel> listItem, FragmentActivity mActivity) {
//        super();
//        this.listItem = listItem;
//        this.mActivity = mActivity;
//    }

//    public CustomListViewAdapter(FragmentActivity mActivity) {
////        super(mActivity.getApplicationContext());
//        this.listItem = listItem;
//        this.mActivity = mActivity;
//        mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }

    public CustomListViewAdapter(FragmentActivity activity, int resource, List<ImageModel> imageModels) {
        super(activity, resource);
        this.mActivity = activity;
        this.mImageModels = imageModels;

    }

    @Override
    public int getCount() {
        return mImageModels.size();
    }

    @Override
    public ImageModel getItem(int position) {
        return mImageModels.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_image, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            mViewHolder.textName = (TextView) convertView.findViewById(R.id.text_name);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        ImageModel item = getItem(position);

        mViewHolder.textName.setText(item.getName());

        Glide.with(mActivity)
                .load(item.getImage())
                .into(mViewHolder.image);

        return convertView;
    }


    private static class ViewHolder {

        TextView textName;
        ImageView image;
    }

    @Override
    public void add(ImageModel imageModel) {
        super.add(imageModel);
        mImageModels.add(imageModel);
    }
}
