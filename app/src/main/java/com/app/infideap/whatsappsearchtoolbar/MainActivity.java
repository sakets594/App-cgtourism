package com.app.infideap.whatsappsearchtoolbar;

import android.Manifest;
import android.animation.Animator;
import android.app.NotificationManager;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.infideap.whatsappsearchtoolbar.list.MyItemRecyclerViewAdapter;
import com.app.infideap.whatsappsearchtoolbar.list.dummy.DummyContent;
import com.app.infideap.whatsappsearchtoolbar.list.dummy.Dummy_explore;
import com.app.infideap.whatsappsearchtoolbar.list.dummy.Dummy_nearby;
import com.app.infideap.whatsappsearchtoolbar.list.dummy.Dummy_trending;
import com.app.infideap.whatsappsearchtoolbar.list.dummy.Dummy_wishlist;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.nineoldandroids.animation.AnimatorSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import android.content.Context;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private TabLayout tabLayout;
    private  Toolbar toolbar;
    private AppBarLayout appBar;
    private View searchAppBarLayout;
    private ViewPager viewPager;
    private Toolbar searchToolBar;
    public static EditText searchEditText;
    private float positionFromRight = 2;
    public static String PACKAGE_NAME;
    public  static Resources RESOURCE;
    public static Databasehelper mydb;
    public static Cursor Alldata;
    public static Cursor mdata;
    public static String order="0";
    public static String searchvalue;
    private SectionsPagerAdapter1 sectionsPagerAdapter1;
    public static ExploreFragment exploreFragment;
    public static TrendingFragment trendingFragment;
    public static NearbyFragment nearbyFragment;
    public static WishlistFragment wishlistFragment;
    public  static MyItemRecyclerViewAdapter exploreAdapter,trendingAdapter;
     public static SearchableAdapter wishlistAdapter,nearbyAdapter;
     public static DateFormat dateFormat=new SimpleDateFormat("MM");
     public static Date date=new Date();
     public static int month=Integer.valueOf(dateFormat.format(date));
    MenuItem actionRestart;
    Menu mMenu;
    public static float lt,ln;
    public static String title;;
    public static  int nearbydist=50;


    private  static GoogleMap mMap;
public static double distance;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    public static double dist[]=new double[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStoragePermissionGranted();
        PACKAGE_NAME = getApplicationContext().getPackageName();
        RESOURCE =getResources();
        mydb=new Databasehelper(this);
        Alldata=mydb.getAllData();
        mdata=mydb.getmdata();
        SectionsPagerAdapter1 sectionsPagerAdapter1;
        appBar =  findViewById(R.id.appBar);
        searchAppBarLayout = findViewById(R.id.layout_appbar_search);
        toolbar =  findViewById(R.id.toolbar);
        searchEditText =  findViewById(R.id.editText_search);

        for(double it:dist)
            it=100000000.00;


        setSupportActionBar(toolbar);
        initSearchBar();
        initTabLayout();
        initViewPager();


        tabLayout.getTabAt(0).select();
        searchEditText.addTextChangedListener(filterTextWatcher);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
//end latlng




try{
    exploreAdapter=new MyItemRecyclerViewAdapter(MainActivity.this, Dummy_explore.ITEMS, ExploreFragment.getmListener());
    wishlistAdapter=new SearchableAdapter(MainActivity.this, Dummy_wishlist.ITEMS);

    nearbyAdapter=new  SearchableAdapter(MainActivity.this, Dummy_nearby.ITEMS);
        trendingAdapter=new MyItemRecyclerViewAdapter(MainActivity.this,Dummy_trending.ITEMS, ExploreFragment.getmListener());}
        catch(Exception e){};
//if(Dummy_explore.ITEMS==null)
  //  Toast.makeText(this,"asdf",Toast.LENGTH_SHORT).show();

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {


                    exploreAdapter.filter(v.getText().toString());
                    exploreAdapter.notifyDataSetChanged();
                    wishlistAdapter.getFilter().filter(v.getText());
                    wishlistAdapter.notifyDataSetChanged();
                    trendingAdapter.filter(v.getText().toString());
                    trendingAdapter.notifyDataSetChanged();
                    nearbyAdapter.getFilter().filter(v.getText());
                    nearbyAdapter.notifyDataSetChanged();

                    Utils.hideKeyBoard(v);
                    return true;
                }
                return false;
            }
        });



       // mydb.close();

    }
    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            exploreAdapter.filter(searchEditText.getText().toString());
            exploreAdapter.notifyDataSetChanged();
            wishlistAdapter.getFilter().filter(s);
            wishlistAdapter.notifyDataSetChanged();
            trendingAdapter.filter(searchEditText.getText().toString());
            trendingAdapter.notifyDataSetChanged();
            nearbyAdapter.getFilter().filter(searchEditText.getText());
            nearbyAdapter.notifyDataSetChanged();
        }

    };


    /**
     * Initialize searchBar.
     */
    private void initSearchBar() {
        searchToolBar = (Toolbar) findViewById(R.id.toolbar_search);
        if (searchToolBar != null) {
            searchToolBar.setNavigationIcon(R.drawable.back_button);
            searchAppBarLayout.setVisibility(View.GONE);
          //  Toast.makeText(MainActivity.this,order+"initsearchif",Toast.LENGTH_SHORT).show();
           // order++;
            searchToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideSearchBar(positionFromRight);
                }
            });
        }

    }

    /**
     * Initialize viewPager.
     */
    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(sectionsPagerAdapter1);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                Fragment fragment ;
                switch(position)
                {

                   case 0: return trendingFragment=TrendingFragment.newInstance(1);
                    case 1:return exploreFragment=ExploreFragment.newInstance(1);
                    case 2:return  nearbyFragment=NearbyFragment.newInstance(1);
                    case 3:return  wishlistFragment=WishlistFragment.newInstance(1);
                   // default: return ItemFragment.newInstance(1);

                }

                return WishlistFragment.newInstance(1);
            }

            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }



    /**
     * Initialize tabLayout.
     * create and add tab.
     */
    private void initTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        final int [] unselected={
                R.drawable.ic_fire,
                R.drawable.explore,
                R.drawable.nearbyicon,
                R.drawable.wishlist_icon};
        final int [] selected={
                R.drawable.strending,
                R.drawable.sexplore,
                R.drawable.snearby,
                R.drawable.swishlist};
        final TabLayout.Tab[] tabs = {
                tabLayout.newTab().setText("trending"),
                tabLayout.newTab().setText("explore"),
                tabLayout.newTab().setText("nearby"),
                tabLayout.newTab().setText("wishlist")
        };

        for (TabLayout.Tab tab : tabs) {
            tabLayout.addTab(tab);
        }
        for(int i=0;i<tabs.length;i++)
            tabs[i].setIcon(unselected[i]);
        tabs[0].setIcon(selected[0]);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //getSupportActionBar().setTitle(tab.getText());
                viewPager.setCurrentItem(tab.getPosition());
                for(int i=0;i<tabs.length;i++){
                    if(tab.getPosition()==i)
                        tabs[i].setIcon(selected[i]);
                    else
                        tabs[i].setIcon(unselected[i]);
                }
                if (searchAppBarLayout.getVisibility() == View.VISIBLE) {
                    hideSearchBar(positionFromRight);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
               // databasesupdate();
                /*ExploreFragment.recyclerView.setAdapter(exploreAdapter);
                TrendingFragment.recyclerView.setAdapter(trendingAdapter);
                WishlistFragment.recyclerView.setAdapter(wishlistAdapter);
                NearbyFragment.recyclerView.setAdapter(nearbyAdapter);
*/

            }
        });
    }

    /**
     * to show the searchAppBarLayout and hide the mainAppBar with animation.     *
     * @param positionFromRight
     */
    private void showSearchBar(float positionFromRight) {
        AnimatorSet set = new AnimatorSet();
      /* set.playTogether(
                ObjectAnimator.ofFloat(appBar, "translationY", -tabLayout.getHeight())
               , ObjectAnimator.ofFloat(viewPager, "translationY", -tabLayout.getHeight())//
               //  ObjectAnimator.ofFloat(appBar, "alpha", 0)
        );*/
        set.setDuration(100).addListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {

            }

            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                //appBar.setVisibility(View.GONE);
                searchEditText.requestFocus();
                Utils.showKeyBoard(searchEditText);

            }

            @Override
            public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {
                Utils.hideKeyBoard(searchEditText);
            }

            @Override
            public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {

            }
        });
        set.start();
        // start x-index for circular animation
        int cx = toolbar.getWidth() - (int) (getResources().getDimension(R.dimen.dp48)* (0.5f + positionFromRight));
        // start y-index for circular animation
        int cy = (toolbar.getTop() + toolbar.getBottom()) / 2;

        // calculate max radius
        int dx = Math.max(cx, toolbar.getWidth() - cx);
        int dy = Math.min(cy, toolbar.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);

        // Circular animation declaration begin
        final Animator animator;
        animator = io.codetail.animation.ViewAnimationUtils
                .createCircularReveal(searchAppBarLayout, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);
        searchAppBarLayout.setVisibility(View.VISIBLE);
        animator.start();
        // Circular animation declaration end
    }


    /**
     * to hide the searchAppBarLayout and show the mainAppBar with animation.
     *
     * @param positionFromRight
     */
    private void hideSearchBar(float positionFromRight) {
        //Toast.makeText(MainActivity.this,order+"hidesearch",Toast.LENGTH_SHORT).show();
        //order++;


        // start x-index for circular animation
        int cx = toolbar.getWidth() - (int) (getResources().getDimension(R.dimen.dp48) * (0.5f + positionFromRight));
        // start  y-index for circular animation
        int cy = (toolbar.getTop() + toolbar.getBottom()) / 2;

        // calculate max radius
        int dx = Math.max(cx, toolbar.getWidth() - cx);
        int dy = Math.max(cy, toolbar.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);

        // Circular animation declaration begin
        Animator animator;
        animator = io.codetail.animation.ViewAnimationUtils
                .createCircularReveal(searchAppBarLayout, cx, cy, finalRadius, 0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                searchAppBarLayout.setVisibility(View.GONE);
                Utils.hideKeyBoard(searchEditText);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animator.start();
        // Circular animation declaration end

        appBar.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet();
       /* set.playTogether(
               // ObjectAnimator.ofFloat(appBar, "translationY", 0),
                //ObjectAnimator.ofFloat(appBar, "alpha", 1),
                //ObjectAnimator.ofFloat(viewPager, "translationY", 0)
        );*/
        set.setDuration(100).start();
        searchEditText.setText("");
        exploreAdapter.filter(searchEditText.getText().toString());
        exploreAdapter.notifyDataSetChanged();
        wishlistAdapter.getFilter().filter(searchEditText.getText());
        wishlistAdapter.notifyDataSetChanged();
        trendingAdapter.filter(searchEditText.getText().toString());
        trendingAdapter.notifyDataSetChanged();
        nearbyAdapter.getFilter().filter(searchEditText.getText());
        nearbyAdapter.notifyDataSetChanged();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

       mMenu=menu;
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_search) {
            showSearchBar(positionFromRight);
            return true;
        }*/
        boolean ans;
        switch (item.getItemId()) {

            case R.id.action_search:
                showSearchBar(positionFromRight);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        //return super.onOptionsItemSelected(item);
    }




    @Override
    public void onBackPressed() {
        // if the searchToolBar is visible, hide it
        // otherwise, do parent onBackPressed method
        if (searchAppBarLayout.getVisibility() == View.VISIBLE)
            hideSearchBar(positionFromRight);
        else
            super.onBackPressed();

    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        if(getCurrentFocus()==searchEditText)
        {
            showSearchBar(positionFromRight);
        }
        // Checks the orientation of the screen
        /*if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }*/
        super.onConfigurationChanged(newConfig);


    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
              //  Toast.makeText(this,"granted",Toast.LENGTH_SHORT).show();
                return true;
            } else {

                // Log.v(TAG,"Permission is revoked");
               // Toast.makeText(this,"Permission is revoked",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
           // Toast.makeText(this,"Permission is granted",Toast.LENGTH_SHORT).show();
            // Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
           // Toast.makeText(this,"Permission: "+permissions[0]+ "was "+grantResults[0],Toast.LENGTH_SHORT).show();
            //Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    public static  void updateAlldata()
    {
        Alldata=mydb.getAllData();

    }

    public static void  databasesupdate() {
        nearbyAdapter.notifyDataSetChanged();
        trendingAdapter.notifyDataSetChanged();
        wishlistAdapter.notifyDataSetChanged();
        exploreAdapter.notifyDataSetChanged();
    }


    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.i_1:item.setChecked(true);
            return true;
            case R.id.i_2:item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/


    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }



    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }


        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

          //  Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        //distance calculation
        Location startpoint = new Location("location A");
        startpoint.setLatitude(currentLatitude);
        startpoint.setLongitude(currentLongitude);

        Location endpoint = new Location("location A");

       // nearbyAdapter.mValues.removeAll(nearbyAdapter.mValues);
        for(DummyContent.DummyItem it:Dummy_explore.ITEMS) {
            endpoint.setLatitude(it.lat);
            endpoint.setLongitude(it.long_);
            distance = startpoint.distanceTo(endpoint);
            dist[Integer.valueOf(it.id.substring(2,it.id.length()-2))]=distance;
            ListIterator<DummyContent.DummyItem> iter=MainActivity.nearbyAdapter.mValues.listIterator();
            if(distance<nearbydist*1000&& !MainActivity.nearbyAdapter.mValues.contains(it))
            {
               ;MainActivity.nearbyAdapter.mValues.add(it);
            }
            else if (distance>=nearbydist*1000&& MainActivity.nearbyAdapter.mValues.contains(it))
            {
                ;MainActivity.nearbyAdapter.mValues.remove(it);
            }

            //Toast.makeText(this, distance + "", Toast.LENGTH_LONG).show();
            //Log.d("xyz", String.format("onLocationChanged: %s", distance));
        }
        MainActivity.nearbyAdapter.notifyDataSetChanged();

        //nearbyAdapter.notifyDataSetChanged();
        /*if ((distance / 1000) < 1) {
            NotificationCompat.Builder mBuilder=(NotificationCompat.Builder) new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.near_me)
                    .setContentTitle("Simple Notification")
                    .setContentText("You're near to your point of Interest");
            NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0,mBuilder.build());
*/
            // Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        Toast.makeText(this,"fetching nearby",Toast.LENGTH_SHORT).show();
        mGoogleApiClient.connect();
    }


    }


