package cn.cloud.onetech.besl.conf;

import lombok.Data;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/25 15:29
 */
@Data

public class DorisConf {

    private static final String LOAD_URL_PATTERN = "http://%s/api/%s/%s/_stream_load";

    private String beHost;
    private String beHttpPort;
    private String username;
    private String password;
    private String db;
    private String table;

    public String getBeHost() {
        return beHost;
    }

    public void setBeHost(String beHost) {
        this.beHost = beHost;
    }

    public String getBeHttpPort() {
        return beHttpPort;
    }

    public void setBeHttpPort(String beHttpPort) {
        this.beHttpPort = beHttpPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public DorisConf(String beHost, String beHttpPort, String username, String password, String db, String table) {
        this.beHost = beHost;
        this.beHttpPort = beHttpPort;
        this.username = username;
        this.password = password;
        this.db = db;
        this.table = table;
    }

    public String beLoadUrl() {
        return String.format(LOAD_URL_PATTERN, beHost + ":" + beHttpPort, db, table);
    }

}
