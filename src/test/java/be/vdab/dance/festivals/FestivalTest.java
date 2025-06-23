package be.vdab.dance.festivals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class FestivalTest {

    private Festival festival;

    @BeforeEach
    void beforeEach() {
        festival = new Festival(1, "Test", 100, BigDecimal.TEN);
    }
    @Test
    void boekWijzigtHetAantalBeschikbareTickets() {
        festival.boek(100);
        assertThat(festival.getTicketsBeschikbaar()).isZero();
    }
    @Test
    void nulTicketsBoekenMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> festival.boek(0));
    }
    @Test
    void eenNegatiefAantalTicketsBoekenMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> festival.boek(-2));
    }
    @Test
    void boekenMisluktBijOnvoldoendeBeschikbareTickets() {
        assertThatExceptionOfType(OnvoldoendeTicketsBeschikbaar.class).isThrownBy(
                () -> festival.boek(101));
    }
}
