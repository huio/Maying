package co.tgbot.peekfun.job;


import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import co.tgbot.peekfun.R;
import co.tgbot.peekfun.database.SSRSub;
import co.tgbot.peekfun.network.ssrsub.SubUpdateCallback;
import co.tgbot.peekfun.network.ssrsub.SubUpdateHelper;
import co.tgbot.peekfun.utils.Constants;
import co.tgbot.peekfun.utils.ToastUtils;
import co.tgbot.peekfun.utils.VayLog;
import co.tgbot.peekfun.ShadowsocksApplication;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

/**
 * @author Mygod
 */
public class SSRSubUpdateJob extends Job {

    public static final String TAG = SSRSubUpdateJob.class.getSimpleName();

    public static int schedule() {
        return new JobRequest.Builder(SSRSubUpdateJob.TAG)
                .setPeriodic(TimeUnit.HOURS.toMillis(1))
                .setRequirementsEnforced(true)
                .setRequiresCharging(false)
                .setUpdateCurrent(true)
                .build().schedule();
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        if (ShadowsocksApplication.app.settings.getInt(Constants.Key.ssrsub_autoupdate, 0) == 1) {
            List<SSRSub> subs = ShadowsocksApplication.app.ssrsubManager.getAllSSRSubs();
            SubUpdateHelper.Companion.instance().updateSub(subs,  new SubUpdateCallback() {
                @Override
                public void onSuccess(String subname) {
                    VayLog.d(TAG, "onRunJob() update sub success!");
                    ToastUtils.showShort(getContext().getString(R.string.sub_autoupdate_success, subname));
                    Log.i("sub", subname);
                }

                @Override
                public void onFailed() {
                    VayLog.e(TAG, "onRunJob() update sub failed!");
                }
            });
            return Result.SUCCESS;
        } else {
            return Result.RESCHEDULE;
        }
    }
}
