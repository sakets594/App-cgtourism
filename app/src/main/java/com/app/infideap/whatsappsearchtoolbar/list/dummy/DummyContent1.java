package com.app.infideap.whatsappsearchtoolbar.list.dummy;

import com.app.infideap.whatsappsearchtoolbar.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample placeloc for user interfaces created by
 * Android template wizards.
 * <p>
 *
 */
public class DummyContent1 {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        int i=1;
        MainActivity.Alldata.moveToFirst();
       /*do
        {
            addItem(new DummyItem(MainActivity.Alldata.getString(0),MainActivity.Alldata.getString(1),MainActivity.Alldata.getString(2),MainActivity.Alldata.getFloat(3),MainActivity.Alldata.getFloat(4),MainActivity.Alldata.getInt(5),MainActivity.Alldata.getInt(6),MainActivity.Alldata.getString(7),MainActivity.Alldata.getString(8)));
        }while(MainActivity.Alldata.moveToNext());
        MainActivity.Alldata.moveToFirst();
*/
    }
    /*public static void updatelist()
    {
        ITEMS = new ArrayList<DummyItem>();
        MainActivity.Alldata.moveToFirst();
        do
        {
            addItem(new DummyItem(MainActivity.Alldata.getString(0),MainActivity.Alldata.getString(1),MainActivity.Alldata.getString(2),MainActivity.Alldata.getString(3)));
        }while(MainActivity.Alldata.moveToNext());
        MainActivity.Alldata.moveToFirst();
    }
*/

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.id), item);
    }



    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of placeloc.
     */
    public static class DummyItem {

        public String id;
        public String cat;
        public String p_name;
        public float lat;
        public float long_;
        public int begin_;
        public int end_;
        public String favrt;
        public  String loc;

        public DummyItem()
        {

        }


        public DummyItem(String id_, String cat, String p_name, float lat, float long_, int begin_, int end_,String loc, String favrt) {
            this.id = id_;
            this.cat = cat;
            this.p_name = p_name;
            this.lat = lat;
            this.long_ = long_;
            this.begin_ = begin_;
            this.end_ = end_;
            this.favrt = favrt;
            this.loc=loc;
        }


    }


}
