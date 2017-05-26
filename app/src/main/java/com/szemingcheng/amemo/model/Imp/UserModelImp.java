package com.szemingcheng.amemo.model.Imp;

import com.szemingcheng.amemo.dao.UserDao;
import com.szemingcheng.amemo.db.DBUtils;
import com.szemingcheng.amemo.db.UserHelper;
import com.szemingcheng.amemo.entity.Response;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.model.UserModel;

/**
 * Created by Jaygren on 2017/5/24.
 */

public class UserModelImp implements UserModel{
    private UserHelper userHelper= DBUtils.getUserDriverHelper();
    private User user;
private Response response;
    @Override
    public void user_check(String Userid, UseridexistedListener useridexistedListener) {
        user=userHelper.queryBuilder().where(UserDao.Properties.User_id.eq(Userid)).unique();
    if(user==null){
        useridexistedListener.UseridUnexited(Userid);
    }else{
        useridexistedListener.Useidexisted(Userid);
    }
    }

    @Override
    public Response loadresponse(String resCode, String message,String password,String userid,String phone,String onscreen_name) {
        response=new Response(resCode,message,password,userid,phone,onscreen_name);
        return response;
    }
}
