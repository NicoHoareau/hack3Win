package fr.nicolashoareau_toulousewcs.hack3win;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<ArticleModel> {

        public ItemAdapter(Context context, ArrayList<ArticleModel> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            ArticleModel articlemodel = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_item, parent, false);
            }
            // Lookup view for data population
            TextView tvtitle = (TextView) convertView.findViewById(R.id.et_title);
            ImageView tvHome = convertView.findViewById(R.id.img_article);
            Drawable drawableProfil= ContextCompat.getDrawable(getContext(), articlemodel.getMiniature());
            tvHome.setImageDrawable(drawableProfil);
            // Populate the data into the template view using the data object
            tvtitle.setText(articlemodel.getTitle());

            // Return the completed view to render on screen
            return convertView;
        }


}
