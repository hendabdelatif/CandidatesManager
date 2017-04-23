package com.example.hend.candidatesmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hend.candidatesmanager.BaseActivity;
import com.example.hend.candidatesmanager.R;
import com.example.hend.candidatesmanager.candidatedetails.CandidateDetailsActivity;
import com.example.hend.candidatesmanager.database.RealmController;
import com.example.hend.candidatesmanager.misc.DialogClickListener;
import com.example.hend.candidatesmanager.misc.Utilities;
import com.example.hend.candidatesmanager.models.Candidate;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

public class CandidatesAdapter extends RealmRecyclerViewAdapter<Candidate> {

    private Context context;
    private Realm realm;

    public CandidatesAdapter(Context context) {
        setHasStableIds(true);
        this.context = context;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_candidate, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        realm = RealmController.getInstance().getRealm();

        final Candidate candidate = getItem(position);
        CardViewHolder holder = (CardViewHolder) viewHolder;

        holder.txtName.setText(candidate.getName());
        holder.txtPosition.setText(candidate.getPosition());
        holder.txtMobile.setText(candidate.getMobile());

        if (candidate.getPhoto() != null) {

            Picasso.with(context).load(Utilities.getImageContentUri(context, new File(candidate.getPhoto()))).placeholder(R.drawable.placeholder).into(holder.imgPhoto);
        }

        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                BaseActivity.generateChoiceMessageAlert(context, context.getString(R.string.delete_confrim, candidate.getName()), new DialogClickListener() {
                    @Override
                    public void onDialogButtonClick() {
                        deleteCandidate(position);
                    }
                }, null);


                return false;
            }
        });

        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CandidateDetailsActivity.class);
                intent.putExtra("id", candidate.getId());
                context.startActivity(intent);
            }
        });
    }

    private void deleteCandidate(int position) {
        RealmResults<Candidate> results = realm.where(Candidate.class).findAll();

        Candidate selectedCandidate = results.get(position);
        String candidateName = selectedCandidate.getName();

        realm.beginTransaction();
        results.deleteFromRealm(position);
        realm.commitTransaction();

        notifyDataSetChanged();

        Toast.makeText(context, candidateName + " is deleted", Toast.LENGTH_SHORT).show();
    }

    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        CircleImageView imgPhoto;
        TextView txtName;
        TextView txtPosition;
        TextView txtMobile;

        CardViewHolder(View itemView) {
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_candidate);
            imgPhoto = (CircleImageView) itemView.findViewById(R.id.img_photo);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtPosition = (TextView) itemView.findViewById(R.id.txt_position);
            txtMobile = (TextView) itemView.findViewById(R.id.txt_mobile);
        }

    }
}
