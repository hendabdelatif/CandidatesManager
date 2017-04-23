package com.example.hend.candidatesmanager.searchcandidate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hend.candidatesmanager.BaseActivity;
import com.example.hend.candidatesmanager.R;
import com.example.hend.candidatesmanager.adapters.CandidatesAdapter;
import com.example.hend.candidatesmanager.adapters.RealmCandidateAdapter;
import com.example.hend.candidatesmanager.customviews.RecyclerViewEmptySupport;
import com.example.hend.candidatesmanager.database.RealmController;
import com.example.hend.candidatesmanager.misc.Utilities;
import com.example.hend.candidatesmanager.models.Candidate;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Hend on 4/21/2017.
 */

public class SearchCandidateActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private CandidatesAdapter adapter;
    private Realm realm;
    private RecyclerViewEmptySupport recyclerView;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_candidate);
        init();
    }

    private void init() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        realm = Realm.getDefaultInstance();
        recyclerView = (RecyclerViewEmptySupport) findViewById(R.id.candidates_list);
        recyclerView.setEmptyView(findViewById(R.id.list_empty));
        setUpRecyclerView();
        setRealmAdapter(RealmController.getInstance().getCandidates());

    }

    private void setUpRecyclerView() {
        adapter = new CandidatesAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }

    public void setRealmAdapter(RealmResults<Candidate> candidates) {

        RealmCandidateAdapter realmAdapter = new RealmCandidateAdapter(candidates);
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_candidates, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Utilities.hidKeyboard(SearchCandidateActivity.this);
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        RealmResults<Candidate> filteredCandidateList = filter(RealmController.getInstance().getCandidates(), query);
        setRealmAdapter(filteredCandidateList);
        searchView.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        RealmResults<Candidate> filteredCandidateList = filter(RealmController.getInstance().getCandidates(), newText);
        setRealmAdapter(filteredCandidateList);
        return false;
    }

    private static RealmResults<Candidate> filter(RealmResults<Candidate> candidates, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        RealmResults<Candidate> realmResults = candidates.where()
                .contains("name", lowerCaseQuery)
                .or()
                .contains("position", lowerCaseQuery)
                .findAll();

        return realmResults;
    }

}
