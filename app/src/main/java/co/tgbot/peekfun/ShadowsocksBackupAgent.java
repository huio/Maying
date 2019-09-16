package co.tgbot.peekfun;

import android.app.backup.BackupAgentHelper;
import android.app.backup.FileBackupHelper;
import android.app.backup.SharedPreferencesBackupHelper;

import co.tgbot.peekfun.database.DBHelper;

public class ShadowsocksBackupAgent extends BackupAgentHelper {

    /**
     * The names of the SharedPreferences groups that the application maintains.  These
     * are the same strings that are passed to getSharedPreferences(String, int).
     */
    private static final String PREFS_DISPLAY = "co.tgbot.peekfun_preferences";

    /**
     * An arbitrary string used within the BackupAgentHelper implementation to
     * identify the SharedPreferencesBackupHelper's data.
     */
    private static final String MY_PREFS_BACKUP_KEY = "co.tgbot.peekfun";

    private static final String DATABASE = "co.tgbot.peekfun.database.profile";

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, PREFS_DISPLAY);
        addHelper(MY_PREFS_BACKUP_KEY, helper);
        addHelper(DATABASE, new FileBackupHelper(this, "../databases/" + DBHelper.PROFILE));
    }
}
