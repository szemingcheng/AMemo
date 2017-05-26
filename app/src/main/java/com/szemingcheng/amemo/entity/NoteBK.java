package com.szemingcheng.amemo.entity;

import com.szemingcheng.amemo.dao.DaoSession;
import com.szemingcheng.amemo.dao.MemoDao;
import com.szemingcheng.amemo.dao.NoteBKDao;
import com.szemingcheng.amemo.dao.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/16.
 */
@Entity
public class NoteBK {
    @Id(autoincrement = true)
    Long _ID;

    @Unique String notebk_id;
    @NotNull String title;
    Long User_id;

    @ToMany(referencedJoinProperty = "NoteBK_ID")
    List<Memo> memos;
    @ToOne(joinProperty = "User_id")
    User user;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1336144060)
    private transient NoteBKDao myDao;
    @Generated(hash = 1973851309)
    public NoteBK(Long _ID, String notebk_id, @NotNull String title, Long User_id) {
        this._ID = _ID;
        this.notebk_id = notebk_id;
        this.title = title;
        this.User_id = User_id;
    }
    @Generated(hash = 1268618589)
    public NoteBK() {
    }
    public Long get_ID() {
        return this._ID;
    }
    public void set_ID(Long _ID) {
        this._ID = _ID;
    }
    public String getNotebk_id() {
        return this.notebk_id;
    }
    public void setNotebk_id(String notebk_id) {
        this.notebk_id = notebk_id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getUser_id() {
        return this.User_id;
    }
    public void setUser_id(Long User_id) {
        this.User_id = User_id;
    }
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2043031943)
    public User getUser() {
        Long __key = this.User_id;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 434649685)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            User_id = user == null ? null : user.get_ID();
            user__resolvedKey = User_id;
        }
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1637879271)
    public List<Memo> getMemos() {
        if (memos == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MemoDao targetDao = daoSession.getMemoDao();
            List<Memo> memosNew = targetDao._queryNoteBK_Memos(_ID);
            synchronized (this) {
                if (memos == null) {
                    memos = memosNew;
                }
            }
        }
        return memos;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1481839123)
    public synchronized void resetMemos() {
        memos = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 414423300)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNoteBKDao() : null;
    }

}
