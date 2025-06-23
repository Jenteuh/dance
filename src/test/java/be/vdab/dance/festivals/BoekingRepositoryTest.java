package be.vdab.dance.festivals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BoekingRepository.class)
@Sql("/festivals.sql")
public class BoekingRepositoryTest {

    private static final String BOEKINGEN_TABLE = "boekingen";
    private final BoekingRepository boekingRepository;
    private final JdbcClient jdbcClient;

    BoekingRepositoryTest(BoekingRepository boekingRepository, JdbcClient jdbcClient) {
        this.boekingRepository = boekingRepository;
        this.jdbcClient = jdbcClient;
    }

    private long idVanTestFestival1() {
        return jdbcClient.sql("select id from festivals where naam = 'testFestival1'")
                .query(Long.class)
                .single();
    }
    @Test
    void createVoegtEenBoekingToe() {
        var festivalId = idVanTestFestival1();
        boekingRepository.create(new Boeking(0, "test", 2, festivalId));
        var aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, BOEKINGEN_TABLE,
                "naam = 'test' and aantalTickets = 2 and festivalId = " + festivalId);
        assertThat(aantalRecords).isOne();
    }
}
