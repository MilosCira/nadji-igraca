package main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    private DriverManagerDataSource dataSource;

    @Autowired
    HttpSession session;

    /**
     * ***********
     * GRAD
     */
    private ArrayList<Grad> prikaziGradBaza() {
        ArrayList<Grad> grad = new ArrayList<>();
        try {
            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("SELECT * FROM grad inner join drzava using(drz_id)");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("grd_id");
                String ime = rs.getString("grd_ime");
                int drzID = rs.getInt("drz_id");
                String drzIme = rs.getString("drz_ime");

                grad.add(new Grad(id, ime, drzID, drzIme));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return grad;
    }

    @RequestMapping(value = "/prikazigrad")
    public String prikaziGrad(ModelMap m, HttpServletRequest request) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<Grad> grad = prikaziGradBaza();
        m.addAttribute("grad", grad);
        return "grad";
    }

    @RequestMapping(value = "/dodajgrad", method = RequestMethod.POST)
    public String DodajGradBaza(@RequestParam String ime, @RequestParam("drzava") int drzID, ModelMap m) {
        try {
            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("INSERT INTO grad(grd_ime,drz_id) VALUE(?,?)");

            s.setString(1, ime);
            s.setInt(2, drzID);
            s.execute();
        } catch (Exception ex) {
            m.addAttribute("greska", "greska u dodavanju grada");
            return "dodajgrad";
        }
        return "redirect:/admin/prikazigrad";
    }

    @RequestMapping(value = "/prikazidodajgrad")
    public String DodajGrad(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<Drzava> drzave = prikaziDrzave();
        m.addAttribute("drzave", drzave);
        return "dodajgrad";
    }

    @RequestMapping(value = "/obrisigrad{id}")
    public String ObrisiGrad(ModelMap model, @RequestParam Integer id) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("delete from  grad where grd_id=?");
            s.setInt(1, id);
            s.execute();
            return "redirect:/admin/prikazigrad";
        } catch (Exception e) {
            e.printStackTrace();
            return "/admin/prikazigrad";
        }

    }

    @RequestMapping(value = "/izmenigrad{id}")
    public String IzmenaGrad(@RequestParam("id") String id, ModelMap m) {

        try {

            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().prepareStatement("select * from grad where grd_id=?");
            s.setInt(1, Integer.parseInt(id));
            ResultSet rs = s.executeQuery();
            Grad grad = new Grad(0, id, 0, id);
            rs.next();
            grad.setId(rs.getInt("grd_id"));
            grad.setIme(rs.getString("grd_ime"));
            m.addAttribute("grad", grad);
            m.addAttribute("izmena", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "dodajgrad";
    }

    @RequestMapping(value = "/updategrad{id}")
    public String updateGrad(
            @RequestParam Integer id,
            @RequestParam String ime,
            ModelMap model) {
        try {

            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("update grad set grd_ime=? where grd_id=?");

            s.setString(1, ime);
            s.setInt(2, id);
            s.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "dodajgrad";
        }
        return "redirect:/admin/prikazigrad";

    }

    /**
     * ***********
     * TIP
     */
    private ArrayList<Tip> prikaziTipBaza() {
        ArrayList<Tip> tip = new ArrayList<>();
        try {
            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("SELECT * FROM tip");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("tip_id");
                String podloga = rs.getString("tip_podloga");
                String namena = rs.getString("tip_namena");
                tip.add(new Tip(id, podloga, namena));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tip;
    }

    @RequestMapping(value = "/prikazitip")
    public String prikaziTip(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<Tip> tip = prikaziTipBaza();
        m.addAttribute("tip", tip);
        return "tip";
    }

    @RequestMapping(value = "/prikaziDodajTip")
    public String DodajTip() {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        return "dodajtip";
    }

    @RequestMapping(value = "/dodajtip")
    public String DodajTipBaza(@RequestParam String podloga, @RequestParam String namena, ModelMap m) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("INSERT INTO tip (tip_podloga,tip_namena) VALUE(?,?)");
            s.setString(1, podloga);
            s.setString(2, namena);
            s.execute();
        } catch (Exception ex) {
            m.addAttribute("greska", ex.getMessage());
            return "dodajtip";
        }
        return "redirect:/admin/prikazitip";
    }

    @RequestMapping(value = "/izmenitip{id}")
    public String IzmenaTip(@RequestParam("id") String id, ModelMap m) {

        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().prepareStatement("select * from tip where tip_id=?");
            s.setInt(1, Integer.parseInt(id));
            ResultSet rs = s.executeQuery();
            Tip tip = new Tip(0, id, id);
            rs.next();
            tip.setId(rs.getInt("tip_id"));
            tip.setPodloga(rs.getString("tip_podloga"));
            tip.setNamena(rs.getString("tip_namena"));
            m.addAttribute("tip", tip);
            m.addAttribute("izmena", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "dodajtip";
    }

    @RequestMapping(value = "/updatetip{id}")
    public String updateTip(
            @RequestParam Integer id,
            @RequestParam String namena,
            @RequestParam String podloga,
            ModelMap model) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }
            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("update tip set tip_namena=?, tip_podloga=? where tip_id=?");

            s.setString(1, namena);
            s.setString(2, podloga);
            s.setInt(3, id);
            s.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "dodajtip";
        }
        return "redirect:/admin/prikazitip";

    }

    @RequestMapping(value = "/obrisitip{id}")
    public String ObrisiTip(ModelMap model, @RequestParam Integer id) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("delete from  tip where tip_id=?");
            s.setInt(1, id);
            s.execute();
            return "redirect:/admin/prikazitip";
        } catch (Exception e) {
            e.printStackTrace();
            return "/admin/prikazitip";
        }

    }

    /**
     * ***********
     * IGRAČ
     */
    private ArrayList<User> podaciIzBazePodataka() {
        ArrayList<User> igrac = new ArrayList<>();
        try {
            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("SELECT * FROM igrac");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("igr_id");
                String ime = rs.getString("igr_ime");
                String prezime = rs.getString("igr_prezime");
                String telefon = rs.getString("igr_telefon");
                int godine = rs.getInt("igr_godine");
                String email = rs.getString("igr_email");
                String password = rs.getString("igr_password");
                Blob slika = rs.getBlob("igr_slika");
                igrac.add(new User(id, ime, prezime, godine, telefon, '0', email, password, godine, slika));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return igrac;
    }

    @RequestMapping(value = "/prikaziDodajIgraca")
    public String DodajIgraca() {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }
        return "dodajigraca";
    }

    @RequestMapping(value = "/prikaziizmeniigraca{id}")
    public String IzmenaIgraca(@RequestParam("id") String id, ModelMap m) {

        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().prepareStatement("select * from igrac where igr_id=?");
            s.setInt(1, Integer.parseInt(id));
            ResultSet rs = s.executeQuery();
            User igrac = new User();
            rs.next();
            igrac.setId(rs.getInt("igr_id"));
            igrac.setIme(rs.getString("igr_ime"));
            igrac.setPrezime(rs.getString("igr_prezime"));
            igrac.setTelefon(rs.getString("igr_telefon"));
            igrac.setGodine(rs.getInt("igr_godine"));
            igrac.setEmail(rs.getString("igr_email"));
            igrac.setPassword(rs.getString("igr_password"));
            igrac.setSlika(rs.getBlob("igr_slika"));
            m.addAttribute("igrac", igrac);
            m.addAttribute("edit", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "dodajigraca";
    }

    @RequestMapping(value = "/obrisiigraca{id}")
    public String ObrisiIgraca(ModelMap model, @RequestParam Integer id) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("delete from igrac where igr_id=?");
            s.setInt(1, id);
            s.executeUpdate();
            return "redirect:/admin/prikaziigraca";
        } catch (Exception e) {
            e.printStackTrace();
            return "/admin/prikaziigraca";
        }

    }

    @RequestMapping(value = "/prikaziigraca")
    public String prikaziIgraca(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<User> igrac = podaciIzBazePodataka();
        m.addAttribute("igrac", igrac);
        return "igrac";
    }

    @RequestMapping(value = "/updateigraca{id}")
    public String updateIgraca(
            @RequestParam Integer id,
            @RequestParam String ime,
            @RequestParam String prezime,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String telefon,
            @RequestParam int godine,
            ModelMap model) {

        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("update igrac set igr_ime=?,igr_prezime=?, igr_email=?, igr_password=?, igr_telefon=?, igr_godine=? where igr_id=?");

            s.setString(1, ime);
            s.setString(2, prezime);
            s.setString(3, email);
            s.setString(4, password);
            s.setString(5, telefon);
            s.setInt(6, godine);
            s.setInt(7, id);
            s.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "dodajigraca";
        }
        return "redirect:/admin/prikaziigraca";

    }

    @RequestMapping("/dodajigraca")
    public String DodajIgraca(
            @RequestParam String ime,
            @RequestParam String prezime,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String telefon,
            @RequestParam int godine,
            ModelMap model) {

        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("INSERT INTO igrac (igr_ime,igr_prezime,igr_email,igr_password,igr_telefon,igr_godine) VALUE(?,?,?,?,?,?)");
            s.setString(1, ime);
            s.setString(2, prezime);
            s.setString(3, email);
            s.setString(4, password);
            s.setString(5, telefon);
            s.setInt(6, godine);
            s.execute();
        } catch (Exception e) {
            model.addAttribute("poruka", e.getMessage());
            return "dodajigraca";
        }
        return "redirect:/admin/prikaziigraca";
    }

    /**
     * ***********
     * SPORT
     */
    private ArrayList<Sport> prikaziSportBaza() {
        ArrayList<Sport> sport = new ArrayList<>();
        try {
            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("SELECT * FROM sport");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("spo_id");
                String ime = rs.getString("spo_ime");
                sport.add(new Sport(id, ime));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sport;
    }

    @RequestMapping(value = "/prikazisport")
    public String prikaziSport(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<Sport> sport = prikaziSportBaza();
        m.addAttribute("sport", sport);
        return "sport";
    }

    @RequestMapping(value = "/dodajsport")
    public String DodajSport(@RequestParam String ime, ModelMap m) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("INSERT INTO sport (spo_ime) VALUE(?)");
            s.setString(1, ime);
            s.execute();
        } catch (Exception ex) {
            m.addAttribute("greska", ex.getMessage());
            return "dodajtip";
        }
        return "redirect:/admin/prikazisport";
    }

    @RequestMapping(value = "/prikazidodajsport")
    public String DodajSport() {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        return "dodajsport";
    }

    @RequestMapping(value = "/izmenisport{id}")
    public String IzmenaSporta(@RequestParam("id") String id, ModelMap m) {

        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().prepareStatement("select * from sport where spo_id=?");
            s.setInt(1, Integer.parseInt(id));
            ResultSet rs = s.executeQuery();
            Sport sport = new Sport(0, id);
            rs.next();
            sport.setId(rs.getInt("spo_id"));
            sport.setIme(rs.getString("spo_ime"));
            m.addAttribute("sport", sport);
            m.addAttribute("izmena", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "dodajsport";
    }

    @RequestMapping(value = "/updatesport{id}")
    public String updateSport(
            @RequestParam Integer id,
            @RequestParam String ime,
            ModelMap model) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("update sport set spo_ime=? where spo_id=?");

            s.setString(1, ime);
            s.setInt(2, id);
            s.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "dodajsport";
        }
        return "redirect:/admin/prikazisport";

    }

    @RequestMapping(value = "/obrisisport{id}")
    public String ObrisiSport(ModelMap model, @RequestParam Integer id) {
        try {

            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("delete from  sport where spo_id=?");
            s.setInt(1, id);
            s.execute();
            return "redirect:/admin/prikazisport";
        } catch (Exception e) {
            e.printStackTrace();
            return "/admin/prikazisport";
        }

    }

    /**
     * ***********
     * DRŽAVA
     */
    private ArrayList<Drzava> prikaziDrzave() {
        ArrayList<Drzava> drzava = new ArrayList<>();
        try {
            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("SELECT * FROM drzava");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("drz_id");
                String ime = rs.getString("drz_ime");
                drzava.add(new Drzava(id, ime));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drzava;
    }

    @RequestMapping(value = "/prikazidrzavu")
    public String prikaziDrzavu(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<Drzava> drzava = prikaziDrzave();
        m.addAttribute("drzava", drzava);
        return "drzava";

    }

    @RequestMapping(value = "/dodajdrzavu")
    public String DodajDrzavu(@RequestParam String ime, ModelMap m) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("INSERT INTO drzava (drz_ime) VALUE(?)");
            s.setString(1, ime);
            s.execute();
        } catch (Exception ex) {
            m.addAttribute("greska", ex.getMessage());
            return "dodajdrzavu";
        }
        return "redirect:/admin/prikazidrzavu";
    }

    @RequestMapping(value = "/prikazidodajdrzavu")
    public String DodajDrzavu() {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }
        return "dodajdrzavu";
    }

    @RequestMapping(value = "/izmenidrzave{id}")
    public String IzmenaDrzave(@RequestParam("id") String id, ModelMap m) {

        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().prepareStatement("select * from drzava where drz_id=?");
            s.setInt(1, Integer.parseInt(id));
            ResultSet rs = s.executeQuery();
            Drzava drzava = new Drzava(0, id);
            rs.next();
            drzava.setId(rs.getInt("drz_id"));
            drzava.setIme(rs.getString("drz_ime"));
            m.addAttribute("drzava", drzava);
            m.addAttribute("izmena", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "dodajdrzavu";
    }

    @RequestMapping(value = "/updatedrzavu{id}")
    public String updateDrzavu(
            @RequestParam Integer id,
            @RequestParam String ime,
            ModelMap model) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("update drzava set drz_ime=? where drz_id=?");

            s.setString(1, ime);
            s.setInt(2, id);
            s.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "dodajdrzavu";
        }
        return "redirect:/admin/prikazidrzavu";

    }

    @RequestMapping(value = "/obrisidrzavu{id}")
    public String ObrisiDrzavu(ModelMap model, @RequestParam Integer id) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("delete from drzava where drz_id=?");
            s.setInt(1, id);
            s.execute();
            return "redirect:/admin/prikazidrzavu";
        } catch (Exception e) {
            e.printStackTrace();
            return "/admin/prikazidrzavu";
        }

    }

    /**
     * ***********
     * teren
     */
    private ArrayList<TerenZaIgranje> prikaziTeren() {
        ArrayList<TerenZaIgranje> terenList = new ArrayList<>();
        try {
            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("SELECT spo_ime, tip_namena,tip_podloga,grd_ime, ter_lokacija,ter_id,ter_slika,tip_id,grd_id FROM spo_ter\n"
                            + "inner join sport using(spo_id)\n"
                            + "inner join teren using(ter_id)\n"
                            + "inner join tip using (tip_id)\n"
                            + "inner join grad using(grd_id)");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ter_id");
                String lokacija = rs.getString("ter_lokacija");
                Blob slika = rs.getBlob("ter_slika");
                int tipId = rs.getInt("tip_id");
                int grdId = rs.getInt("grd_id");
                String tipIme = rs.getString("tip_namena");
                String tipPodloga = rs.getString("tip_podloga");
                String gradIme = rs.getString("grd_ime");
                String spoIme = rs.getString("spo_ime");
                TerenZaIgranje t = new TerenZaIgranje(id, lokacija, slika, tipId, grdId, tipIme, gradIme, tipPodloga, spoIme);
                terenList.add(t);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return terenList;
    }

    @RequestMapping(value = "/prikaziteren")
    public String prikaziTeren(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<TerenZaIgranje> teren = prikaziTeren();
        ArrayList<TerenIgra> tereni = new ArrayList<>();
        for (TerenZaIgranje t : teren) {

            try {
                ByteArrayOutputStream outputStream;
                String base64Image;

                if (t.getSlika() != null) {

                    try (InputStream inputStream = t.getSlika().getBinaryStream()) {
                        outputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[4096];
                        int bytesRead = -1;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        byte[] imageBytes = outputStream.toByteArray();
                        base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    }
                    outputStream.close();

                    tereni.add(new TerenIgra(t.getId(), t.getLokacija(), base64Image, t.getTipId(), t.getGrdId(), t.getTipIme(), t.getGradIme(), t.getTipPodloga(), t.getSpoIme()));
                }
            } catch (NullPointerException e) {

                e.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        m.addAttribute("teren", tereni);
        return "teren";
    }

    @RequestMapping(value = "/dodajteren")
    public String DodajTeren(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<Grad> gradovi = prikaziGradBaza();
        ArrayList<Tip> tipovi = prikaziTipBaza();
        ArrayList<Sport> sportovi = prikaziSportBaza();
        m.addAttribute("gradovi", gradovi);
        m.addAttribute("tipovi", tipovi);
        m.addAttribute("sportovi", sportovi);
        return "dodajteren";
    }

    @RequestMapping(value = "/dodajteren", method = RequestMethod.POST)
    public String DodajTerene(@RequestParam String lokacija, @RequestParam int tipId, @RequestParam int[] sportovi, @RequestParam int grdId, @RequestParam("slika") MultipartFile file, ModelMap m) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("INSERT INTO teren (ter_lokacija, grd_id, tip_id, ter_slika) VALUE(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            s.setString(1, lokacija);
            s.setInt(2, grdId);
            s.setInt(3, tipId);
            s.setBlob(4, blob);
            s.executeUpdate();
            ResultSet rs = s.getGeneratedKeys();
            rs.next();
            int terId = rs.getInt(1);

            for (int i = 0; i < sportovi.length; i++) {
                PreparedStatement s1
                        = dataSource.getConnection().
                                prepareStatement("insert into spo_ter (spo_id,ter_id) VALUE(?,?)");

                s1.setInt(1, sportovi[i]);
                s1.setInt(2, terId);
                s1.execute();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/admin/prikaziteren";
    }

    @RequestMapping(value = "/obrisiteren{id}")
    public String ObrisiTeren(ModelMap model, @RequestParam Integer id) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("delete from teren where ter_id=?");
            s.setInt(1, id);
            s.executeUpdate();
            return "redirect:/admin/prikaziteren";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/prikaziteren";
        }

    }

    @RequestMapping(value = "/izmeniteren{id}")
    public String IzmenaTeren(@RequestParam("id") String id, ModelMap m) {

        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().prepareStatement("select * from teren where ter_id=?");
            s.setInt(1, Integer.parseInt(id));
            ResultSet rs = s.executeQuery();
            TerenIgra teren = new TerenIgra(0, id, id, 0, 0, id, id, id, id);
            rs.next();
            teren.setId(rs.getInt("ter_id"));
            teren.setSlika(rs.getString("ter_slika"));
            teren.setLokacija(rs.getString("ter_lokacija"));
            m.addAttribute("teren", teren);
            m.addAttribute("izmena", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "dodajteren";
    }

    @RequestMapping(value = "/updateteren{id}")
    public String updateTeren(
            @RequestParam Integer id,
            @RequestParam String lokacija,
            @RequestParam("slika") MultipartFile file,
            ModelMap model) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }
            try {
                byte[] bytes = file.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

                PreparedStatement s = dataSource.getConnection().
                        prepareStatement("update teren set ter_lokacija=?, ter_slika=? where ter_id=?");

                s.setString(1, lokacija);
                s.setBlob(2, blob);
                s.setInt(3, id);
                s.execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "dodajteren";
        }
        return "redirect:/admin/prikaziteren";

    }

    /**
     * ***********
     * DOGOVOR
     */
    private ArrayList<Dogovor> prikaziDogovor() {
        ArrayList<Dogovor> dogovor = new ArrayList<>();
        try {
            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("SELECT igr_ime,igr_prezime,igr_email,igr_telefon,igr_godine, dog_lokacija,dog_vreme_datum, dog_broj_Igraca, spo_ime,dog_id,spo_id,igr_id FROM dog_igr\n"
                                    + "inner join igrac using(igr_id)\n"
                                    + "inner join dogovor using(dog_id) \n"
                                    + "inner join sport using(spo_id) ");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {

                int id = rs.getInt("dog_id");
                int igraci = rs.getInt("dog_broj_Igraca");
                String lokacija = rs.getString("dog_lokacija");
                java.sql.Timestamp vreme = rs.getTimestamp("dog_vreme_datum");
                int spoid = rs.getInt("spo_id");
                String spoIme = rs.getString("spo_ime");
                String igrIme = rs.getString("igr_ime");
                String igrEmail = rs.getString("igr_email");
                int igrId = rs.getInt("igr_id");

                Dogovor t = new Dogovor(id, lokacija, igraci, vreme.toLocalDateTime(), igrIme, spoid, spoIme, igrId, igrIme, igrEmail, igrIme, igrId, igrIme, lokacija);
                dogovor.add(t);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dogovor;
    }

    @RequestMapping(value = "/prikazidogovor")
    public String prikaziDogovor(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<Dogovor> dogovor = prikaziDogovor();
        m.addAttribute("dogovor", dogovor);
        return "dogovor";
    }

    @RequestMapping(value = "/dodajdogovor")
    public String dodajDogovorGet(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<Sport> sport = prikaziSportBaza();
        m.addAttribute("sport", sport);
        return "dogovor";
    }

    @RequestMapping(value = "/dodajdogovor", method = RequestMethod.POST)
    public String DodajDogovor(
            @RequestParam String lokacija,
            @RequestParam int igraci,
            @RequestParam("bday") String datum,
            @RequestParam("time") String vreme,
            @RequestParam int spoId,
            ModelMap m) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("insert into dogovor (dog_lokacija, dog_broj_Igraca, dog_vreme_datum, spo_id) VALUE(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            String[] datum_niz = datum.split("-");
            String[] vreme_niz = vreme.split(":");
            int godina = Integer.parseInt(datum_niz[0]);
            int mesec = Integer.parseInt(datum_niz[1]);
            int dan = Integer.parseInt(datum_niz[2]);
            int sat = Integer.parseInt(vreme_niz[0]);
            int minut = Integer.parseInt(vreme_niz[1]);
            LocalDateTime date = LocalDateTime.of(godina, mesec, dan, sat, minut, 0, 0);

            PreparedStatement s2
                    = dataSource.getConnection().
                            prepareStatement("select * from igrac where igr_email=? ");
            String email = session.getAttribute("email").toString();
            s2.setString(1, email);
            ResultSet rs = s2.executeQuery();
            rs.next();
            int igrId = rs.getInt("igr_id");
            s.setString(1, lokacija);
            s.setInt(2, igraci);
            s.setTimestamp(3, java.sql.Timestamp.valueOf(date));
            s.setInt(4, spoId);
            s.executeUpdate();

            ResultSet rs1 = s.getGeneratedKeys();
            rs1.next();
            int dogId = rs1.getInt(1);

            PreparedStatement s3
                    = dataSource.getConnection().
                            prepareStatement("insert into dog_igr (dog_id,igr_id) VALUE(?,?)");
            s3.setInt(1, dogId);
            s3.setInt(2, igrId);
            s3.execute();

            return "dodajdogovor";
        } catch (Exception e) {
            e.printStackTrace();
            // m.addAttribute("uspeh", "greska prilikom ponude, pokusaj ponovo");

        }
        return "redirect:/admin/prikazidogovor";
    }

    @RequestMapping(value = "/prikazidodajdogovor")
    public String DodajDogovor(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<Sport> sport = prikaziSportBaza();
        m.addAttribute("sport", sport);
        return "dodajdogovor";
    }

    @RequestMapping(value = "/obrisidogovor{id}")
    public String ObrisiDogovor(ModelMap model, @RequestParam Integer id) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("delete from dogovor where dog_id=? ");
            s.setInt(1, id);
            s.executeUpdate();

            return "redirect:/admin/prikazidogovor";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/prikazidogovor";
        }

    }

    @RequestMapping(value = "/izmenidogovore{id}")
    public String IzmenaDogovora(@RequestParam int id,
            ModelMap m) {

        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().prepareStatement("SELECT dog_lokacija, dog_broj_Igraca, dog_vreme_datum FROM dogovor where dog_id=?");
            s.setInt(1, id);

            ResultSet rs = s.executeQuery();

            Dogovor dogovor = new Dogovor();
            rs.next();
            dogovor.setId(id);
            dogovor.setLokacija(rs.getString("dog_lokacija"));
            dogovor.setIgraci(rs.getInt("dog_broj_Igraca"));
            java.sql.Timestamp vreme = rs.getTimestamp("dog_vreme_datum");
            dogovor.setVreme(vreme.toLocalDateTime());

            ArrayList<Sport> sport = prikaziSportBaza();
            m.addAttribute("sport", sport);
            m.addAttribute("dogovor", dogovor);
            m.addAttribute("izmena", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "dodajdogovor";
    }

    @RequestMapping(value = "/updatedogovore{id}")
    public String updateDogovora(
            @RequestParam Integer id,
            @RequestParam String lokacija,
            @RequestParam int igraci,
            @RequestParam("bday") String datum,
            @RequestParam("time") String vreme,
            @RequestParam int spoId,
            ModelMap model) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("update dogovor set dog_lokacija=?, dog_broj_Igraca=?, dog_vreme_datum=?,spo_id=? where dog_id=?");
            String[] datum_niz = datum.split("-");
            String[] vreme_niz = vreme.split(":");
            int godina = Integer.parseInt(datum_niz[0]);
            int mesec = Integer.parseInt(datum_niz[1]);
            int dan = Integer.parseInt(datum_niz[2]);
            int sat = Integer.parseInt(vreme_niz[0]);
            int minut = Integer.parseInt(vreme_niz[1]);
            LocalDateTime date = LocalDateTime.of(godina, mesec, dan, sat, minut, 0, 0);

            s.setString(1, lokacija);
            s.setInt(2, igraci);
            s.setTimestamp(3, java.sql.Timestamp.valueOf(date));
            s.setInt(4, spoId);
            s.setInt(5, id);
            s.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return "redirect:/admin/prikazidogovor";

    }

    /**
     * ***********
     * Ocena
     */
    private ArrayList<Ocena> prikaziOcene() {
        ArrayList<Ocena> ocene = new ArrayList<>();
        try {
            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("SELECT * FROM ocena");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {

                int id = rs.getInt("ocn_id");
                String komentar = rs.getString("ocn_komentar");
                int ocena = rs.getInt("ocn_ocena");
                int igrId = rs.getInt("igr_id");
                int igrKoga = rs.getInt("igr_id_koga");

                PreparedStatement s1 = dataSource.getConnection().
                        prepareStatement("SELECT * FROM igrac where igr_id=?");

                s1.setInt(1, igrId);
                ResultSet rs1 = s1.executeQuery();
                rs1.next();
                String IgrIme = rs1.getString("igr_ime");

                PreparedStatement s2 = dataSource.getConnection().
                        prepareStatement("SELECT * FROM igrac where igr_id=?");

                s2.setInt(1, igrKoga);
                ResultSet rs2 = s2.executeQuery();
                rs2.next();
                String IgrKome = rs2.getString("igr_ime");

                Ocena t = new Ocena(id, igrId, igrKoga, IgrIme, IgrKome, komentar, ocena);
                ocene.add(t);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ocene;
    }

    @RequestMapping(value = "/prikaziocenu")
    public String prikaziOcenu(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<Ocena> ocena = prikaziOcene();
        m.addAttribute("ocena", ocena);
        return "ocena";
    }

    @RequestMapping(value = "/obrisiocenu{id}")
    public String ObrisiOcenu(ModelMap model, @RequestParam Integer id) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("delete from ocena where ocn_id=?");
            s.setInt(1, id);
            s.execute();
            return "redirect:/admin/prikaziocenu";
        } catch (Exception e) {
            e.printStackTrace();
            return "/admin/prikaziocenu";
        }

    }

    @RequestMapping(value = "/dodajocenu")
    public String DodajOcenu(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<User> igrac = podaciIzBazePodataka();
        m.addAttribute("igrac", igrac);
        return "dodajocenu";
    }

    @RequestMapping(value = "/dodajocenu", method = RequestMethod.POST)
    public String DodajOcenu(@RequestParam String komentar, @RequestParam int igrId, @RequestParam int igrIdKoga, @RequestParam int ocena, ModelMap m) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }
            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("INSERT INTO ocena (igr_id,igr_id_koga, ocn_ocena, ocn_komentar) VALUE(?,?,?,?)");
            s.setInt(1, igrId);
            s.setInt(2, igrIdKoga);
            s.setInt(3, ocena);
            s.setString(4, komentar);
            s.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/admin/prikaziocenu";
    }

    @RequestMapping(value = "/izmeniocenu{id}")
    public String IzmenaOcene(@RequestParam int id, ModelMap m) {

        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().prepareStatement("select * from ocena where ocn_id=?");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            Ocena ocena = new Ocena();
            rs.next();
            ocena.setId(rs.getInt("ocn_id"));
            ocena.setKomentar(rs.getString("ocn_komentar"));
            ocena.setOcena(rs.getInt("ocn_ocena"));

            m.addAttribute("ocena", ocena);
            m.addAttribute("izmena", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "dodajocenu";
    }

    @RequestMapping(value = "/updateocenu{id}")
    public String updateOcenu(
            @RequestParam Integer id,
            @RequestParam String komentar,
            @RequestParam Integer ocena,
            ModelMap model) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("update ocena set ocn_komentar=?, ocn_ocena=? where ocn_id=?");

            s.setString(1, komentar);
            s.setInt(2, ocena);
            s.setInt(3, id);
            s.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "dodajocenu";
        }
        return "redirect:/admin/prikaziocenu";

    }

    /**
     * ***********
     * PORUKA
     */
    private ArrayList<Poruke> prikaziPorukeBaza() {
        ArrayList<Poruke> poruke = new ArrayList<>();
        try {
            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("select igr_ime, igr_prezime, por_poruka, por_vreme,dog_lokacija, por_id, dog_id, igr_id from poruke"
                            + " inner join dogovor using(dog_id) "
                            + "inner join igrac using(igr_id)");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("por_id");
                int dogIdPor = rs.getInt("dog_id");
                int igrIdPor = rs.getInt("igr_id");
                String igrImePor = rs.getString("igr_ime");
                String igrPrezPor = rs.getString("igr_prezime");
                String poruka = rs.getString("por_poruka");
                java.sql.Timestamp porVreme = rs.getTimestamp("por_vreme");
                String dogLokacija = rs.getString("dog_lokacija");

                poruke.add(new Poruke(id, dogIdPor, igrIdPor, igrImePor, igrPrezPor, poruka, porVreme.toLocalDateTime(), dogLokacija));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return poruke;
    }

    @RequestMapping(value = "/prikaziporuku")
    public String prikaziPoruku(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<Poruke> poruke = prikaziPorukeBaza();
        m.addAttribute("poruke", poruke);
        return "poruka";
    }

    @RequestMapping(value = "/dodajporuku")
    public String DodajPoruku(ModelMap m) {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }

        ArrayList<User> igrac = podaciIzBazePodataka();
        ArrayList<Dogovor> dogovor = prikaziDogovor();
        m.addAttribute("dogovor", dogovor);
        m.addAttribute("igrac", igrac);

        return "dodajporuku";
    }

    @RequestMapping(value = "/dodajporuku", method = RequestMethod.POST)
    public String DodajPorukuBaza(@RequestParam String poruka, @RequestParam int dogIdPor, @RequestParam int igrId, @RequestParam int igrIdKome, ModelMap m) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }
            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("INSERT INTO poruke (dog_id, igr_id, por_vreme, por_poruka, igr_id_kome) VALUE(?,?,CURRENT_TIMESTAMP(),?,?)");

            s.setInt(1, dogIdPor);
            s.setInt(2, igrId);
            s.setString(3, poruka);
            s.setInt(4, igrIdKome);
            s.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "dodajporuku";
        }
        return "redirect:/admin/prikaziporuku";
    }

    @RequestMapping(value = "/obrisiporuku{id}")
    public String ObrisiPoruku(ModelMap model, @RequestParam Integer id) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("delete from  poruke where por_id=?");
            s.setInt(1, id);
            s.execute();
            return "redirect:/admin/prikaziporuku";
        } catch (Exception e) {
            e.printStackTrace();
            return "/admin/prikaziporuku";
        }

    }

    @RequestMapping(value = "/izmeniporuku{id}")
    public String IzmenaPoruke(@RequestParam("id") String id, ModelMap m) {

        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().prepareStatement("select * from poruke where por_id=?");
            s.setInt(1, Integer.parseInt(id));
            ResultSet rs = s.executeQuery();
            Poruke poruke = new Poruke();
            rs.next();
            poruke.setId(rs.getInt("por_id"));
            // poruke.setDogIdPor(rs.getInt("dog_id"));
            // poruke.setIgrIdPor(rs.getInt("igr_id"));
            //poruke.setIgrImePor(rs.getString("igr_ime"));
            // poruke.setIgrPrezPor(rs.getString("igr_prezime"));
            poruke.setPoruka(rs.getString("por_poruka"));
            //poruke.setVremePor(rs.getString("por_vreme"));
            m.addAttribute("poruke", poruke);
            m.addAttribute("izmena", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "dodajporuku";
    }

    @RequestMapping(value = "/updateporuku{id}")
    public String updatePoruku(
            @RequestParam Integer id,
            @RequestParam String poruka,
            ModelMap model) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("update poruke set por_poruka=? where por_id=?");

            s.setString(1, poruka);
            s.setInt(2, id);
            s.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "dodajporuku";
        }
        return "redirect:/admin/prikaziporuku";

    }

    /**
     * ***********
     * ADMIN
     */
    @RequestMapping(value = "/dodajadmina")
    public String dodajAdmina(
            @RequestParam String ime,
            @RequestParam String prezime,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String telefon,
            @RequestParam int godine,
            ModelMap model) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("INSERT INTO igrac (igr_ime,igr_prezime,igr_email,igr_password,igr_telefon,igr_godine,igr_admin) VALUE(?,?,?,?,?,?,?)");
            s.setString(1, ime);
            s.setString(2, prezime);
            s.setString(3, email);
            s.setString(4, password);
            s.setString(5, telefon);
            s.setInt(6, godine);
            s.setInt(7, 1);
            s.execute();
        } catch (Exception e) {
            model.addAttribute("mess", "GRESKA U DODAVANJU");
            return "adminadd";
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/prikaziDodajAdmina")
    public String prikaziAdmina() {
        String res = MainController.testPriviledges(session, true);
        if (res != null) {
            return res;
        }
        return "adminadd";
    }
}
