package com.example.cinemhub.ricerca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cinemhub.R;
import com.example.cinemhub.ui.add_list.AddListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterHandler {
    private static final String TAG = "FilterHandler";
    private Fragment fragment;
    private Menu menu;
    private static final String DIGIT_PATTERN = "\\d+";
    private static final Pattern DIGIT_PATTERN_COMPILED = Pattern.compile(DIGIT_PATTERN);


    public FilterHandler(Menu menu, Fragment fragment){
        this.fragment = fragment;
        this.menu = menu;
    }

    public void implementFilter(int tipo) {
        LayoutInflater factory = LayoutInflater.from(fragment.getActivity());
        View textEntryView = factory.inflate(R.layout.filter_dialog, null);

        MenuItem filterMenuItem;
        if(tipo==1)
            filterMenuItem = menu.findItem(R.id.filter);
        else
            filterMenuItem = menu.findItem(R.id.filter1);

        Spinner spinnerCategroy = textEntryView.findViewById(R.id.spinner1);
        Spinner spinnerOrder = textEntryView.findViewById(R.id.spinner2);
        EditText editTextVote = textEntryView.findViewById(R.id.vote);
        EditText editTextYear = textEntryView.findViewById(R.id.year);

        spinnerList(spinnerCategroy, "Action", "Romance", "Thriller", "Animation");
        spinnerList(spinnerOrder, "Name", "Vote", "Popularity", "Year");

        editTextVote.setText("", TextView.BufferType.EDITABLE);
        editTextYear.setText("", TextView.BufferType.EDITABLE);

        AlertDialog.Builder alert = new AlertDialog.Builder(fragment.getActivity());

        filterMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                alert.setIcon(R.drawable.heart_on).setTitle(" Filter:").setView(textEntryView).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                Matcher matcher = DIGIT_PATTERN_COMPILED.matcher(editTextVote.getText().toString());
                                if(matcher.matches()) {
                                    int a1 = Integer.parseInt(editTextVote.getText().toString());
                                    if(a1<1 || a1>10)
                                        Log.d(TAG, "Qua facciamo spuntare un messaggio d'errore");
                                }

                                Matcher matcher2 = DIGIT_PATTERN_COMPILED.matcher(editTextYear.getText().toString());
                                if(matcher2.matches()) {
                                    int a2 = Integer.parseInt(editTextYear.getText().toString());
                                    if(a2<1900 || a2>2020)
                                        Log.d(TAG, "Qua facciamo spuntare un messaggio d'errore");
                                }

                                ViewGroup viewGroup = (ViewGroup) textEntryView.getParent();
                                viewGroup.removeView(textEntryView);

                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                ViewGroup viewGroup = (ViewGroup) textEntryView.getParent();
                                viewGroup.removeView(textEntryView);
                                Log.d(TAG, "Cancel");
                            }
                        });
                alert.show();
                return true;
            }
        });
    }


    void spinnerList(Spinner spinner, String s1, String s2, String s3, String s4) {
        List<String> categroyList = new ArrayList<>();

        categroyList.add(s1);
        categroyList.add(s2);
        categroyList.add(s3);
        categroyList.add(s4);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(fragment.getActivity(),
                android.R.layout.simple_spinner_item, categroyList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Enter");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}