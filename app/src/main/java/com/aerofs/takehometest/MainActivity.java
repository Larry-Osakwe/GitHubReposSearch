package com.aerofs.takehometest;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Repo>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int REPO_LOADER_ID = 1;
    private static final String REPO_JSON_RESPONSE = "https://api.github.com/users";
    private RepoAdapter mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView repoListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        mAdapter = new RepoAdapter(this, new ArrayList<Repo>());

        repoListView.setEmptyView(mEmptyStateTextView);
        repoListView.setAdapter(mAdapter);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkOnline()) {
                    getLoaderManager().restartLoader(REPO_LOADER_ID, null, MainActivity.this);
                } else {
                    mEmptyStateTextView.setText("No internet connection.");
                    View loadSpinner = findViewById(R.id.loading_spinner);
                    loadSpinner.setVisibility(View.GONE);
                }
            }
        });

    }

    public static class RepoLoader extends AsyncTaskLoader<List<Repo>> {

        private String mUrl;

        public RepoLoader(Context context, String url) {
            super(context);
            this.mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public List<Repo> loadInBackground() {
            if (mUrl == null) {
                return null;
            }
            List<Repo> repos = QueryUtils.extractRepos(mUrl);
            return repos;
        }
    }


    private String getUserInput() {
        EditText userInput = (EditText) findViewById(R.id.editText);
        return userInput.getText().toString();
    }

    @Override
    public Loader<List<Repo>> onCreateLoader(int i, Bundle bundle) {

        Uri baseUri = Uri.parse(REPO_JSON_RESPONSE);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendPath(getUserInput());
        uriBuilder.appendPath("repos");


        return new RepoLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Repo>> loader, List<Repo> repos) {
        mEmptyStateTextView.setText("No repositories found");
        mAdapter.clear();

        if(repos != null && !repos.isEmpty()) {
            mAdapter.addAll(repos);
        }

        ProgressBar loadSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
        loadSpinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Repo>> loader) {
        mAdapter.clear();
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }

}
