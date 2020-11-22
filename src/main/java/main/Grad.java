
package main;


public class Grad {
    private int id;
    private String ime;
    private int drzID;
    private String drzIme;

    public Grad(int id, String ime, int drzID, String drzIme) {
        this.id = id;
        this.ime = ime;
        this.drzID = drzID;
        this.drzIme = drzIme;
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

    public int getDrzID() {
        return drzID;
    }

    public void setDrzID(int drzID) {
        this.drzID = drzID;
    }

    public String getDrzIme() {
        return drzIme;
    }

    public void setDrzIme(String drzIme) {
        this.drzIme = drzIme;
    }

    
    
    
}
