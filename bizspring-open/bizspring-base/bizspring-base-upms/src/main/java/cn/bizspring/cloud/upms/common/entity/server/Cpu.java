package cn.bizspring.cloud.upms.common.entity.server;

/**
 * CPU相关信息
 * 
 * @author bizspring
 */
public class Cpu
{
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNum()
    {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum)
    {
        this.cpuNum = cpuNum;
    }

    public double getTotal()
    {
        return total * 100;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public double getSys()
    {
        return sys / total * 100;
    }

    public void setSys(double sys)
    {
        this.sys = sys;
    }

    public double getUsed()
    {
        return used / total * 100;
    }

    public void setUsed(double used)
    {
        this.used = used;
    }

    public double getWait()
    {
        return wait / total * 100;
    }

    public void setWait(double wait)
    {
        this.wait = wait;
    }

    public double getFree()
    {
        return free / total * 100;
    }

    public void setFree(double free)
    {
        this.free = free;
    }
}
