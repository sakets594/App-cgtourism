package com.app.infideap.whatsappsearchtoolbar.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.infideap.whatsappsearchtoolbar.MainActivity;
import com.app.infideap.whatsappsearchtoolbar.MyBounceInterpolator;
import com.app.infideap.whatsappsearchtoolbar.R;
import com.app.infideap.whatsappsearchtoolbar.list.ItemFragment.OnListFragmentInteractionListener;
import com.app.infideap.whatsappsearchtoolbar.list.dummy.DummyContent.DummyItem;
import com.app.infideap.whatsappsearchtoolbar.target_activity1;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * : Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    public List<DummyItem> mValues,filterList;;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    private final View.OnTouchListener mtouch=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.image_click));

            return false;
        }
    };
    private final View.OnLongClickListener mlongtouch=new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            v.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.image_click));
            return false;
        }

    };



    @Override
    public int getItemCount(){
        return filterList==null?0:filterList.size();
    }

    public MyItemRecyclerViewAdapter(Context context,List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mContext=context;
        filterList=new ArrayList<DummyItem>() ;
        this.filterList.addAll(this.mValues);
        mListener = new OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(DummyItem item) {
                Intent intent=new Intent(mContext,target_activity1.class);
                MainActivity.order=item.id;
                mContext.startActivity(intent);
            }
        };

    }
    public void swap(List<DummyItem> list){
        if (mValues != null) {
            mValues.clear();
            mValues.addAll(list);
        }
        else {
            mValues = list;
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return filterList!=null?filterList.size():0;
    }



    public long getItemId(int position) {
        return position;
    }

    public static class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }
        //... constructor

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("probe", "meet a IOOBE in RecyclerView");
            }
        }
        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }
        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

                private static final float SPEED = 300f;// Change this value (default=25f)

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return SPEED / displayMetrics.densityDpi;
                }

            };
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //final int holder.getAdapterPosition()=holder.getAdapterPosition();
        final DummyItem item=filterList.get(holder.getAdapterPosition());
        holder.mItem = filterList.get(holder.getAdapterPosition());
        holder.mIdView.setText(filterList.get(holder.getAdapterPosition()).p_name);
        holder.mplaceloc.setText(filterList.get(holder.getAdapterPosition()).loc);
        int tmpid= MainActivity.RESOURCE.getIdentifier(String.valueOf(filterList.get(holder.getAdapterPosition()).id), "drawable", MainActivity.PACKAGE_NAME);
        holder.mplaceimg.setImageResource(tmpid);
        try{if(item.favrt.charAt(0)=='n') {

            holder.mfavrt.setImageResource(R.drawable.heartborder);
        }
        else
            holder.mfavrt.setImageResource(R.drawable.heartfil);


        holder.mfavrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Toast.makeText(mContext,item.id,Toast.LENGTH_SHORT).show();
                DummyItem tmp=filterList.get(holder.getAdapterPosition());
                int tmpi;
                DummyItem tmpu=filterList.get(holder.getAdapterPosition());
                ListIterator<DummyItem> iter=MainActivity.wishlistAdapter.mValues.listIterator();

               if(item.favrt.charAt(0)=='n')
               {
                   Toast.makeText(mContext,"added to wishlist",Toast.LENGTH_SHORT).show();
                   tmpu.favrt="yes";
                   mValues.get(holder.getAdapterPosition()).favrt="yes";
                   holder.mfavrt.setImageResource(R.drawable.heartfil);
                   final Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);

                   // Use bounce interpolator with amplitude 0.2 and frequency 20
                   MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                   myAnim.setInterpolator(interpolator);
                   //holder.mfavrt.startAnimation(myAnim);
                   holder.mfavrt.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.image_click));
                    
                   MainActivity.wishlistAdapter.mValues.add(tmpu);
                    MainActivity.wishlistAdapter.notifyDataSetChanged();
                   MainActivity.mydb.updateData(item.id,item.cat,item.p_name,item.lat,item.long_,item.begin_,item.end_,item.loc,"yes");
                   //String id,String cat,String p_name,float lat,float long_,int col_6,int end,String loc ,String favrt
               }
               else
               {
                   tmpu.favrt="no";
                   mValues.get(holder.getAdapterPosition()).favrt="no";
                   holder.mfavrt.setImageResource(R.drawable.heartborder);
                   while(iter.hasNext()){
                       if(iter.next().id.equals(tmpu.id)) {
                           Toast.makeText(mContext,"removed from wishlist ",Toast.LENGTH_SHORT).show();
                           // MainActivity.wishlistFragment.recyclerView.getRecycledViewPool().clear();
                           iter.remove();
                       }
                   }
                   MainActivity.wishlistAdapter.notifyDataSetChanged();
                   MainActivity.mydb.updateData(item.id,item.cat,item.p_name,item.lat,item.long_,item.begin_,item.end_,item.loc,"no");

               }
               //Iterator<Items> iter = CartItems.iterator();
                iter=MainActivity.exploreAdapter.mValues.listIterator();

                while(iter.hasNext()) {
                    if (iter.next().id.equals(tmpu.id)) {
                        iter.set(tmpu);
                        MainActivity.exploreAdapter.notifyDataSetChanged();

                    }
                }


                iter=MainActivity.nearbyAdapter.mValues.listIterator();
                while(iter.hasNext()) {
                    if (iter.next().id.equals(tmpu.id)) {
                        iter.set(tmpu);
                        MainActivity.nearbyAdapter.notifyDataSetChanged();

                    }
                }

                iter=MainActivity.trendingAdapter.mValues.listIterator();
                while(iter.hasNext()) {
                    if (iter.next().id.equals(tmpu.id) ){
                        iter.set(tmpu);
                        MainActivity.trendingAdapter.notifyDataSetChanged();

                    }
                }


               MainActivity.updateAlldata();

            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    MainActivity.order=item.id;
                    MainActivity.lt=item.lat;
                    MainActivity.ln=item.long_;
                    MainActivity.title=item.p_name;

                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.mView.setOnTouchListener(mtouch);}
        catch (Exception e){
            holder.mView.setVisibility(View.GONE);
            //holder.setIsRecyclable(false);
            holder.setVisibility(false);
        }
        //holder.mView.setOnLongClickListener(mlongtouch);
        ;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mplaceloc;
        public DummyItem mItem;
        public final ImageView mplaceimg;
        public final ImageView mfavrt;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.placename);
            mplaceloc = (TextView) view.findViewById(R.id.placeloc);
            mplaceimg=(ImageView)view.findViewById(R.id.placepic);
            mfavrt=view.findViewById(R.id.favrt);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mplaceloc.getText() + "'";
        }
        public void setVisibility(boolean isVisible){
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
            if (isVisible){
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            }else{
                itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
            }
            itemView.setLayoutParams(param);
        }


    }

    public void filter(final String text) {
        // Searching could be complex..so we will dispatch it to a different thread...
        //Toast.makeText(mContext,"infilter",Toast.LENGTH_SHORT).show();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Clear the filter list
                    filterList.clear();
                    // If there is no search value, then add all original list items to filter list
                    if (text.length() == 0) {
                        filterList.addAll(mValues);
                    } else {
                        // Iterate in the original List and add it to filter list...
                        for (DummyItem item : mValues) {
                            if (item.p_name.trim().toLowerCase().contains(text.trim().toLowerCase()) ||
                                    item.loc.trim().toLowerCase().contains(text.trim().toLowerCase())
                                    ) {
                                // Adding Matched items

                                filterList.add(item);
                            }
                        }
                    }


                    // Set on UI Thread
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Notify the List that the DataSet has changed...
                            try {
                                notifyDataSetChanged();
                            } catch (Exception e) {
                            }
                        }
                    });

                }
            }).start();
        }catch (Exception e){}
        ;
        }


    }





