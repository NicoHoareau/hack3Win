package fr.nicolashoareau_toulousewcs.hack3win;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ContributeurActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributeur);
        setTitle("Créer mes news");

        final ListView listView = findViewById(R.id.listview_contributeur);
        final ArrayList<ContributeurModel> contrib = new ArrayList<>();
        final ContributeurAdapter adapter = new ContributeurAdapter(ContributeurActivity.this, contrib);
        listView.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users").child("news");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contrib.clear();
                for (DataSnapshot listSnapshot : dataSnapshot.getChildren()){
                    ContributeurModel contributeurModel = listSnapshot.getValue(ContributeurModel.class);
                    contrib.add(contributeurModel);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        FloatingActionButton fabContributor = findViewById(R.id.fab_contributor);
        fabContributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContributeurActivity.this, TempActivity.class);
                startActivity(intent);
            }
        });
    }
}
