package com.sharpflux.shetkarimaza.sqlite;

import android.content.Context;
import android.database.Cursor;

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

    public static final String productType = "productType";//1
    public static final String productTypeId = "productTypeId";//2

    public static final String productVariety = "productVariety";//3
    public static final String productVarietyId = "productVarietyId";//4

    public static final String quality = "quality";//5
    public static final String qualityId = "qualityId";//6

    public static final String quantity = "quantity";//7

    public static final String unit = "unit";//8
    public static final String unitId = "unitId";//9

    public static final String expectedPrice = "expectedPrice";//10

    public static final String days = "days";//11

    public static final String availablityInMonths = "availablityInMonths";//12

    public static final String address = "address";//13

    public static final String state = "state";//14
    public static final String stateId = "stateId";//15

    public static final String district = "district";//16
    public static final String districtId = "districtId";//17

    public static final String taluka = "taluka";//18
    public static final String talukaId = "talukaId";//19

    public static final String villagenam = "villagenam";//20
    public static final String areaheactor = "areaheactor";//21
    public static final String imagename = "imagename";//22

    public static final String organic = "organic";//23
    public static final String certificateno = "certificateno";//24

    public static final String SurveyNo = "SurveyNo";//25

    //public static final String video="video";


    private int DB_VERSION = 1;

    List<String> tableNameList = null;

    List<String> createTableSqlList = null;

    public UserInfoDBManager(Context ctx) {
        this.ctx = ctx;
        this.init();
        this.dbManager = new DatabaseManager(ctx, this.DB_NAME, this.DB_VERSION, this.tableNameList, this.createTableSqlList);
    }

    private void init() {
        if (this.tableNameList == null) {
            this.tableNameList = new ArrayList<String>();
        }

        if (this.createTableSqlList == null) {
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

        sqlBuf.append(productType);//1
        sqlBuf.append(" text,");
        sqlBuf.append(productTypeId);//2
        sqlBuf.append(" text,");

        sqlBuf.append(productVariety);//3
        sqlBuf.append(" text,");
        sqlBuf.append(productVarietyId);//4
        sqlBuf.append(" text,");


        sqlBuf.append(quality);//5
        sqlBuf.append(" text, ");
        sqlBuf.append(qualityId);//6
        sqlBuf.append(" text, ");


        sqlBuf.append(quantity);//7
        sqlBuf.append(" text, ");

        sqlBuf.append(unit);//8
        sqlBuf.append(" text, ");
        sqlBuf.append(unitId);//9
        sqlBuf.append(" text, ");


        sqlBuf.append(expectedPrice);//10
        sqlBuf.append(" text, ");


        sqlBuf.append(days);//11
        sqlBuf.append(" text, ");


        sqlBuf.append(availablityInMonths);//12
        sqlBuf.append(" text, ");


        sqlBuf.append(address);//13
        sqlBuf.append(" text, ");


        sqlBuf.append(state);//14
        sqlBuf.append(" text, ");
        sqlBuf.append(stateId);//15
        sqlBuf.append(" text, ");


        sqlBuf.append(district);//16
        sqlBuf.append(" text, ");
        sqlBuf.append(districtId);//17
        sqlBuf.append(" text, ");


        sqlBuf.append(taluka);//18
        sqlBuf.append(" text, ");
        sqlBuf.append(talukaId);//19
        sqlBuf.append(" text, ");


        sqlBuf.append(villagenam);//20
        sqlBuf.append(" text, ");


        sqlBuf.append(areaheactor);//21
        sqlBuf.append(" text, ");

        sqlBuf.append(imagename);//22
        sqlBuf.append(" text,");

        sqlBuf.append(organic);//23
        sqlBuf.append(" text, ");

        sqlBuf.append(certificateno);//24
        sqlBuf.append(" text, ");

        sqlBuf.append(SurveyNo);//24
        sqlBuf.append(" text) ");


        this.createTableSqlList.add(sqlBuf.toString());
    }


    public void open() {
        this.dbManager.openDB();
    }

    public void close() {
        this.dbManager.closeDB();
        ;
    }

    // Insert one row.
    public void insertAccount(String productType, String productTypeId, String productVariety, String productVarietyId,
                              String quality, String qualityId, String quantity, String unit, String unitId, String expectedPrice,
                              String days, String availablityInMonths, String address, String state, String stateId,
                              String district, String districtId, String taluka, String talukaId, String villagenam,
                              String areaheactor,String imagename,String organic, String certificateno,String SurveyNo) {
        // Create table column list.
        List<TableColumn> tableColumnList = new ArrayList<TableColumn>();

        //

        // Add user name column.
        TableColumn typeColumn = new TableColumn();//0
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

        TableColumn districtColumn = new TableColumn();
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


        TableColumn organicColumn = new TableColumn();
        organicColumn.setColumnName(this.organic);
        organicColumn.setColumnValue(organic);
        tableColumnList.add(organicColumn);


        TableColumn certificatenoColumn = new TableColumn();
        certificatenoColumn.setColumnName(this.certificateno);
        certificatenoColumn.setColumnValue(certificateno);
        tableColumnList.add(certificatenoColumn);

        TableColumn surveyNoColumn = new TableColumn();
        surveyNoColumn.setColumnName(this.SurveyNo);
        surveyNoColumn.setColumnValue(SurveyNo);
        tableColumnList.add(surveyNoColumn);



        // Insert added column in to account table.
        this.dbManager.insert(this.TABLE_NAME_ACCOUNT, tableColumnList);
    }

    // Update one row. User name column can not be updated.
    public void updateAccount(int id, String password, String email, String pty) {
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
    public void deleteAccount(int id) {
        this.dbManager.delete(this.TABLE_NAME_ACCOUNT, this.TABLE_ACCOUNT_COLUMN_ID + " = " + id);
    }


    public void deleteAll() {
        this.dbManager.delete(this.TABLE_NAME_ACCOUNT, null);
    }


    // Get all user account dto list.
    public List<SaveProductInfo> getAllAccount() {
        List<SaveProductInfo> ret = new ArrayList<SaveProductInfo>();
        Cursor cursor = this.dbManager.queryAllReturnCursor(this.TABLE_NAME_ACCOUNT);
        if (cursor != null) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(this.TABLE_ACCOUNT_COLUMN_ID));
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
                String organic = cursor.getString(cursor.getColumnIndex(this.organic));
                String certificateno = cursor.getString(cursor.getColumnIndex(this.certificateno));


                SaveProductInfo userAccountDto = new SaveProductInfo(id,
                        productType, productVariety, quality, quantity, unit, expectedPrice,
                        days, availablityInMonths, address, state,
                        district, taluka, villagenam, areaheactor, imagename);
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
                userAccountDto.setQuality(organic);
                userAccountDto.setQuality(certificateno);


                ret.add(userAccountDto);

            } while (cursor.moveToNext());

            // Close cursor after query.
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return ret;
    }

    // Return sqlite database cursor object.
    public Cursor getAllAccountCursor() {
        Cursor cursor = this.dbManager.queryAllReturnCursor(this.TABLE_NAME_ACCOUNT);
        return cursor;
    }
}