package be.vdab.dance.festivals;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class BoekingTest {
    @Test
    void eenBoekingDieLukt() {
        new Boeking(0, "jente", 2, 1);
    }
    @Test
    void deNaamIsVerplicht() {
        assertThatIllegalArgumentException().isThrownBy(
                ()-> new Boeking(0, "",2,1));
    }
    @Test
    void nulTicketsBoekenMislukt() {
        assertThatIllegalArgumentException().isThrownBy(
                ()->new Boeking(0, "jente",0,1));
    }
    @Test
    void eenNegatiefAantalTicketsBoekenMislukt() {
        assertThatIllegalArgumentException().isThrownBy(
                ()->new Boeking(0, "jente",-1,1));
    }
    @Test
    void deFestivalIdMoetPositiefZijn() {
        assertThatIllegalArgumentException().isThrownBy(
                ()->new Boeking(0, "jente",2,0));
    }
}
