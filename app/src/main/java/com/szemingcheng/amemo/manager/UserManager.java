package com.szemingcheng.amemo.manager;

/**
 * Created by Jaygren on 2017/5/23.
 */

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.utils.PreferencesUtils;

/**
* @date on 2017/5/22
* @author Jaygren
* @title  UserManager
* @describe 用户在线状态的判断与控制
*
*/

public class UserManager {
    private static String UserId;

    //强制主进程优先进行在线状态检查，保证异步操作安全
    public static synchronized String getUserId(){
        if(UserId==null){
            initUserid();
        }
        return  UserId;
    }

    private static void initUserid() {
        if(!PreferencesUtils.islogin(App.getAppcontext(),PreferencesUtils.LOGINED,false)){
            UserId="";
        }else{
            UserId=PreferencesUtils.getUserId(App.getAppcontext(),PreferencesUtils.USERID,"");
        }
    }
}
