package com.xuemooc.edxapp.utils.exception;

import com.xuemooc.edxapp.model.api.AuthErrorResponse;

/**
 * Created by hackeris on 15/7/29.
 */
public class AuthException extends Exception {

    private AuthErrorResponse authResponseObject;

    public AuthException(AuthErrorResponse res) {
        this.authResponseObject = res;
    }

    @Override
    public String getMessage() {
        return authResponseObject.detail;
    }
}
