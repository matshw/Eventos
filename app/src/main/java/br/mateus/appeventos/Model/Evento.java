package br.mateus.appeventos.Model;

public class Evento {
    private int id; // ID único do evento
    private String nome;
    private String data;
    private String tipo;

    public Evento(String nome, String data, String tipo) {
        this.nome = nome;
        this.data = data;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}