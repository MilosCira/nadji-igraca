
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/login.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>nadjiigraca.com</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <nav class="slika">          
                <img src="/NadjiIgraca/resources/img/logo.png"/>  
            </nav>
            <div class="container">

                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">

                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="http://localhost:8080/NadjiIgraca">Početna<span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="http://localhost:8080/NadjiIgraca/onama">O nama</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="http://localhost:8080/NadjiIgraca/pravila">Pravila</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#kontakt">Kontakt</a>
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

        <section class="two-columns">
            <div class="container">
                <div class="row">  
                    <p>${uspeh}</p>

                </div>
                <div class="row">
                    <div class="col-6" border="1">
                        <c:choose>
                            <c:when test="${izmena}">
                                <form id="register-form" action="http://localhost:8080/NadjiIgraca/updatedogovor?id=${dogovori.id}" method="post" role="form">
                                </c:when>
                                <c:otherwise>    
                                    <form id="register-form" action="http://localhost:8080/NadjiIgraca/ponuda" method="post" role="form">
                                    </c:otherwise>
                                </c:choose> 


                                <div class="form-group">    
                                    <label>Izaberi teren</label>
                                    <select name="terId" class="form-control">
                                        <option selected="true"  value="-1">Izaberi teren</option>
                                        <c:forEach items="${tereni}" var="teren">     
                                            
                                            <option value="<c:out value='${teren.id}'/>"> <c:out value="${teren.lokacija}" /> ${teren.gradIme} ${teren.tipPodloga} ${teren.tipIme} </option>
                                        </c:forEach>

                                    </select>

                                    <label>Ili unesi lokaciju dogovora</label>
                                    <input type="text" name="lokacija" id="loakcija" tabindex="1" class="form-control" placeholder="lokacija dogovora" value="${dogovori.lokacija}">   

                                </div>

                                <div class="form-group">
                                    <label>Grad</label>
                                    <br>
                                    <select name="GrdId">
                                        <c:forEach items="${gradovi}" var="grad">
                                            <option value="<c:out value='${grad.id}'/>" > <c:out value="${grad.ime}" />, ${grad.drzIme}</option>
                                        </c:forEach>
                                    </select>
                                </div>  

                                <div class="form-group">
                                    <label>Broj igrača</label>
                                    <input type="text" name="igraci" id="igraci" tabindex="1" class="form-control" placeholder="Broj Igraca" value="${dogovori.igraci}" required>
                                </div>

                                <div class="form-group">
                                    <label >Odaberi datum i vreme kada vam je potreban igrač</label>
                                    <div>

                                        <div class="form-control">
                                            <input type="date" name="bday" max="3000-12-31" min="1000-01-01" >                           
                                            <input id="appt-time" type="time" name="time" value="00:00" >
                                        </div>
                                    </div>
                                </div>



                                <div class="form-group">
                                    <label>Sport</label>
                                    <br>
                                    <select name="SpoId">
                                        <c:forEach items="${sportovi}" var="sport">
                                            <option value="<c:out value='${sport.id}'/>" > <c:out value="${sport.ime}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>                 

                                <c:if test="${izmena==null}">
                                    <input type="submit" value="Daj Ponudu" class="btn btn-success">
                                </c:if>
                                <c:if test="${izmena}">
                                    <input type="submit" value="Izmeni Ponudu" class="btn btn-success">
                                </c:if>

                            </form>
                    </div>
                    <div class="col-6">

                    </div>
                </div>
                <div class="row">		  
                </div>
            </div>
        </section>

        <!-- footer -->
        <footer class="py-5" id="kontakt">
            <section class="form py-5">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <h2 class="heading">Contact us</h2>
                            <div class="underline"></div>
                            <p>Ukoliko imate pitanje ili zelite da nam pošaljete neku sugestiju kontaktirajte nas putem mejla! Srdačan pozdrav želi vam tim Nadji Igrača!</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4"></div>
                        <div class="col-md-4">
                            <form>
                                <div class="form-group">
                                    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
                                </div>
                                <div class="form-group">
                                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                                </div>

                                <div class="form-group">
                                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="Message"></textarea>
                                </div>
                                <button type="submit" class="btn btn-block">Pošalji</button>
                            </form>
                        </div>
                        <div class="col-md-4"></div>
                    </div>
                </div>
            </section>

            <!-- CONTACT -->
            <section class="contact py-5">
                <div class="container">
                    <div class="row">
                        <div class="col-md-4 text-center">
                            <i class="fas fa-map-marker-alt"></i>
                            <h4>Main office</h4>
                            <p>Selo Kozare bb <br> Leskovac,Grdelica</p>
                        </div>
                        <div class="col-md-4 text-center">
                            <i class="fas fa-phone"></i>
                            <h4>Phones</h4>
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



        <script>
            $(function () {

            $('#login-form-link').click(function (e) {
            $("#login-form").show();
                    $("#register-form").hide();
                    $('#register-form-link').removeClass('active');
                    $(this).addClass('active');
                    e.preventDefault();
            });
        </script>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
</html>
