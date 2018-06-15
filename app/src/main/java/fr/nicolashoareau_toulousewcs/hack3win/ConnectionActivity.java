package fr.nicolashoareau_toulousewcs.hack3win;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;


public class ConnectionActivity extends AppCompatActivity {

    private int mSpinnerPosition;
    private String mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final Spinner spinner = findViewById(R.id.spinner_profile);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ConnectionActivity.this, R.array.status, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final Button buttonConnection = findViewById(R.id.bt_connection);
        final Button buttonMember = findViewById(R.id.bt_member);
        final EditText editTextNumber = findViewById(R.id.et_number);
        final EditText editTextPassword = findViewById(R.id.et_password);
        final Spinner spinnerProfile = findViewById(R.id.spinner_profile);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        buttonConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stringPseudo = editTextNumber.getText().toString();
                String stringPassword = editTextPassword.getText().toString();
                final HashCode hashCode = Hashing.sha256().hashString(stringPassword, Charset.defaultCharset());

                DatabaseReference myRef = database.getReference("Users");
                myRef.orderByChild("number").equalTo(stringPseudo).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() == 0) {
                            Toast.makeText(ConnectionActivity.this, "Votre numéro est incorrect", Toast.LENGTH_SHORT).show();
                        }

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            UserModel userModel = snapshot.getValue(UserModel.class);
                            String password = userModel.getPassword();

                            if (password.equals(hashCode.toString())) {
                                Intent intentMain = new Intent(ConnectionActivity.this, MainActivity.class);
                                startActivity(intentMain);
                            } else {

                                Toast.makeText(ConnectionActivity.this, "Votre mot de passe est incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        buttonMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonConnection.setVisibility(View.GONE);
                spinnerProfile.setVisibility(View.VISIBLE);

                buttonMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mSpinnerPosition = spinner.getSelectedItemPosition();
                        final String stringNumber = editTextNumber.getText().toString();
                        final String stringPassword = editTextPassword.getText().toString();
                        final HashCode hashCode = Hashing.sha256().hashString(stringPassword, Charset.defaultCharset());

                        if (mSpinnerPosition == 0) {
                            mStatus = "Lecteur";
                            UserModel studentModel = new UserModel(stringNumber, hashCode.toString(), mStatus, null, null, null);
                            DatabaseReference studentRef = myRef.child("Users");
                            studentRef.push().setValue(studentModel);
                            Intent intentMainActivity = new Intent(ConnectionActivity.this, MainActivity.class);
                            startActivity(intentMainActivity);

                        } else {
                            mStatus = "Contributeur";
                            AlertDialog.Builder builder = new AlertDialog.Builder(ConnectionActivity.this);
                            LayoutInflater inflater = getLayoutInflater();
                            final View dialogView = inflater.inflate(R.layout.alertdialog_custom_view, null);
                            builder.setCancelable(false);
                            builder.setView(dialogView);
                            Button btn_positive = dialogView.findViewById(R.id.bt_validate);

                            final AlertDialog dialog = builder.create();

                            btn_positive.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final EditText editTextName = dialogView.findViewById(R.id.et_name);
                                    final EditText editTextFirstName = dialogView.findViewById(R.id.et_firstname);
                                    final EditText editTextEmployer = dialogView.findViewById(R.id.et_employer);

                                    final String stringName = editTextName.getText().toString();
                                    final String stringFirstName = editTextFirstName.getText().toString();
                                    final String stringEmployer = editTextEmployer.getText().toString();

                                    UserModel studentModel = new UserModel(stringNumber, hashCode.toString(), mStatus, stringName, stringFirstName, stringEmployer);
                                    DatabaseReference studentRef = myRef.child("Users");
                                    studentRef.push().setValue(studentModel);

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
        });
    }
}
