/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasi.pemesanan.tiket.bioskop.kel06;

/**
 *
 * @author felix
 */
public class Ticket {
    private int id;
    private Movie movie;
    private int ticketCount;
    private double totalPrice;

    public Ticket(Movie movie, int ticketCount) {
        this.movie = movie;
        this.ticketCount = ticketCount;
        this.totalPrice = movie.getPrice() * ticketCount;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}