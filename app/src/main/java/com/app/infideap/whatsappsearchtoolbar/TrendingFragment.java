package com.app.infideap.whatsappsearchtoolbar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.infideap.whatsappsearchtoolbar.list.ItemFragment;
import com.app.infideap.whatsappsearchtoolbar.list.MyItemRecyclerViewAdapter;
import com.app.infideap.whatsappsearchtoolbar.list.dummy.DummyContent;
import com.app.infideap.whatsappsearchtoolbar.list.dummy.Dummy_trending;
import com.app.infideap.whatsappsearchtoolbar.list.dummy.Dummy_wishlist;

/**
 * Created by sks on 11/3/18.
 */

public class TrendingFragment  extends android.support.v4.app.Fragment {

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
        public TrendingFragment() {
        }

        // Customize parameter initialization
        @SuppressWarnings("unused")
        public static TrendingFragment newInstance(int columnCount) {
                TrendingFragment fragment = new TrendingFragment();
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
                View view = inflater.inflate(R.layout.fragment_trending, container, false);

                // Set the adapter
                if (view instanceof RecyclerView) {
                        Context context = view.getContext();
                        recyclerView = (RecyclerView) view;
                        if (mColumnCount <= 1) {
                                recyclerView.setLayoutManager(new MyItemRecyclerViewAdapter.WrapContentLinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                        }else {
                                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                        }
                        //Dummy_wishlist.updatelist();
                       // wishlistAdapter=new MyItemRecyclerViewAdapter(getContext(),Dummy_trending.ITEMS, mListener);
                        recyclerView.setAdapter(MainActivity.trendingAdapter);

                }
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
