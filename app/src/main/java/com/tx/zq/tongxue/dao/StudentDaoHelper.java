package com.tx.zq.tongxue.dao;

import org.xutils.DbManager;

/**
 * Created by Administrator on 2016/3/11.
 */
public class StudentDaoHelper {
    public DbManager.DaoConfig StudentDaoHelper() {
        DbManager.DaoConfig config = new DbManager.DaoConfig()
                .setDbName("student")
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                });
        return config;
    }


}
