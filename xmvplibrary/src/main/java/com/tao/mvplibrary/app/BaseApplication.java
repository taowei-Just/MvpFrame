package com.tao.mvplibrary.app;

import android.app.Application;

import com.arialyy.aria.core.Aria;
import com.tao.logger.log.Logger;
import com.tao.mvpbaselibrary.app.BasicApplication;
import com.tao.mvpbaselibrary.basic.network.NetworkManager;
import com.tao.mvpbaselibrary.basic.utils.ToastUtil;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.business.DefaultDownloadWorker;
import org.lzh.framework.updatepluginlib.callback.DefaultCheckCB;
import org.lzh.framework.updatepluginlib.callback.DefaultDownloadCB;
import org.lzh.framework.updatepluginlib.creator.DefaultFileChecker;
import org.lzh.framework.updatepluginlib.creator.DefaultFileCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedDownloadCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedInstallCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedUpdateCreator;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.DefaultChecker;
import org.lzh.framework.updatepluginlib.model.HttpMethod;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.strategy.WifiFirstStrategy;

public class BaseApplication extends BasicApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        NetworkManager.getInstance().init(this);
        initDownloadManager();
        initUpdata();
        Logger.init(this);
        ToastUtil.init(this);
    }

    private void initUpdata() {
        CheckEntity entity = new CheckEntity();
        entity.setMethod(HttpMethod.GET);
        entity.setUrl("");

        UpdateConfig.getConfig()
                // 设置 请求方式和参数 
                .checkEntity(entity)
                // 版本比对
                .updateChecker(new DefaultChecker())
                //
                .updateDialogCreator(new DefaultNeedUpdateCreator())
                // 下载回调
                .downloadCB(new DefaultDownloadCB())
                // 下载进度弹窗
                .downloadDialogCreator(new DefaultNeedDownloadCreator())
                // 下载工作线程
                .downloadWorker(new DefaultDownloadWorker())
                //版本检查回调
                .checkCB(new DefaultCheckCB())
                // 设置url 此处设置会重置  checkEntity
                .url("")
                //文件创建器
                .fileCreator(new DefaultFileCreator())
                //数据解析
                .jsonParser(new UpdateParser() {
                    @Override
                    public <T extends Update> T parse(String httpResponse) {
                        return null;
                    }
                })
                // 安装前版本检查
                .installChecker(new DefaultFileChecker())
                //更新策略 
                .strategy(new WifiFirstStrategy())
                // 安装选择弹窗
                .installDialogCreator(new DefaultNeedInstallCreator());
                // 初始化
//                .init(this);

//        UpdateBuilder.create().check();
    }

    /**
     * 初始化下载配置
     */
    private void initDownloadManager() {
        Aria.init(this);
    }


}
