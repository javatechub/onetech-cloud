create database if not exists doris_test;
use doris_test;
create table tbl_dk_50(
    col_1 bigint not null,
    col_2 char,
    col_3 date,
    col_4 datetime,
    col_5 decimal(20,10),
    col_6 double,
    col_7 float,
    col_8 int,
    col_9 largeint,
    col_10 smallint,
    col_11 tinyint,
    col_12 varchar,
    col_13 varchar,
    col_14 varchar,
    col_15 varchar,
    col_16 varchar,
    col_17 varchar,
    col_18 varchar,
    col_19 varchar,
    col_20 varchar,
    col_21 varchar,
    col_22 varchar,
    col_23 varchar,
    col_24 varchar,
    col_25 varchar,
    col_26 varchar,
    col_27 varchar,
    col_28 varchar,
    col_29 varchar,
    col_30 varchar,
    col_31 varchar,
    col_32 varchar,
    col_33 varchar,
    col_34 varchar,
    col_35 varchar,
    col_36 varchar,
    col_37 varchar,
    col_38 varchar,
    col_39 varchar,
    col_40 varchar,
    col_41 varchar,
    col_42 varchar,
    col_43 varchar,
    col_44 varchar,
    col_45 varchar,
    col_46 varchar,
    col_47 varchar,
    col_48 varchar,
    col_49 varchar,
    col_50 varchar
)
duplicate key(col_1)
distributed by hash(col_1)
properties(
	"replication_num" = "1"
);