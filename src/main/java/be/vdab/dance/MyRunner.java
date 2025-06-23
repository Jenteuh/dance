package be.vdab.dance;

import be.vdab.dance.festivals.FestivalNietGevondenException;
import be.vdab.dance.festivals.FestivalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {
    private final FestivalService festivalService;
    public MyRunner(FestivalService festivalService) {
        this.festivalService = festivalService;
    }
    @Override
    public void run(String... args) {
//        festivalService.findUitverkocht()
//                .forEach(festival -> System.out.println(festival.getNaam()));
        var scanner = new Scanner(System.in);
        System.out.print("Festival id:");
        long id = scanner.nextLong();
        try {
            festivalService.annuleer(id);
            System.out.println("Festival geannuleerd.");
        } catch (FestivalNietGevondenException ex) {
            System.err.println("Festival " + ex.getId() + " niet gevonden.");
        }
    }
}
