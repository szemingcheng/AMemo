package com.szemingcheng.amemo.entity;

import com.szemingcheng.amemo.dao.DaoSession;
import com.szemingcheng.amemo.dao.MemoDao;
import com.szemingcheng.amemo.dao.NoteBKDao;
import com.szemingcheng.amemo.dao.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Created by szemingcheng on 2017/5/16.
 */
@Entity
public class Memo {
    public static final int IS_EXSIT=0x11;
    public static final int IS_DELETE=0x12;
    public static final int TYPE_CAM=0x21;
    public static final int TYPE_REMINDER=0x22;
    public static final int TYPE_TXT=0x23;
    @Id(autoincrement = true)
    Long _ID;

    String title;
    String context;
    String memotxt;
    String pic;

    Long creatat;
    @OrderBy Long updateat;
    Long reminder_date;
    int state;
    int type;

    Long NoteBK_ID;
    Long User_ID;

    @ToOne(joinProperty = "NoteBK_ID")
    NoteBK noteBK;
    @ToOne(joinProperty = "User_ID")
    User user;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1945564409)
    private transient MemoDao myDao;
    @Generated(hash = 654701953)
    public Memo(Long _ID, String title, String context, String memotxt, String pic,
            Long creatat, Long updateat, Long reminder_date, int state, int type,
            Long NoteBK_ID, Long User_ID) {
        this._ID = _ID;
        this.title = title;
        this.context = context;
        this.memotxt = memotxt;
        this.pic = pic;
        this.creatat = creatat;
        this.updateat = updateat;
        this.reminder_date = reminder_date;
        this.state = state;
        this.type = type;
        this.NoteBK_ID = NoteBK_ID;
        this.User_ID = User_ID;
    }
    @Generated(hash = 1901232184)
    public Memo() {
    }
    public Long get_ID() {
        return this._ID;
    }
    public void set_ID(Long _ID) {
        this._ID = _ID;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContext() {
        return this.context;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public String getMemotxt() {
        return this.memotxt;
    }
    public void setMemotxt(String memotxt) {
        this.memotxt = memotxt;
    }
    public String getPic() {
        return this.pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public Long getCreatat() {
        return this.creatat;
    }
    public void setCreatat(Long creatat) {
        this.creatat = creatat;
    }
    public Long getUpdateat() {
        return this.updateat;
    }
    public void setUpdateat(Long updateat) {
        this.updateat = updateat;
    }
    public Long getReminder_date() {
        return this.reminder_date;
    }
    public void setReminder_date(Long reminder_date) {
        this.reminder_date = reminder_date;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public Long getNoteBK_ID() {
        return this.NoteBK_ID;
    }
    public void setNoteBK_ID(Long NoteBK_ID) {
        this.NoteBK_ID = NoteBK_ID;
    }
    public Long getUser_ID() {
        return this.User_ID;
    }
    public void setUser_ID(Long User_ID) {
        this.User_ID = User_ID;
    }
    @Generated(hash = 913586305)
    private transient Long noteBK__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 668324443)
    public NoteBK getNoteBK() {
        Long __key = this.NoteBK_ID;
        if (noteBK__resolvedKey == null || !noteBK__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NoteBKDao targetDao = daoSession.getNoteBKDao();
            NoteBK noteBKNew = targetDao.load(__key);
            synchronized (this) {
                noteBK = noteBKNew;
                noteBK__resolvedKey = __key;
            }
        }
        return noteBK;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1549336661)
    public void setNoteBK(NoteBK noteBK) {
        synchronized (this) {
            this.noteBK = noteBK;
            NoteBK_ID = noteBK == null ? null : noteBK.get_ID();
            noteBK__resolvedKey = NoteBK_ID;
        }
    }
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1086511214)
    public User getUser() {
        Long __key = this.User_ID;
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
    @Generated(hash = 32337006)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            User_ID = user == null ? null : user.get_ID();
            user__resolvedKey = User_ID;
        }
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
    @Generated(hash = 1319799281)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMemoDao() : null;
    }

}
