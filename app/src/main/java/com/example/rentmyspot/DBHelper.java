package com.example.rentmyspot;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import android.util.Log;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//
public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "RentMySpot.db";
    public static final String TABLENAME1 = "users";
    public static final String T1COL1 = "username";
    public static final String T1COL2 = "password";
    public static final String T1COL3 = "age";
    public static final String T1COL4 = "email";
    public static final String T1COL5 = "ReantedSeatingName";

    public static final String TABLENAME2 = "seating";
    public static final String T2COL1 = "SeatingID";
    public static final String T2COL2 = "username";
    public static final String T2COL3 = "SeatingName";
    public static final String T2COL4 = "SeatingCategory";
    public static final String T2COL5 = "SeatingPrice";
    public static final String T2COL6 = "SeatingDescription";
    public static final String T2COL7 = "seatingImage";

    // New RentedSeating table
    public static final String TABLENAME3 = "RentedSeating";
    public static final String T3COL1 = "RentedSeatingID";
    public static final String T3COL2 = "OwnerUsername";
    public static final String T3COL3 = "RenterUsername";

    public static final String T3COL4="SeatingName";

    public static final String T3COL5="SeatingCategory";

    public static final String T3COL6="SeatingPrice";

    public static final String T3COL7="SeatingDescription";

    public static final String T3COL8="seatingImage";


    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLENAME1 + "(" + T1COL1 + " TEXT PRIMARY KEY," + T1COL2 + " TEXT," + T1COL3 + " INTEGER," + T1COL4 + " TEXT," + T1COL5 + " TEXT" + ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLENAME2 + "(" + T2COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + T2COL2 + " TEXT,"
                + T2COL3 + " TEXT,"
                + T2COL4 + " TEXT,"
                + T2COL5 + " INTEGER,"
                + T2COL6 + " TEXT,"
                + T2COL7 + " BLOB" +
                ")");

        // New RentedSeating table creation
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLENAME3 + "("
                + T3COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + T3COL2 + " TEXT, "
                + T3COL3 + " TEXT, "
                + T3COL4 + " TEXT, "
                + T3COL5 + " TEXT, " // Add space before "TEXT"
                + T3COL6 + " INTEGER, " // Add space before "INTEGER"
                + T3COL7 + " TEXT, " // Add space before "TEXT"
                + T3COL8 + " BLOB"
                + ")");
    }


        @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists " + TABLENAME3); // Drop RentedSeating table first
        sqLiteDatabase.execSQL("drop Table if exists " + TABLENAME2);
        sqLiteDatabase.execSQL("drop Table if exists " + TABLENAME1);
        onCreate(sqLiteDatabase);
    }


    public boolean addSeating(Seating newSeating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(T2COL2, newSeating.getUserneme());
        cv.put(T2COL3, newSeating.getSname());
        cv.put(T2COL4, newSeating.getScategory());
        cv.put(T2COL5, newSeating.getSprice());
        cv.put(T2COL6, newSeating.getSdescription());
        cv.put(T2COL7, newSeating.getImageData());
        long insert = db.insert(TABLENAME2, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean DeleteOne(Seating deleteSeating) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLENAME2, T2COL3 + "=?", new String[] { deleteSeating.getSname() });
        if (deletedRows > 0) {
            return true;
        } else {
            // nothing happens. no one is added.
            return false;
        }
        //close
    }

    public Boolean insertData(String username, String password ,String email, String age) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(T1COL1, username);
        contentValues.put(T1COL2, password);
        contentValues.put(T1COL3, age);
        contentValues.put(T1COL4, email);

        long result = MyDB.insert(TABLENAME1, null, contentValues);

        if (result == -1)
            return false;
        return true;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME1 + " where " + T1COL1 + " = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        return false;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME1 + " where " + T1COL4 + " = ?", new String[]{email});
        if (cursor.getCount() > 0 )
            return true;
        return false;
    }



    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME1 + " where " + T1COL1 + " = ? and " + T1COL2 + " = ?", new String[]{username, password});
        if (cursor.getCount() > 0) return true;
        return false;
    }


    public List<Seating> SeatingList(String userneme) {
        List<Seating> returnList = new ArrayList<>();
        String queryString = "Select * from " + TABLENAME2 + " WHERE " +
                T2COL2 + " = '" + userneme + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String SName = cursor.getString(2);
                String Scat = cursor.getString(3);
                int Sprice = cursor.getInt(4);
                String Sdes = cursor.getString(5);
                byte[] image = cursor.getBlob(6);

                Seating newSeat = new Seating(userneme, SName, Scat, Sprice, Sdes,image);
                returnList.add(newSeat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<Seating> ListALLseatings() {
        List<Seating> returnList = new ArrayList<>();
        String queryString = "Select * from " + TABLENAME2 ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(1);
                String SName = cursor.getString(2);
                String Scat = cursor.getString(3);
                int Sprice = cursor.getInt(4);
                String Sdes = cursor.getString(5);
                byte[] image = cursor.getBlob(6);

                Seating newSeat = new Seating(username, SName, Scat, Sprice, Sdes,image);
                returnList.add(newSeat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }
    public List<Seating> ListAllSeatings(String currentUser) {
        List<Seating> seatings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLENAME2;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ownerUsername = cursor.getString(1);

                if (!ownerUsername.equals(currentUser)) {
                    // Check if the seating is rented
                    String rentedSeatingQuery = "SELECT * FROM " + TABLENAME3 + " WHERE " + T3COL2 + " = ?";
                    Cursor rentedSeatingCursor = db.rawQuery(rentedSeatingQuery, new String[]{String.valueOf(id)});

                    // If the seating is not rented, add it to the list
                    if (!rentedSeatingCursor.moveToFirst()) {
                        String seatingName = cursor.getString(2);
                        String seatingCategory = cursor.getString(3);
                        int seatingPrice = Integer.parseInt(cursor.getString(4));
                        String seatingDescription = cursor.getString(5);
                        byte[] seatingImageData = cursor.getBlob(6);

                        Seating seating = new Seating( ownerUsername, seatingName, seatingCategory, seatingPrice, seatingDescription, seatingImageData);
                        seatings.add(seating);
                    }
                    rentedSeatingCursor.close();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return seatings;
    }
    public boolean rentSeating(Seating seating, String currentUser) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert the rented seating into the rented seating table
        ContentValues contentValues = new ContentValues();
        contentValues.put(T3COL2, seating.getUserneme());
        contentValues.put(T3COL3, currentUser);
        contentValues.put(T3COL4, seating.getSname());
        contentValues.put(T3COL5, seating.getScategory());
        contentValues.put(T3COL6, seating.getSprice());
        contentValues.put(T3COL7, seating.getSdescription());
        contentValues.put(T3COL8, seating.getImageData());

        long insertResult = db.insert(TABLENAME3, null, contentValues);

        // If the insertion was successful, delete the seating from the available seatings table
        if (insertResult != -1) {
            String whereClause = T2COL2 + "=? AND " + T2COL3 + "=?";
            String[] whereArgs = new String[]{seating.getUserneme(), seating.getSname()};
            Log.d("DBHelper", "Delete seating SQL: WHERE " + whereClause + ", Args: " + Arrays.toString(whereArgs));

            int deleteResult = db.delete(TABLENAME2, whereClause, whereArgs);

            if (deleteResult > 0) {
                // Seating was successfully rented (inserted into rented seating table and deleted from available seatings table)
                db.close();
                Log.e("DBHelper","rent successfully");
                return true;
            } else {
                // An error occurred while deleting the seating from the available seatings table
                Log.e("DBHelper", "Failed to delete seating from available seatings table. Seating name: " + seating.getSname() + ", owner username: " + seating.getUserneme());
                db.close();
                return false;
            }
        } else {
            // An error occurred while inserting the seating into the rented seating table
            Log.e("DBHelper", "Failed to insert seating into rented seating table.");
            db.close();
            return false;
        }
    }

    public boolean removeRentedSeating(Seating seating) {
        SQLiteDatabase db = this.getWritableDatabase();

        int deleteResult = db.delete(TABLENAME3, T3COL1 + "=? AND " + T3COL3 + "=?", new String[]{seating.getSname(), seating.getUserneme()});

        if (deleteResult > 0) {
            // Seating was successfully removed from the rented seatings table
            db.close();
            return true;
        } else {
            // An error occurred while deleting the seating from the rented seatings table
            db.close();
            return false;
        }
    }


    // DBHelper.java
    public List<Seating> returnpagelist(String username) {
        List<Seating> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = T3COL3 + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLENAME3, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String owener = cursor.getString(cursor.getColumnIndex(T3COL2));
                String SName = cursor.getString(cursor.getColumnIndex(T3COL4));
                String Scat = cursor.getString(cursor.getColumnIndex(T3COL5));
                int Sprice = cursor.getInt(cursor.getColumnIndex(T3COL6));
                String Sdes = cursor.getString(cursor.getColumnIndex(T3COL7));
                byte[] image = cursor.getBlob(cursor.getColumnIndex(T3COL8));

                Seating newSeat = new Seating(owener, SName, Scat, Sprice, Sdes, image);
                returnList.add(newSeat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<Seating> getAllRentedSeatings(String currentUser) {
        List<Seating> rentedSeatings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch rented seatings
        String queryString = "SELECT s.* FROM " + TABLENAME3 + " rs" +
                " INNER JOIN " + TABLENAME2 + " s ON rs." + T3COL1 + " = s." + T2COL1 +
                " WHERE rs." + T3COL3 + " = ?";

        Cursor cursor = db.rawQuery(queryString, new String[]{currentUser});

        if (cursor.moveToFirst()) {
            do {
                int seatingIdIndex = cursor.getColumnIndex(T2COL1);
                int usernameIndex = cursor.getColumnIndex(T2COL2);
                int seatingNameIndex = cursor.getColumnIndex(T2COL3);
                int seatingCategoryIndex = cursor.getColumnIndex(T2COL4);
                int seatingPriceIndex = cursor.getColumnIndex(T2COL5);
                int seatingDescriptionIndex = cursor.getColumnIndex(T2COL6);
                int seatingImageIndex = cursor.getColumnIndex(T2COL7);

                if (seatingIdIndex != -1 && usernameIndex != -1 && seatingNameIndex != -1 && seatingCategoryIndex != -1 && seatingPriceIndex != -1 && seatingDescriptionIndex != -1 && seatingImageIndex != -1) {
                    int seatingId = cursor.getInt(seatingIdIndex);
                    String username = cursor.getString(usernameIndex);
                    String seatingName = cursor.getString(seatingNameIndex);
                    String seatingCategory = cursor.getString(seatingCategoryIndex);
                    int seatingPrice = cursor.getInt(seatingPriceIndex);
                    String seatingDescription = cursor.getString(seatingDescriptionIndex);
                    byte[] seatingImage = cursor.getBlob(seatingImageIndex);

                    Seating rentedSeating = new Seating(username, seatingName, seatingCategory, seatingPrice, seatingDescription, seatingImage);
                    rentedSeatings.add(rentedSeating);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return rentedSeatings;
    }
    public Seating getSeatingByID(int seatingID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLENAME2 + " WHERE " + T2COL1 + " = ?", new String[]{String.valueOf(seatingID)});

        if (cursor.moveToFirst()) {
            String ownerUsername = cursor.getString(1); // T2COL2
            String seatingName = cursor.getString(2);   // T2COL3
            String seatingCategory = cursor.getString(3); // T2COL4
            int seatingPrice = cursor.getInt(4);         // T2COL5
            String seatingDescription = cursor.getString(5); // T2COL6
            byte[] imageData = cursor.getBlob(6);        // T2COL7

            Seating seating = new Seating(ownerUsername, seatingName, seatingCategory, seatingPrice, seatingDescription, imageData);
            cursor.close();
            db.close();
            return seating;
        }

        cursor.close();
        db.close();
        return null;
    }
    public boolean returnSeating2(Seating seating) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert seating into TABLENAME2
        ContentValues contentValues = new ContentValues();
        contentValues.put(T2COL2, seating.getUserneme());
        contentValues.put(T2COL3, seating.getSname());
        contentValues.put(T2COL4, seating.getScategory());
        contentValues.put(T2COL5, seating.getSprice());
        contentValues.put(T2COL6, seating.getSdescription());
        contentValues.put(T2COL7, seating.getImageData());
        long insertResult = db.insert(TABLENAME2, null, contentValues);

        // Delete seating from TABLENAME3
        String whereClause = T3COL4 + " = ? AND " + T3COL2 + " = ?"; // Updated the whereClause
        String[] whereArgs = new String[]{seating.getSname(), seating.getUserneme()}; // Updated the whereArgs
        int deleteResult = db.delete(TABLENAME3, whereClause, whereArgs);

        db.close();
        return insertResult != -1 && deleteResult > 0;
    }
}