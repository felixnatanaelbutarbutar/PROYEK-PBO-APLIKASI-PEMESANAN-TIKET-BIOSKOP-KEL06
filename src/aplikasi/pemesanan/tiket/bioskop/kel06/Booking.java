/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasi.pemesanan.tiket.bioskop.kel06;

/**
 *
 * @author felix
 */
public class Booking {
    private int id;
    private Movie movie;
    private int ticketCount;
    private double totalPrice;

    public Booking(int id, Movie movie, int ticketCount, double totalPrice) {
        this.id = id;
        this.movie = movie;
        this.ticketCount = ticketCount;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
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