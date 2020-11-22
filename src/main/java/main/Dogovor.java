
package main;

import java.time.LocalDateTime;


public class Dogovor {
    private int id;
    private String lokacija;
    private int igraci;
    private LocalDateTime vreme;
    private String grdIme;
    private int Spoid;
    private String spoIme;
    private int igrId;
    private String igrIme;
    private String igrEmail;
    private String igrTel;
    private int igrGod;
    private String igrPrez;
    private String tekstVreme;
    private String terLokacija;

    public Dogovor(int id, String lokacija, int igraci, LocalDateTime vreme, String grdIme, int Spoid, String spoIme, int igrId, String igrIme, String igrEmail, String igrTel, int igrGod, String igrPrez, String terLokacija) {
        this.id = id;
        this.lokacija = lokacija;
        this.igraci = igraci;
        this.vreme = vreme;
        this.grdIme = grdIme;
        this.Spoid = Spoid;
        this.spoIme = spoIme;
        this.igrId = igrId;
        this.igrIme = igrIme;
        this.igrEmail = igrEmail;
        this.igrTel = igrTel;
        this.igrGod = igrGod;
        this.igrPrez = igrPrez;
        this.terLokacija = terLokacija;
    }

   
    
    
  
  

   public Dogovor() {
       
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public int getIgraci() {
        return igraci;
    }

    public void setIgraci(int igraci) {
        this.igraci = igraci;
    }

    public LocalDateTime getVreme() {
        return vreme;
    }

    public void setVreme(LocalDateTime vreme) {
        this.vreme = vreme;
    }

    public String getGrdIme() {
        return grdIme;
    }

    public void setGrdIme(String grdIme) {
        this.grdIme = grdIme;
    }

    public int getSpoid() {
        return Spoid;
    }

    public void setSpoid(int Spoid) {
        this.Spoid = Spoid;
    }

    public String getSpoIme() {
        return spoIme;
    }

    public void setSpoIme(String spoIme) {
        this.spoIme = spoIme;
    }

    public int getIgrId() {
        return igrId;
    }

    public void setIgrId(int igrId) {
        this.igrId = igrId;
    }

    public String getIgrIme() {
        return igrIme;
    }

    public void setIgrIme(String igrIme) {
        this.igrIme = igrIme;
    }

    public String getIgrEmail() {
        return igrEmail;
    }

    public void setIgrEmail(String igrEmail) {
        this.igrEmail = igrEmail;
    }

    public String getIgrTel() {
        return igrTel;
    }

    public void setIgrTel(String igrTel) {
        this.igrTel = igrTel;
    }

    public int getIgrGod() {
        return igrGod;
    }

    public void setIgrGod(int igrGod) {
        this.igrGod = igrGod;
    }

    public String getIgrPrez() {
        return igrPrez;
    }

    public void setIgrPrez(String igrPrez) {
        this.igrPrez = igrPrez;
    }

    public String getTekstVreme() {
        return tekstVreme;
    }

    public void setTekstVreme(String tekstVreme) {
        this.tekstVreme = tekstVreme;
    }

    public String getTerLokacija() {
        return terLokacija;
    }

    public void setTerLokacija(String terLokacija) {
        this.terLokacija = terLokacija;
    }    
    
}
