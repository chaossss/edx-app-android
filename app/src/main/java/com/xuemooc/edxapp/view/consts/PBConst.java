package com.xuemooc.edxapp.view.consts;

import com.xuemooc.edxapp.view.utils.IpbConst;

/**
 * Const Values used to deal with ProgressButton's event
 * Created by chaossss on 2015/9/22.
 */
public enum PBConst implements IpbConst {
    PB_INIT(0),
    PB_WRONG_PSD("密码错误", -1),
    PB_BAD_WEB("网络环境差", -1),
    PB_LOGIN_SUCCESS("登录成功",100),
    PB_SUBMIT_SUCCESS("提交成功", 100),
    PB_INVALID_USER("无效的用户名", -1);

    private int state;
    private String info;

    PBConst(int state){
        this(null, state);
    }

    PBConst(String info, int state){
        this.info = info;
        this.state = state;
    }

    @Override
    public int getPBState() {
        return this.state;
    }

    @Override
    public String getPBString() {
        return this.info;
    }
}
