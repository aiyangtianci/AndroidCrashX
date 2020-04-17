package com.aiyang.crashx.inter;

public interface ICrash {
    /**
     * 自定义错误处理
     * @return true:处理了该异常; 否则返回false
     */
    boolean handleException(Throwable ex);
}
