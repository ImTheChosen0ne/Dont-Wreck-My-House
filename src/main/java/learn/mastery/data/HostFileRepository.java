package learn.mastery.data;

import learn.mastery.models.Host;

public class HostFileRepository implements HostRepository{
    private String filePath;

    public HostFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Host getHostById(String host) {
        return null;
    }

    private Host deserialize(String value) {
        return null;
    }
}
