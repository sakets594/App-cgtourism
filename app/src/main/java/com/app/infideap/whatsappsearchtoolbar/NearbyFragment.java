package com.app.infideap.whatsappsearchtoolbar;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.infideap.whatsappsearchtoolbar.list.ItemFragment;
import com.app.infideap.whatsappsearchtoolbar.list.MyItemRecyclerViewAdapter;
import com.app.infideap.whatsappsearchtoolbar.list.dummy.DummyContent;

/**
 * Created by sks on 11/3/18.
 */

public class NearbyFragment  extends android.support.v4.app.Fragment {

// : Customize parameter argument names
private static final String ARG_COLUMN_COUNT = "column-count";
// : Customize parameters
private int mColumnCount = 1;
private ItemFragment.OnListFragmentInteractionListener mListener;
public RecyclerView recyclerView;

/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
public NearbyFragment() {
        }

// Customize parameter initialization
@SuppressWarnings("unused")
public static NearbyFragment newInstance(int columnCount) {
        NearbyFragment fragment = new NearbyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
        }

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        }

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);

        // Set the adapter
        /*
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
             recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(new MyItemRecyclerViewAdapter.WrapContentLinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            }else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(MainActivity.wishlistAdapter);
            */
        // Dummy_wishlist.updatelist();
        ListView listView=view.findViewById(R.id.listView1);
        listView.setAdapter(MainActivity.nearbyAdapter);
        //listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        //OverScrollDecoratorHelper.setUpOverScroll(listView);


        return view;
        }



/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p/>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */
public interface OnListFragmentInteractionListener {
    // : Update argument type and name
    void onListFragmentInteraction(DummyContent.DummyItem item);
}
}
