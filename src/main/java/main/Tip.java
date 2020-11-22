
package main;


public class Tip {
    private int id;
    private String podloga;
    private String namena;

    public Tip(int id, String podloga, String namena) {
        this.id = id;
        this.podloga = podloga;
        this.namena = namena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPodloga() {
        return podloga;
    }

    public void setPodloga(String podloga) {
        this.podloga = podloga;
    }

    public String getNamena() {
        return namena;
    }

    public void setNamena(String namena) {
        this.namena = namena;
    }
    
}
