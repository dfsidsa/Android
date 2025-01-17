package com.leaf.collegeidleapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.leaf.collegeidleapp.bean.Commodity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 商品数据库连接类
 * @author : autumn_leaf
 */
public class CommodityDbHelper extends SQLiteOpenHelper {

    //定义商品表
    public static final String DB_NAME = "tb_commodity";

    /**创建商品表*/
    private static final String CREATE_COMMODITY_DB = "create table tb_commodity(" +

            "id INTEGER PRIMARY KEY,"+
            "commodityId INTEGER UNIQUE,"+
            "title text," +
            "category text," +
            "cartCount INTEGER DEFAULT 0,"+
            "collectionNum INTEGER DEFAULT 0,"+
            "reviewNum INTEGER DEFAULT 0,"+
            "price float," +
            "phone text," +
            "description text," +
            "picture blob," +
            "stuId text)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COMMODITY_DB);
    }


    private Context context;
    public CommodityDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 添加物品方法
     * @param commodity 物品对象
     */
    public boolean AddCommodity(Commodity commodity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("commodityId",commodity.getCommodityId());
        values.put("title",commodity.getTitle());
        values.put("category",commodity.getCategory());
        values.put("price",commodity.getPrice());
        values.put("phone",commodity.getPhone());
        values.put("description",commodity.getDescription());
        values.put("picture",commodity.getPicture());
        values.put("stuId",commodity.getStuId());
        db.insert(DB_NAME,null,values);
        values.clear();
        return true;
    }

    /**
     * 通过学号查找我的发布物品信息
     * @param stuId 学生学号
     * @return 查找到的物品
     * 使其得以通过学号来获取该学号发布的商品
     */
    public List<Commodity> readMyCommodities(String stuId) {
        List<Commodity> myCommodities = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_commodity where stuId=?",new String[]{stuId});
        if(cursor.moveToFirst()) {
            do {
                int titleIndex = cursor.getColumnIndex("title");
                int categoryIndex = cursor.getColumnIndex("category");
                int priceIndex = cursor.getColumnIndex("price");
                int phoneIndex = cursor.getColumnIndex("phone");
                int descriptionIndex = cursor.getColumnIndex("description");
                int pictureIndex = cursor.getColumnIndex("picture");
                int commodityIdIndex = cursor.getColumnIndex("commodityId");
                int collectionNumIndex=cursor.getColumnIndex("collectionNum");
                int reviewNumIndex=cursor.getColumnIndex("reviewNum");

                if (titleIndex >= 0 && categoryIndex >= 0 && priceIndex >= 0 && phoneIndex >= 0 && descriptionIndex >= 0 && pictureIndex >= 0 && commodityIdIndex >= 0) {
                    String title = cursor.getString(titleIndex);
                    String category = cursor.getString(categoryIndex);
                    float price = cursor.getFloat(priceIndex);
                    String phone = cursor.getString(phoneIndex);
                    String description = cursor.getString(descriptionIndex);
                    byte[] picture = cursor.getBlob(pictureIndex);
                    int commodityId = cursor.getInt(commodityIdIndex);
                    int collectionNum=cursor.getInt(collectionNumIndex);
                    int reviewNum=cursor.getInt(reviewNumIndex);

                    Commodity commodity = new Commodity();
                    commodity.setTitle(title);
                    commodity.setCategory(category);
                    commodity.setPrice(price);
                    commodity.setDescription(description);
                    commodity.setCommodityId(commodityId);
                    commodity.setPhone(phone);
                    commodity.setPicture(picture);
                    commodity.setCollectionNum(collectionNum);
                    commodity.setReviewNum(reviewNum);

                    myCommodities.add(commodity);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return myCommodities;
    }


    /**
     * 通过商品号查找商品信息
     * @param commodityId1 商品号
     * @return 查找到的物品
     * 使其得以通过学号来获取该学号发布的商品
     */
    public Commodity readCommodity(int commodityId1) {
        Commodity commodity = new Commodity();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_commodity where commodityId=?",new String[]{String.valueOf(commodityId1)});
        if(cursor.moveToFirst()) {
            do {
                int titleIndex = cursor.getColumnIndex("title");
                int categoryIndex = cursor.getColumnIndex("category");
                int priceIndex = cursor.getColumnIndex("price");
                int phoneIndex = cursor.getColumnIndex("phone");
                int descriptionIndex = cursor.getColumnIndex("description");
                int pictureIndex = cursor.getColumnIndex("picture");
                int commodityIdIndex = cursor.getColumnIndex("commodityId");
                int collectionNumIndex=cursor.getColumnIndex("collectionNum");
                int reviewNumIndex=cursor.getColumnIndex("reviewNum");

                if (titleIndex >= 0 && categoryIndex >= 0 && priceIndex >= 0 && phoneIndex >= 0 && descriptionIndex >= 0 && pictureIndex >= 0 && commodityIdIndex >= 0) {
                    String title = cursor.getString(titleIndex);
                    String category = cursor.getString(categoryIndex);
                    float price = cursor.getFloat(priceIndex);
                    String phone = cursor.getString(phoneIndex);
                    String description = cursor.getString(descriptionIndex);
                    byte[] picture = cursor.getBlob(pictureIndex);
                    int commodityId = cursor.getInt(commodityIdIndex);
                    int collectionNum=cursor.getInt(collectionNumIndex);
                    int reviewNum=cursor.getInt(reviewNumIndex);


                    commodity.setTitle(title);
                    commodity.setCategory(category);
                    commodity.setPrice(price);
                    commodity.setDescription(description);
                    commodity.setCommodityId(commodityId);
                    commodity.setPhone(phone);
                    commodity.setPicture(picture);
                    commodity.setCollectionNum(collectionNum);
                    commodity.setReviewNum(reviewNum);


                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return commodity;
    }


    /**
     * 通过学号查找我的发布物品信息
     * @param stuId 学生学号和商品id
     * @return 修改购物车Count+1
     * 使其得以通过学号来使得购物车的商品数量+1
     */
    public int  upCartCount1(String stuId ,Commodity commodity) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int cartCount=commodity.getCartCount()+1;

        values.put("cartCount", cartCount);
        int commodityId=commodity.getCommodityId();

        int update=db.update("tb_commodity", values, "stuId = ? AND commodityId = ?", new String[]{stuId, String.valueOf(commodityId)});


        db.close();
        return update;
    }



    /**
     * 通过学号查找我的发布物品信息
     * @param stuId 学生学号和商品id
     * @return 修改购物车Count-1
     * 使其得以通过学号来使得购物车的商品数量-1
     */
    public int upCartCount2(String stuId , Commodity commodity) {
        SQLiteDatabase db = this.getWritableDatabase();
        int update;
        int cartCount=commodity.getCartCount()-1;
        if (commodity.getCartCount()-1>=0){
            ContentValues values = new ContentValues();
            values.put("cartCount", cartCount);

            int commodityId=commodity.getCommodityId();

            update=db.update("tb_commodity", values, "stuId = ? AND commodityId = ?", new String[]{stuId, String.valueOf(commodityId)});
            db.close();
            return update;
        }
        db.close();
        return 0;
    }


    /**
     * 通过学号查找我的发布物品信息
     * @param commodityId 商品id
     * @return 修改购物车collectionNum+1
     * 使其得以通过学号来使得商品的收藏数量+1
     */
    public int upCollectionNum1(int commodityId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("SELECT * FROM tb_commodity WHERE commodityId = ?", new String[]{String.valueOf(commodityId)});

        if (cursor != null && cursor.moveToFirst()) {
            int collectionNumIndex = cursor.getColumnIndex("collectionNum");

            if (collectionNumIndex >= 0) {
                int collectionNum = cursor.getInt(collectionNumIndex) + 1;
                values.put("collectionNum", collectionNum);

                int update = db.update("tb_commodity", values, "commodityId = ?", new String[]{String.valueOf(commodityId)});

                cursor.close();
                db.close();
                return update;
            } else {
                // Handle the case where "collectionNum" column is not found
                cursor.close();
                db.close();
                return 0; // or any appropriate error code
            }
        } else {
            // Handle the case where the cursor is empty
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return 0; // or any appropriate error code
        }
    }

    /**
     * 通过学号查找我的发布物品信息
     * @param commodity 商品id
     * @return 修改购物车collectionNum-1
     * 使其得以通过学号来使得商品的收藏数量-1
     */
    public int  upCollectionNum2(Commodity commodity) {
        int collectionNum=commodity.getCollectionNum()-1;

        if (collectionNum >=0){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();


            values.put("collectionNum", collectionNum);
            int commodityId=commodity.getCommodityId();

            int update=db.update("tb_commodity", values, "commodityId = ?", new String[]{String.valueOf(commodityId)});


            db.close();
            return update;
        }

        return 0;
    }


    /**
     * 通过学号查找我的发布物品信息
     * @param commodity 商品id
     * @return 修改商品评论数reviewNum+1
     * 使其得以通过学号来使得商品的评论数量+1
     */
    public int  upReviewNum1(Commodity commodity) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int reviewNum=commodity.getReviewNum()+1;

        values.put("reviewNum", reviewNum);
        int commodityId=commodity.getCommodityId();

        int update=db.update("tb_commodity", values, "commodityId = ?", new String[]{String.valueOf(commodityId)});


        db.close();
        return update;
    }


    /**
     * 通过学号查找我的发布物品信息
     * @param commodity 商品id
     * @return 修改商品评论数reviewNum-1
     * 使其得以通过学号来使得商品的评论数量-1
     */
    public int  upReviewNum2(Commodity commodity) {
        int reviewNum=commodity.getReviewNum()-1;

        if (reviewNum >=0){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();


            values.put("reviewNum", reviewNum);
            int commodityId=commodity.getCommodityId();

            int update=db.update("tb_commodity", values, "commodityId = ?", new String[]{String.valueOf(commodityId)});


            db.close();
            return update;
        }

        return 0;
    }



    /**
     * 获取所有的商品信息
     * @return 所有的商品列表
     */
    public List<Commodity> readAllCommodities() {
        List<Commodity> allCommodities = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_commodity order by price", null);
        if (cursor.moveToFirst()) {
            do {
                int titleIndex = cursor.getColumnIndex("title");
                int categoryIndex = cursor.getColumnIndex("category");
                int priceIndex = cursor.getColumnIndex("price");
                int phoneIndex = cursor.getColumnIndex("phone");
                int descriptionIndex = cursor.getColumnIndex("description");
                int pictureIndex = cursor.getColumnIndex("picture");
                int stuIdIndex = cursor.getColumnIndex("stuId");
                int commodityIdIndex = cursor.getColumnIndex("commodityId");
                int collectionNumIndex=cursor.getColumnIndex("collectionNum");
                int reviewNumIndex=cursor.getColumnIndex("reviewNum");

                if (titleIndex >= 0 && categoryIndex >= 0 && priceIndex >= 0 && phoneIndex >= 0 && descriptionIndex >= 0 && pictureIndex >= 0 && stuIdIndex >= 0 && commodityIdIndex >= 0) {
                    String title = cursor.getString(titleIndex);
                    String category = cursor.getString(categoryIndex);
                    float price = cursor.getFloat(priceIndex);
                    String phone = cursor.getString(phoneIndex);
                    String description = cursor.getString(descriptionIndex);
                    byte[] picture = cursor.getBlob(pictureIndex);
                    String stuId = cursor.getString(stuIdIndex);
                    int commodityId = cursor.getInt(commodityIdIndex);
                    int collectionNum=cursor.getInt(collectionNumIndex);
                    int reviewNum=cursor.getInt(reviewNumIndex);

                    Commodity commodity = new Commodity();
                    commodity.setTitle(title);
                    commodity.setCategory(category);
                    commodity.setPrice(price);
                    commodity.setDescription(description);
                    commodity.setPhone(phone);
                    commodity.setPicture(picture);
                    commodity.setStuId(stuId);
                    commodity.setCommodityId(commodityId);
                    commodity.setCollectionNum(collectionNum);
                    commodity.setReviewNum(reviewNum);

                    allCommodities.add(commodity);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return allCommodities;
    }





    /**
     * 根据商品名称删除商品
     * @param title 商品名称
     * @param description 商品描述
     * @param price 商品价格
     */
    public void deleteMyCommodity(String title,String description,float price) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {
            db.delete(DB_NAME,"title=? and description=? and price=?",new String[]{title,description,String.valueOf(price)});
            db.close();
        }
    }

    /**
     * 读取不同类别的商品信息
     * @param category 类别
     * @return 商品列表
     */
    public List<Commodity> readCommodityType(String category) {
        List<Commodity> differentTypes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_commodity where category=?",new String[]{category});
        if(cursor.moveToFirst()) {
            do{
                int titleIndex = cursor.getColumnIndex("title");
                int categoryIndex = cursor.getColumnIndex("category");
                int priceIndex = cursor.getColumnIndex("price");
                int phoneIndex = cursor.getColumnIndex("phone");
                int descriptionIndex = cursor.getColumnIndex("description");
                int pictureIndex = cursor.getColumnIndex("picture");
                int stuIdIndex = cursor.getColumnIndex("stuId");
                int commodityIdIndex = cursor.getColumnIndex("commodityId");
                int collectionNumIndex=cursor.getColumnIndex("collectionNum");
                int reviewNumIndex=cursor.getColumnIndex("reviewNum");

                if (titleIndex >= 0 && categoryIndex >= 0 && priceIndex >= 0 && phoneIndex >= 0 && descriptionIndex >= 0 && pictureIndex >= 0 && stuIdIndex >= 0 && commodityIdIndex >= 0) {
                    String title = cursor.getString(titleIndex);
                    String category1 = cursor.getString(categoryIndex);
                    float price = cursor.getFloat(priceIndex);
                    String phone = cursor.getString(phoneIndex);
                    String description = cursor.getString(descriptionIndex);
                    byte[] picture = cursor.getBlob(pictureIndex);
                    String stuId = cursor.getString(stuIdIndex);
                    int commodityId = cursor.getInt(commodityIdIndex);
                    int collectionNum=cursor.getInt(collectionNumIndex);
                    int reviewNum=cursor.getInt(reviewNumIndex);

                    Commodity commodity = new Commodity();
                    commodity.setTitle(title);
                    commodity.setCategory(category1);
                    commodity.setPrice(price);
                    commodity.setDescription(description);
                    commodity.setPhone(phone);
                    commodity.setPicture(picture);
                    commodity.setStuId(stuId);
                    commodity.setCommodityId(commodityId);
                    commodity.setCollectionNum(collectionNum);
                    commodity.setReviewNum(reviewNum);

                    differentTypes.add(commodity);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return differentTypes;
    }

}
