package app.awitcha.asynctasklistview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AsyncTaskListViewFragment extends Fragment {

    private ListView mListView;
    private ProgressBar mProgressBar;

    private List<String> mStudents;


    public static AsyncTaskListViewFragment newInstance() {
        AsyncTaskListViewFragment fragment = new AsyncTaskListViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_async_task_list_view, container, false);

        infixView(rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initStudentList();
        displayListView();
    }

    private void displayListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());
        mListView.setAdapter(adapter);
        new MyTask().execute();
    }

    private void initStudentList() {
        mStudents = new ArrayList<>();
        mStudents.add("A");
        mStudents.add("B");
        mStudents.add("C");
        mStudents.add("D");
        mStudents.add("E");
        mStudents.add("F");
        mStudents.add("G");
        mStudents.add("H");
        mStudents.add("I");
    }

    private class MyTask extends AsyncTask<Void, String, String> {

        ArrayAdapter<String> adapter;
        int count = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            adapter = (ArrayAdapter<String>) mListView.getAdapter();

            mProgressBar.setProgress(count);
            mProgressBar.setMax(mStudents.size());
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {

            for (String student : mStudents) {
                publishProgress(student);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "OK";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            adapter.add(values[0]);

            count++;
            mProgressBar.setProgress(count);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void infixView(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
    }
}
