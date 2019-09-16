package co.tgbot.peekfun;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import co.tgbot.peekfun.database.Profile;
import co.tgbot.peekfun.utils.TaskerSettings;
import co.tgbot.peekfun.utils.Utils;

/**
 * @author CzBiX
 */
public class TaskerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TaskerSettings settings = TaskerSettings.fromIntent(intent);
        Profile profile = ShadowsocksApplication.app.profileManager.getProfile(settings.profileId);

        if (profile != null) {
             ShadowsocksApplication.app.switchProfile(settings.profileId);
        }

        if (settings.switchOn) {
            Utils.startSsService(context);
        } else {
            Utils.stopSsService(context);
        }
    }
}
