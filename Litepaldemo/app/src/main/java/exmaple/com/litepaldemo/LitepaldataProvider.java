package exmaple.com.litepaldemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import org.litepal.tablemanager.Connector;

public class LitepaldataProvider extends ContentProvider {
    private SQLiteDatabase sqlDb;
    private static UriMatcher uriMatcher;

    public LitepaldataProvider() {

    }

    //注册uri，提供对外访问的接口；
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("example.com.litepaldemo.provider", "News", 0);
        uriMatcher.addURI("example.com.litepaldemo.provider", "News/1", 1);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteRows = 0;

        switch (uriMatcher.match(uri)) {
            case 0:
                deleteRows = sqlDb.delete("News", selection, selectionArgs);
                break;
            case 1:
                String Newsid_str = uri.getPathSegments().get(1);
                deleteRows = sqlDb.delete("News", "id=1", new String[]{Newsid_str});
                break;
        }
        return deleteRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case 0:
                return "vnd.android.cursor.dir/News";
            case 1:
                return "vnd.android.cursor.item/News";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri uriReturn = null;
        //判断uri和已经注册的uri的匹配码；
        switch (uriMatcher.match(uri)) {
            case 0:
            case 1:
                long newBookId = sqlDb.insert("News", null, values);
                uriReturn = Uri.parse("content://example.com.litepaldemo.provider/News/" + newBookId);
                break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        sqlDb = Connector.getDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case 0:
                cursor = sqlDb.query("News", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case 1:
                String id_str = uri.getPathSegments().get(1);
                cursor = sqlDb.query("News", projection, "id=1", new String[]{id_str}, null, null, sortOrder);
                break;

        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updateRows = 0;
        switch (uriMatcher.match(uri)) {
            case 0:
                updateRows = sqlDb.update("News", values, selection, selectionArgs);
                break;
            case 1:
                String News_idstr = uri.getPathSegments().get(1);
                updateRows = sqlDb.update("News", values, "id=1", new String[]{News_idstr});
                break;
        }
        return updateRows;
    }
}
