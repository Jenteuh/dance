package be.vdab.dance.festivals;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public FestivalService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    public List<Festival> findAll() {
        return festivalRepository.findAll();
    }

    public List<Festival> findUitverkocht() {
        return festivalRepository.findUitverkocht();
    }

    @Transactional
    public void delete(long id) {
        festivalRepository.delete(id);
    }

    @Transactional
    public long create(Festival festival) {
        return festivalRepository.create(festival);
    }

    @Transactional
    public void annuleer(long id) {
        var festival = festivalRepository.findAndLockById(id)
                .orElseThrow(() -> new FestivalNietGevondenException(id));
        var teVerdelenReclameBudget = festival.getReclameBudget();
        festivalRepository.delete(id);
        var aantalResterendeFestivals = festivalRepository.findAantal();
        var extraReclameBudgetPerFestival =
                teVerdelenReclameBudget.divide(BigDecimal.valueOf(aantalResterendeFestivals),
                        2, RoundingMode.HALF_UP);
        festivalRepository.verhoogBudget(extraReclameBudgetPerFestival);
    }

    public List<AantalBoekingenPerFestival> findAantalBoekingenPerFestival() {
        return festivalRepository.findAantalBoekingenPerFestival();
    }
}
