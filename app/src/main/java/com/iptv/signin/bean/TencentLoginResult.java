package com.iptv.signin.bean;

/**
 * Created by ZhangQian on 2017/3/22 0022.
 */

/**
 * {"ret":0,"pay_token":"07AED3EB54D1AE33563CE88D5B97BA47","pf":"desktop_m_qq-10000144-android-2002-","query_authority_cost":1528,"authority_cost":0,"openid":"E4EF98DA5802A31D98F57F0E9034E370","expires_in":7776000,"pfkey":"c9c58fa19b60b76a6af67950dda9aa4e","msg":"","access_token":"0CC612DD865F561DE9DF1C80687C73CA","login_cost":1467}
 */
public class TencentLoginResult {

    /**
     * ret : 0
     * pay_token : 07AED3EB54D1AE33563CE88D5B97BA47
     * pf : desktop_m_qq-10000144-android-2002-
     * query_authority_cost : 1528
     * authority_cost : 0
     * openid : E4EF98DA5802A31D98F57F0E9034E370
     * expires_in : 7776000
     * pfkey : c9c58fa19b60b76a6af67950dda9aa4e
     * msg :
     * access_token : 0CC612DD865F561DE9DF1C80687C73CA
     * login_cost : 1467
     */

    private int ret;
    private String pay_token;
    private String pf;
    private int query_authority_cost;
    private int authority_cost;
    private String openid;
    private String expires_in;
    private String pfkey;
    private String msg;
    private String access_token;
    private int login_cost;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getPay_token() {
        return pay_token;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public int getQuery_authority_cost() {
        return query_authority_cost;
    }

    public void setQuery_authority_cost(int query_authority_cost) {
        this.query_authority_cost = query_authority_cost;
    }

    public int getAuthority_cost() {
        return authority_cost;
    }

    public void setAuthority_cost(int authority_cost) {
        this.authority_cost = authority_cost;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getPfkey() {
        return pfkey;
    }

    public void setPfkey(String pfkey) {
        this.pfkey = pfkey;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getLogin_cost() {
        return login_cost;
    }

    public void setLogin_cost(int login_cost) {
        this.login_cost = login_cost;
    }
}
