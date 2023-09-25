package br.mateus.appeventos.Model;

public class Evento {
    private Integer id; // ID único do evento
    private String nome;
    private String data;
    private String tipo;
    private String relogio;
    public String getRelogio() {
        return relogio;
    }

    public void setRelogio(String relogio) {
        this.relogio = relogio;
    }



    public Evento(String nome, String data, String tipo, String relogio) {
        this.nome = nome;
        this.data = data;
        this.tipo = tipo;
        this.relogio = relogio;
    }

    public Evento() {

    }

    public Integer getId() {
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
    public String toString(){
        return tipo + " " + nome + " " + data + " " + relogio;
    }
}