package Utils;

import java.sql.Blob;
import java.util.Objects;

public class User {
    String Username;
    String Pw;
    String Nome;
    String Cognome;
    String Email;
    Blob Immagine;
    String Università;
    Boolean Admin;

    public User(String username, String pw, String nome, String cognome, String email, Blob immagine, String università, Boolean admin) {
        Username = username;
        Pw = pw;
        Nome = nome;
        Cognome = cognome;
        Email = email;
        Immagine = immagine;
        Università = università;
        Admin = admin;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPw() {
        return Pw;
    }

    public void setPw(String pw) {
        Pw = pw;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Blob getImmagine() {
        return Immagine;
    }

    public void setImmagine(Blob immagine) {
        Immagine = immagine;
    }

    public String getUniversità() {
        return Università;
    }

    public void setUniversità(String università) {
        Università = università;
    }

    public Boolean getAdmin() {
        return Admin;
    }

    public void setAdmin(Boolean admin) {
        Admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(Username, user.Username) && Objects.equals(Pw, user.Pw) && Objects.equals(Nome, user.Nome) && Objects.equals(Cognome, user.Cognome) && Objects.equals(Email, user.Email) && Objects.equals(Immagine, user.Immagine) && Objects.equals(Università, user.Università) && Objects.equals(Admin, user.Admin);
    }

}
