package com.szemingcheng.amemo.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.szemingcheng.amemo.entity.User;

import com.szemingcheng.amemo.entity.NoteBK;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NOTE_BK".
*/
public class NoteBKDao extends AbstractDao<NoteBK, Long> {

    public static final String TABLENAME = "NOTE_BK";

    /**
     * Properties of entity NoteBK.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _ID = new Property(0, Long.class, "_ID", true, "_id");
        public final static Property Notebk_id = new Property(1, String.class, "notebk_id", false, "NOTEBK_ID");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property User_id = new Property(3, Long.class, "User_id", false, "USER_ID");
    }

    private DaoSession daoSession;

    private Query<NoteBK> user_NoteBKsQuery;

    public NoteBKDao(DaoConfig config) {
        super(config);
    }
    
    public NoteBKDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOTE_BK\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _ID
                "\"NOTEBK_ID\" TEXT UNIQUE ," + // 1: notebk_id
                "\"TITLE\" TEXT NOT NULL ," + // 2: title
                "\"USER_ID\" INTEGER);"); // 3: User_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOTE_BK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, NoteBK entity) {
        stmt.clearBindings();
 
        Long _ID = entity.get_ID();
        if (_ID != null) {
            stmt.bindLong(1, _ID);
        }
 
        String notebk_id = entity.getNotebk_id();
        if (notebk_id != null) {
            stmt.bindString(2, notebk_id);
        }
        stmt.bindString(3, entity.getTitle());
 
        Long User_id = entity.getUser_id();
        if (User_id != null) {
            stmt.bindLong(4, User_id);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, NoteBK entity) {
        stmt.clearBindings();
 
        Long _ID = entity.get_ID();
        if (_ID != null) {
            stmt.bindLong(1, _ID);
        }
 
        String notebk_id = entity.getNotebk_id();
        if (notebk_id != null) {
            stmt.bindString(2, notebk_id);
        }
        stmt.bindString(3, entity.getTitle());
 
        Long User_id = entity.getUser_id();
        if (User_id != null) {
            stmt.bindLong(4, User_id);
        }
    }

    @Override
    protected final void attachEntity(NoteBK entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public NoteBK readEntity(Cursor cursor, int offset) {
        NoteBK entity = new NoteBK( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // notebk_id
            cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // User_id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, NoteBK entity, int offset) {
        entity.set_ID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNotebk_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTitle(cursor.getString(offset + 2));
        entity.setUser_id(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(NoteBK entity, long rowId) {
        entity.set_ID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(NoteBK entity) {
        if(entity != null) {
            return entity.get_ID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(NoteBK entity) {
        return entity.get_ID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "noteBKs" to-many relationship of User. */
    public List<NoteBK> _queryUser_NoteBKs(Long User_id) {
        synchronized (this) {
            if (user_NoteBKsQuery == null) {
                QueryBuilder<NoteBK> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.User_id.eq(null));
                user_NoteBKsQuery = queryBuilder.build();
            }
        }
        Query<NoteBK> query = user_NoteBKsQuery.forCurrentThread();
        query.setParameter(0, User_id);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getUserDao().getAllColumns());
            builder.append(" FROM NOTE_BK T");
            builder.append(" LEFT JOIN USER T0 ON T.\"USER_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected NoteBK loadCurrentDeep(Cursor cursor, boolean lock) {
        NoteBK entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        User user = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
        entity.setUser(user);

        return entity;    
    }

    public NoteBK loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<NoteBK> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<NoteBK> list = new ArrayList<NoteBK>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<NoteBK> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<NoteBK> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
