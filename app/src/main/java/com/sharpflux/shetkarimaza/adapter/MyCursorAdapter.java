package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MyCursorAdapter extends SimpleCursorAdapter {

    private LayoutInflater cursorInflater;
    private Cursor c;
    private Context context;
    String xmlImg;
    public MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.context = context;
    }



    @Override
    public View getView(int pos, View inView, ViewGroup parent) {

        View v = inView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_product_list, null);
        }
        this.c.moveToPosition(pos);
        String id = this.c.getString(this.c.getColumnIndex("_id"));
        String productType = this.c.getString(this.c.getColumnIndex("productType"));
        String productVariety = this.c.getString(this.c.getColumnIndex("productVariety"));
        String quality = this.c.getString(this.c.getColumnIndex("quality"));
        String expectedPrice = this.c.getString(this.c.getColumnIndex("expectedPrice"));
        xmlImg = this.c.getString(this.c.getColumnIndex("imagename"));
        Document doc = convertStringToXMLDocument( xmlImg );
        ImageView iv = (ImageView) v.findViewById(R.id.row_cartlist_ivProImg);
        Bitmap bitmap;
        if(doc!=null) {
            try {
                byte[] imgbytes = Base64.decode(doc.getFirstChild().getTextContent(), Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(imgbytes, 0, imgbytes.length);
                iv.setImageBitmap(bitmap);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        TextView row_cartlist_tvNameType=(TextView) v.findViewById(R.id.row_cartlist_tvNameType);
        row_cartlist_tvNameType.setText(productType);

        TextView user_account_list_item_id=(TextView) v.findViewById(R.id.row_cartlist_tvName);
        user_account_list_item_id.setText(productVariety);

        TextView user_account_list_item_user_name=(TextView) v.findViewById(R.id.row_cartlist_tvKg);
        user_account_list_item_user_name.setText(quality);

        TextView row_cartlist_tvPrice=(TextView) v.findViewById(R.id.row_cartlist_tvPrice);
        row_cartlist_tvPrice.setText("₹"+expectedPrice);

        return(v);
    }
    private static Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}



