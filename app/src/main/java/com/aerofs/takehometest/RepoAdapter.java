package com.aerofs.takehometest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Larry Osakwe on 8/26/2017.
 */

public class RepoAdapter extends ArrayAdapter<Repo> {


    public RepoAdapter(Context context, List<Repo> repos) {
        super(context, 0, repos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.)
        }
    }


}
