package Utils;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class Book {
    String isbn;
    String titolo;
    String autore;
    String università;
    BufferedImage immagine;
    int prezzo;
    String descrizione;
    int quantità;
    String path;

    public Book(String isbn, String titolo, String autore, String università, BufferedImage immagine, int prezzo,
                String descrizione, int quantità, String path) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.autore = autore;
        this.università = università;
        this.immagine = immagine;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.quantità = quantità;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getUniversità() {
        return università;
    }

    public void setUniversità(String università) {
        this.università = università;
    }

    public BufferedImage getImmagine() {
        return immagine;
    }

    public void setImmagine(BufferedImage immagine) {
        this.immagine = immagine;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getQuantità() {
        return quantità;
    }

    public void setQuantità(int quantità) {
        this.quantità = quantità;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Book{" + "isbn='" + isbn + '\'' + ", titolo='" + titolo + '\'' + ", autore='" + autore + '\'' + ", università='" + università + '\'' + ", immagine=" + immagine + ", prezzo=" + prezzo + ", descrizione='" + descrizione + '\'' + ", quantità=" + quantità + '}';
    }
}
