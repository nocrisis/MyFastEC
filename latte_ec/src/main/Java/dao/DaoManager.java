package dao;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

public class DaoManager {
    private DaoSession mDaoSession = null;
    private UserProfileDao mUserDao = null;

    private DaoManager() {
    }

    public DaoManager init(Context context) {
        initDao(context);
        return this;
    }

    private static final class Holder {
        private static final DaoManager INSTANCE = new DaoManager();
    }

    public static DaoManager getInstance() {
        return Holder.INSTANCE;
    }

    private void initDao(Context context) {
        final ReleaseOpenHelper openHelper = new ReleaseOpenHelper(context, "fast_ec.db");
        final Database database = openHelper.getWritableDb();
        mDaoSession = new DaoMaster(database).newSession();
        mUserDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao() {
        return mUserDao;
    }
}
