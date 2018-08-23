package com.example.abc.myapplication22;

public class Login {

    /**
     * retCode : 200
     * msg : success
     * result : {"token":"d8b5403cb22f6e17e8e57d8d8a24e497","uid":"e5b0d1b60461ea4605cf27947f739bce"}
     */

    private String retCode;
    private String msg;
    private ResultBean result;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * token : d8b5403cb22f6e17e8e57d8d8a24e497
         * uid : e5b0d1b60461ea4605cf27947f739bce
         */

        private String token;
        private String uid;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
