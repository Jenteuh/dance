package be.vdab.dance.festivals;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoekingRepository {
    private final JdbcClient jdbcClient;
    public BoekingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    public void create(Boeking boeking) {
        var sql = """
                insert into boekingen(naam, aantalTickets, festivalId)
                values (?, ?, ?)
                """;
        jdbcClient.sql(sql)
                .params(boeking.getNaam(), boeking.getAantalTickets(),
                        boeking.getFestivalId())
                .update();
    }

    public List<BoekingMetFestival> findBoekingenMetFestivals() {
        var sql = """
                select boekingen.id, boekingen.naam as boekingNaam,
                festivals.naam as festivalNaam, aantalTickets
                from boekingen inner join festivals
                on boekingen.festivalId = festivals.id
                order by boekingen.id;
                """;
        return jdbcClient.sql(sql)
                .query(BoekingMetFestival.class)
                .list();
    }
}
