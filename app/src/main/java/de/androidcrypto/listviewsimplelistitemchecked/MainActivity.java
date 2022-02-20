package de.androidcrypto.listviewsimplelistitemchecked;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // source: https://o7planning.org/10923/android-listview-with-checkbox-using-arrayadapter
    // funktioniert

    public static final String TAG = "ListViewExample";

    private ListView listView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView)findViewById(R.id.listView);
        this.button = (Button)findViewById(R.id.button);

        // CHOICE_MODE_NONE: (Default)
        // (listView.setItemChecked(..) doest not work with CHOICE_MODE_NONE).
        // CHOICE_MODE_SINGLE:
        // CHOICE_MODE_MULTIPLE:
        // CHOICE_MODE_MULTIPLE_MODAL:
        this.listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // this.listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // nur ein eintrag kann ausgewaehlt werden
        //this.listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL); // app crashes


        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: " +position);
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                UserAccount user = (UserAccount) listView.getItemAtPosition(position);
                user.setActive(!currentCheck);
            }
        });
        //

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printSelectedItems();
            }
        });

        this.initListViewData();
    }

    private void initListViewData()  {
        UserAccount tom = new UserAccount("Tom","admin");
        UserAccount jerry = new UserAccount("Jerry","user");
        UserAccount donald = new UserAccount("Donald","guest", false);

        UserAccount[] users = new UserAccount[]{tom,jerry, donald};

        // add 20 more items to make the listview scrollable
        for (int i = 0; i < 20; i++){
            UserAccount test = new UserAccount("name " + i, "type " + i);
            users = addX(users, test);
        }

        // android.R.layout.simple_list_item_checked:
        // ListItem is very simple (Only one CheckedTextView).
        // hat nur einen grünen haken
        //ArrayAdapter<UserAccount> arrayAdapter = new ArrayAdapter<UserAccount>(this, android.R.layout.simple_list_item_checked , users);
        // sieht aus wie eine checkbox
        ArrayAdapter<UserAccount> arrayAdapter = new ArrayAdapter<UserAccount>(this, android.R.layout.simple_list_item_multiple_choice , users);

        this.listView.setAdapter(arrayAdapter);

        /*
        for(int i=0;i< users.length; i++ )  {
            this.listView.setItemChecked(i,users[i].isActive());
        }
         */
        //this.listView.setItemChecked(1, true);
    }

    // When user click "Print Selected Items".
    public void printSelectedItems()  {


        SparseBooleanArray sp = listView.getCheckedItemPositions();

        StringBuilder sb= new StringBuilder();
        //System.out.println("sp.size: " + sp.size());
        //for(int i=0;i<sp.size();i++){ // this is not working correctly
        for(int i=0;i<listView.getAdapter().getCount();i++){
            /*
            System.out.println(" for i: " + i + " size: " + sp.size());
            System.out.println("sp.get 0: " + sp.get(0));
            System.out.println("sp.get 1: " + sp.get(1));
            System.out.println("sp.get 2: " + sp.get(2));*/
            //if(sp.valueAt(i)==true){ // this is not working
            if(sp.get(i)==true){
                //System.out.println("i: " + i + " is true");
                UserAccount user= (UserAccount) listView.getItemAtPosition(i);
                // Or:
                //String s = ((CheckedTextView) listView.getChildAt(i)).getText().toString();
                //System.out.println("user: " + user.getUserName());
                //
                String s= user.getUserName();
                sb = sb.append(" "+s);
            }
        }
        // Toast.makeText(this, "Selected items are: "+sb.toString(), Toast.LENGTH_LONG).show();
        int itemsChecked = listView.getCheckedItemCount();
        Toast.makeText(this, "all:"+itemsChecked+" Selected items are: "+sb.toString(), Toast.LENGTH_SHORT).show();
    }

    public void printSelectedItemsOriginal()  {
        // is not working correctly on multiple items

        SparseBooleanArray sp = listView.getCheckedItemPositions();

        StringBuilder sb= new StringBuilder();

        for(int i=0;i<sp.size();i++){
            if(sp.valueAt(i)==true){
                UserAccount user= (UserAccount) listView.getItemAtPosition(i);
                // Or:
                // String s = ((CheckedTextView) listView.getChildAt(i)).getText().toString();
                //
                String s= user.getUserName();
                sb = sb.append(" "+s);
            }
        }
        Toast.makeText(this, "Selected items are: "+sb.toString(), Toast.LENGTH_LONG).show();
    }

    // Function to add x in arr
    public static UserAccount[] addX(UserAccount oldArray[], UserAccount newEntry)
    {
        int i;

        // create a new ArrayList
        List<UserAccount> arrlist
                = new ArrayList<UserAccount>(
                Arrays.asList(oldArray));

        // Add the new element
        arrlist.add(newEntry);

        // Convert the Arraylist to array
        oldArray = arrlist.toArray(oldArray);

        // return the array
        return oldArray;
    }

}
