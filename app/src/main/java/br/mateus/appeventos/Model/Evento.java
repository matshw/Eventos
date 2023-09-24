package br.mateus.appeventos.Model;

public class Evento {
    private int id; // ID único do evento
    private String name;
    private String date;
    private String type;

    public Evento(String name, String date, String type) {
        this.name = name;
        this.date = date;
        this.type = type;
    }

    // getters e setters
    // ...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
