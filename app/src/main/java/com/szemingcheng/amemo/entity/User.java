package com.szemingcheng.amemo.entity;

import com.szemingcheng.amemo.dao.DaoSession;
import com.szemingcheng.amemo.dao.MemoDao;
import com.szemingcheng.amemo.dao.NoteBKDao;
import com.szemingcheng.amemo.dao.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/16.
 */
@Entity
public class User {
    public static final int TYPE_USER =0x11;
    public static final int TYPE_VISITOR=0x12;
    @Id (autoincrement = true)
    Long _ID;

   @Unique String user_id;
    String passwrd;
    String phone;
    String onscreen_name;
    String avatar;

    int type;

    @ToMany(referencedJoinProperty = "User_ID")
    List<Memo>memos;
    @ToMany(referencedJoinProperty = "User_id")
    List<NoteBK>noteBKs;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;
    @Generated(hash = 1017096943)
    public User(Long _ID, String user_id, String passwrd, String phone,
            String onscreen_name, String avatar, int type) {
        this._ID = _ID;
        this.user_id = user_id;
        this.passwrd = passwrd;
        this.phone = phone;
        this.onscreen_name = onscreen_name;
        this.avatar = avatar;
        this.type = type;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long get_ID() {
        return this._ID;
    }
    public void set_ID(Long _ID) {
        this._ID = _ID;
    }
    public String getUser_id() {
        return this.user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getPasswrd() {
        return this.passwrd;
    }
    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getOnscreen_name() {
        return this.onscreen_name;
    }
    public void setOnscreen_name(String onscreen_name) {
        this.onscreen_name = onscreen_name;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1014608735)
    public List<Memo> getMemos() {
        if (memos == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MemoDao targetDao = daoSession.getMemoDao();
            List<Memo> memosNew = targetDao._queryUser_Memos(_ID);
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
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 534871373)
    public List<NoteBK> getNoteBKs() {
        if (noteBKs == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NoteBKDao targetDao = daoSession.getNoteBKDao();
            List<NoteBK> noteBKsNew = targetDao._queryUser_NoteBKs(_ID);
            synchronized (this) {
                if (noteBKs == null) {
                    noteBKs = noteBKsNew;
                }
            }
        }
        return noteBKs;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 354392246)
    public synchronized void resetNoteBKs() {
        noteBKs = null;
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
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

}
