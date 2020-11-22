<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel=”stylesheet” href=”css/bootstrap-star-rating/star-rating.css” media=”all” rel=”stylesheet” type=”text/css”/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/profil.css">
        <title>nadjiigraca.com</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <nav class="slika">          
                <img src="/NadjiIgraca/resources/img/logo.png"/>  
            </nav>
            <div>

                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">

                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="http://localhost:8080/NadjiIgraca">Početna <span class="sr-only">(current)</span></a>
                        </li>
                        <c:if test="${isAdmin}">
                            <li class="nav-item active">
                                <a class="nav-link" href="http://localhost:8080/NadjiIgraca/admin">Admin <span class="sr-only"></span></a>
                            </li>
                        </c:if>
                        <li class="nav-item">
                            <a class="nav-link" href="http://localhost:8080/NadjiIgraca/onama">O nama</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="http://localhost:8080/NadjiIgraca/pravila">Pravila</a>
                        </li>

                        <c:if test="${email==null}">
                            <li class="nav-item">
                                <a class="nav-link" href="http://localhost:8080/NadjiIgraca/login">Registracija/Prijava</a>
                            </li>
                        </c:if>
                        <c:if test="${email!=null}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Profil
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                    <a class="dropdown-item" href="http://localhost:8080/NadjiIgraca/profil">Moj Nalog</a>

                                    <a class="dropdown-item" href="http://localhost:8080/NadjiIgraca/logout">Izloguj se</a>
                                </div>
                            </li>
                        </c:if>
                    </ul>
                </div>
        </nav>


        <div class="container">
            <!--/Profilna slika-->
            <form action="http://localhost:8080/NadjiIgraca/dodajsliku" method="post" enctype="multipart/form-data">

                <div class="col-lg-4 order-lg-1 text-center">
                    <c:if test="${slika==true}">
                        <img src="data:image/jpg;base64,${b64}" width="150" height="150"/>
                    </c:if>

                    <c:if test="${slika==false}">

                        <img src="https://pluspng.com/img-png/user-png-icon-big-image-png-2240.png"
                             width="150" height="150" />
                    </c:if>

                    <c:if test="${njegovprofil}">
                        <label class="custom-file">
                            <input type="file" id="file" name="slika" class="custom-file-input" size="5000">
                            <a class="btn btn-secondary"><span class="custom-file-control">Izaberi sliku</span></a>
                            <br>
                            <br>
                            <button type="submit" class="btn btn-secondary">Sacuvaj</button>
                        </label>
                    </c:if>
                </div>

            </form>  
            <div class="row my-2">

                <div class="col-lg-8 order-lg-2">
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <a href="" data-target="#profile" data-toggle="tab" class="nav-link active">Profil</a>
                        </li>
                        <c:if test="${njegovprofil}">
                            <li class="nav-item">
                                <a href="" data-target="#edit" data-toggle="tab" class="nav-link">Izmeni</a>
                            </li>
                        </c:if>
                        <c:if test="${njegovprofil==false}">
                            <li class="nav-item">
                                <a href="" data-target="#poruke"  data-toggle="tab" class="nav-link">Posalji poruku</a>
                            </li>
                        </c:if>
                        <c:if test="${njegovprofil}">
                            <li class="nav-item">
                                <a href="" data-target="#porukeprikaz"  data-toggle="tab" class="nav-link">Prikazi Poruke</a>
                            </li>
                        </c:if>
                        <li class="nav-item">
                            <a href="" data-target="#komentari" data-toggle="tab"  class="nav-link">Prikazi Komentare</a>
                        </li>
                    </ul>



                    <div class="tab-content py-4">
                        <div class="tab-pane active" id="profile">
                            <table class="table table-dark" >
                                <thead>
                                    <tr>
                                        <th scope="col">Ime</th>
                                        <th scope="col">Prezime</th>
                                        <th scope="col">Godine</th>
                                        <th scope="col">Telefon</th>
                                        <th scope="col">Email</th>
                                        <th scope="col">Prosecna ocena</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <tr>
                                        <th scope="row"><c:out value="${profil.ime}"/></th>
                                        <td><c:out value="${profil.prezime}"/></td>
                                        <td> <c:out value="${profil.godine}"/></td>
                                        <td> <c:out value="${profil.telefon}"/></td>
                                        <td> <c:out value="${profil.email}"/></td>
                                        <td><c:out value="${profil.prosek}"/> </td>

                                    </tr> 
                                </tbody>

                            </table>

                            <div class="row">
                                <c:if test="${njegovprofil==false}">
                                    <form id="forma" action="http://localhost:8080/NadjiIgraca/dodajocenukomentar" method="post">
                                        <input type="hidden" value="<c:out value="${profil.id}"/>" name="igrIdKoga">
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <label class="input-group-text" for="inputGroupSelect01">Ocena</label>
                                            </div>
                                            <select class="custom-select" id="inputGroupSelect01" name="ocena" id="ocena">
                                                <option selected>Izaberi Ocenu</option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <label for="comment">Komentar</label>
                                            <textarea class="form-control" rows="1" id="komentar" name="komentar" form="forma"></textarea>
                                        </div> 
                                        <button type="submit" class="btn btn-secondary">Dodaj Ocenu i Komentar</button>
                                    </form>
                                </c:if>
                                <c:if test="${njegovprofil}">
                                    <div class="col-md-12">
                                        <h5 class="mt-2"><span class="fa fa-clock-o ion-clock float-right"></span> Aktivni dogovori</h5>
                                        <table class="table table-sm table-hover table-striped">
                                            <tbody>                                    
                                            <thead>
                                                <tr>

                                                    <th scope="col">Lokacija</th>
                                                    <th scope="col">Datum i vreme</th>
                                                    <th scope="col">Broj Igraca</th>  
                                                    <th scope="col">Sport</th>
                                                    <th scope="col">Izmeni Dogovor</th>
                                                    <th scope="col">Obrisi Dogovor</th> 
                                                </tr>
                                            </thead>
                                            <c:forEach items="${dogovori}" var="dogovor">
                                                <tbody>

                                                    <tr>

                                                        <td><c:out value="${dogovor.lokacija}"/></td>
                                                        <td><c:out value="${dogovor.tekstVreme}"/></td>
                                                        <td><c:out value="${dogovor.igraci}"/></td>
                                                        <td><c:out value="${dogovor.spoIme}"/></td>

                                                        <td><a href="http://localhost:8080/NadjiIgraca/izmenidogovor/<c:out value='${dogovor.id}'/>" class="btn btn-primary"/>Izmeni Dogovor</td>
                                                <form action="http://localhost:8080/NadjiIgraca/obrisidogprofila?id=<c:out value='${dogovor.id}'/>" method="post">
                                                    <td> <input type="submit" class="btn btn-primary" value="Obrisi Dogovor"></td>
                                                </form>
                                                </tr>

                                                </tbody>
                                            </c:forEach>

                                            </tbody>
                                        </table>

                                    </div>
                                </c:if>
                            </div>
                            <!--/row-->

                        </div>

                        <div class="tab-pane" id="edit">


                            <form action="http://localhost:8080/NadjiIgraca/updatelozinka" role="form" method="post">


                                <div class="form-group row">
                                </div>
                                <div class="form-group row">
                                    <label class="col-lg-3 col-form-label form-control-label">Sifra</label>
                                    <div class="col-lg-9">
                                        <input class="form-control" type="password" name="">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-lg-3 col-form-label form-control-label">Nova Sifra</label>
                                    <div class="col-lg-9">
                                        <input class="form-control" type="password" name="novasifra">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-lg-3 col-form-label form-control-label"></label>
                                    <div class="col-lg-9">
                                        <input type="reset" class="btn btn-secondary" value="Cancel">
                                        <input type="submit" class="btn btn-primary" value="Sacuvaj Izmene">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <c:if test="${njegovprofil==false}">
                            <div class="tab-pane" id="poruke">


                                <form action="http://localhost:8080/NadjiIgraca/poruke"  id="forme" method="post">
                                    <input type="hidden" value="<c:out value="${profil.id}"/>" name="igrIdKome">
                                    <input type="hidden" value="<c:out value="${profil.id}"/>" name="igrId">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <label class="input-group-text" for="inputGroupSelect01">Izaberi dogovor</label>
                                        </div>
                                        <select name="dogId">
                                            <c:forEach items="${dogovori}" var="dogovor">
                                                <option value=<c:out value="${dogovor.id}"/> > <c:out value="${dogovor.lokacija}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>  

                                    <div class="form-group row">
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-lg-3 col-form-label form-control-label">Poruka</label>
                                        <div class="col-lg-9">
                                            <textarea class="form-control" rows="1" id="poruka" name="poruka" form="forme" placeholder="Napisi poruku"></textarea>
                                            <br>
                                            <input type="submit" class="btn btn-primary" value="Posalji poruku">
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label class="col-lg-3 col-form-label form-control-label"></label>
                                        <div class="col-lg-9">


                                        </div>
                                    </div>
                                </form>
                            </div>

                        </c:if>
                        <c:if test="${njegovprofil}">
                            <div class="tab-pane" id="porukeprikaz">

                                <c:forEach items="${poruka}" var="poruke">
                                    <form action="http://localhost:8080/NadjiIgraca/poruke"  id="formee ${poruke.vremePor}" method="post">
                                        <div class="form-group row">

                                            <div class="col-lg-9">
                                                <table class="table table-sm table-hover table-striped">
                                                    <tbody>
                                                    <td>${poruke.dogLokacija}</td>
                                                    <td>${poruke.igrImePor}</td>
                                                    <td>${poruke.igrPrezPor}</td>
                                                    <td>${poruke.poruka}</td>
                                                    <td>${poruke.vremePor}</td>
                                                    <td>

                                                        <input type="hidden" value="<c:out value="${poruke.igrIdPor}"/>" name="igrIdKome"> 
                                                        <input type="hidden" value="<c:out value="${poruke.dogIdPor}"/>" name="dogId"> 
                                                        <textarea class="form-control" rows="1" id="poruka" name="poruka" form="formee ${poruke.vremePor}"></textarea>
                                                        <br>
                                                        <input type="submit" class="btn btn-primary"  value="odgovori ${poruke.igrImePor}">              
                                                    </td>

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </form>
                                </c:forEach>
                            </div>

                        </c:if>


                        <div class="tab-pane" id="komentari">                           

                            <c:forEach items="${komentari}" var="komentar">
                                <div class="form-group row">

                                    <div class="col-lg-9">
                                        <table class="table table-sm table-hover table-striped">
                                            <td>${komentar.igrIme}</td>

                                            <td>${komentar.komentar}</td>

                                        </table>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>



            </div>
        </div>


        <footer class="py-5" id="kontakt">


            <!-- CONTACT -->
            <section class="contact py-5">
                <div class="container">
                    <div class="row">
                        <div class="col-md-4 text-center">
                            <i class="fas fa-map-marker-alt"></i>
                            <h4>Kancelarija</h4>
                            <p>Selo Kozare bb <br> Leskovac,Grdelica</p>
                        </div>
                        <div class="col-md-4 text-center">
                            <i class="fas fa-phone"></i>
                            <h4>Telefon</h4>
                            <p>060 52 78 508</p>
                        </div>
                        <div class="col-md-4 text-center">
                            <i class="fas fa-envelope"></i>
                            <h4>Email</h4>
                            <p>cirkovicmilos99@gmail.com <br> cirkovicmilos61@gmail</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4"></div>
                        <div class="col-md-4">
                            <h3 class="text-center">Društvene mreže:</h3>
                            <ul class="list-unstyled d-flex justify-content-around">
                                <a href="" id="fb"><i class="fab fa-facebook"></i></a>
                                <a href="https://www.instagram.com/mcirkovic1/" id="ins"><i class="fab fa-instagram"></i></a>
                                <a href="" id="yt"><i class="fab fa-youtube"></i></a>
                            </ul>
                        </div>
                        <div class="col-md-4"></div>
                    </div>
                    <h3 class="text-center">&copy; Made by Miloš Ćirković </h3>
                </div>
                </div>
                </div>
                </div>
            </section>             
        </footer>	




        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
</html>
