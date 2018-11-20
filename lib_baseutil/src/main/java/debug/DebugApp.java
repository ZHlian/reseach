package debug;

import com.zhlian.lib_baseutil.GreenDaoUtil;
import com.zhlian.lib_baseutil.util.ThirdAppUtil;
import com.zhlian.lib_common.base.BaseApplication;
import com.zhlian.lib_common.utils.Utils;

/**
 * Created by Hua on 2018-11-20.
 * Pown
 */

public class DebugApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ThirdAppUtil.getInstance().init(getApplicationContext());
        GreenDaoUtil.getInstance().init(getApplicationContext());
    }
}
