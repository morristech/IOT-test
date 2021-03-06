package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public interface JavaJSExecutor {

    public interface Factory {
        JavaJSExecutor create() throws Exception;
    }

    public static class ProxyExecutorException extends Exception {
        public ProxyExecutorException(Throwable cause) {
            super(cause);
        }
    }

    void close();

    @DoNotStrip
    String executeJSCall(String str, String str2) throws ProxyExecutorException;

    @DoNotStrip
    void loadApplicationScript(String str) throws ProxyExecutorException;

    @DoNotStrip
    void setGlobalVariable(String str, String str2);
}
