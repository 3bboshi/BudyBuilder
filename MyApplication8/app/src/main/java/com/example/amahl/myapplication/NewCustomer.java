package com.example.amahl.myapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class NewCustomer extends AppCompatActivity {

    final Calendar c = Calendar.getInstance();
    public int year = c.get(Calendar.YEAR);
    final int month = c.get(Calendar.MONTH) + 1;
    final int day = c.get(Calendar.DAY_OF_MONTH);
    ImageView imageview;

    private ProgressDialog progressDialog;
    EditText EEname;
    EditText Emobile;
    EditText Eremark;
    Button Save_data;
    EditText txtInvoiceNo;
    EditText KDamount;
    EditText Tdate ;
    EditText ToDate;
    EditText Ivoice;
    TextView imageId;

ListView listView ;

    String encoding ;


    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Storing server url into String variable.
    String HttpUrl = "https://o-j-a2003.000webhostapp.com/budy_inseart.php";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.Search): {
                Intent serchIntent = new Intent(this, SearchCustomer.class);
                startActivity(serchIntent);
            }


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        final EditText TxtJoinDate = findViewById(R.id.DateNewCusttxt);
        final EditText Todatetxt = findViewById(R.id.txtTodate);
        Button BtuToDate = findViewById(R.id.btutodate);
        imageview = findViewById(R.id.ImageView01);

        progressDialog = new ProgressDialog(this);
         EEname= findViewById(R.id.Ename);
         Emobile=(EditText)findViewById(R.id.editText3);
        txtInvoiceNo=(EditText)findViewById(R.id.InvoiceNo);
        KDamount=(EditText)findViewById(R.id.editText5);
        Tdate=(EditText)findViewById(R.id.DateNewCusttxt);
        ToDate=(EditText)findViewById(R.id.txtTodate);
        Ivoice=(EditText)findViewById(R.id.InvoiceNo);
        imageId=(TextView)findViewById(R.id.PhotoId);
        Eremark=(EditText)findViewById(R.id.editText4);

        Save_data=(Button)findViewById(R.id.btu_save);




        //imageview.setImageURI();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(NewCustomer.this);

        chkinternet();

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while ((!isInterrupted())) {

                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                long date = System.currentTimeMillis();

                                TxtJoinDate.setText(year + "-" + month + "-" + day);
                                TxtJoinDate.setText(year + "-" + month + "-" + day);
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {

                }
            }
        };
        t.start();


        Save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Ename = EEname.getText().toString().trim();
                String Emob = Emobile.getText().toString().trim();
                String EEremark = Eremark.getText().toString().trim();
                String EKD = KDamount.getText().toString().trim() ;
                String Tdate1 = Tdate.getText().toString();
                String ToDate1 = ToDate.getText().toString() ;
                String Einvoice = Ivoice.getText().toString().trim() ;
                String EimageId = imageId.getText().toString().trim() ;


                Bitmap Bimg =((BitmapDrawable)imageview.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Bimg.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                encoding= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);



    Response.Listener<String> reponslstener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

       JSONObject jsonObject = new JSONObject(response);
        boolean success = jsonObject.getBoolean("Success");


 if (success){Toast.makeText(NewCustomer.this, "تم التسجيل", Toast.LENGTH_SHORT).show(); }else{Toast.makeText(NewCustomer.this, "يوجد خطأ", Toast.LENGTH_SHORT).show();}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

send_data send_data = new send_data(Ename,Emob,EEremark,EKD,Tdate1,ToDate1, Einvoice,EimageId,encoding,reponslstener);

    RequestQueue queue = Volley.newRequestQueue(NewCustomer.this);
    queue.add(send_data);}
        });




        BtuToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewCustomer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Todatetxt.setText(year + "-" + (month + 1) + "-" + dayOfMonth);

                    }
                }, year, month, day);
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();
            }
        });

    }

    static final int CAMERA_PIC_REQUEST = 100;

    public void captch(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        Toast.makeText(NewCustomer.this, "Hello", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            imageview.setImageBitmap((Bitmap) data.getExtras().get("data"));

            try {
                createImageFile();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        imageId.setText(imageFileName);
        return image;
    }

    public void chkinternet() {

        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();

        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage(getString(R.string.Waite));
            progressDialog.setCancelable(true);
            progressDialog.show();

        }
    }

}


