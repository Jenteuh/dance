package be.vdab.dance.festivals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@JdbcTest
@Import({BoekingService.class,BoekingRepository.class,FestivalRepository.class})
@Sql("/festivals.sql")
public class BoekingServiceIntegrationTest {

    private static final String FESTIVALS_TABLE = "festivals";
    private static final String BOEKINGEN_TABLE = "boekingen";
    private final BoekingService boekingService;
    private final JdbcClient jdbcClient;

    public BoekingServiceIntegrationTest(BoekingService boekingService, JdbcClient jdbcClient) {
        this.boekingService = boekingService;
        this.jdbcClient = jdbcClient;
    }

    private long idVanTestFestival() {
        return jdbcClient.sql("select id from festivals where naam = 'testFestival1'")
                .query(Long.class)
                .single();
    }

    @Test
    void createVoegtEenBoekingToeEnWijzigtBeschikbareTickets() {
        var vanFestivalId = idVanTestFestival();
        boekingService.create(new Boeking(0, "Jente", 2, vanFestivalId));
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, BOEKINGEN_TABLE,
                "aantalTickets = 2 and festivalId = " + vanFestivalId)).isOne();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, FESTIVALS_TABLE,
                "ticketsBeschikbaar = 998 and Id = " + vanFestivalId)).isOne();
    }

    @Test
    void boekingVanOnbestaandFestivalMislukt() {
        assertThatExceptionOfType(FestivalNietGevondenException.class).isThrownBy(() ->
                boekingService.create(new Boeking(0, "Jente", 2, Long.MAX_VALUE)));
    }

    @Test
    void boekingMetTeveelTicketsMislukt() {
        var vanFestivalId = idVanTestFestival();
        assertThatExceptionOfType(OnvoldoendeTicketsBeschikbaar.class).isThrownBy(() ->
                boekingService.create(new Boeking(0, "Jente", 3000, vanFestivalId)));
    }
}
