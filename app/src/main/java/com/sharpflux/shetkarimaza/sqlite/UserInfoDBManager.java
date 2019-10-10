package com.sharpflux.shetkarimaza.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.sharpflux.shetkarimaza.model.SaveProductInfo;

import java.util.ArrayList;
import java.util.List;



public class UserInfoDBManager {

    private Context ctx;

    private DatabaseManager dbManager;

    private static final String DB_NAME = "UserInfo.db";

    private static final String TABLE_NAME_ACCOUNT = "saleproduct";

    // SQLite database need table primary key column named as _id. Otherwise a "java.lang.IllegalArgumentException: column '_id' does not exist" exception will be thrown.
    public static final String TABLE_ACCOUNT_COLUMN_ID = "_id";

    public static final String productType = "productType";
    public static final String productTypeId = "productTypeId";

    public static final String productVariety = "productVariety";
    public static final String productVarietyId = "productVarietyId";

    public static final String quality = "quality";
    public static final String qualityId = "qualityId";

    public static final String quantity="quantity";

    public static final String unit="unit";
    public static final String unitId="unitId";

    public static final String expectedPrice="expectedPrice";

    public static final String days = "days";

    public static final String availablityInMonths = "availablityInMonths";

    public static final String address= "address";

    public static final String state="state";
    public static final String stateId="stateId";

    public static final String district="district";
    public static final String districtId="districtId";

    public static final String taluka="taluka";
    public static final String talukaId="talukaId";

    public static final String villagenam="villagenam";
    public static final String areaheactor="areaheactor";
    public static final String imagename="imagename";
    //public static final String video="video";







    private int DB_VERSION = 1;

    List<String> tableNameList = null;

    List<String> createTableSqlList = null;

    public UserInfoDBManager(Context ctx) {
        this.ctx = ctx;
        this.init();
        this.dbManager = new DatabaseManager(ctx, this.DB_NAME, this.DB_VERSION, this.tableNameList, this.createTableSqlList);
    }

    private void init()
    {
        if(this.tableNameList==null)
        {
            this.tableNameList = new ArrayList<String>();
        }

        if(this.createTableSqlList==null)
        {
            this.createTableSqlList = new ArrayList<String>();
        }

        this.tableNameList.add(TABLE_NAME_ACCOUNT);

        // Build create account table sql.
        StringBuffer sqlBuf = new StringBuffer();

        // Create table sql.
        sqlBuf.append("create table ");
        sqlBuf.append(TABLE_NAME_ACCOUNT);
        sqlBuf.append("( ");
        sqlBuf.append(TABLE_ACCOUNT_COLUMN_ID);
        sqlBuf.append(" integer primary key autoincrement,");

        sqlBuf.append(productType);
        sqlBuf.append(" text,");
        sqlBuf.append(productTypeId);
        sqlBuf.append(" text,");

        sqlBuf.append(productVariety);
        sqlBuf.append(" text,");
        sqlBuf.append(productVarietyId);
        sqlBuf.append(" text,");


        sqlBuf.append(quality);
        sqlBuf.append(" text, ");
        sqlBuf.append(qualityId);
        sqlBuf.append(" text, ");


        sqlBuf.append(quantity);
        sqlBuf.append(" text, ");

        sqlBuf.append(unit);
        sqlBuf.append(" text, ");
        sqlBuf.append(unitId);
        sqlBuf.append(" text, ");


        sqlBuf.append(expectedPrice);
        sqlBuf.append(" text, ");


        sqlBuf.append(days);
        sqlBuf.append(" text, ");


        sqlBuf.append(availablityInMonths);
        sqlBuf.append(" text, ");


        sqlBuf.append(address);
        sqlBuf.append(" text, ");


        sqlBuf.append(state);
        sqlBuf.append(" text, ");
        sqlBuf.append(stateId);
        sqlBuf.append(" text, ");


        sqlBuf.append(district);
        sqlBuf.append(" text, ");
        sqlBuf.append(districtId);
        sqlBuf.append(" text, ");


        sqlBuf.append(taluka);
        sqlBuf.append(" text, ");
        sqlBuf.append(talukaId);
        sqlBuf.append(" text, ");


        sqlBuf.append(villagenam);
        sqlBuf.append(" text, ");


        sqlBuf.append(areaheactor);
        sqlBuf.append(" text, ");

        sqlBuf.append(imagename);
        sqlBuf.append(" text)");



        this.createTableSqlList.add(sqlBuf.toString());
    }


    public void open()
    {
        this.dbManager.openDB();
    }

    public void close()
    {
        this.dbManager.closeDB();;
    }

    // Insert one row.
    public void insertAccount(String productType,String productTypeId, String productVariety,String productVarietyId,
                              String quality,String qualityId, String quantity, String unit,String unitId, String expectedPrice,
                              String days, String availablityInMonths, String address, String state, String stateId,
                              String district,  String districtId,String taluka, String talukaId, String villagenam,
                              String areaheactor,String imagename)
    {
        // Create table column list.
        List<TableColumn> tableColumnList = new ArrayList<TableColumn>();

        // Add user name column.
        TableColumn typeColumn = new TableColumn();
        typeColumn.setColumnName(this.productType);
        typeColumn.setColumnValue(productType);
        tableColumnList.add(typeColumn);

        TableColumn typeColumnId = new TableColumn();
        typeColumnId.setColumnName(this.productTypeId);
        typeColumnId.setColumnValue(productTypeId);
        tableColumnList.add(typeColumnId);

        // Add password column.
        TableColumn varityColumn = new TableColumn();
        varityColumn.setColumnName(this.productVariety);
        varityColumn.setColumnValue(productVariety);
        tableColumnList.add(varityColumn);

        TableColumn varityColumnId = new TableColumn();
        varityColumnId.setColumnName(this.productVarietyId);
        varityColumnId.setColumnValue(productVarietyId);
        tableColumnList.add(varityColumnId);

        // Add email column.
        TableColumn qualityColumn = new TableColumn();
        qualityColumn.setColumnName(this.quality);
        qualityColumn.setColumnValue(quality);
        tableColumnList.add(qualityColumn);

        TableColumn qualityColumnId = new TableColumn();
        qualityColumnId.setColumnName(this.qualityId);
        qualityColumnId.setColumnValue(qualityId);
        tableColumnList.add(qualityColumnId);

        TableColumn quantityColumn = new TableColumn();
        quantityColumn.setColumnName(this.quantity);
        quantityColumn.setColumnValue(quantity);
        tableColumnList.add(quantityColumn);

        TableColumn unitColumn = new TableColumn();
        unitColumn.setColumnName(this.unit);
        unitColumn.setColumnValue(unit);
        tableColumnList.add(unitColumn);

        TableColumn unitColumnId = new TableColumn();
        unitColumnId.setColumnName(this.unitId);
        unitColumnId.setColumnValue(unitId);
        tableColumnList.add(unitColumnId);


        TableColumn priceColumn = new TableColumn();
        priceColumn.setColumnName(this.expectedPrice);
        priceColumn.setColumnValue(expectedPrice);
        tableColumnList.add(priceColumn);


        TableColumn daysColumn = new TableColumn();
        daysColumn.setColumnName(this.days);
        daysColumn.setColumnValue(days);
        tableColumnList.add(daysColumn);


        TableColumn montColumn = new TableColumn();
        montColumn.setColumnName(this.availablityInMonths);
        montColumn.setColumnValue(availablityInMonths);
        tableColumnList.add(montColumn);


        TableColumn addColumn = new TableColumn();
        addColumn.setColumnName(this.address);
        addColumn.setColumnValue(address);
        tableColumnList.add(addColumn);


        TableColumn stateColumn = new TableColumn();
        stateColumn.setColumnName(this.state);
        stateColumn.setColumnValue(state);
        tableColumnList.add(stateColumn);

        TableColumn stateColumnId = new TableColumn();
        stateColumnId.setColumnName(this.stateId);
        stateColumnId.setColumnValue(stateId);
        tableColumnList.add(stateColumnId);

        TableColumn districtColumn= new TableColumn();
        districtColumn.setColumnName(this.district);
        districtColumn.setColumnValue(district);
        tableColumnList.add(districtColumn);


        TableColumn districtColumnId = new TableColumn();
        districtColumnId.setColumnName(this.districtId);
        districtColumnId.setColumnValue(districtId);
        tableColumnList.add(districtColumnId);


        TableColumn talukaColumn = new TableColumn();
        talukaColumn.setColumnName(this.taluka);
        talukaColumn.setColumnValue(taluka);
        tableColumnList.add(talukaColumn);

        TableColumn talukaColumnId = new TableColumn();
        talukaColumnId.setColumnName(this.talukaId);
        talukaColumnId.setColumnValue(talukaId);
        tableColumnList.add(talukaColumnId);


        TableColumn villageColumn = new TableColumn();
        villageColumn.setColumnName(this.villagenam);
        villageColumn.setColumnValue(villagenam);
        tableColumnList.add(villageColumn);



        TableColumn areaColumn = new TableColumn();
        areaColumn.setColumnName(this.areaheactor);
        areaColumn.setColumnValue(areaheactor);
        tableColumnList.add(areaColumn);

        TableColumn imageColumn = new TableColumn();
        imageColumn.setColumnName(this.imagename);
        imageColumn.setColumnValue(imagename);
        tableColumnList.add(imageColumn);




        // Insert added column in to account table.
        this.dbManager.insert(this.TABLE_NAME_ACCOUNT, tableColumnList);
    }

    // Update one row. User name column can not be updated.
    public void updateAccount(int id, String password, String email,String pty)
    {
        // Create table column list.
        List<TableColumn> updateColumnList = new ArrayList<TableColumn>();


        TableColumn passwordColumn = new TableColumn();
        passwordColumn.setColumnName(this.productVariety);
        passwordColumn.setColumnValue(password);
        updateColumnList.add(passwordColumn);


        TableColumn emailColumn = new TableColumn();
        emailColumn.setColumnName(this.days);
        emailColumn.setColumnValue(email);
        updateColumnList.add(emailColumn);

        // Add email column.
        TableColumn variety = new TableColumn();
        variety.setColumnName(this.productType);
        variety.setColumnValue(pty);
        updateColumnList.add(variety);

        String whereClause = this.TABLE_ACCOUNT_COLUMN_ID + " = " + id;

        // Insert added column in to account table.
        this.dbManager.update(this.TABLE_NAME_ACCOUNT, updateColumnList, whereClause);
    }

    // Delete one account.
    public void deleteAccount(int id)
    {
        this.dbManager.delete(this.TABLE_NAME_ACCOUNT, this.TABLE_ACCOUNT_COLUMN_ID + " = " + id);
    }
    public void deleteAll()
    {
            this.dbManager.delete(this.TABLE_NAME_ACCOUNT,null);

    }


    // Get all user account dto list.
    public List<SaveProductInfo> getAllAccount()
    {
        List<SaveProductInfo> ret = new ArrayList<SaveProductInfo>();
        Cursor cursor = this.dbManager.queryAllReturnCursor(this.TABLE_NAME_ACCOUNT);
        if(cursor!=null)
        {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_ID));
                String productType = cursor.getString(cursor.getColumnIndex(this.productType));
                String productVariety = cursor.getString(cursor.getColumnIndex(this.productVariety));
                String quality = cursor.getString(cursor.getColumnIndex(this.quality));
                String quantity = cursor.getString(cursor.getColumnIndex(this.quantity));
                String unit = cursor.getString(cursor.getColumnIndex(this.unit));
                String expectedPrice = cursor.getString(cursor.getColumnIndex(this.expectedPrice));
                String days = cursor.getString(cursor.getColumnIndex(this.days));
                String availablityInMonths = cursor.getString(cursor.getColumnIndex(this.availablityInMonths));
                String address = cursor.getString(cursor.getColumnIndex(this.address));
                String state = cursor.getString(cursor.getColumnIndex(this.state));
                String district = cursor.getString(cursor.getColumnIndex(this.district));
                String taluka = cursor.getString(cursor.getColumnIndex(this.taluka));
                String villagenam = cursor.getString(cursor.getColumnIndex(this.villagenam));
                String areaheactor = cursor.getString(cursor.getColumnIndex(this.areaheactor));
                String imagename = cursor.getString(cursor.getColumnIndex(this.imagename));





                SaveProductInfo userAccountDto = new SaveProductInfo(id,
                        productType, productVariety, quality, quantity, unit,  expectedPrice,
                        days,availablityInMonths,address,  state,
                        district,taluka,villagenam, areaheactor,imagename);
                userAccountDto.setId(id);
                userAccountDto.setProductType(productType);
                userAccountDto.setProductVariety(productVariety);
                userAccountDto.setQuality(quality);
                userAccountDto.setQuality(quantity);
                userAccountDto.setQuality(unit);
                userAccountDto.setQuality(expectedPrice);
                userAccountDto.setQuality(days);
                userAccountDto.setQuality(availablityInMonths);
                userAccountDto.setQuality(address);
                userAccountDto.setQuality(state);
                userAccountDto.setQuality(district);
                userAccountDto.setQuality(taluka);
                userAccountDto.setQuality(villagenam);
                userAccountDto.setQuality(areaheactor);



                ret.add(userAccountDto);

            }while(cursor.moveToNext());

            // Close cursor after query.
            if(!cursor.isClosed()) {
                cursor.close();
            }
        }

        return ret;
    }

    // Return sqlite database cursor object.
    public Cursor getAllAccountCursor()
    {
        Cursor cursor = this.dbManager.queryAllReturnCursor(this.TABLE_NAME_ACCOUNT);
        return cursor;
    }}