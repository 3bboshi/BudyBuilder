package com.example.amahl.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchCustomer extends AppCompatActivity {
    EditText Search;
    EditText txt;

    ListView listView;
    RequestQueue requestQueue;
    Button SearchBtu;

    final String mJSONURLString = "http://192.168.8.102:81/login/budy_select.php";

    final String budyMobileSearch = "http://192.168.8.102:81/login/budy_sarch.php";

    ArrayList<listviewcust> listitems = new ArrayList<listviewcust>();


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.poupup_menu, menu);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_customer);
        Search = (EditText) findViewById(R.id.SearchCustName);
        listView = (ListView) findViewById(R.id.SearchListView);


        Search.setFocusable(false);
        Search.setFocusableInTouchMode(false);
        SearchBtu = (Button) findViewById(R.id.button3);

        requestQueue = Volley.newRequestQueue(this);


        SearchBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listitems.clear();
                RadioButton R_name = (RadioButton) findViewById(R.id.radio_custName);
              //  RadioButton R_mobile = (RadioButton) findViewById(R.id.radio_custMobile);

                if (R_name.isChecked()) {
                    NameSearch();
                } else {
                    MobileSearch();
                }

            }

        });

        final PopupMenu popupMenu = new PopupMenu(SearchCustomer.this , listView);
        popupMenu.getMenuInflater().inflate(R.menu.poupup_menu,popupMenu.getMenu());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {



                final TextView textViewName = (TextView) view.findViewById(R.id.custname);


popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem item) {





        switch (item.getItemId()){
            case  R.id.edit:

                AlertDialog.Builder builder = new AlertDialog.Builder(SearchCustomer.this);
                View view = getLayoutInflater().inflate(R.layout.edit_item, null);



                final EditText textName = (EditText) view.findViewById(R.id.T1);

               textName.setText(textViewName.getText());



                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();

         Toast.makeText(getApplicationContext(), "Edit Menu ", Toast.LENGTH_SHORT).show();


        }


        return true;
    }
});


                popupMenu.show();

            }
        }

        );


    }


    public void NameSearch() {

        final String Ename = Search.getText().toString();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, "http://192.168.8.102:81/login/budy_search_name.php?Ename=" + Ename, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("All_users");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject emp = jsonArray.getJSONObject(i);

                        String cust_name = emp.getString("cust_name");
                        String cust_mobile = emp.getString("cust_mobile");
                        String remark = emp.getString("remark");


                        String id = emp.getString("id");
                        String image_id = emp.getString("image_id");
                        String valid_date = emp.getString("valid_date");



 listitems.add(new listviewcust(cust_name, cust_mobile, remark, id, image_id,valid_date));
listAllItem();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }


      /*  public void JSONPublicSearch() {

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, mJSONURLString, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("All_users");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject emp = jsonArray.getJSONObject(i);

                        String cust_name = emp.getString("cust_name");
                        String cust_mobile = emp.getString("cust_mobile");
                        String remark = emp.getString("remark");
                        String img = emp.getString("img");


                   listitems.add(new listviewcust(cust_name, cust_mobile, remark));
                        listAllItem();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
                //       JSON() ;
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

    } // Public Search
*/

    public void MobileSearch() {

        final String Emob = Search.getText().toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://192.168.8.102:81/login/budy_search_mobile.php?Emob=" + Emob, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("All_users");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject emp = jsonArray.getJSONObject(i);
                        String cust_name = emp.getString("cust_name");
                        String cust_mobile = emp.getString("cust_mobile");
                        String remark = emp.getString("remark");

                        String id = emp.getString("id");
                        String valid_date = emp.getString("valid_date");

                        String image_id = emp.getString("image_id");

                        //      listitems.clear();
                        listitems.add(new listviewcust(cust_name, cust_mobile, remark, id, image_id,valid_date));
                        listAllItem();


                        ;
                    }
                } catch (JSONException e) {

                 e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);

    }

    //endregion
    //</editor-fold>





    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_custName:
                if (checked)
                    // Pirates are the best
                    Search.setFocusableInTouchMode(true);
                Search.setHint("Input Customer Name");
                Search.setInputType(InputType.TYPE_CLASS_TEXT);

                break;
            case R.id.radio_custMobile:
                if (checked)
                    // Ninjas rule
                    Search.setFocusableInTouchMode(true);
                Search.setHint("Input Customer Mobile");
                Search.setInputType(InputType.TYPE_CLASS_NUMBER);

                break;
        }
    }


    public void listAllItem() {
        ListAdapter la = new ListAdapter(listitems);
        listView.setAdapter(la);
    }

    class ListAdapter extends BaseAdapter {
        ArrayList<listviewcust> listitem = new ArrayList<listviewcust>();

        ListAdapter(ArrayList<listviewcust> listitem) {

            this.listitem = listitem;
        }

        @Override
        public int getCount() {
            return listitem.size();
        }

        @Override
        public Object getItem(int position) {
            return listitem.get(position).Name;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();

            final View view = layoutInflater.inflate(R.layout.list_cust, null);


            TextView textViewName = (TextView) view.findViewById(R.id.custname);
            TextView textViewMob = (TextView) view.findViewById(R.id.custmobile);
            TextView textViewremark = (TextView) view.findViewById(R.id.custremark);
            TextView textID = (TextView) view.findViewById(R.id.textView2);
            ImageView imageView = view.findViewById(R.id.imgview);

            TextView txtcustendcontract = (TextView) view.findViewById(R.id.custendcontract);




            textViewName.setText(listitem.get(i).Name);
            textViewMob.setText(listitem.get(i).Mobile);
            textViewremark.setText(listitem.get(i).Remark);
            txtcustendcontract.setText(listitem.get(i).valid_date);


            textID.setText(listitem.get(i).Id);
            String path = listitem.get(i).image_id;

            Picasso.with(SearchCustomer.this).load("http://192.168.8.102:81/login/" + path + ".jpg").into(imageView);


            //  Toast.makeText(SearchCustomer. this, "" + listitem.get(i).image_id, Toast.LENGTH_LONG).show();

            return view;
        }
    }

}
