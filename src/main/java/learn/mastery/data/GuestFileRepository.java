package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Repository
public class GuestFileRepository implements GuestRepository{
    private String filePath;

    public GuestFileRepository(@Value("${guestFilePath}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Guest getGuestById(int guestId) {
        return findAll().stream()
                .filter(i -> i.getId() == guestId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest getGuestByEmail(String guestEmail) {
        return findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(guestEmail))
                .findFirst()
                .orElse(null);
    }

    public List<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setId(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhone(fields[4]);
        result.setState(fields[5]);
        return result;
    }
}
