package fr.nicolashoareau_toulousewcs.hack3win;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Toutes les news");

        mAuth = FirebaseAuth.getInstance();

        //Navigation Drawer :
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_menu);
        mToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Navigation View :
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ListView listTrip = findViewById(R.id.lv_home);
        final ArrayList<ArticleModel> articleList = new ArrayList<>();


        articleList.add(new ArticleModel("Le sport rend heureux, c'est mesuré!!","Une étude portant sur plus de 500 000 personnes publiée dans leJournal of\n" +
                "Happiness Studies, a découvert quela pratique d’une activité physique\n" +
                "durant seulement 10 minutes au courant de la semaine peut améliorer\n" +
                "considérablement les chances de se sentir heureux.","nc7Z3EBMevg",R.drawable.video1,"https://www.pepsnews.com/combien-de-minutes-de-sport-pour-etre-heureux/"));
        articleList.add(new ArticleModel("Une IA contre le cancer","Une équipe de chercheurs allemands, américains et français a créé un système\n" +
                "d’intelligence artificielle qui permet de distinguer les lésions cutanées\n" +
                "dangereuses des lésions bénignes.Pour son apprentissage, la machine a\n" +
                "assimilé plus de 100 000 images et lors d’une confrontation avec des\n" +
                "dermatologues, cette intelligence artificielle a détecté 95% de mélanomes\n" +
                "contre 86,6","CP_2T9oTyBc",R.drawable.video2,"https://www.pepsnews.com/un-ordinateur-pour-detecter-plus-precisement-le-cancer-de-la-peau/"));
        articleList.add(new ArticleModel("Les cargos écolos 2.0","Dès la rentrée lasociété Néerlandaise Port-Linerlancera des cargos « verts »\n" +
                "munis de batteries rechargeables par énergie solaire et renouvelable\n" +
                "(éolienne).\n" +
                "Ce sont une quinzaine de bateaux entre 50 et 110 mètres de long capables de\n" +
                "transporter jusqu’à 280 conteneurs qui seront louéspour remplacer les\n" +
                "dizaines de milliers de camions qui circulent chaque année.","-PvMOppVmeo",R.drawable.video3,"https://www.pepsnews.com/ecologie-des-cargos-electriques/"));
        articleList.add(new ArticleModel("Une ecole de Journalisme fait peau neuve à Toulouse","C’est un ancien eleve d’une école de Journalisme qui a permis l’Institut\n" +
                "Supérieur de Journalisme de Toulouse (ISJT) de ne pas mettre la clé\n" +
                "sous la porte. Le nom reste le même mais c’est bien la seule chose qui\n" +
                "n’a pas été chambouler par Guillaume Truilhé, repreneur et directeur de\n" +
                "l’établissement.","https://france3-regions.blog.francetvinfo.fr/medias-midi-pyrenees/2015/08/18/une-nouvelle-ecole-de-journalisme-a-toulouse.html",R.drawable.video4,"https://france3-regions.blog.francetvinfo.fr/medias-midi-pyrenees/2015/08/18/une-nouvelle-ecole-de-journalisme-a-toulouse.html"));
        articleList.add(new ArticleModel("L’économie est plus rentable c’est prouvé !","L&#39;économie verte est non seulement nécessaire mais réalisable. Si nous\n" +
                "ne misons pas sur elle, les générations futures en pâtiront, ont plaidé des\n" +
                "scientifiques des EPF en appelant à soutenir l&#39;initiative des écologistes en\n" +
                "votation le 25 septembre.","kVpVbEaWdIY",R.drawable.video5,"https://www.tdg.ch/savoirs/environnement/choisir-economie-verte-rentable/story/30129048"));
        articleList.add(new ArticleModel("Un espace de CoWorking en province levé 1,7 millions\n" +
                "d’euros","Le Tiers-Lieu Lab’Oikos basé à Toulouse dans le quartier de SAont-aubin se donne\n" +
                "les moyens de ses ambitions grace à une levée de fonds de 1,7 Milllions auprès\n" +
                "duCredit Agricole ou encore la Societe Generale.","CVa-vneVoQs",R.drawable.video6,"http://www.alloweb.org/levee-de-fonds-coworking-laboikos-leve-1-7-million-deuros/"));
        ItemAdapter adapter = new ItemAdapter(this, articleList);
        listTrip.setAdapter(adapter);

        listTrip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ZoomArticlesActivity.class);
                ArticleModel currentArticle = articleList.get(i);
                intent.putExtra("title", currentArticle.getTitle());
                intent.putExtra("resume", currentArticle.getResume());
                intent.putExtra("url", currentArticle.getUrl());
                intent.putExtra("site", currentArticle.getSite());
                startActivity(intent);
            }

        });
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.reader) {
            Intent goToJoin = new Intent(this, MainActivity.class);
            this.startActivity(goToJoin);
        } else if (id == R.id.contributor) {
            Intent goToContributor = new Intent(this, ContributeurActivity.class);
            this.startActivity(goToContributor);
        } else if (id == R.id.logout) {
            //Déconnexion
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            startActivity(new Intent(this, ConnectionActivity.class));
        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}