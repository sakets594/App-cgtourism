package com.app.infideap.whatsappsearchtoolbar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.infideap.whatsappsearchtoolbar.list.dummy.DummyContent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by sks on 14/3/18.
 */

public class SearchableAdapter  extends BaseAdapter implements Filterable {

    public List<DummyContent.DummyItem> mValues = null;
    public List<DummyContent.DummyItem>filterList = null;

    private LayoutInflater mInflater;

    Context mContext;
    View.OnClickListener mListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            v.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.image_click));

        }
    };

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


    public SearchableAdapter(Context context, List<DummyContent.DummyItem> data) {
        this.filterList = data ;
        this.mValues = data ;
        mInflater = LayoutInflater.from(context);
        mContext=context;
    }

    public int getCount() {
        return filterList!=null?filterList.size():0;
    }

    public Object getItem(int position) {
        return filterList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        ;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.wishlist_item, null);
            holder = new ViewHolder(convertView);
            //holder.mIdView = (TextView) convertView.findViewById(R.id.placename);
            /*holder.mplaceloc = (TextView) convertView.findViewById(R.id.placeloc);
            holder.mplaceimg = (ImageView) convertView.findViewById(R.id.placepic);
            holder.mfavrt = (ImageView) convertView.findViewById(R.id.favrt);*/
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DummyContent.DummyItem item=filterList.get(position);
        holder.mItem = filterList.get(position);

        int tmpid= MainActivity.RESOURCE.getIdentifier((filterList.get(position).id), "drawable", MainActivity.PACKAGE_NAME);
        holder.mplaceimg.setImageResource(tmpid);
        holder.mplaceloc.setText(filterList.get(position).loc);
        holder.mIdView.setText(filterList.get(position).p_name);
        if(item.favrt.charAt(0)=='n') {

            holder.mfavrt.setImageResource(R.drawable.heartborder);
        }
        else
            holder.mfavrt.setImageResource(R.drawable.heartfil);

        holder.mfavrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,item.id,Toast.LENGTH_SHORT).show();
                DummyContent.DummyItem tmp=filterList.get(position);
                int tmpi;
                DummyContent.DummyItem tmpu=filterList.get(position);
                Iterator<DummyContent.DummyItem>tmpter=MainActivity.wishlistAdapter.filterList.listIterator();
                ListIterator<DummyContent.DummyItem> iter=MainActivity.wishlistAdapter.mValues.listIterator();

                if(item.favrt.charAt(0)=='n')
                {
                    Toast.makeText(mContext,"added to wishlist",Toast.LENGTH_SHORT).show();
                    tmpu.favrt="yes";
                    filterList.get(position).favrt="yes";
                    holder.mfavrt.setImageResource(R.drawable.heartfil);
                    MainActivity.wishlistAdapter.filterList.add(tmpu);
                    MainActivity.wishlistAdapter.notifyDataSetChanged();
                    MainActivity.mydb.updateData(item.id,item.cat,item.p_name,item.lat,item.long_,item.begin_,item.end_,item.loc,"yes");
                }
                else
                {
                    Toast.makeText(mContext,"removed from wishlist",Toast.LENGTH_SHORT).show();
                    tmpu.favrt="no";
                    MainActivity.wishlistAdapter.filterList.get(position).favrt="no";
                    MainActivity.wishlistAdapter.mValues.get(position).favrt="no";

                    holder.mfavrt.setImageResource(R.drawable.heartborder);
                    while(iter.hasNext()){
                        if(iter.next().id.equals(tmpu.id)) {

                            // MainActivity.wishlistFragment.recyclerView.getRecycledViewPool().clear();
                            iter.remove();
                        }
                    }

                    //MainActivity.wishlistAdapter.filterList.addAll(MainActivity.wishlistAdapter.mValues);

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
                /*iter=MainActivity.exploreAdapter.filterList.listIterator();
                while(iter.hasNext()) {
                    if (iter.next().id.equals(tmpu.id)) {
                        iter.set(tmpu);
                        MainActivity.exploreAdapter.notifyDataSetChanged();

                    }
                }*/


                iter=MainActivity.nearbyAdapter.mValues.listIterator();
                while(iter.hasNext()) {
                    if (iter.next().id.equals(tmpu.id)) {
                        iter.set(tmpu);
                        MainActivity.nearbyAdapter.notifyDataSetChanged();

                    }
                }
/*                iter=MainActivity.nearbyAdapter.filterList.listIterator();
                while(iter.hasNext()) {
                    if (iter.next().id.equals(tmpu.id)) {
                        iter.set(tmpu);
                        MainActivity.nearbyAdapter.notifyDataSetChanged();

                    }
                }
*/
                iter=MainActivity.trendingAdapter.mValues.listIterator();
                while(iter.hasNext()) {
                    if (iter.next().id.equals(tmpu.id) ){
                        iter.set(tmpu);
                        MainActivity.trendingAdapter.notifyDataSetChanged();

                    }
                }
  /*              iter=MainActivity.trendingAdapter.filterList.listIterator();
                while(iter.hasNext()) {
                    if (iter.next().id.equals(tmpu.id)) {
                        iter.set(tmpu);
                        MainActivity.trendingAdapter.notifyDataSetChanged();

                    }
                }

*/
                MainActivity.updateAlldata();

            }
        });
        holder.mView.setOnTouchListener(mtouch);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.order=item.id;
                MainActivity.lt=item.lat;
                MainActivity.ln=item.long_;
                MainActivity.title=item.p_name;
                Intent intent=new Intent(mContext,target_activity1.class);
                 mContext.startActivity(intent);
            }
        });

        // If weren't re-ordering this you could rely on what you set last time
        /*holder.text.setText(mValues.get(position).name);
        holder.text.setText(filterList.get(position).name);*/
        return convertView;
    }



    static class ViewHolder {
        public View mView;
        public TextView mIdView;
        public TextView mplaceloc;
        public DummyContent.DummyItem mItem;
        public ImageView mplaceimg;
        public ImageView mfavrt;


        public ViewHolder(View view) {
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.placename);
            mplaceloc = (TextView) view.findViewById(R.id.placeloc);
            mplaceimg = (ImageView) view.findViewById(R.id.placepic);
            mfavrt = view.findViewById(R.id.favrt);
        }
    }

    @Override
    public Filter getFilter() {
        final Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {


                filterList = (ArrayList<DummyContent.DummyItem>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<DummyContent.DummyItem> FilteredArrList = new ArrayList<DummyContent.DummyItem>();

                if (mValues == null) {
                    mValues = new ArrayList<DummyContent.DummyItem>(filterList); // saves the original data in mOriginalValues

                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mValues.size();
                    results.values = mValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mValues.size(); i++) {

                        String data = mValues.get(i).p_name;
                        String data2 =mValues.get(i).loc;

                        if (data.trim().toLowerCase().contains(constraint.toString().trim()) || data2.trim().toLowerCase().contains(constraint.toString())) {
                            DummyContent.DummyItem hmap=new DummyContent.DummyItem();
                           hmap=mValues.get(i);
                            FilteredArrList.add(mValues.get(i));
                            Log.e("DEBUG", "name : "+data);
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }






}
