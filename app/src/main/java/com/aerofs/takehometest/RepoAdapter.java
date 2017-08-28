package com.aerofs.takehometest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Larry Osakwe on 8/26/2017.
 */

/**
 * A {@link RepoAdapter} creates a list item layout for each repository
 */

public class RepoAdapter extends ArrayAdapter<Repo> {

    private Repo currentRepo;

    public RepoAdapter(Context context, List<Repo> repos) {
        super(context, 0, repos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,
                    parent, false);
        }

        currentRepo = getItem(position);

        TextView repoName = listItemView.findViewById(R.id.repo_name);
        repoName.setText(currentRepo.getRepoName());

        TextView forkCount = listItemView.findViewById(R.id.fork_count);
        forkCount.setText(currentRepo.getForkCount() + "");

        TextView language = listItemView.findViewById(R.id.language);
        if (currentRepo.getLanguage() == null || currentRepo.getLanguage().equals("null")) {
            language.setText("");
        } else {
            language.setText(currentRepo.getLanguage());
        }


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat newFormat = new SimpleDateFormat("MMM d, yyyy");
        String oldDate = currentRepo.getLastUpdated();
        Date date = null;
        try {
            date = formatter.parse(oldDate.substring(0, 20));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        TextView dateUpdated = listItemView.findViewById(R.id.date_last_updated);
        dateUpdated.setText(newFormat.format(date));

        return listItemView;


    }


}
