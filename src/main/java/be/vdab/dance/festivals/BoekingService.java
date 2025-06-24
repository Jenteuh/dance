package be.vdab.dance.festivals;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BoekingService {

    private final BoekingRepository boekingRepository;
    private final FestivalRepository festivalRepository;

    public BoekingService(BoekingRepository boekingRepository, FestivalRepository festivalRepository) {
        this.boekingRepository = boekingRepository;
        this.festivalRepository = festivalRepository;
    }

    @Transactional
    public void create(Boeking boeking) {
        var festival = festivalRepository.findAndLockById(boeking.getFestivalId())
                .orElseThrow(() ->
                        new FestivalNietGevondenException(boeking.getFestivalId()));
        festival.boek(boeking.getAantalTickets());
        boekingRepository.create(boeking);
        festivalRepository.update(festival);
    }

    public List<BoekingMetFestival> findBoekingenMetFestivals() {
        return boekingRepository.findBoekingenMetFestivals();
    }

    @Transactional
    public void annuleer(long id) {
        var boeking = boekingRepository.findAndLockById(id)
                .orElseThrow(() -> new BoekingNietGevondenException(id));
        var festival = festivalRepository.findAndLockById(boeking.getFestivalId())
                .orElseThrow(() -> new FestivalNietGevondenException(boeking.getFestivalId()));
        festival.annuleer(boeking.getAantalTickets());
        boekingRepository.delete(boeking.getId());
        festivalRepository.update(festival);

    }
}
