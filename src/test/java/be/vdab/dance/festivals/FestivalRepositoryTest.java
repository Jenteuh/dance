package be.vdab.dance.festivals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.math.BigDecimal;

@JdbcTest
@Import(FestivalRepository.class)
@Sql("/festivals.sql")
class FestivalRepositoryTest {
    private static final String FESTIVALS_TABLE = "festivals";
    private final FestivalRepository festivalRepository;
    private final JdbcClient jdbcClient;

    public FestivalRepositoryTest(FestivalRepository festivalRepository, JdbcClient jdbcClient) {
        this.festivalRepository = festivalRepository;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findAllGeeftAlleFestivalsGesorteerdOpNaam() {
        var aantalRecords = JdbcTestUtils.countRowsInTable(jdbcClient, FESTIVALS_TABLE);
        assertThat(festivalRepository.findAll())
                .hasSize(aantalRecords)
                .extracting(Festival::getNaam)
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test
    void findUitverkochtGeeftDeUitverkochteFestivalsGesorteerdOpNaam() {
        var aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, FESTIVALS_TABLE, "ticketsBeschikbaar = 0");
        assertThat(festivalRepository.findUitverkocht())
                .hasSize(aantalRecords)
                .extracting(Festival::getNaam)
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    private long idVanTestFestival1() {
        return jdbcClient.sql("select id from festivals where naam = 'testFestival1'")
                .query(Long.class)
                .single();
    }

    @Test
    void deleteVerwijdertEenFestival() {
        var id = idVanTestFestival1();
        festivalRepository.delete(id);
        var aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, FESTIVALS_TABLE, "id = " + id);
        assertThat(aantalRecords).isZero();
    }

    @Test
    void createVoegtEenFestivalToe() {
        var id = festivalRepository.create(
                new Festival(0, "testFestival3", 3, BigDecimal.TEN));
        assertThat(id).isPositive();
        var aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, FESTIVALS_TABLE, "id = " + id);
        assertThat(aantalRecords).isOne();
    }

    @Test
    void findAndLockByIdMetEenBestaandeIdVindtEenFestival() {
        assertThat(festivalRepository.findAndLockById(idVanTestFestival1()))
                .hasValueSatisfying(festival ->
                        assertThat(festival.getNaam()).isEqualTo("testFestival1"));
    }

    @Test
    void findAndLockByIdMetEenOnbestaandeIdVindtGeenFestival() {
        assertThat(festivalRepository.findAndLockById(Long.MAX_VALUE)).isEmpty();
    }
    @Test
    void findAantalVindtHetJuisteAantalFestivals() {
        var aantalRecords = JdbcTestUtils.countRowsInTable(jdbcClient, FESTIVALS_TABLE);
        assertThat(festivalRepository.findAantal()).isEqualTo(aantalRecords);
    }
    @Test
    void verhoogBudgetVerhoogtHetBudgetVanEenFestival() {
        festivalRepository.verhoogBudget(BigDecimal.TEN);
        var id = idVanTestFestival1();
        var aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, FESTIVALS_TABLE, "reclameBudget = 2510 and id = " + id);
        assertThat(aantalRecords).isOne();
    }
}

