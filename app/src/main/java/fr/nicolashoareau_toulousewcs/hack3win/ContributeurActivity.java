package fr.nicolashoareau_toulousewcs.hack3win;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class ContributeurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributeur);

        final ListView listView = findViewById(R.id.listview_contributeur);
        ArrayList<ContributeurModel> contributeur = new ArrayList<>();




        ContributeurAdapter adapter = new ContributeurAdapter(this, contributeur);
        listView.setAdapter(adapter);
    }
}
