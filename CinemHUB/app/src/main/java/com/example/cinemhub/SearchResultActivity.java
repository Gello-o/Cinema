package com.example.cinemhub;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.os.Bundle;
import java.io.Serializable;
/*
public class SearchResultsActivity extends Activity {
        private ListView listResults;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.search);

            // get the action bar
            ActionBar actionBar = getActionBar();

            // Enabling Back navigation on Action Bar icon
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);

            listResults = (ListView) findViewById(R.id.listResults);

            handleIntent(getIntent());
        }

        @Override
        protected void onNewIntent(Intent intent) {
            setIntent(intent);
            handleIntent(intent);
        }

        /**
         * Handling intent data
         */

/*
        private void handleIntent(Intent intent) {
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);

                final Serializable[] results = find(query);

                listResults.setAdapter(new ArrayAdapter<BusLine>(this, R.layout.list_view, busLines));
                listResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Serializable selectedResult = (Serializable) parent.getItemAtPosition(position);
                        setResult(RESULT_OK, new Intent().putExtra("result", selectedResult));
                        finish();
                    }
                });
            }
        }
    }
}



 */
