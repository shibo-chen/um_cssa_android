package edu.umich.umcssa.umich_cssa;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import edu.umich.umcssa.umich_cssa.dataManage.CourseDBHelper;
import edu.umich.umcssa.umich_cssa.dataManage.FeedItemDBHelper;
import edu.umich.umcssa.umich_cssa.dataManage.FeedItemsContract;
import edu.umich.umcssa.umich_cssa.dummy.DummyContent;
import edu.umich.umcssa.umich_cssa.schedule.ScheduleFragment;
import edu.umich.umcssa.umich_cssa.settings.SettingsFragment;

/**
 * Navigation drawer activity
 * Shows fragment on content_main
 * @author Shibo Chen
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SettingsFragment.OnFragmentInteractionListener,
        ScheduleFragment.OnFragmentInteractionListener, PageFragment.OnListFragmentInteractionListener{
    private FeedItemDBHelper feedItemDBHelper;
    private CourseDBHelper courseDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        sets the first item clicked at the start up
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SettingsFragment settingsFragment=new SettingsFragment();
            replaceWithFragment(settingsFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //TODO: MAYBE USEFULL...
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_courseSchedule) {
            ScheduleFragment scheduleFragment = new ScheduleFragment();
            replaceWithFragment(scheduleFragment);
        }else{
            Bundle args=new Bundle();
            if (id == R.id.nav_recentActivities) {
                args.putSerializable(PageFragment.ARG_ITEM_TYPE, FeedItemsContract.TYPES.RECENT_ACTIVITIES);
            } else if (id == R.id.nav_news) {
                args.putSerializable(PageFragment.ARG_ITEM_TYPE, FeedItemsContract.TYPES.NEWS);
            } else if (id == R.id.nav_tickets) {
                args.putSerializable(PageFragment.ARG_ITEM_TYPE, FeedItemsContract.TYPES.TICKET);
            } else if (id == R.id.nav_sales) {
                args.putSerializable(PageFragment.ARG_ITEM_TYPE, FeedItemsContract.TYPES.SALES);
            }
            PageFragment pageFragment=new PageFragment();
            replaceWithFragment(pageFragment);
        }

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceWithFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,fragment);
//        Add this line if you want to enable back_button_clicked
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        //TODO
    }
}
