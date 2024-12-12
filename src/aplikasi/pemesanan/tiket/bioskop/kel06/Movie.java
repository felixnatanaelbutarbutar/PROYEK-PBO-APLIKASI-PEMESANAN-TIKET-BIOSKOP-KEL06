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
    private int capacity; // Tambahkan atribut capacity

    // Constructor
    public Movie(int id, String title, String schedule, double price, int capacity) {
        this.id = id;
        this.title = title;
        this.schedule = schedule;
        this.price = price;
        this.capacity = capacity;
    }

    // Getter untuk capacity
    public int getCapacity() {
        return capacity;
    }

    // Setter untuk capacity (opsional, jika perlu)
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Getter & setter lain (id, title, schedule, price, dll.)
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

