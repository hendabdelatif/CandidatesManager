package com.example.hend.candidatesmanager.database;


import com.example.hend.candidatesmanager.models.Candidate;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Hend on 4/21/2017.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    private RealmController() {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController getInstance() {
        if (instance == null) {
            instance = new RealmController();
        }
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public void deleteAll() {

        realm.beginTransaction();
        realm.delete(Candidate.class);
        realm.commitTransaction();
    }

    public RealmResults<Candidate> getCandidates() {

        return realm.where(Candidate.class).findAll();
    }

    public Candidate getCandidate(long id) {
        return realm.where(Candidate.class).equalTo("id", id).findFirst();
    }
}
