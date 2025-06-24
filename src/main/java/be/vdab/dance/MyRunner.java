package be.vdab.dance;

import be.vdab.dance.festivals.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {

    private final FestivalService festivalService;
//    private final BoekingService boekingService;

    public MyRunner(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

//    public MyRunner(BoekingService boekingService) {
//        this.boekingService = boekingService;
//    }

    @Override
    public void run(String... args) {
//        festivalService.findUitverkocht()
//                .forEach(festival -> System.out.println(festival.getNaam()));

        //Annuleer Festival
//        var scanner = new Scanner(System.in);
//        System.out.print("Festival id:");
//        long id = scanner.nextLong();
//        try {
//            festivalService.annuleer(id);
//            System.out.println("Festival geannuleerd.");
//        } catch (FestivalNietGevondenException ex) {
//            System.err.println("Festival " + ex.getId() + " niet gevonden.");
//        }

        //Boeking
//        var scanner = new Scanner(System.in);
//        System.out.print("Naam:");
//        var naam = scanner.nextLine();
//        System.out.print("Aantal tickets:");
//        var aantalTickets = scanner.nextInt();
//        System.out.print("Festival id:");
//        var festivalId = scanner.nextInt();
//        try {
//            var boeking = new Boeking(0, naam, aantalTickets, festivalId);
//            boekingService.create(boeking);
//            System.out.println("Boeking ok");
//        } catch (IllegalArgumentException ex) {
//            System.err.println(ex.getMessage());
//        } catch (FestivalNietGevondenException ex) {
//            System.err.println("Festival " + ex.getId() + " niet gevonden.");
//        } catch (OnvoldoendeTicketsBeschikbaar ex) {
//            System.err.println("Onvoldoende tickets beschikbaar");
//        }

        //Boekingen met Festivals
//        boekingService.findBoekingenMetFestivals().forEach(System.out::println);

        //Boeking annuleren
//        var scanner = new Scanner(System.in);
//        System.out.print("Boeking id:");
//        var id = scanner.nextLong();
//        try {
//            boekingService.annuleer(id);
//            System.out.println("Boeking geannuleerd");
//        } catch (BoekingNietGevondenException ex) {
//            System.out.println("Boeking niet gevonden");
//        }

        //Aantal boekingen per festival
        festivalService.findAantalBoekingenPerFestival().forEach(System.out::println);
    }
}
