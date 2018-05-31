package com.github.kahlkn.demo.dns;

import org.junit.Test;
import org.xbill.DNS.*;
import org.xbill.DNS.spi.DNSJavaNameServiceDescriptor;
import sun.net.spi.nameservice.NameService;

import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;

public class DnsjavaDemo {

    @Test
    public void test1() throws Exception {
//        Resolver resolver = new SimpleResolver("114.114.114.114");
//        Resolver resolver = new SimpleResolver("8.8.8.8");
        Resolver resolver = new SimpleResolver("8.8.4.4");
//        resolver.setPort(53);
        Lookup lookup = new Lookup("www.google.com", Type.A);
        lookup.setResolver(resolver);
        lookup.run();
        if (lookup.getResult() == Lookup.SUCCESSFUL) {
            Record[] answers = lookup.getAnswers();
            for (Record answer : answers) {
                System.out.println(answer.rdataToString());
            }
        }
    }

    @Test
    public void test2() throws Exception {
        ZoneTransferIn xfr = ZoneTransferIn.newAXFR(new Name("test.com."), "192.168.36.54", null);
        List records = xfr.run();
        Message response = new Message();
        response.getHeader().setFlag(Flags.AA);
        response.getHeader().setFlag(Flags.QR);
        // response.addRecord(query.getQuestion(),Section.QUESTION);
        Iterator it = records.iterator();
        while (it.hasNext()) {
            response.addRecord((Record) it.next(), Section.ANSWER);
        }
        System.out.println(response);
    }

    @Test
    public void test3() throws Exception {
        Name zone = Name.fromString("test.com.");
        Name host = Name.fromString("host", zone);
        Update update = new Update(zone, DClass.IN);
        Record record = new ARecord(host, DClass.IN, 3600, InetAddress.getByName("192.0.0.2"));
        update.add(record);
        Resolver resolver = new SimpleResolver("192.168.36.54");
        resolver.setPort(53);
        TSIG tsig = new TSIG("test_key", "epYaIl5VMJGRSG4WMeFW5g==");
        resolver.setTSIGKey(tsig);
        resolver.setTCP(true);
        Message response = resolver.send(update);
        System.out.println(response);
    }

    @Test
    public void test4() throws Exception {
        System.getProperties().setProperty("sun.net.spi.nameservice.provider.1", "dns,dnsjava");
        DNSJavaNameServiceDescriptor descriptor = new DNSJavaNameServiceDescriptor();
        NameService nameService = descriptor.createNameService();

    }

}
