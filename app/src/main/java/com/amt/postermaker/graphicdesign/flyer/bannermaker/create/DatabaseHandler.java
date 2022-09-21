package com.amt.postermaker.graphicdesign.flyer.bannermaker.create;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.msl.demo.view.ComponentInfo;
import com.msl.textmodule.TextInfo;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String BG_ALPHA = "BG_ALPHA";
    private static final String BG_COLOR = "BG_COLOR";
    private static final String BG_DRAWABLE = "BG_DRAWABLE";
    private static final String COLORTYPE = "COLORTYPE";
    private static final String COMPONENT_INFO = "COMPONENT_INFO";
    private static final String COMP_ID = "COMP_ID";
    private static final String CREATE_TABLE_COMPONENT_INFO = "CREATE TABLE COMPONENT_INFO(COMP_ID INTEGER PRIMARY KEY,TEMPLATE_ID TEXT,POS_X TEXT,POS_Y TEXT,WIDHT TEXT,HEIGHT TEXT,ROTATION TEXT,Y_ROTATION TEXT,RES_ID TEXT,TYPE TEXT,ORDER_ TEXT,STC_COLOR TEXT,STC_OPACITY TEXT,XROTATEPROG TEXT,YROTATEPROG TEXT,ZROTATEPROG TEXT,STC_SCALE TEXT,STKR_PATH TEXT,COLORTYPE TEXT,STC_HUE TEXT,FIELD_ONE TEXT,FIELD_TWO TEXT,FIELD_THREE TEXT,FIELD_FOUR TEXT)";
    private static final String CREATE_TABLE_TEMPLATES = "CREATE TABLE TEMPLATES(TEMPLATE_ID INTEGER PRIMARY KEY,THUMB_URI TEXT,FRAME_NAME TEXT,RATIO TEXT,PROFILE_TYPE TEXT,SEEK_VALUE TEXT,TYPE TEXT,TEMP_PATH TEXT,TEMP_COLOR TEXT,OVERLAY_NAME TEXT,OVERLAY_OPACITY TEXT,OVERLAY_BLUR TEXT)";
    private static final String CREATE_TABLE_TEXT_INFO = "CREATE TABLE TEXT_INFO(TEXT_ID INTEGER PRIMARY KEY,TEMPLATE_ID TEXT,TEXT TEXT,FONT_NAME TEXT,TEXT_COLOR TEXT,TEXT_ALPHA TEXT,SHADOW_COLOR TEXT,SHADOW_PROG TEXT,BG_DRAWABLE TEXT,BG_COLOR TEXT,BG_ALPHA TEXT,POS_X TEXT,POS_Y TEXT,WIDHT TEXT,HEIGHT TEXT,ROTATION TEXT,TYPE TEXT,ORDER_ TEXT,XROTATEPROG TEXT,YROTATEPROG TEXT,ZROTATEPROG TEXT,CURVEPROG TEXT,FIELD_ONE TEXT,FIELD_TWO TEXT,FIELD_THREE TEXT,FIELD_FOUR TEXT)";
    private static final String CURVEPROG = "CURVEPROG";
    private static final String DATABASE_NAME = "POSTERMAKER_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String FIELD_FOUR = "FIELD_FOUR";
    private static final String FIELD_ONE = "FIELD_ONE";
    private static final String FIELD_THREE = "FIELD_THREE";
    private static final String FIELD_TWO = "FIELD_TWO";
    private static final String FONT_NAME = "FONT_NAME";
    private static final String FRAME_NAME = "FRAME_NAME";
    private static final String HEIGHT = "HEIGHT";
    private static final String ORDER = "ORDER_";
    private static final String OVERLAY_BLUR = "OVERLAY_BLUR";
    private static final String OVERLAY_NAME = "OVERLAY_NAME";
    private static final String OVERLAY_OPACITY = "OVERLAY_OPACITY";
    private static final String POS_X = "POS_X";
    private static final String POS_Y = "POS_Y";
    private static final String PROFILE_TYPE = "PROFILE_TYPE";
    private static final String RATIO = "RATIO";
    private static final String RES_ID = "RES_ID";
    private static final String ROTATION = "ROTATION";
    private static final String SEEK_VALUE = "SEEK_VALUE";
    private static final String SHADOW_COLOR = "SHADOW_COLOR";
    private static final String SHADOW_PROG = "SHADOW_PROG";
    private static final String STC_COLOR = "STC_COLOR";
    private static final String STC_HUE = "STC_HUE";
    private static final String STC_OPACITY = "STC_OPACITY";
    private static final String STC_SCALE = "STC_SCALE";
    private static final String STKR_PATH = "STKR_PATH";
    private static final String TEMPLATES = "TEMPLATES";
    private static final String TEMPLATE_ID = "TEMPLATE_ID";
    private static final String TEMP_COLOR = "TEMP_COLOR";
    private static final String TEMP_PATH = "TEMP_PATH";
    private static final String TEXT = "TEXT";
    private static final String TEXT_ALPHA = "TEXT_ALPHA";
    private static final String TEXT_COLOR = "TEXT_COLOR";
    private static final String TEXT_ID = "TEXT_ID";
    private static final String TEXT_INFO = "TEXT_INFO";
    private static final String THUMB_URI = "THUMB_URI";
    private static final String TYPE = "TYPE";
    private static final String WIDHT = "WIDHT";
    private static final String XROTATEPROG = "XROTATEPROG";
    private static final String YROTATEPROG = "YROTATEPROG";
    private static final String Y_ROTATION = "Y_ROTATION";
    private static final String ZROTATEPROG = "ZROTATEPROG";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DatabaseHandler getDbHandler(Context context) {
        return new DatabaseHandler(context);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TEMPLATES);
        db.execSQL(CREATE_TABLE_TEXT_INFO);
        db.execSQL(CREATE_TABLE_COMPONENT_INFO);
        Log.i("testing", "Database Created");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TEMPLATES");
        db.execSQL("DROP TABLE IF EXISTS TEXT_INFO");
        db.execSQL("DROP TABLE IF EXISTS COMPONENT_INFO");
        onCreate(db);
    }

    public long insertTemplateRow(TemplateInfo tInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(THUMB_URI, tInfo.getTHUMB_URI());
        values.put(FRAME_NAME, tInfo.getFRAME_NAME());
        values.put(RATIO, tInfo.getRATIO());
        values.put(PROFILE_TYPE, tInfo.getPROFILE_TYPE());
        values.put(SEEK_VALUE, tInfo.getSEEK_VALUE());
        values.put(TYPE, tInfo.getTYPE());
        values.put(TEMP_PATH, tInfo.getTEMP_PATH());
        values.put(TEMP_COLOR, tInfo.getTEMPCOLOR());
        values.put(OVERLAY_NAME, tInfo.getOVERLAY_NAME());
        values.put(OVERLAY_OPACITY, Integer.valueOf(tInfo.getOVERLAY_OPACITY()));
        values.put(OVERLAY_BLUR, Integer.valueOf(tInfo.getOVERLAY_BLUR()));
        Log.i("testing", "Before insertion ");
        long insert = db.insert(TEMPLATES, null, values);
        Log.i("testing", "ID " + insert);
        Log.i("testing", "Framepath " + tInfo.getFRAME_NAME());
        Log.i("testing", "Thumb Path " + tInfo.getTHUMB_URI());
        db.close();
        return insert;
    }

    public void insertComponentInfoRow(ComponentInfo cInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEMPLATE_ID, Integer.valueOf(cInfo.getTEMPLATE_ID()));
        values.put(POS_X, Float.valueOf(cInfo.getPOS_X()));
        values.put(POS_Y, Float.valueOf(cInfo.getPOS_Y()));
        values.put(WIDHT, Integer.valueOf(cInfo.getWIDTH()));
        values.put(HEIGHT, Integer.valueOf(cInfo.getHEIGHT()));
        values.put(ROTATION, Float.valueOf(cInfo.getROTATION()));
        values.put(Y_ROTATION, Float.valueOf(cInfo.getY_ROTATION()));
        values.put(RES_ID, cInfo.getRES_ID());
        values.put(TYPE, cInfo.getTYPE());
        values.put(ORDER, Integer.valueOf(cInfo.getORDER()));
        values.put(STC_COLOR, Integer.valueOf(cInfo.getSTC_COLOR()));
        values.put(STC_OPACITY, Integer.valueOf(cInfo.getSTC_OPACITY()));
        values.put(XROTATEPROG, Integer.valueOf(cInfo.getXRotateProg()));
        values.put(YROTATEPROG, Integer.valueOf(cInfo.getYRotateProg()));
        values.put(ZROTATEPROG, Integer.valueOf(cInfo.getZRotateProg()));
        values.put(STC_SCALE, Integer.valueOf(cInfo.getScaleProg()));
        values.put(STKR_PATH, cInfo.getSTKR_PATH());
        values.put(COLORTYPE, cInfo.getCOLORTYPE());
        values.put(STC_HUE, Integer.valueOf(cInfo.getSTC_HUE()));
        values.put(FIELD_ONE, Integer.valueOf(cInfo.getFIELD_ONE()));
        values.put(FIELD_TWO, cInfo.getFIELD_TWO());
        values.put(FIELD_THREE, cInfo.getFIELD_THREE());
        values.put(FIELD_FOUR, cInfo.getFIELD_FOUR());
        Log.e("insert sticker", "" + cInfo.getPOS_X() + " ," + cInfo.getPOS_Y() + " ," + cInfo.getWIDTH() + " ," + cInfo.getHEIGHT() + " ," + cInfo.getROTATION() + " ," + cInfo.getY_ROTATION() + " ," + cInfo.getRES_ID());
        Log.e("insert id", "" + db.insert(COMPONENT_INFO, null, values));
        db.close();
    }

    public void insertTextRow(TextInfo tInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEMPLATE_ID, Integer.valueOf(tInfo.getTEMPLATE_ID()));
        values.put(TEXT, tInfo.getTEXT());
        values.put(FONT_NAME, tInfo.getFONT_NAME());
        values.put(TEXT_COLOR, Integer.valueOf(tInfo.getTEXT_COLOR()));
        values.put(TEXT_ALPHA, Integer.valueOf(tInfo.getTEXT_ALPHA()));
        values.put(SHADOW_COLOR, Integer.valueOf(tInfo.getSHADOW_COLOR()));
        values.put(SHADOW_PROG, Integer.valueOf(tInfo.getSHADOW_PROG()));
        values.put(BG_DRAWABLE, tInfo.getBG_DRAWABLE());
        values.put(BG_COLOR, Integer.valueOf(tInfo.getBG_COLOR()));
        values.put(BG_ALPHA, Integer.valueOf(tInfo.getBG_ALPHA()));
        values.put(POS_X, Float.valueOf(tInfo.getPOS_X()));
        values.put(POS_Y, Float.valueOf(tInfo.getPOS_Y()));
        values.put(WIDHT, Integer.valueOf(tInfo.getWIDTH()));
        values.put(HEIGHT, Integer.valueOf(tInfo.getHEIGHT()));
        values.put(ROTATION, Float.valueOf(tInfo.getROTATION()));
        values.put(TYPE, tInfo.getTYPE());
        values.put(ORDER, Integer.valueOf(tInfo.getORDER()));
        values.put(XROTATEPROG, Integer.valueOf(tInfo.getXRotateProg()));
        values.put(YROTATEPROG, Integer.valueOf(tInfo.getYRotateProg()));
        values.put(ZROTATEPROG, Integer.valueOf(tInfo.getZRotateProg()));
        values.put(CURVEPROG, Integer.valueOf(tInfo.getCurveRotateProg()));
        values.put(FIELD_ONE, Integer.valueOf(tInfo.getFIELD_ONE()));
        values.put(FIELD_TWO, tInfo.getFIELD_TWO());
        values.put(FIELD_THREE, tInfo.getFIELD_THREE());
        values.put(FIELD_FOUR, tInfo.getFIELD_FOUR());
        Log.i("testing", "Before TEXT insertion ");
        Log.i("testing", "TEXT ID " + db.insert(TEXT_INFO, null, values));
        db.close();
    }

    public ArrayList<TemplateInfo> getTemplateList(String type) {
        ArrayList<TemplateInfo> templateList = new ArrayList();
        String query = "SELECT  * FROM TEMPLATES WHERE TYPE='" + type + "' ORDER BY " + TEMPLATE_ID + " ASC;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            db.close();
            Log.e("templateList size is", "" + templateList.size());
        } else {
            do {
                TemplateInfo values = new TemplateInfo();
                values.setTEMPLATE_ID(cursor.getInt(0));
                values.setTHUMB_URI(cursor.getString(1));
                values.setFRAME_NAME(cursor.getString(2));
                values.setRATIO(cursor.getString(3));
                values.setPROFILE_TYPE(cursor.getString(4));
                values.setSEEK_VALUE(cursor.getString(5));
                values.setTYPE(cursor.getString(6));
                values.setTEMP_PATH(cursor.getString(7));
                values.setTEMPCOLOR(cursor.getString(8));
                values.setOVERLAY_NAME(cursor.getString(9));
                values.setOVERLAY_OPACITY(cursor.getInt(10));
                values.setOVERLAY_BLUR(cursor.getInt(11));
                templateList.add(values);
            } while (cursor.moveToNext());
            db.close();
            Log.e("templateList size is", "" + templateList.size());
        }
        return templateList;
    }

    public ArrayList<TemplateInfo> getTemplateListDes(String type) {
        ArrayList<TemplateInfo> templateList = new ArrayList();
        String query = "SELECT  * FROM TEMPLATES WHERE TYPE='" + type + "' ORDER BY " + TEMPLATE_ID + " DESC;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            db.close();
            Log.e("templateList size is", "" + templateList.size());
        } else {
            do {
                TemplateInfo values = new TemplateInfo();
                values.setTEMPLATE_ID(cursor.getInt(0));
                values.setTHUMB_URI(cursor.getString(1));
                values.setFRAME_NAME(cursor.getString(2));
                values.setRATIO(cursor.getString(3));
                values.setPROFILE_TYPE(cursor.getString(4));
                values.setSEEK_VALUE(cursor.getString(5));
                values.setTYPE(cursor.getString(6));
                values.setTEMP_PATH(cursor.getString(7));
                values.setTEMPCOLOR(cursor.getString(8));
                values.setOVERLAY_NAME(cursor.getString(9));
                values.setOVERLAY_OPACITY(cursor.getInt(10));
                values.setOVERLAY_BLUR(cursor.getInt(11));
                templateList.add(values);
            } while (cursor.moveToNext());
            db.close();
            Log.e("templateList size is", "" + templateList.size());
        }
        return templateList;
    }

    public ArrayList<TemplateInfo> getTemplateList() {
        ArrayList<TemplateInfo> templateList = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM TEMPLATES ORDER BY TEMPLATE_ID DESC;", null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            db.close();
        } else {
            do {
                TemplateInfo values = new TemplateInfo();
                values.setTEMPLATE_ID(cursor.getInt(0));
                values.setTHUMB_URI(cursor.getString(1));
                values.setFRAME_NAME(cursor.getString(2));
                values.setRATIO(cursor.getString(3));
                values.setPROFILE_TYPE(cursor.getString(4));
                values.setSEEK_VALUE(cursor.getString(5));
                values.setTYPE(cursor.getString(6));
                values.setTEMP_PATH(cursor.getString(7));
                values.setTEMPCOLOR(cursor.getString(8));
                values.setOVERLAY_NAME(cursor.getString(9));
                values.setOVERLAY_OPACITY(cursor.getInt(10));
                values.setOVERLAY_BLUR(cursor.getInt(11));
                templateList.add(values);
            } while (cursor.moveToNext());
            db.close();
        }
        return templateList;
    }

    public ArrayList<ComponentInfo> getComponentInfoList(int templateID, String shape) {
        ArrayList<ComponentInfo> componentInfoList = new ArrayList();
        String query = "SELECT * FROM COMPONENT_INFO WHERE TEMPLATE_ID='" + templateID + "' AND " + TYPE + " = '" + shape + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            db.close();
        } else {
            do {
                ComponentInfo values = new ComponentInfo();
                values.setCOMP_ID(cursor.getInt(0));
                values.setTEMPLATE_ID(cursor.getInt(1));
                values.setPOS_X(cursor.getFloat(2));
                values.setPOS_Y(cursor.getFloat(3));
                values.setWIDTH(cursor.getInt(4));
                values.setHEIGHT(cursor.getInt(5));
                values.setROTATION(cursor.getFloat(6));
                values.setY_ROTATION(cursor.getFloat(7));
                values.setRES_ID(cursor.getString(8));
                values.setTYPE(cursor.getString(9));
                values.setORDER(cursor.getInt(10));
                values.setSTC_COLOR(cursor.getInt(11));
                values.setSTC_OPACITY(cursor.getInt(12));
                values.setXRotateProg(cursor.getInt(13));
                values.setYRotateProg(cursor.getInt(14));
                values.setZRotateProg(cursor.getInt(15));
                values.setScaleProg(cursor.getInt(16));
                values.setSTKR_PATH(cursor.getString(17));
                values.setCOLORTYPE(cursor.getString(18));
                values.setSTC_HUE(cursor.getInt(19));
                values.setFIELD_ONE(cursor.getInt(20));
                values.setFIELD_TWO(cursor.getString(21));
                values.setFIELD_THREE(cursor.getString(22));
                values.setFIELD_FOUR(cursor.getString(23));
                componentInfoList.add(values);
            } while (cursor.moveToNext());
            db.close();
        }
        return componentInfoList;
    }

    public ArrayList<TextInfo> getTextInfoList(int templateID) {
        ArrayList<TextInfo> textInfoArrayList = new ArrayList();
        String query = "SELECT * FROM TEXT_INFO WHERE TEMPLATE_ID='" + templateID + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            db.close();
        } else {
            do {
                TextInfo values = new TextInfo();
                values.setTEXT_ID(cursor.getInt(0));
                values.setTEMPLATE_ID(cursor.getInt(1));
                values.setTEXT(cursor.getString(2));
                values.setFONT_NAME(cursor.getString(3));
                values.setTEXT_COLOR(cursor.getInt(4));
                values.setTEXT_ALPHA(cursor.getInt(5));
                values.setSHADOW_COLOR(cursor.getInt(6));
                values.setSHADOW_PROG(cursor.getInt(7));
                values.setBG_DRAWABLE(cursor.getString(8));
                values.setBG_COLOR(cursor.getInt(9));
                values.setBG_ALPHA(cursor.getInt(10));
                values.setPOS_X(cursor.getFloat(11));
                values.setPOS_Y(cursor.getFloat(12));
                values.setWIDTH(cursor.getInt(13));
                values.setHEIGHT(cursor.getInt(14));
                values.setROTATION(cursor.getFloat(15));
                values.setTYPE(cursor.getString(16));
                values.setORDER(cursor.getInt(17));
                values.setXRotateProg(cursor.getInt(18));
                values.setYRotateProg(cursor.getInt(19));
                values.setZRotateProg(cursor.getInt(20));
                values.setCurveRotateProg(cursor.getInt(21));
                values.setFIELD_ONE(cursor.getInt(22));
                values.setFIELD_TWO(cursor.getString(23));
                values.setFIELD_THREE(cursor.getString(24));
                values.setFIELD_FOUR(cursor.getString(25));
                textInfoArrayList.add(values);
            } while (cursor.moveToNext());
            db.close();
        }
        return textInfoArrayList;
    }

    public boolean deleteTemplateInfo(int templateID) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM TEMPLATES WHERE TEMPLATE_ID='" + templateID + "'");
            db.execSQL("DELETE FROM COMPONENT_INFO WHERE TEMPLATE_ID='" + templateID + "'");
            db.execSQL("DELETE FROM TEXT_INFO WHERE TEMPLATE_ID='" + templateID + "'");
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
