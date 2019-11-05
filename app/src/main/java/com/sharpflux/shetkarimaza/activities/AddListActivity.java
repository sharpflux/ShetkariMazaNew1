package com.sharpflux.shetkarimaza.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.SaveProductInfo;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.SQLiteDatabaseHelper;
import com.sharpflux.shetkarimaza.sqlite.UserInfoDBManager;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.sharpflux.shetkarimaza.sqlite.UserInfoDBManager.areaheactor;
import static com.sharpflux.shetkarimaza.sqlite.UserInfoDBManager.availablityInMonths;
import static com.sharpflux.shetkarimaza.sqlite.UserInfoDBManager.days;
import static com.sharpflux.shetkarimaza.sqlite.UserInfoDBManager.expectedPrice;
import static com.sharpflux.shetkarimaza.sqlite.UserInfoDBManager.imagename;
import static com.sharpflux.shetkarimaza.sqlite.UserInfoDBManager.productType;
import static com.sharpflux.shetkarimaza.sqlite.UserInfoDBManager.villagenam;

public class AddListActivity extends AppCompatActivity {
    JSONArray array;
    StringBuilder
            builder;
    private Button btnsubmit;
    private UserInfoDBManager userInfoDBManager = null;

    private ListView userAccountListView = null;
    SQLiteCursor sqcursor;
    Bundle bundle;
    String type = "", varity = "", quality = "", unit = "", month = "", state = "", district = "", taluka = "";

    String ProductId,organic,certificateno,SurveyNo;
    Cursor cursor;
    int UserId;
    private TextView userAccountListEmptyTextView = null;

    private SimpleCursorAdapter listViewDataAdapter = null;
    ProgressDialog mProgressDialog;
    Document doc;
    String xmlImg;
    Bitmap bitmap;
    SQLiteDatabaseHelper database;
    ImageView row_cartlist_ivDelete;
    private String fromColumnArr[] = {productType, UserInfoDBManager.productVariety, UserInfoDBManager.quality, expectedPrice};
    CheckBox itemCheckbox;
    private final int toViewIdArr[] = {R.id.user_account_list_item_id, R.id.user_account_list_item_user_name, R.id.user_account_list_item_password, R.id.user_account_list_item_email};

    // Store user checked account DTO.
    private List<SaveProductInfo> userCheckedItemList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        builder = new StringBuilder();
        btnsubmit = findViewById(R.id.btnSubmitDb);
        row_cartlist_ivDelete = findViewById(R.id.row_cartlist_ivDelete);

        userAccountListView = (ListView) findViewById(R.id.user_account_list_view);
        userAccountListEmptyTextView = (TextView) findViewById(R.id.user_account_list_empty_text_view);
        userAccountListView.setEmptyView(userAccountListEmptyTextView);

        // builder.append("<?xml version=\"1.0\" ?>");
        // Initialize the progress dialog
        mProgressDialog = new ProgressDialog(AddListActivity.this);
        mProgressDialog.setIndeterminate(false);
        // Progress dialog horizontal style
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Progress dialog title
        mProgressDialog.setTitle("Saving data");
        // Progress dialog message
        mProgressDialog.setMessage("Please wait, we are saving your data...");

        userCheckedItemList = new ArrayList<SaveProductInfo>();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            ProductId = bundle.getString("ProductId");
            type = bundle.getString("productTypeId");
            varity = bundle.getString("productVariety");
            quality = bundle.getString("qualityId");
            unit = bundle.getString("unitId");
            //month = bundle.getString("monthId");
            state = bundle.getString("stateId");
            district = bundle.getString("districtId");
            taluka = bundle.getString("talukaId");
            organic= bundle.getString("organic");
            certificateno = bundle.getString("certificateno");
            SurveyNo = bundle.getString("SurveyNo");


        }

        User user = SharedPrefManager.getInstance(this).getUser();
        UserId = user.getId();

        // Get SQLite database query cursor.
        userInfoDBManager = new UserInfoDBManager(getApplicationContext());
        userInfoDBManager.open();
        Cursor cursor = userInfoDBManager.getAllAccountCursor();

        listViewDataAdapter =
                listViewDataAdapter = new SimpleCursorAdapter(this, R.layout.activity_user_account_list_view_item, cursor, fromColumnArr, toViewIdArr, CursorAdapter.FLAG_AUTO_REQUERY);



        // Set simple cursor adapter to list view.
        userAccountListView.setAdapter(listViewDataAdapter);
        // userAccountListView.setEnabled(false);
        //userAccountListView.setFocusable(false);
        userAccountListView.setClickable(false);
        userAccountListView.setSelector(android.R.color.transparent);
        // When list view item is clicked.
        userAccountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long viewId) {
                // Get list view item SQLiteCursor object.
                Object clickItemObject = adapterView.getAdapter().getItem(index);
                sqcursor = (SQLiteCursor) clickItemObject;

                // Get row column data.
                //   int rowId = sqcursor.getInt(sqcursor.getColumnIndex(UserInfoDBManager.TABLE_ACCOUNT_COLUMN_ID));
                String producttype = sqcursor.getString(sqcursor.getColumnIndex(productType));
                String productvarity = sqcursor.getString(sqcursor.getColumnIndex(UserInfoDBManager.productVariety));
                String quality = sqcursor.getString(sqcursor.getColumnIndex(UserInfoDBManager.quality));
                String price = sqcursor.getString(sqcursor.getColumnIndex(expectedPrice));
                String image = sqcursor.getString(sqcursor.getColumnIndex(imagename));


                // Create a UserAccountDTO object to save row column data.

                String id="";
                String productVariety="";
                String quantity="";
                String address="";
                SaveProductInfo userAccountDto = new SaveProductInfo(id, productType, productVariety, quality, quantity, unit, expectedPrice, days, availablityInMonths, address, state, district, taluka, villagenam, areaheactor, imagename);
                userAccountDto.setProductType(producttype);
                userAccountDto.setProductVariety(productvarity);
                userAccountDto.setQuality(quality);
                userAccountDto.setExpectedPrice(price);
                userAccountDto.setImagename(image);

                // Get checkbox object.
               itemCheckbox = (CheckBox) view.findViewById(R.id.user_account_list_item_checkbox);
                boolean checkboxChecked = false;
                if (itemCheckbox.isChecked()) {
                    itemCheckbox.setChecked(false);
                    checkboxChecked = false;
                } else {
                    itemCheckbox.setChecked(true);
                    checkboxChecked = true;
                }

                // Add ( or remove ) userAccountDto from the instance variable userCheckedItemList.
                addCheckListItem(userAccountDto, checkboxChecked);

                // Show user select list view item id.
              Toast.makeText(getApplicationContext(), "Select id : " + getUserCheckedItemIds(), Toast.LENGTH_SHORT).show();
            }
        });


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute("Submit");

            }
        });



    }



    private String getUserCheckedItemIds() {
        StringBuffer retBuf = new StringBuffer();

        if (userCheckedItemList != null) {
            int size = userCheckedItemList.size();
            for (int i = 0; i < size; i++) {
                SaveProductInfo tmpDto = userCheckedItemList.get(i);
                retBuf.append(tmpDto.getId());
                retBuf.append(" ");
            }
        }

        return retBuf.toString().trim();
    }

    private static Document convertStringToXMLDocument(String xmlString) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addCheckListItem(SaveProductInfo userAccountDto, boolean add) {


        if (userCheckedItemList != null) {
            boolean accountExist = false;
            int existPosition = -1;

            // Loop to check whether the user account dto exist or not.
            int size = userCheckedItemList.size();
            for (int i = 0; i < size; i++) {
                SaveProductInfo tmpDto = userCheckedItemList.get(i);
                if (tmpDto.getId() == userAccountDto.getId()) {
                    accountExist = true;
                    existPosition = i;
                    break;
                }
            }

            if (add) {
                // If not exist then add it.
                if (!accountExist) {
                    userCheckedItemList.add(userAccountDto);
                }
            } else {
                // If exist then remove it.
                if (accountExist) {
                    if (existPosition != -1) {
                        userCheckedItemList.remove(existPosition);
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the action bar menu.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_add_edit_delete_example, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_add) {
            Intent in = new Intent(AddListActivity.this, ProductInfoForSaleActivity.class);
            in.putExtra("ProductId", ProductId);
            startActivity(in);


        } else if (itemId == R.id.menu_delete) {

            if (userCheckedItemList != null) {

                int size = userCheckedItemList.size();

                if (size == 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(false);
                    builder.setMessage("Please select at least one item to delete.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user pressed "yes", then he is allowed to exit from application
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                    ///Toast.makeText(this, "Please select at least one row to delete.", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < size; i++) {

                        SaveProductInfo tmpDto = userCheckedItemList.get(i);

                       // userInfoDBManager.deleteAccount(tmpDto.getId());

                        userCheckedItemList.remove(i);

                        size = userCheckedItemList.size();

                        i--;
                    }

                    listViewDataAdapter = new SimpleCursorAdapter(this, R.layout.activity_user_account_list_view_item, cursor, fromColumnArr, toViewIdArr, CursorAdapter.FLAG_AUTO_REQUERY);

                    userAccountListView.setAdapter(listViewDataAdapter);

                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userInfoDBManager != null) {
            userInfoDBManager.close();
            userInfoDBManager = null;
        }
    }


    private void submitToDb() {
        cursor = userInfoDBManager.getAllAccountCursor();
        array = new JSONArray();
        builder.append("<Parent>");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                builder.append("<Assign>");
                builder.append("<RequestId>" + 0 + "</RequestId>");
                builder.append("<Id>" + cursor.getString(0) + "</Id>");
                builder.append("<UserId>" + UserId + "</UserId>");
                builder.append("<productTypeId>" + cursor.getString(2) + "</productTypeId>");
                builder.append("<productVarietyId>" + cursor.getString(4) + "</productVarietyId>");
                builder.append("<qualityId>" + cursor.getString(6) + "</qualityId>");
                builder.append("<quantity>" + cursor.getString(7) + "</quantity>");
                builder.append("<unitId>" + cursor.getString(9) + "</unitId>");
                builder.append("<expectedPrice>" + cursor.getString(10) + "</expectedPrice>");
                builder.append("<days>" + cursor.getString(11) + "</days>");
                builder.append("<availablityInMonths>" + cursor.getString(12) + "</availablityInMonths>");
                builder.append("<address>" + cursor.getString(13) + "</address>");
                builder.append("<stateId>" + cursor.getString(15) + "</stateId>");
                builder.append("<districtId>" + cursor.getString(17) + "</districtId>");
                builder.append("<talukaId>" + cursor.getString(19) + "</talukaId>");
                builder.append("<villagenam>" + cursor.getString(20) + "</villagenam>");
                builder.append("<areaheactor>" + cursor.getString(21) + "</areaheactor>");
                builder.append("<imagename>" + cursor.getString(22) + "</imagename>");
                builder.append("<organic>" + cursor.getString(23) + "</organic>");
                builder.append("<certificateno>" + cursor.getString(24) + "</certificateno>");
                builder.append("<SurveyNo>" + cursor.getString(25) + "</SurveyNo>");
                builder.append("</Assign>");
                //cursor.getString(22).replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim()
            }

        }

        builder.append("</Parent>");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SAVEPRODUCTDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddListActivity.this);
                        builder.setCancelable(false);
                        mProgressDialog.dismiss();
                        builder.setMessage("Data submitted successfully");
                        userInfoDBManager.deleteAll();
                        builder.setIcon(R.drawable.ic_check_circle);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //if user pressed "yes", then he is allowed to exit from application
                                //dialog.cancel();
                                Intent i= new Intent(AddListActivity.this,HomeActivity.class);
                                startActivity(i);
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(AddListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("xmlData", builder.toString());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(AddListActivity.this).addToRequestQueue(stringRequest);

    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;


        @Override
        protected String doInBackground(String... params) {

            try {

                submitToDb();

            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            // mProgressDialog.dismiss();

            // finalResult.setText(result);
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(String... text) {
            mProgressDialog.setProgress(92);

        }

    }
}
