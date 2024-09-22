package cn.bizspring.cloud.upms.common.entity.server;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * JVM相关信息
 * 
 * @author bizspring
 */
public class Jvm
{
    /**
     * 当前JVM占用的内存总数(M)
     */
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    public double getTotal()
    {
        return total/(1024 * 1024);
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public double getMax()
    {
        return max/(1024 * 1024);
    }

    public void setMax(double max)
    {
        this.max = max;
    }

    public double getFree()
    {
        return free/(1024 * 1024);
    }

    public void setFree(double free)
    {
        this.free = free;
    }

    public double getUsed()
    {
        return (total - free)/(1024 * 1024);
    }

    public double getUsage()
    {
        return (total - free)/total * 100;
    }

    /**
     * 获取JDK名称
     */
    public String getName()
    {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getHome()
    {
        return home;
    }

    public void setHome(String home)
    {
        this.home = home;
    }

    /**
     * JDK启动时间
     */
    public String getStartTime()
    {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return DateUtil.format(new Date(time), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * JDK运行时间
     */
    public String getRunTime()
    {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return String.valueOf(DateUtil.between(new Date(ManagementFactory.getRuntimeMXBean().getStartTime()) , new Date() , DateUnit.MINUTE));
    }
}
