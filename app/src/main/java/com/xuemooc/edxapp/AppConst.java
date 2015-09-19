package com.xuemooc.edxapp;

/**
 * Created by chaossss on 2015/9/18.
 */
public class AppConst {
    /**
     * Login const
     */
    public enum LoginConst{
        LOGIN_INIT(0),
        LOGIN_SUCCESS(100),
        LOGIN_LOGINING(50),
        LOGIN_WRONG_PSD("密码错误", -1),
        LOGIN_BAD_WEB("网络环境差", -1),
        LOGIN_INVALID_USER("无效的用户名", -1);

        private int state;
        private String info;

        LoginConst(int state){
            this.state = state;
        }

        LoginConst(String info, int state){
            this.info = info;
            this.state = state;
        }

        public String getErrorInfo(){
            return this.info;
        }

        public int getLoginState(){
            return this.state;
        }
    }

}
