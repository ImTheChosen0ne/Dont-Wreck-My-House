package learn.mastery.data;

import learn.mastery.models.Host;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostFileRepository implements HostRepository{
    private String filePath;

    public HostFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Host getHostById(String hostId) {
        return findAll().stream()
                .filter(i -> i.getId().equalsIgnoreCase(hostId))
                .findFirst()
                .orElse(null);
    }

    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setId(fields[0]);
        result.setLastName(fields[1]);
        result.setEmail(fields[2]);
        result.setPhone(fields[3]);
        result.setAddress(fields[4]);
        result.setCity(fields[5]);
        result.setState(fields[6]);
        result.setZip(Integer.parseInt(fields[7]));
        result.setStandardRate(new BigDecimal(fields[8]));
        result.setWeekendRate(new BigDecimal(fields[9]));
        return result;
    }
}
