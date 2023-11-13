package learn.mastery.data;

import learn.mastery.models.Host;

public interface HostRepository {
    public Host getHostByEmail(String host);
}
