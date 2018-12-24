package com.monitoring.seckill.Util.ZookeeperUtil;

public class LockNode  implements Comparable{
    private String name;

    public LockNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int compareTo(Object o) {
        LockNode cp = (LockNode)o;
        return this.name.compareTo(cp.getName());
    }
}
