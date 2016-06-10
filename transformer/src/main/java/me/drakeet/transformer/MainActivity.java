package me.drakeet.transformer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import me.drakeet.timemachine.CoreContract;
import me.drakeet.timemachine.CoreFragment;
import me.drakeet.timemachine.Message;
import me.drakeet.timemachine.Now;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, CoreContract.Delegate {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    private CoreContract.Service service;
    private CoreContract.View coreView;
    private List<Message> messages = new ArrayList<Message>(100) {
        {
            add(new Message("Hello world"));
            add(new Message("Sit down! We're going to drive!"));
            add(new Message("Parking Fenglin love to sit late."));

            add(new Message("~~", /*from = */"someone", /*to = */"drakeet", new Now()));
            add(new Message("I am drakeet", "someone", "drakeet", new Now()));
            add(new Message("What are you doing now?", "someone", "drakeet", new Now()));
        }
    };


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupDrawerLayout(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CoreFragment fragment = CoreFragment.newInstance();
        coreView = fragment;
        fragment.setDelegate(this);
        transaction.add(R.id.core_container, fragment).commitAllowingStateLoss();
        assert messages != null;
    }


    @Override public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.v(TAG, "onAttachFragment");
    }


    @Override protected void onResume() {
        super.onResume();
        // Fragment's initial may not complete. Maybe the initial process is async_(:з」∠)_
        // Testing...
        // TODO: 16/5/15 Service bind View
        service = new ServiceImpl(coreView);
        service.start();
    }


    private void setupDrawerLayout(Toolbar toolbar) {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
    }


    // TODO: 16/5/14 Add real data.
    @Override public List<Message> provideInitialMessages() {
        return messages;
    }


    @Override public void onNewOut(Message message) {
        Log.v(TAG, "onNewOut: " + message.toString());
        service.onNewOut(message);
    }


    @Override public void onMessageClick(Message message) {
        Log.v(TAG, "onMessageClicked: " + message.toString());
    }


    @Override public void onMessageLongClick(Message message) {
        Log.v(TAG, "onMessageLongClicked: " + message.toString());
    }


    @Override public boolean onLeftActionClick() {
        return false;
    }


    @Override public boolean onRightActionClick() {
        return false;
    }


    @Override public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            drawer.removeDrawerListener(toggle);
        }
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody") @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
