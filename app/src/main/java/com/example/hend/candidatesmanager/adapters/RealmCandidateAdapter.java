package com.example.hend.candidatesmanager.adapters;

import com.example.hend.candidatesmanager.models.Candidate;

import io.realm.RealmResults;

public class RealmCandidateAdapter extends RealmModelAdapter<Candidate> {

    public RealmCandidateAdapter(RealmResults<Candidate> realmResults) {
        super(realmResults);
    }
}