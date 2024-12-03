/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasi.pemesanan.tiket.bioskop.kel06;

/**
 *
 * @author felix
 */

public class Movie {
    private int id;
    private String title;
    private String schedule;
    private double price;

    public Movie(int id, String title, String schedule, double price) {
        this.id = id;
        this.title = title;
        this.schedule = schedule;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSchedule() {
        return schedule;
    }

    public double getPrice() {
        return price;
    }
}
