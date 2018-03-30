package com.app.infideap.whatsappsearchtoolbar.list.dummy;

import com.app.infideap.whatsappsearchtoolbar.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sks on 13/3/18.
 */

public class Dummy_trending {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyContent.DummyItem> ITEMS = new ArrayList<DummyContent.DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyContent.DummyItem> ITEM_MAP = new HashMap<String, DummyContent.DummyItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        int i=1;
        MainActivity.Alldata.moveToFirst();
        do
        { if(MainActivity.Alldata.getInt(5)<=MainActivity.Alldata.getInt(6)&&(MainActivity.Alldata.getInt(5)<=MainActivity.month&&MainActivity.month<=MainActivity.Alldata.getInt(6))
                ||((MainActivity.Alldata.getInt(5)<=MainActivity.Alldata.getInt(6)
                &&MainActivity.month>=MainActivity.Alldata.getInt(6)&&MainActivity.month<=MainActivity.Alldata.getInt(5))))
            addItem(new DummyContent.DummyItem(MainActivity.Alldata.getString(0),MainActivity.Alldata.getString(1),MainActivity.Alldata.getString(2),MainActivity.Alldata.getFloat(3),MainActivity.Alldata.getFloat(4),MainActivity.Alldata.getInt(5),MainActivity.Alldata.getInt(6),MainActivity.Alldata.getString(7),MainActivity.Alldata.getString(8)));
        }while(MainActivity.Alldata.moveToNext());
        MainActivity.Alldata.moveToFirst();

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

    private static void addItem(DummyContent.DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
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



}
