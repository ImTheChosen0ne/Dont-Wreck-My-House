package learn.mastery.data;

import learn.mastery.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {
    static final String SEED_FILE_PATH = "./data/hosts-seed.csv";
    static final String TEST_FILE_PATH = "./data/hosts-test.csv";

    HostFileRepository repository = new HostFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAll() {
        List<Host> actual = repository.findAll();
        assertEquals(1, actual.size());
    }
    @Test
    void findHostByEmail() {
        Host host = repository.getHostByEmail("eyearnes0@sfgate.com");
        assertNotNull(host);
        assertEquals("Yearnes", host.getLastName());

        host = repository.getHostByEmail("fake@fake.com");
        assertNull(host);
    }
}