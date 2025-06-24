package be.vdab.dance.festivals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoekingServiceTest {

    private BoekingService boekingService;
    @Mock
    private BoekingRepository boekingRepository;
    @Mock
    private FestivalRepository festivalRepository;

    @BeforeEach
    void beforeEach(){
        boekingService = new BoekingService(boekingRepository, festivalRepository);
    }

    @Test
    void boekingVanOnbestaandFestivalMislukt() {
        assertThatExceptionOfType(FestivalNietGevondenException.class).isThrownBy(() ->
                boekingService.create(new Boeking(0, "Jente", 2, 1)));
    }

    @Test
    void boekingMetTeveelTicketsMislukt() {
        var festival = new Festival(1, "testFestival", 2, BigDecimal.TEN);
        when(festivalRepository.findAndLockById(1)).thenReturn(Optional.of(festival));
        assertThatExceptionOfType(OnvoldoendeTicketsBeschikbaar.class).isThrownBy(() ->
                boekingService.create(new Boeking(0, "Jente", 3, 1)));
    }

    @Test
    void createVoegtEenBoekingToeEnWijzigtBeschikbareTickets(){
        var festival = new Festival(1, "testFestival", 100, BigDecimal.TEN);
        when(festivalRepository.findAndLockById(1)).thenReturn(Optional.of(festival));
        var boeking = new Boeking(0, "Jente", 2, 1);
        boekingService.create(boeking);
        assertThat(festival.getTicketsBeschikbaar()).isEqualTo(98);
    }

}
