package com.fyp.furqan.security;

        import java.util.ArrayList;


        import android.app.Activity;
        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.view.ContextMenu;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ContextMenu.ContextMenuInfo;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemLongClickListener;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

public class Locations_List extends Activity implements OnItemLongClickListener,AdapterView.OnItemClickListener{
    /** Called when the activity is first created. */
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    private RemindersDbAdapter mDbAdapter;
    private   ArrayList<String> numberList=null;
    private   ArrayList<String> idList=null;
    private   ArrayList<Bitmap> bitm=null;
    ListView list;
    // private int[] position=new int[100];
    // private String[] titleItems=new String[100];
    private int clickItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations__list);

       // startService(new Intent(getApplicationContext(), MyService.class));
        numberList = new ArrayList<String>();
        idList = new ArrayList<String>();
        list = (ListView)findViewById(R.id.anb);

        bitm =new ArrayList<Bitmap>();
        mDbAdapter=new RemindersDbAdapter(this);
        mDbAdapter.open();
        registerForContextMenu(list);
        displayLits();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                clickItem = Integer.parseInt(idList.get(position));
            }
        });
    }
    public void displayLits()
    {

        Cursor c=mDbAdapter.fetchAllReminders();
        numberList.clear();
        idList.clear();
        startManagingCursor(c);
        c.moveToFirst();
        //NEW
        if (c .moveToFirst()) {

            while (c.isAfterLast() == false) {
                String  id= c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_ROWID));

                String name = c.getString(c
                        .getColumnIndex(RemindersDbAdapter.KEY_TITLE));
              bitm.add(BitmapFactory.decodeFile(c.getString(c.getColumnIndex(RemindersDbAdapter.picPath))));
                numberList.add(name);
                idList.add(id);

                c.moveToNext();
            }
        }
        //
        CustomList adapter = new CustomList(Locations_List.this, numberList, bitm);
        list.setAdapter(adapter);

       //setListAdapter(bd);

        //setListAdapter(ad);

    }
   /* @Override
    public int getSelectedItemPosition() {
        // TODO Auto-generated method stub
        return super.getSelectedItemPosition();
    }
    //
    // list item selected
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // clickItem=position;
        clickItem=Integer.parseInt(idList.get(position));
        //Toast.makeText(getBaseContext(), "number  "+clickItem, Toast.LENGTH_SHORT).show();

    }*/
    // creating menu


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.items_menu, menu);
    }
    //
    //
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menu_delete:

                Boolean bb=    mDbAdapter.deleteReminder(clickItem);
                displayLits();
                return true;
            case R.id.menu_cancel:

                return true;
            case R.id.menu_update:
                Intent imn = new Intent(getApplicationContext(),Locaion_Config.class);
               Locaion_Config.Updates =true;
               // int lk=Integer.parseInt(idList.get(clickItem));
                imn.putExtra("idRow",clickItem);
                startActivity(imn);
                return true;
        }
        return super.onContextItemSelected(item);
    }
    public boolean onItemLongClick(AdapterView<?> arg, View arg1, int pos,
                                   long id) {
        // TODO Autogenerated method stub

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clickItem=Integer.parseInt(idList.get(position));
        Toast.makeText(getBaseContext(), "number  "+clickItem, Toast.LENGTH_SHORT).show();

    }
}