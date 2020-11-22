/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Blob;

/**
 *
 * @author Milos
 */
public class TerenZaIgranje {
    private int id;    
    private String lokacija;
    private Blob slika;
    private int tipId;
    private int grdId;
    private String tipIme;
    private String gradIme;
    private String tipPodloga;
    private String spoIme;

    public TerenZaIgranje(int id, String lokacija, Blob slika, int tipId, int grdId, String tipIme, String gradIme, String tipPodloga, String spoIme) {
        this.id = id;
        this.lokacija = lokacija;
        this.slika = slika;
        this.tipId = tipId;
        this.grdId = grdId;
        this.tipIme = tipIme;
        this.gradIme = gradIme;
        this.tipPodloga = tipPodloga;
        this.spoIme = spoIme;
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

    public Blob getSlika() {
        return slika;
    }

    public void setSlika(Blob slika) {
        this.slika = slika;
    }

    public int getTipId() {
        return tipId;
    }

    public void setTipId(int tipId) {
        this.tipId = tipId;
    }

    public int getGrdId() {
        return grdId;
    }

    public void setGrdId(int grdId) {
        this.grdId = grdId;
    }

    public String getTipIme() {
        return tipIme;
    }

    public void setTipIme(String tipIme) {
        this.tipIme = tipIme;
    }

    public String getGradIme() {
        return gradIme;
    }

    public void setGradIme(String gradIme) {
        this.gradIme = gradIme;
    }

    public String getTipPodloga() {
        return tipPodloga;
    }

    public void setTipPodloga(String tipPodloga) {
        this.tipPodloga = tipPodloga;
    }

    public String getSpoIme() {
        return spoIme;
    }

    public void setSpoIme(String spoIme) {
        this.spoIme = spoIme;
    }
    
    

   
    
    
}
