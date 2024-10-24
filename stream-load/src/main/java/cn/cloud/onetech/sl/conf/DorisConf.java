package cn.cloud.onetech.sl.conf;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/24 11:33
 */
public class DorisConf {

    private String feHost;
    private String feHttpPort;
    private String feQueryPort;

    public static void main(String[] args) {
        String ddl = "create database if not exists doris_test;\n" +
                "use doris_test;\n" +
                "create table tbl_dk_50(\n" +
                "    col_1 bigint not null,\n" +
                "    col_2 char,\n" +
                "    col_3 date,\n" +
                "    col_4 datetime,\n" +
                "    col_5 decimal(20,10),\n" +
                "    col_6 double,\n" +
                "    col_7 float,\n" +
                "    col_8 int,\n" +
                "    col_9 largeint,\n" +
                "    col_10 smallint,\n" +
                "    col_11 tinyint,\n" +
                "    col_12 varchar,\n" +
                "    col_13 varchar,\n" +
                "    col_14 varchar,\n" +
                "    col_15 varchar,\n" +
                "    col_16 varchar,\n" +
                "    col_17 varchar,\n" +
                "    col_18 varchar,\n" +
                "    col_19 varchar,\n" +
                "    col_20 varchar,\n" +
                "    col_21 varchar,\n" +
                "    col_22 varchar,\n" +
                "    col_23 varchar,\n" +
                "    col_24 varchar,\n" +
                "    col_25 varchar,\n" +
                "    col_26 varchar,\n" +
                "    col_27 varchar,\n" +
                "    col_28 varchar,\n" +
                "    col_29 varchar,\n" +
                "    col_30 varchar,\n" +
                "    col_31 varchar,\n" +
                "    col_32 varchar,\n" +
                "    col_33 varchar,\n" +
                "    col_34 varchar,\n" +
                "    col_35 varchar,\n" +
                "    col_36 varchar,\n" +
                "    col_37 varchar,\n" +
                "    col_38 varchar,\n" +
                "    col_39 varchar,\n" +
                "    col_40 varchar,\n" +
                "    col_41 varchar,\n" +
                "    col_42 varchar,\n" +
                "    col_43 varchar,\n" +
                "    col_44 varchar,\n" +
                "    col_45 varchar,\n" +
                "    col_46 varchar,\n" +
                "    col_47 varchar,\n" +
                "    col_48 varchar,\n" +
                "    col_49 varchar,\n" +
                "    col_50 varchar\n" +
                ")\n" +
                "duplicate key(col_1)\n" +
                "distributed by hash(col_1)\n" +
                "properties(\n" +
                "\t\"replication_num\" = \"1\"\n" +
                ");";
        String[] sqls = ddl.split("\\;\n");
        for (String sql : sqls) {
            System.out.println(sql);
        }
    }

}
