package learn.mastery.domain;

import learn.mastery.data.HostRepository;
import learn.mastery.models.Host;

public class HostService {
    private HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host getHostById(String hostId) {
        return repository.getHostById(hostId);
    }
}
