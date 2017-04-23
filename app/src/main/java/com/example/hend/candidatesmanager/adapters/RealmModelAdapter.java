package com.example.hend.candidatesmanager.adapters;

import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmObject;
import io.realm.RealmResults;


public class RealmModelAdapter<T extends RealmObject> extends RealmBaseAdapter<T> {

    public RealmModelAdapter(RealmResults<T> realmResults) {
        super(realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}