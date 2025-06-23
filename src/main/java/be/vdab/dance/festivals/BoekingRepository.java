package be.vdab.dance.festivals;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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
}
