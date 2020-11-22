
package main;

public class Ocena {
    private int id;
    private int igracId;
    private int igrKoga;
    private String igrIme;
    private String igrKome;
    private String komentar;
    private int ocena;

    public Ocena(int id, int igracId, int igrKoga, String igrIme, String igrKome, String komentar, int ocena) {
        this.id = id;
        this.igracId = igracId;
        this.igrKoga = igrKoga;
        this.igrIme = igrIme;
        this.igrKome = igrKome;
        this.komentar = komentar;
        this.ocena = ocena;
    }

   public Ocena() {
         
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIgracId() {
        return igracId;
    }

    public void setIgracId(int igracId) {
        this.igracId = igracId;
    }

    public int getIgrKoga() {
        return igrKoga;
    }

    public void setIgrKoga(int igrKoga) {
        this.igrKoga = igrKoga;
    }

    public String getIgrIme() {
        return igrIme;
    }

    public void setIgrIme(String igrIme) {
        this.igrIme = igrIme;
    }

    public String getIgrKome() {
        return igrKome;
    }

    public void setIgrKome(String igrKome) {
        this.igrKome = igrKome;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

   
}
