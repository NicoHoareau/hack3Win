package fr.nicolashoareau_toulousewcs.hack3win;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.VideoView;
import java.util.ArrayList;

public class ContributeurAdapter extends ArrayAdapter<ContributeurModel> {
    public ContributeurAdapter(Context context, ArrayList<ContributeurModel> contributeur) {
        super(context, 0, contributeur);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContributeurModel contributeur = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_contributeur, parent, false);

            VideoView videoContributeur = convertView.findViewById(R.id.videoView_contributeur);
            TextView textName = convertView.findViewById(R.id.tv_nameVideo);
            TextView textDescription = convertView.findViewById(R.id.tv_descriptionVideo);

            videoContributeur.setVideoPath(contributeur.getVideo());
            textName.setText(contributeur.getNamevideo());
            textDescription.setText(contributeur.getDescriptionvideo());


        }
        return convertView;
    }
}
