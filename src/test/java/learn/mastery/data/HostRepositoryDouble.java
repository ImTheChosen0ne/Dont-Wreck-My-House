package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class HostRepositoryDouble implements HostRepository{

    final Host HOST = new Host("9d469342-ad0b-4f5a-8d28-e81e690ba29a","Wigfield","kwigfieldiy@php.net","(305) 8769397","88875 Miller Parkway","Miami","FL",33185,new BigDecimal(435),new BigDecimal(543.75));
    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble() {
        hosts.add(HOST);
    }
    @Override
    public Host getHostByEmail(String hostEmail) {
        return hosts.stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(hostEmail))
                .findFirst()
                .orElse(null);
    }
}
