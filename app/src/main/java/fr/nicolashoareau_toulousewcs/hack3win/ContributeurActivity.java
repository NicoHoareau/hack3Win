package fr.nicolashoareau_toulousewcs.hack3win;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ContributeurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributeur);
        setTitle("Créer mes news");

        final ListView listView = findViewById(R.id.listview_contributeur);
        ArrayList<ContributeurModel> contributeur = new ArrayList<>();

        ContributeurAdapter adapter = new ContributeurAdapter(this, contributeur);
        listView.setAdapter(adapter);

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
