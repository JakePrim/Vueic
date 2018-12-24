package com.prim.plug;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author prim
 * @version 1.0.0
 * @desc 插件加载管理器
 * @time 2018/7/25 - 下午10:56
 */
public class PluginManager {
    private Resources resources;

    private DexClassLoader classLoader;

    private static final PluginManager ourInstance = new PluginManager();

    private PackageInfo packageInfo;

    public static PluginManager getInstance() {
        return ourInstance;
    }

    private PluginManager() {
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void loadPath(Context context,String pluginName) {
        File pluginDir = context.getDir("plugin", MODE_PRIVATE);

        String absolutePath = new File(pluginDir, pluginName).getAbsolutePath();

        packageInfo = context.getPackageManager().getPackageArchiveInfo(absolutePath, PackageManager.GET_ACTIVITIES);

        File dex = context.getDir("dex", MODE_PRIVATE);
        //new DexClassLoader
        classLoader = new DexClassLoader(absolutePath, dex.getAbsolutePath(), null, context.getClassLoader());
        try {
            //重置资源路径
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, absolutePath);
            //返回新的资源
            resources = new Resources(assetManager,
                    context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
            //解析插件包中manifests是否有静态广播
//            parserReceive(context,absolutePath);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析xml静态注册的广播
     * @param context
     * @param absolutePath
     */
    private void parserReceive(Context context, String absolutePath) {
        try {
            //反射获取解析apk包的类
            Class packageParserClass = Class.forName("android.content.pm.PackageParser");
            //获取方法
            Method parsePackage = packageParserClass.getDeclaredMethod("parsePackage",
                    File.class, int.class);
            //实例化PackageParser类
            Object newInstance = packageParserClass.newInstance();
            //Package 得到
            Object packageObj = parsePackage.invoke(newInstance, absolutePath, PackageManager.GET_ACTIVITIES);
            Field receiversField = packageObj.getClass().getDeclaredField("receivers");
            //获取List<Activity>
            List receivers = (List) receiversField.get(packageObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Resources getResources() {
        return resources;
    }

    public DexClassLoader getClassLoader() {
        return classLoader;
    }
}
