package com.ivoryworks.pgma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private FragmentManager mFragmentManager;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action)) {
            PickImageFragment.newInstance();
            Fragment fragment = PickImageFragment.newInstance();
            fragment.setRetainInstance(true);
            mFragmentManager.beginTransaction().replace(R.id.container, fragment, PickImageFragment.TAG).commit();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        int itemId = NavigationDrawerFragment.getPositionToId(getBaseContext(), position);
        mTitle = getString(itemId);
        Fragment fragment;
        String tag;
        switch (itemId) {
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
        case R.string.title_progress_variation:
            tag = ProgressVariationFragment.TAG;
            fragment = ProgressVariationFragment.newInstance();
            break;
        case R.string.title_image_crop:
            tag = ImageCropFragment.TAG;
            fragment = ImageCropFragment.newInstance();
            break;
        case R.string.title_image_mix:
            tag = ImageMixFragment.TAG;
            fragment = ImageMixFragment.newInstance();
            break;
        case R.string.title_touch:
            tag = TouchFragment.TAG;
            fragment = TouchFragment.newInstance();
            break;
        case R.string.title_gesture:
            tag = GestureFragment.TAG;
            fragment = GestureFragment.newInstance();
            break;
        case R.string.title_fw_drawable:
            tag = FrameworkDrawableFragment.TAG;
            fragment = FrameworkDrawableFragment.newInstance();
            break;
        case R.string.title_media_effects:
            tag = MediaEffectsFragment.TAG;
            fragment = MediaEffectsFragment.newInstance();
            break;
        case R.string.title_fab:
            tag = FABFragment.TAG;
            fragment = FABFragment.newInstance();
            break;
        case R.string.title_gallery:
            tag = GalleryFragment.TAG;
            fragment = GalleryFragment.newInstance();
            break;
        case R.string.title_scale_type:
            tag = ScaleTypeFragment.TAG;
            fragment = ScaleTypeFragment.newInstance();
            break;
        case R.string.title_top:
        default:
            tag = PlaceholderFragment.TAG;
            fragment = PlaceholderFragment.newInstance();
            break;
        }
        if (mFragmentManager.findFragmentByTag(tag) == null) {
            fragment.setRetainInstance(true);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.container, fragment, tag);
            transaction.addToBackStack(null);
            transaction.commit();
            restoreActionBar();
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
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public static String TAG = PlaceholderFragment.class.getSimpleName();

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
