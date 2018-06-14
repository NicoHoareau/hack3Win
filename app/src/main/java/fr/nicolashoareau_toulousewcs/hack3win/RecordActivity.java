package fr.nicolashoareau_toulousewcs.hack3win;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class RecordActivity extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;
    VideoView mVrecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Button record = findViewById(R.id.b_record);
        mVrecord = findViewById(R.id.vv_record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }
    }


    });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            mVrecord.setVideoURI(videoUri);
        }
    }
}