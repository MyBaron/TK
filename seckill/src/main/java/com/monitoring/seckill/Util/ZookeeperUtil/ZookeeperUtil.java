package com.monitoring.seckill.Util.ZookeeperUtil;

import com.monitoring.seckill.Util.PropertiesUtil;
import org.apache.zookeeper.*;


import java.io.IOException;
import java.util.Objects;
public class ZookeeperUtil {

    // 会话超时时间，设置为与系统默认时间一致
    private static final int SESSION_TIMEOUT = 30 * 1000;
    public static String ip;

    static {
        ip = PropertiesUtil.getProperty("zookeeper.ip", "10.10.10.10");
    }

    /**
     * 创建实例
     */
    private static volatile ZooKeeper zk;

    /**
     * watcher 实例
     */
    private static Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            System.out.println("WatchedEvent >>> " + event.toString());
        }
    };

    public static ZooKeeper createZk() throws IOException {
        if (Objects.isNull(zk)) {
            synchronized (ZookeeperUtil.class) {
                // 连接到ZK服务
                if (Objects.isNull(zk)) {
                    zk = new ZooKeeper(ip, SESSION_TIMEOUT, watcher);
                    System.out.println("创建节点成功");
                }
            }
        }
        return zk;
    }

    public static String createNode(String path,String node) throws IOException, KeeperException, InterruptedException {
        createZk();
        return zk.create(path, node.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    private static void ZKOperations() throws IOException, InterruptedException, KeeperException {
        System.out.println("\n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
        zk.create("/zoo2", "myData2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("\n2. 查看是否创建成功： ");
        System.out.println(new String(zk.getData("/zoo2", watcher, null)));// 添加Watch

        // 前面一行我们添加了对/zoo2节点的监视，所以这里对/zoo2进行修改的时候，会触发Watch事件。
        System.out.println("\n3. 修改节点数据 ");
        zk.setData("/zoo2", "shanhy20160310".getBytes(), -1);

        // 这里再次进行修改，则不会触发Watch事件，这就是我们验证ZK的一个特性“一次性触发”，也就是说设置一次监视，只会对下次操作起一次作用。
        System.out.println("\n3-1. 再次修改节点数据 ");
        zk.setData("/zoo2", "shanhy20160310-ABCD".getBytes(), -1);

        System.out.println("\n4. 查看是否修改成功： ");
        System.out.println(new String(zk.getData("/zoo2", false, null)));

        System.out.println("\n5. 删除节点 ");
        zk.delete("/zoo2", -1);

        System.out.println("\n6. 查看节点是否被删除： ");
        System.out.println(" 节点状态： [" + zk.exists("/zoo2", false) + "]");
    }

    private static void ZKClose() throws InterruptedException {
        zk.close();
    }


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        System.out.println(ip);
        createZk();
        System.out.println(createNode("/test", "test1"));
        System.out.println(createNode("/test", "test1"));
        System.out.println(createNode("/test", "test1"));
    }

}
