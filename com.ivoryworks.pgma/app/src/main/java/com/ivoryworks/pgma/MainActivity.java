package com.ivoryworks.pgma;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        int itemId = NavigationDrawerFragment.getPositionToId(getBaseContext(), position);
        mTitle = getString(itemId);
        Fragment fragment;
        String tag;
        switch (itemId) {
        case R.string.title_take_photo:
            tag = TakePhotoFragment.TAG;
            fragment = TakePhotoFragment.newInstance();
            break;
        case R.string.title_pick_image:
            tag = PickImageFragment.TAG;
            fragment = PickImageFragment.newInstance();
            break;
        case R.string.title_stop_watch:
            tag = StopwatchFragment.TAG;
            fragment = StopwatchFragment.newInstance();
            break;
        case R.string.title_robotium_punchball:
            tag = RobotiumPunchballFragment.TAG;
            fragment = RobotiumPunchballFragment.newInstance();
            break;
        case  R.string.title_image_rotate:
            tag = ImageRotateFragment.TAG;
            fragment = ImageRotateFragment.newInstance();
            break;
        case R.string.title_top:
        default:
            tag = PlaceholderFragment.TAG;
            fragment = PlaceholderFragment.newInstance();
            break;
        }
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragment.setRetainInstance(true);
            fragmentManager.beginTransaction().replace(R.id.container, fragment, tag).commit();
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public static String TAG = "PlaceholderFragment";

        public static PlaceholderFragment newInstance() {
            PlaceholderFragment fragment = new PlaceholderFragment();
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }
    }
}
