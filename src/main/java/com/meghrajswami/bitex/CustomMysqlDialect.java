package com.meghrajswami.bitex;


import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Created by megh on 5/20/2017.
 */
public class CustomMysqlDialect extends MySQL5InnoDBDialect {

    @Override
    public String getTableTypeString() {
        //don't use COLLATION keyword with utf8mb4_unicode_ci, it won't work, maybe a bug in mysql
        return " ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ";
    }
}