package be.vdab.dance.festivals;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class FestivalRepository {

    private final JdbcClient jdbcClient;

    public FestivalRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Festival> findAll() {
        var sql = """
                    select *
                    from festivals
                    order by naam
        """;
        return jdbcClient.sql(sql)
                        .query(Festival.class)
                        .list();
    }

    public List<Festival> findUitverkocht(){
        var sql = """
                    select *
                    from festivals
                    where ticketsBeschikbaar = 0
                    order by naam
        """;
        return jdbcClient.sql(sql)
                .query(Festival.class)
                .list();
    }

    public void delete(long id) {
        var sql = """
                    delete from festivals
                    where id = ?
        """;
        jdbcClient.sql(sql)
                .param(id)
                .update();
    }

    public long create(Festival festival) {
        var sql = """
                    insert into festivals(naam, reclameBudget, ticketsBeschikbaar)
                    values (?, ?, ?)
        """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .params(festival.getNaam(), festival.getReclameBudget(), festival.getTicketsBeschikbaar())
                .update(keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Optional<Festival> findAndLockById(long id) {
        var sql = """
                select id, naam, ticketsBeschikbaar, reclameBudget
                from festivals
                where id = ?
                for update
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Festival.class)
                .optional();
    }

    public long findAantal() {
        var sql = """
                select count(*)
                from festivals
                """;
        return jdbcClient.sql(sql)
                .query(Long.class)
                .single();
    }

    public void verhoogBudget(BigDecimal bedrag) {
        var sql = """
                update festivals
                set reclameBudget = reclameBudget + ?
                """;
        jdbcClient.sql(sql)
                .param(bedrag)
                .update();
    }

    public void update(Festival festival) {
        var sql = """
                update festivals
                set naam = ?, ticketsBeschikbaar = ?
                where id = ?
                """;
        if (jdbcClient.sql(sql)
                .params(festival.getNaam(), festival.getTicketsBeschikbaar(),
                        festival.getId())
                .update() == 0) {
            throw new FestivalNietGevondenException(festival.getId());
        }
    }
}
