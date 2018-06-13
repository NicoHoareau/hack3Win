package fr.nicolashoareau_toulousewcs.hack3win;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class ZoomArticlesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_articles);

        //Navigation Drawer :
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_menu);
        mToggle = new ActionBarDrawerToggle(ZoomArticlesActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Navigation View :
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.profil) {
            //Intent goToProfil = new Intent(ZoomArticlesActivity.this, ProfilActivity.class);
            //this.startActivity(goToProfil);
        } else if (id == R.id.reader) {
            //Intent goToJoin = new Intent(this, JoinQuizzActivity.class);
            //this.startActivity(goToJoin);
        } else if (id == R.id.contributor) {
            //Intent goToCreate = new Intent(this, CreateQuizzActivity.class);
            // this.startActivity(goToCreate);
        } else if (id == R.id.articles) {
            //intent

        } else if (id == R.id.logout) {
            //Déconnexion
            /*mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            startActivity(new Intent(this, MainActivity.class));*/
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
