package br.ufpe.cin.if710.rss.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.ufpe.cin.if710.rss.ItemRSS;

import java.util.ArrayList;
import java.util.List;

public class SQLiteRSSHelper extends SQLiteOpenHelper {
    //Nome do Banco de Dados
    private static final String DATABASE_NAME = "rss";
    //Nome da tabela do Banco a ser usada
    public static final String DATABASE_TABLE = "items";
    //Versão atual do banco
    private static final int DB_VERSION = 1;

    //alternativa
    Context c;

    private SQLiteRSSHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        c = context;
    }

    private static SQLiteRSSHelper db;

    //Definindo Singleton
    public static SQLiteRSSHelper getInstance(Context c) {
        if (db==null) {
            db = new SQLiteRSSHelper(c.getApplicationContext());
        }
        return db;
    }

    //Definindo constantes que representam os campos do banco de dados
    public static final String ITEM_ROWID = RssProviderContract._ID;
    public static final String ITEM_TITLE = RssProviderContract.TITLE;
    public static final String ITEM_DATE = RssProviderContract.DATE;
    public static final String ITEM_DESC = RssProviderContract.DESCRIPTION;
    public static final String ITEM_LINK = RssProviderContract.LINK;
    public static final String ITEM_UNREAD = RssProviderContract.UNREAD;

    //Definindo constante que representa um array com todos os campos
    public final static String[] columns = { ITEM_ROWID, ITEM_TITLE, ITEM_DATE, ITEM_DESC, ITEM_LINK, ITEM_UNREAD};

    //Definindo constante que representa o comando de criação da tabela no banco de dados
    private static final String CREATE_DB_COMMAND = "CREATE TABLE " + DATABASE_TABLE + " (" +
            ITEM_ROWID +" integer primary key autoincrement, "+
            ITEM_TITLE + " text not null unique, " +
            ITEM_DATE + " text not null, " +
            ITEM_DESC + " text not null, " +
            ITEM_LINK + " text not null, " +
            ITEM_UNREAD + " boolean not null);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Executa o comando de criação de tabela
        db.execSQL(CREATE_DB_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //estamos ignorando esta possibilidade no momento
        throw new RuntimeException("nao se aplica");
    }

    //IMPLEMENTAR ABAIXO
    //Implemente a manipulação de dados nos métodos auxiliares para não ficar criando consultas manualmente
    public long insertItem(ItemRSS item) {
        return insertItem(item.getTitle(),item.getPubDate(),item.getDescription(),item.getLink());
    }
    public long insertItem(String title, String pubDate, String description, String link) {
        ContentValues values = new ContentValues();
        values.put(ITEM_TITLE, title);
        values.put(ITEM_DESC, description);
        values.put(ITEM_LINK, link);
        values.put(ITEM_DATE, pubDate);
        values.put(ITEM_UNREAD, true);
        return getWritableDatabase().insertWithOnConflict(DATABASE_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }
    public ItemRSS getItemRSS(String link) throws SQLException {
        String query = String.format("SELECT * FROM %s WHERE %s.%s = %s",
                DATABASE_TABLE,
                DATABASE_TABLE, ITEM_UNREAD,
                link);
        try (Cursor cursor = getReadableDatabase().rawQuery(query, null)) {
            List<ItemRSS> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    list.add(new ItemRSS(
                            cursor.getString(cursor.getColumnIndex(ITEM_TITLE)),
                            cursor.getString(cursor.getColumnIndex(ITEM_LINK)),
                            cursor.getString(cursor.getColumnIndex(ITEM_DATE)),
                            cursor.getString(cursor.getColumnIndex(ITEM_DESC))
                    ));
                } while (cursor.moveToNext());
            }
            if(list.size() >= 1){
                return list.get(0);
            } else {
                return null;
            }
        }
    }
    public List<ItemRSS> getItems() throws SQLException {
        String query = String.format("SELECT * FROM %s WHERE %s.%s = 1",
                DATABASE_TABLE,
                DATABASE_TABLE, ITEM_UNREAD);
        try (Cursor cursor = getReadableDatabase().rawQuery(query, null)) {
            List<ItemRSS> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    list.add(new ItemRSS(
                            cursor.getString(cursor.getColumnIndex(ITEM_TITLE)),
                            cursor.getString(cursor.getColumnIndex(ITEM_LINK)),
                            cursor.getString(cursor.getColumnIndex(ITEM_DATE)),
                            cursor.getString(cursor.getColumnIndex(ITEM_DESC))
                    ));
                } while (cursor.moveToNext());
            }
            return list;
        }
    }
    public boolean markAsUnread(String link) {
        ContentValues values = new ContentValues();
        values.put(ITEM_UNREAD, true);
        return getWritableDatabase().update(
                DATABASE_TABLE,
                values,
                ITEM_LINK + " = ?",
                new String[] {link}) > 0;
    }

    public boolean markAsRead(String link) {
        ContentValues values = new ContentValues();
        values.put(ITEM_UNREAD, false);
        return getWritableDatabase().update(
                DATABASE_TABLE,
                values,
                ITEM_LINK + " = ?",
                new String[] {link}) > 0;
    }
}