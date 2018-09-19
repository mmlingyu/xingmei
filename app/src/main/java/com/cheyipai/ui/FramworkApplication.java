package com.cheyipai.ui;

import com.cheyipai.corec.modules.app.BaseApplication;

/**
 *
 */
public class FramworkApplication extends BaseApplication {
    @Override
    public void initAccount() {
    }

    @Override
    protected void initSettings() {
        super.initSettings();

    }

    /*private void initDatabase(){
        File f = new File(DatabaseHelper.DATABASE_PATH);
        if (!f.exists()) {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                    DatabaseHelper.DATABASE_PATH,null);
            DatabaseHelper.getHelper(this).onCreate(db);
            db.close();
        }
    }*/
}
