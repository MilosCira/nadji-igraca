package main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
@RequestMapping("/")
public class MainController {

    private void setCookie(String key, int value, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, String.valueOf(value));
        response.addCookie(cookie);
    }

    @Autowired
    HttpSession session;

    @Autowired
    DriverManagerDataSource dataSource;

    public static String testPriviledges(HttpSession session, boolean requireAdmin) {
        Object t = session.getAttribute("email");
        if (t == null) {
            return "redirect:/login";
        }
        if (requireAdmin) {
            t = session.getAttribute("admin");
            if (!(t instanceof Number) || ((Number) t).intValue() != 1) {
                return "redirect:/home";
            }
        }
        return null;
    }

    @RequestMapping(value = {"/", "/home", "/start"})
    public String homepage(HttpServletRequest request, ModelMap model) {
        boolean isAdmin = false;
        Cookie cookie = null;
        Cookie[] cookies = null;
        try {
            cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("admin") && cookie.getValue().equals("1")) {
                    isAdmin = true;
                }
            }
            model.addAttribute("isAdmin", isAdmin);
            return "home";
        } catch (NullPointerException e) {
            return "home";
        }

    }

    @RequestMapping(value = "/onama")
    public String onama(HttpServletRequest request, ModelMap model) {
        boolean isAdmin = false;
        Cookie cookie = null;
        Cookie[] cookies = null;
        try {
            cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("admin") && cookie.getValue().equals("1")) {
                    isAdmin = true;
                }

            }
            model.addAttribute("isAdmin", isAdmin);
            return "onama";
        } catch (NullPointerException e) {
            return "onama";
        }

    }

    @RequestMapping(value = "/admin")
    public String admin(HttpServletRequest request) {
        Cookie cookie = null;
        Cookie[] cookies = null;
        try {
            cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("admin") && cookie.getValue().equals("1")) {
                    return "admin";
                }

            }
        } catch (NullPointerException e) {
            return "login";
        }
        return "login";
    }

    @RequestMapping(value = "/pravila")
    public String pravila(HttpServletRequest request, ModelMap model) {
        boolean isAdmin = false;
        Cookie cookie = null;
        Cookie[] cookies = null;
        try {
            cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("admin") && cookie.getValue().equals("1")) {
                    isAdmin = true;
                }

            }
            model.addAttribute("isAdmin", isAdmin);
            return "pravila";
        } catch (NullPointerException e) {
            return "pravila";
        }

    }

    @RequestMapping("/login")
    public String login(@RequestParam(required = false) String email, @RequestParam(required = false) String password, ModelMap model) {

        return "login";

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String someMethod(@RequestParam("email") String email, @RequestParam("password") String password, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (email == null || password == null) {
                return "login";
            }

            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("SELECT * FROM igrac where igr_email=? and igr_password=?");

            s.setString(1, email);
            s.setString(2, password);
            ResultSet rs = s.executeQuery();
            rs.next();
            session.setAttribute("email", rs.getString("igr_email"));
            session.setAttribute("ime", rs.getString("igr_ime"));

            int admin = rs.getInt("igr_admin");
            if (admin == 1) {
                session.setAttribute("admin", 1);
                model.addAttribute("admin", 1);
                setCookie("admin", 1, response);
                return "redirect:/admin";
            }

            return "redirect:/home";

        } catch (Exception e) {
            model.addAttribute("poruka", "Pogresno korisničko ime ili loznika! Pokušajte ponovo");
            return "login";
        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        setCookie("admin", 0, response);
        return "redirect:/home";
    }

    @RequestMapping(value = "/updatelozinka", method = RequestMethod.POST)
    public String updateLozinka(
            @RequestParam("novasifra") String novaSifra, ModelMap m, HttpServletResponse httpResponse) {
        try {
            String res = MainController.testPriviledges(session, true);
            if (res != null) {
                return res;
            }

            PreparedStatement s1 = dataSource.getConnection().
                    prepareStatement("select * from igrac where igr_email=? ");
            String email = session.getAttribute("email").toString();
            s1.setString(1, email);
            ResultSet rs = s1.executeQuery();
            rs.next();
            int igrId = rs.getInt("igr_id");

            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("update igrac set igr_password=? where igr_id=?");
            s.setString(1, novaSifra);
            s.setInt(2, igrId);
            s.execute();
            m.addAttribute("lozinka", true);
            try {
                httpResponse.sendRedirect("/NadjiIgraca/igrac/" + igrId);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            m.addAttribute("greska", "izmenite lozinku ponovo desila se neka greska!");

        }
        return "profil";
    }

    @RequestMapping("/registracija")
    public String registracija(
            @RequestParam String ime,
            @RequestParam String prezime,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String telefon,
            @RequestParam int godine,
            @RequestParam char pol,
            ModelMap model) {

        try {
           
            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("INSERT INTO igrac (igr_ime,igr_prezime,igr_email,igr_password,igr_telefon,igr_godine,igr_pol) VALUE(?,?,?,?,?,?,?)");
            s.setString(1, ime);
            s.setString(2, prezime);
            s.setString(3, email);
            s.setString(4, password);
            s.setString(5, telefon);
            s.setInt(6, godine);
            s.setString(7, String.valueOf(pol));
            s.execute();
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("poruka", "greska prilikom registracije pokusajte ponovo");
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/ponuda", method = RequestMethod.GET)
    public String getPonuda(ModelMap m) {
        try {

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("select * from sport");
            ArrayList<Sport> sportovi = new ArrayList<>();
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("spo_id");
                String ime = rs.getString("spo_ime");
                Sport sport = new Sport(id, ime);
                sportovi.add(sport);
            }
            m.addAttribute("sportovi", sportovi);

            PreparedStatement s1
                    = dataSource.getConnection().
                            prepareStatement("SELECT grd_ime, drz_ime,grd_id,drz_id FROM grad\n"
                                    + "inner join drzava using(drz_id)");
            ArrayList<Grad> gradovi = new ArrayList<>();
            ResultSet rs1 = s1.executeQuery();
            while (rs1.next()) {
                int id = rs1.getInt("grd_id");
                String ime = rs1.getString("grd_ime");
                int drzId = rs1.getInt("drz_id");
                String drzIme = rs1.getString("drz_ime");
                Grad grad = new Grad(id, ime, drzId, drzIme);
                gradovi.add(grad);

            }
            m.addAttribute("gradovi", gradovi);

            PreparedStatement s2
                    = dataSource.getConnection().
                            prepareStatement("SELECT grd_ime, tip_namena, tip_podloga,ter_lokacija, ter_slika,ter_id,drz_id FROM teren\n"
                                    + "inner join grad using(grd_id)\n"
                                    + "inner join tip using (tip_id)");
            ArrayList<TerenZaIgranje> tereni = new ArrayList<>();
            ResultSet rs2 = s2.executeQuery();
            
            while (rs2.next()) {
                int id = rs2.getInt("ter_id");
                String lokacija = rs2.getString("ter_lokacija");
                Blob slika = rs2.getBlob("ter_slika");
                String grdIme=rs2.getString("grd_ime");
                String tipPodloga=rs2.getString("tip_podloga");
                String tipIme=rs2.getString("tip_namena");
                int drzId = rs2.getInt("drz_id");
           
                TerenZaIgranje teren = new TerenZaIgranje(id, lokacija, slika, drzId, drzId, tipIme, grdIme, tipPodloga, tipIme);
                tereni.add(teren);

            }
            m.addAttribute("tereni", tereni);

            return "ponuda";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "ponuda";
    }

    @RequestMapping(value = "/ponuda", method = RequestMethod.POST)
    public String ponuda(
            @RequestParam(required = false) String lokacija,
            @RequestParam int igraci,
            @RequestParam("bday") String datum,
            @RequestParam("time") String vreme,
            @RequestParam int SpoId,
            @RequestParam int GrdId,
            @RequestParam(required = false) int terId,
            ModelMap m) {
        try {
           
            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("insert into dogovor (dog_lokacija, dog_broj_Igraca, dog_vreme_datum, spo_id,grd_id,ter_id) VALUE(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
           
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
            s.setInt(4, SpoId);
            s.setInt(5, GrdId);                    
           
            if(terId!=-1){
                s.setInt(6, terId);
            }
             if(terId==-1){
                s.setNull(6, terId);
            }
            
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
            m.addAttribute("uspeh", "uspesno ste dodali ponudu");
            return "ponuda";
        } catch (Exception e) {
            e.printStackTrace();
            // m.addAttribute("uspeh", "greska prilikom ponude, pokusaj ponovo");
            return "ponuda";
        }
    }

    @RequestMapping(value = "/pronadji", method = RequestMethod.GET)
    public String Pronadji(ModelMap m) {
        try {

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("SELECT igr_ime,igr_prezime,igr_email,igr_telefon,igr_godine, dog_lokacija,dog_vreme_datum, dog_broj_Igraca, spo_ime,dog_id,spo_id,igr_id,ter_id,ter_lokacija FROM dog_igr\n"
                                    + "inner join igrac using(igr_id)\n"
                                    + "inner join dogovor using(dog_id) \n"
                                    + "inner join sport using(spo_id)\n"
                                    + "left join teren using(ter_id)  WHERE dog_vreme_datum >= CURRENT_TIMESTAMP ");

            ResultSet rs = s.executeQuery();
            ArrayList<Dogovor> dogovori = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("dog_id");
                String lokacija = rs.getString("dog_lokacija");
                int igraci = rs.getInt("dog_broj_Igraca");
                java.sql.Timestamp vreme = rs.getTimestamp("dog_vreme_datum");
                int sportId = rs.getInt("spo_id");
                String igrIme = rs.getString("igr_ime");
                String igrEmail = rs.getString("igr_email");
                String igrTel = rs.getString("igr_telefon");
                String igrPrez = rs.getString("igr_prezime");
                int igrGod = rs.getInt("igr_godine");
                String spoIme = rs.getString("spo_ime");
                int igrId = rs.getInt("igr_id");
                String terLokacija=rs.getString("ter_lokacija");
                Dogovor dogovor = new Dogovor(id, lokacija, igraci, vreme.toLocalDateTime(), igrIme, sportId, spoIme, igrId, igrIme, igrEmail, igrTel, igrGod, igrPrez, terLokacija);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                dogovor.setTekstVreme(vreme.toLocalDateTime().format(formatter));
                dogovori.add(dogovor);
            }
            m.addAttribute("dogovori", dogovori);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/home";
        }
        return "pronadji";
    }

    /**
     * ***********
     * sve za profil
     */
    @RequestMapping(value = "/profil")
    public String profil(HttpServletResponse httpResponse, ModelMap m) {

        try {

            PreparedStatement s = dataSource.getConnection().
                    prepareStatement("select * from igrac where igr_email=? ");
            String email = session.getAttribute("email").toString();
            s.setString(1, email);
            ResultSet rs = s.executeQuery();
            rs.next();
            int igrId = rs.getInt("igr_id");

            httpResponse.sendRedirect("/NadjiIgraca/igrac/" + igrId);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "profil";
    }

    @RequestMapping(value = "/igrac/{id}", method = RequestMethod.GET)
    public String getProfilIgraca(@PathVariable int id, ModelMap model) {

        try {
            PreparedStatement s1 = dataSource.getConnection().
                    prepareStatement("select * from igrac where igr_email=? ");
            String email = session.getAttribute("email").toString();
            s1.setString(1, email);
            ResultSet rs1 = s1.executeQuery();
            rs1.next();
            int igrIdPosetilac = rs1.getInt("igr_id");
            boolean njegovProfil = false;
            if (igrIdPosetilac == id) {
                njegovProfil = true;
            }

            model.addAttribute("njegovprofil", njegovProfil);

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("select * from igrac  where igr_id=?");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            rs.next();
            int igrId = rs.getInt("igr_id");
            String igrIme = rs.getString("igr_ime");
            String igrPrezime = rs.getString("igr_prezime");
            int igrGod = rs.getInt("igr_godine");
            String igrTel = rs.getString("igr_telefon");
            String igrEmail = rs.getString("igr_email");
            Blob igrSlika = rs.getBlob("igr_slika");
            if (igrSlika != null) {
                try {
                    InputStream inputStream = igrSlika.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                    model.addAttribute("b64", base64Image);
                    model.addAttribute("slika", true);

                } catch (NullPointerException e) {

                    e.printStackTrace();
                }

            } else {
                model.addAttribute("slika", false);
            }
            User profil = new User(igrId, igrIme, igrPrezime, igrGod, igrTel, igrEmail, igrSlika);

            PreparedStatement s2
                    = dataSource.getConnection().
                            prepareStatement("SELECT AVG(ocn_ocena) AS prosek FROM ocena where igr_id_koga=?;");
            s2.setInt(1, id);
            ResultSet rs2 = s2.executeQuery();
            rs2.next();
            Double prosek = rs2.getDouble("prosek");
            profil.setProsek(prosek);
            model.addAttribute("profil", profil);

            ArrayList<Dogovor> dogovori = new ArrayList<>();

            PreparedStatement s4
                    = dataSource.getConnection().
                            prepareStatement("SELECT dog_lokacija,dog_vreme_datum, dog_broj_Igraca, spo_ime,spo_id,dog_id FROM dog_igr\n"
                                    + "  inner join dogovor using(dog_id)\n"
                                    + "  inner join igrac using(igr_id) \n"
                                    + "  inner join sport using(spo_id)  where igr_id=? and dog_vreme_datum >= CURRENT_TIMESTAMP ");
            s4.setInt(1, igrId);
            ResultSet rs4 = s4.executeQuery();
            while (rs4.next()) {
                int dogId = rs4.getInt("dog_id");
                String lokacija = rs4.getString("dog_lokacija");
                int igraci = rs4.getInt("dog_broj_Igraca");
                java.sql.Timestamp vreme = rs4.getTimestamp("dog_vreme_datum");
                int spoId = rs4.getInt("spo_id");
                String spoIme = rs4.getString("spo_ime");

                Dogovor dogovor = new Dogovor(dogId, lokacija, igraci, vreme.toLocalDateTime(), igrIme, spoId, spoIme, igrId, igrIme, igrEmail, igrTel, igrGod, igrTel, lokacija);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                dogovor.setTekstVreme(vreme.toLocalDateTime().format(formatter));
                dogovori.add(dogovor);

                ArrayList<Poruke> poruke = new ArrayList<>();
                for (int i = 0; i < dogovori.size(); i++) {
                    dogId = dogovori.get(i).getId();
                    PreparedStatement s6
                            = dataSource.getConnection().
                                    prepareStatement("SELECT dog_lokacija, igr_ime, igr_prezime, por_poruka, por_vreme,dog_id,igr_id  FROM poruke\n"
                                            + "inner join dogovor using(dog_id)\n"
                                            + "inner join igrac using(igr_id)\n"
                                            + "where igr_id_kome=?");
                    s6.setInt(1, id);
                    ResultSet rs6 = s6.executeQuery();

                    while (rs6.next()) {
                        int dogIdPor = rs6.getInt("dog_id");
                        int igrIdPor = rs6.getInt("igr_id");
                        String por = rs6.getString("por_poruka");
                        String igrImePor = rs6.getString("igr_ime");
                        String igrPrezPor = rs6.getString("igr_prezime");
                        String dogLokacija = rs6.getString("dog_lokacija");
                        java.sql.Timestamp porVreme = rs6.getTimestamp("por_vreme");

                        Poruke poruka = new Poruke(id, dogIdPor, igrIdPor, igrImePor, igrPrezPor, por, porVreme.toLocalDateTime(), dogLokacija);
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");
                        poruka.setVremePor(porVreme.toLocalDateTime().format(formatter1));

                        poruke.add(poruka);

                    }
                }
                model.addAttribute("poruka", poruke);

                PreparedStatement s5
                        = dataSource.getConnection().
                                prepareStatement("select igr_ime, igr_prezime, ocn_komentar from ocena \n"
                                        + "inner join igrac using(igr_id) where igr_id_koga=?");
                s5.setInt(1, id);
                ResultSet rs5 = s5.executeQuery();
                ArrayList<Ocena> komentari = new ArrayList<>();
                while (rs5.next()) {
                    String komentar = rs5.getString("ocn_komentar");
                    String igrImeKom = rs5.getString("igr_ime");
                    String igrPrezimeKom = rs5.getString("igr_prezime");

                    Ocena ocena = new Ocena();
                    ocena.setKomentar(komentar);
                    ocena.setIgrIme(igrImeKom + " " + igrPrezimeKom);
                    komentari.add(ocena);

                }
                model.addAttribute("komentari", komentari);
            }
            model.addAttribute("dogovori", dogovori);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "profil";
    }

    @RequestMapping(value = "/dodajocenukomentar", method = RequestMethod.POST)
    public String DodajOcene(@RequestParam String komentar, @RequestParam int igrIdKoga, @RequestParam int ocena, ModelMap m) {
        try {

            PreparedStatement s1 = dataSource.getConnection().
                    prepareStatement("select * from igrac where igr_email=? ");
            String email = session.getAttribute("email").toString();
            s1.setString(1, email);
            ResultSet rs1 = s1.executeQuery();
            rs1.next();
            int igrId = rs1.getInt("igr_id");
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
        return "redirect:profil";
    }

    @RequestMapping(value = "/izmenidogovor/{id}")
    public String IzmenaDogovor(@PathVariable("id") int id,
            ModelMap m) {

        try {

            PreparedStatement s = dataSource.getConnection().prepareStatement("select * from dogovor where dog_id=?");
            s.setInt(1, id);

            ResultSet rs = s.executeQuery();

            Dogovor dogovori = new Dogovor();
            rs.next();
            dogovori.setId(id);
            dogovori.setLokacija(rs.getString("dog_lokacija"));
            dogovori.setIgraci(rs.getInt("dog_broj_Igraca"));
            java.sql.Timestamp vreme = rs.getTimestamp("dog_vreme_datum");
            dogovori.setVreme(vreme.toLocalDateTime());

            PreparedStatement s1 = dataSource.getConnection().prepareStatement("select * from sport");
            ResultSet rs1 = s1.executeQuery();
            ArrayList<Sport> sportovi = new ArrayList<>();
            while (rs1.next()) {
                int spoId = rs1.getInt("spo_id");
                String spoIme = rs1.getString("spo_ime");
                Sport sport = new Sport(spoId, spoIme);
                sportovi.add(sport);
            }
            m.addAttribute("sportovi", sportovi);
            m.addAttribute("dogovori", dogovori);
            m.addAttribute("izmena", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "ponuda";
    }

    @RequestMapping(value = "/updatedogovor{id}")
    public String updateDogovor(
            @RequestParam Integer id,
            @RequestParam String lokacija,
            @RequestParam int igraci,
            @RequestParam("bday") String datum,
            @RequestParam("time") String vreme,
            @RequestParam int SpoId,
            ModelMap model, HttpServletResponse httpResponse) {
        try {

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
            s.setInt(4, SpoId);
            s.setInt(5, id);
            s.execute();

            try {
                PreparedStatement s1 = dataSource.getConnection().
                        prepareStatement("select * from igrac where igr_email=? ");
                String email = session.getAttribute("email").toString();
                s1.setString(1, email);
                ResultSet rs = s1.executeQuery();
                rs.next();
                int igrId = rs.getInt("igr_id");

                httpResponse.sendRedirect("/NadjiIgraca/igrac/" + igrId);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return "profil";

    }

    @RequestMapping(value = "/obrisidogprofila{id}", method = RequestMethod.POST)
    public String ObrisiDogProfila(ModelMap model, @RequestParam Integer id, HttpServletResponse httpResponse) {
        try {

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("delete from dogovor where dog_id=?");
            s.setInt(1, id);
            s.executeUpdate();
            try {
                PreparedStatement s1 = dataSource.getConnection().
                        prepareStatement("select * from igrac where igr_email=? ");
                String email = session.getAttribute("email").toString();
                s1.setString(1, email);
                ResultSet rs = s1.executeQuery();
                rs.next();
                int igrId = rs.getInt("igr_id");

                httpResponse.sendRedirect("/NadjiIgraca/igrac/" + igrId);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "profil";
    }

    /**
     * ***********
     * slika
     */
    @RequestMapping(value = "/dodajsliku", method = RequestMethod.POST)
    public String uploadSlike(@RequestParam("slika") MultipartFile file, ModelMap m, HttpServletResponse httpResponse) throws SQLException {
        Blob blob;
        int igrId = 0;
        try {
            

            byte[] bytes = file.getBytes();
            blob = new javax.sql.rowset.serial.SerialBlob(bytes);

            PreparedStatement s1 = dataSource.getConnection().
                    prepareStatement("select * from igrac where igr_email=? ");
            String email = session.getAttribute("email").toString();
            s1.setString(1, email);
            ResultSet rs = s1.executeQuery();
            rs.next();
            igrId = rs.getInt("igr_id");

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("UPDATE igrac SET igr_slika=? WHERE igr_id = ?");
            s.setBlob(1, blob);
            s.setInt(2, igrId);
            s.execute();

            m.addAttribute("uspeh", "Uspesno ste ubacili sliku");
        } catch (Exception ex) {
            m.addAttribute("uspeh", "Greska prilikom ubacivanje slike");
            ex.printStackTrace();
        }
        try {
            httpResponse.sendRedirect("/NadjiIgraca/igrac/" + igrId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "profil";
    }

    /**
     * ***********
     * PORUKA
     */
    @RequestMapping(value = "/poruke", method = RequestMethod.POST)
    public String PorukaPost(@RequestParam int dogId, @RequestParam("poruka") String poruka, @RequestParam int igrIdKome, ModelMap m, HttpServletResponse httpResponse) {

        try {

            PreparedStatement s1 = dataSource.getConnection().
                    prepareStatement("select * from igrac where igr_email=? ");
            String email = session.getAttribute("email").toString();
            s1.setString(1, email);
            ResultSet rs = s1.executeQuery();
            rs.next();
            int igrId = rs.getInt("igr_id");

            PreparedStatement s
                    = dataSource.getConnection().
                            prepareStatement("INSERT INTO poruke (dog_id, igr_id, por_vreme, por_poruka, igr_id_kome) VALUE(?,?,CURRENT_TIMESTAMP(),?,?)");
            s.setInt(1, dogId);
            s.setInt(2, igrId);
            s.setString(3, poruka);
            s.setInt(4, igrIdKome);
            s.execute();
            try {
                httpResponse.sendRedirect("/NadjiIgraca/igrac/" + igrId);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            m.addAttribute("por", "Uspesno ste poslali poruku");

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return "profil";
    }
    
}
