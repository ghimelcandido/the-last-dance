package com.example.trabalhodesenvolvimentoplataformamoveis;

public class Arte {
    private String id;
    private String titulo;
    private String artista;
    private String descricao;
    private String imagemUrl;

    public Arte() {

    }

    public Arte(String id, String titulo, String artista, String descricao, String imagemUrl) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }
}