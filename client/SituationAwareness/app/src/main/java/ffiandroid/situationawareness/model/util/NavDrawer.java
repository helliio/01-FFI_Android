package ffiandroid.situationawareness.model.util;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ffiandroid.situationawareness.R;
import ffiandroid.situationawareness.controller.AppSettings;
import ffiandroid.situationawareness.controller.LocationView;
import ffiandroid.situationawareness.controller.PhotoView;
import ffiandroid.situationawareness.controller.Report;
import ffiandroid.situationawareness.controller.ReportView;
import ffiandroid.situationawareness.controller.Status;

/**
 * Created by Torgrim on 06/05/2015.
 */
public class NavDrawer
{

    private Activity activity;
    private ActionBar actionBar;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] optionsList;

    public NavDrawer(Activity activity,  ActionBar inActionBar)
    {

        // NOTE(Torgrim): Testing new ui drawer
        this.actionBar = inActionBar;
        this.activity = activity;

        mDrawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        optionsList = activity.getResources().getStringArray(R.array.options_list);
        mDrawerList = (ListView) activity.findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(activity,
                R.layout.drawer_list_item, optionsList));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(activity));

        mDrawerToggle = new ActionBarDrawerToggle(
                activity,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                actionBar.setTitle("Drawer Closed");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                actionBar.setTitle("Drawer Opened");
            }
        };
//getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setDisplayShowHomeEnabled(true);

        //getSupportActionBar().setIcon(R.drawable.ic_drawer);
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        Activity mapActivity;

        public DrawerItemClickListener(Activity mapActivity) {
            this.mapActivity = mapActivity;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("Clicked on item at position " + position + " With ID: " + id);
            switch (position) {
                case 0:
                    System.out.println("Already Here");
                    break;
                case 1:
                    activity.startActivity(new Intent(mapActivity, LocationView.class));
                    System.out.println("Starting activity location View");
                    break;
                case 2:
                    activity.startActivity(new Intent(mapActivity, ReportView.class));
                    System.out.println("Starting activity report view");
                    break;
                case 3:
                    activity.startActivity(new Intent(mapActivity, PhotoView.class));
                    System.out.println("Starting activity photo view");
                    break;
                case 4:
                    activity.startActivity(new Intent(mapActivity, Report.class));
                    System.out.println("Starting activity report");
                    break;
                case 5:
                    activity.startActivity(new Intent(mapActivity, Status.class));
                    System.out.println("Starting activity status");
                    break;
                case 6:
                    activity.startActivity(new Intent(mapActivity, AppSettings.class));
                    System.out.println("Starting activity settings");
                    break;
                default:

                    System.out.println("Default item clicked!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
    }


}
