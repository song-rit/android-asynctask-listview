package app.awitcha.asynctasklistview;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import app.awitcha.asynctasklistview.adapter.CustomListViewAdapter;
import app.awitcha.asynctasklistview.model.ImageModel;
import app.awitcha.asynctasklistview.model.MyImageModel;
import app.awitcha.asynctasklistview.utill.CheckNetworkConnection;
import app.awitcha.asynctasklistview.utill.EventControl;
import app.awitcha.asynctasklistview.utill.OkHttpRequest;
import okhttp3.OkHttpClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class GlideAsyncTaskListViewFragment extends Fragment {

    private ListView mListView;
    private ProgressBar mProgressBar;

    private FragmentActivity mActivity;

    private EventControl mEventCtrl;

    private MyImageModel mMyImageModel;

    public GlideAsyncTaskListViewFragment() {
        // Required empty public constructor
    }

    public static GlideAsyncTaskListViewFragment newInstance() {
        GlideAsyncTaskListViewFragment fragment = new GlideAsyncTaskListViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_glide_async_task_list_view, container, false);

        infixView(rootView);

        mEventCtrl = new EventControl(mActivity);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mMyImageModel == null) {
            sendRequest();
        }
    }

    private void sendRequest() {

        if (CheckNetworkConnection.isConnectionAvailable(mActivity)) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();

                    OkHttpRequest okHttp = new OkHttpRequest();
                    Gson gson = new Gson();

                    String jsonUrl = "https://docs.google.com/uc?authuser=0&id=0B_IchW5V8GCWclBBYlJRbzlBeU0&export=download";
                    try {
                        Message myImageMsg = okHttp.HttpGetMessage(jsonUrl);

                        if (myImageMsg.what == 1) {
                            final String myImageJson = (String) myImageMsg.obj;
                            mMyImageModel = gson.fromJson(myImageJson, MyImageModel.class);

                            if (mMyImageModel.isStatus()) {
                                updateViewOk();
                            } else {
                                updateViewNotFound();
                            }
                        } else {
                            updateViewFail();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                private void updateViewOk() {
                    displayListView();
                    mEventCtrl.ToastShort("Ok");
                }
            };

            thread.start();
        } else {
            mEventCtrl.ToastShort("No connection");
        }
    }

    private void displayListView() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CustomListViewAdapter adapter = new CustomListViewAdapter(mActivity, R.layout.list_item_image, new ArrayList<ImageModel>());
                mListView.setAdapter(adapter);
                new MyTask().execute();
            }
        });
    }

    private void updateViewFail() {
        mEventCtrl.ToastShort("Not found");
    }

    private void updateViewNotFound() {
        mEventCtrl.ToastShort("Not found");
    }

    private class MyTask extends AsyncTask<Void, ImageModel, Message> {

        CustomListViewAdapter adapter;
        Message message;
        Handler addItemHandler;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            adapter = (CustomListViewAdapter) mListView.getAdapter();
            Message message = new Message();

        }

        @Override
        protected Message doInBackground(Void... voids) {

            for (final ImageModel image : mMyImageModel.getImages()) {

                try {

                    FutureTarget<GlideDrawable> future  = (FutureTarget<GlideDrawable>) Glide.with(mActivity)
                            .load(image.getImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    mEventCtrl.ToastShort("Image Ok");
                                    publishProgress(image);
                                    return false;
                                }
                            })
                            .into(500, 500);


                    GlideDrawable cacheFile = future.get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }



            }
            return message;
        }

        @Override
        protected void onProgressUpdate(ImageModel... imageModels) {
            super.onProgressUpdate(imageModels);

            final ImageModel item = imageModels[0];
            adapter.add(item);
        }

        @Override
        protected void onPostExecute(Message msg) {
            super.onPostExecute(msg);
        }
    }

    private void infixView(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
    }

}
