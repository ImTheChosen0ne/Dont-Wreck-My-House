package learn.mastery.domain;

import learn.mastery.data.HostRepository;
import learn.mastery.models.Host;
import org.springframework.stereotype.Service;

@Service
public class HostService {
    private HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host getHostByEmail(String hostEmail) {
        return repository.getHostByEmail(hostEmail);
    }
}
