package com.szemingcheng.amemo.model;

import com.szemingcheng.amemo.entity.Response;

/**
 * Created by Jaygren on 2017/5/24.
 */

public interface UserModel {
    interface UseridexistedListener{
        void Useidexisted(String userid);
        void UseridUnexited(String userid);
    }
void user_check(String Userid,UseridexistedListener useridexistedListener);
 Response loadresponse(String resCode, String message,String password,String userid,String phone,String onscreen_name);
}
