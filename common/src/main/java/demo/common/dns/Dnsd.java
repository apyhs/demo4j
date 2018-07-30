package demo.common.dns;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Dnsd {
    private DatagramSocket socket;

    public Dnsd() {
        //设置socket，监听端口53
        try {
            this.socket = new DatagramSocket(53);
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Starting。。。。。。\n");
        while (true) {
            try {
                byte[] buffer = new byte[1024];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);
                //输出客户端的dns请求数据
                InetAddress sourceIpAddr = request.getAddress();
                int sourcePort = request.getPort();
                System.out.println("\nsourceIpAddr = " + sourceIpAddr.toString() + "\nsourcePort = " + sourcePort);
                System.out.println("data = " + new String(request.getData(), 0 , request.getLength()));
            }
            catch (Exception e) {
                System.out.println(">>>> Exception:");
                e.printStackTrace();
            }
        }
    }

}
