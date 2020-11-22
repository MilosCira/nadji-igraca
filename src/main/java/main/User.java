
package main;

import java.sql.Blob;


public class User {
     int id;
     String ime;
     String prezime;
     int godine;
     String telefon;
     char pol;
     String email;
     String password;
     int admin;
     Blob slika;
     Double prosek;

    public User(int id, String ime, String prezime, int godine, String telefon, String email, Blob slika) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.godine = godine;
        this.telefon = telefon;
        this.email = email;
        this.slika = slika;
    }

     
    public User(int id, String ime, String prezime, int godine, String telefon, char pol, String email, String password, int admin, Blob slika) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.godine = godine;
        this.telefon = telefon;
        this.pol = pol;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.slika = slika;
    }

     public User() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public int getGodine() {
        return godine;
    }

    public void setGodine(int godine) {
        this.godine = godine;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public char getPol() {
        return pol;
    }

    public void setPol(char pol) {
        this.pol = pol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public Blob getSlika() {
        return slika;
    }

    public void setSlika(Blob slika) {
        this.slika = slika;
    }

    public Double getProsek() {
        return prosek;
    }

    public void setProsek(Double prosek) {
        this.prosek = prosek;
    }
   
   

  
    
}


