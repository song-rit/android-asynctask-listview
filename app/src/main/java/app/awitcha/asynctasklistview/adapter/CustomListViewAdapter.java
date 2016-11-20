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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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

        final ImageModel item = getItem(position);

        mViewHolder.textName.setText(item.getName());

//        Glide.with(mActivity)
//                .load(item.getImage())
//                .downloadOnly(1000, 1000);

        Glide.with(mActivity)
                .load(item.getImage())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Toast.makeText(mActivity, "Image Ok", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
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
