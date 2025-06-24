package be.vdab.dance.festivals;

import java.math.BigDecimal;

public class Festival {

    private final long id;
    private final String naam;
    private long ticketsBeschikbaar;
    private final BigDecimal reclameBudget;

    public Festival(long id, String naam, long ticketsBeschikbaar, BigDecimal reclameBudget) {
        this.id = id;
        this.naam = naam;
        this.ticketsBeschikbaar = ticketsBeschikbaar;
        this.reclameBudget = reclameBudget;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public long getTicketsBeschikbaar() {
        return ticketsBeschikbaar;
    }

    public BigDecimal getReclameBudget() {
        return reclameBudget;
    }

    public void boek(int tickets) {
        if (tickets <= 0) {
            throw new IllegalArgumentException();
        }
        if (tickets > ticketsBeschikbaar) {
            throw new OnvoldoendeTicketsBeschikbaar();
        }
        ticketsBeschikbaar -= tickets;
    }

    public void annuleer(int tickets) {
        ticketsBeschikbaar += tickets;
    }
}
