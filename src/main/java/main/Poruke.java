
package main;

import java.time.LocalDateTime;


public class Poruke {
    private int id;
    private int dogIdPor;
    private int igrIdPor;
    private String igrImePor;
    private String igrPrezPor;
    private String poruka;
    private LocalDateTime porVreme;
    private String dogLokacija;
    private String vremePor;

    public Poruke(int id, int dogIdPor, int igrIdPor, String igrImePor, String igrPrezPor, String poruka, LocalDateTime porVreme, String dogLokacija) {
        this.id = id;
        this.dogIdPor = dogIdPor;
        this.igrIdPor = igrIdPor;
        this.igrImePor = igrImePor;
        this.igrPrezPor = igrPrezPor;
        this.poruka = poruka;
        this.porVreme = porVreme;
        this.dogLokacija = dogLokacija;
    }

   public Poruke() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDogIdPor() {
        return dogIdPor;
    }

    public void setDogIdPor(int dogIdPor) {
        this.dogIdPor = dogIdPor;
    }

    public int getIgrIdPor() {
        return igrIdPor;
    }

    public void setIgrIdPor(int igrIdPor) {
        this.igrIdPor = igrIdPor;
    }

    public String getIgrImePor() {
        return igrImePor;
    }

    public void setIgrImePor(String igrImePor) {
        this.igrImePor = igrImePor;
    }

    public String getIgrPrezPor() {
        return igrPrezPor;
    }

    public void setIgrPrezPor(String igrPrezPor) {
        this.igrPrezPor = igrPrezPor;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public LocalDateTime getPorVreme() {
        return porVreme;
    }

    public void setPorVreme(LocalDateTime porVreme) {
        this.porVreme = porVreme;
    }

    public String getDogLokacija() {
        return dogLokacija;
    }

    public void setDogLokacija(String dogLokacija) {
        this.dogLokacija = dogLokacija;
    }

    public String getVremePor() {
        return vremePor;
    }

    public void setVremePor(String vremePor) {
        this.vremePor = vremePor;
    }

   
        
}
