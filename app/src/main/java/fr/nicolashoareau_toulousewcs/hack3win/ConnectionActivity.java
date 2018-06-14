package fr.nicolashoareau_toulousewcs.hack3win;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class ConnectionActivity extends AppCompatActivity {

    private int mSpinnerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final Spinner spinner = findViewById(R.id.spinner_profile);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ConnectionActivity.this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button buttonConnection = findViewById(R.id.bt_connection);

        buttonConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSpinnerPosition = spinner.getSelectedItemPosition();

                if (mSpinnerPosition == 0) {

                    Intent intentMainActivity = new Intent(ConnectionActivity.this, MainActivity.class);
                    startActivity(intentMainActivity);
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ConnectionActivity.this);

                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alertdialog_custom_view, null);

                    builder.setCancelable(false);

                    builder.setView(dialogView);

                    Button btn_positive = dialogView.findViewById(R.id.bt_validate);

                    final AlertDialog dialog = builder.create();

                    btn_positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.cancel();

                            Intent intentMainActivity = new Intent(ConnectionActivity.this, MainActivity.class);
                            startActivity(intentMainActivity);
                        }
                    });

                    dialog.show();
                }
            }
        });
    }
}
