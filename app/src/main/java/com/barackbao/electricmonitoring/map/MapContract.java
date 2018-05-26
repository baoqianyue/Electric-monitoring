package com.barackbao.electricmonitoring.map;

import com.barackbao.electricmonitoring.mvp.BasePresenter;
import com.barackbao.electricmonitoring.mvp.BaseView;

/**
 * Created by Baoqianyue on 2018/5/26.
 */

public interface MapContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
    }
}
