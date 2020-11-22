
<%@page import="main.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="resources/login.css">
        <link rel="stylesheet" href="resources/style.css">
        <title>nadjiigraca.com</title>
    </head>



    <body>
        <!-- header-->

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <nav class="slika">          
                <img src="/NadjiIgraca/resources/img/logo.png" width="30" height="30" alt="">
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
                                <a class="nav-link" href="http://localhost:8080/NadjiIgraca/login">Prijava/Registracija</a>
                            </li>
                        </c:if>
                        <c:if test="${email!=null}">
                            <li>  
                                <a href="http://localhost:8080/NadjiIgraca/logout" class="nav-link">Izloguj se</a>
                            </li>
                        </c:if>
                    </ul>

                </div>

        </nav> 

        <!-- Login-->

        <section class="two-columns">
            <div class="container">

                <div class="row">
                    <div class="col-md-6 col-md-offset-3">
                        <div class="panel panel-login">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-6">
                                        <a href="#" class="active" id="login-form-link">PRIJAVI SE</a>
                                    </div>
                                    <div class="col-xs-6">
                                        <a href="#" id="register-form-link">REGISTRUJ SE</a>
                                    </div>
                                </div>
                                <hr>
                            </div>

                            <div class="panel-body">

                                <div class="row">
                                    <div class="col-lg-12">
                                        <form id="login-form" action="http://localhost:8080/NadjiIgraca/login" method="post" role="form"> 
                                            <c:if test="${email==null }">
                                                <p>
                                                    ${poruka}
                                                </p>
                                            </c:if>



                                            <div class="form-group">
                                                <input type="text" name="email" id="email" tabindex="1" class="form-control" placeholder="Email" value="" required>

                                            </div>
                                            <div class="form-group">
                                                <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password" required>

                                            </div>

                                            <div class="form-group">
                                                <div class="row">
                                                    <div class="col-sm-6 col-sm-offset-3">
                                                        <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Prijavi se">
                                                    </div>

                                                </div>
                                            </div>
                                           
                                        </form>
                                        <form id="register-form" action="http://localhost:8080/NadjiIgraca/registracija" method="post" role="form" style="display: none;">


                                            <div class="form-group">
                                                <input type="text" name="ime" id="ime" tabindex="1" class="form-control" placeholder="Ime" value="" required>
                                            </div>
                                            <div class="form-group">                                 
                                                <input type="text" name="prezime" id="prezime" tabindex="1" class="form-control" placeholder="Prezime" value="" required>
                                            </div>
                                            <div class="form-group">                                 
                                                <input type="text" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="" required>
                                            </div>

                                            <div class="form-group">
                                                <input type="password" name="password" id="password" tabindex="1" class="form-control" placeholder="Password" required>
                                            </div>

                                            <div class="form-group">
                                                <input type="text" name="telefon" id="telefon" tabindex="2" class="form-control" placeholder="Telefon" required> 
                                            </div>
                                            <div class="form-group">
                                                <input type="text" name="godine" id="godine" tabindex="2" class="form-control" placeholder="Godine" required>
                                            </div>

                                            <div class="form-group">
                                                
                                                <select class="form-control form-control-sm" name="pol">
                                                        <option value="0">Muški</option>
                                                        <option value="1">Ženski</option>
                                                    </select>
                                               
                                            </div>

                                            <div class="form-group">
                                                <div class="row">
                                                    <div class="col-sm-6 col-sm-offset-3">
                                                        <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Registruj se">
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- footer -->
        <footer class="py-5" id="kontakt">
            <section class="form py-5">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <h2 class="heading">Kontakt</h2>
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
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>   
        <script>
            $(function () {

                $('#login-form-link').click(function (e) {
                    $("#login-form").show();
                    $("#register-form").hide();
                    $('#register-form-link').removeClass('active');
                    $(this).addClass('active');
                    e.preventDefault();
                });
                $('#register-form-link').click(function (e) {
                    $("#register-form").show();
                    $("#login-form").hide();
                    $('#login-form-link').removeClass('active');
                    $(this).addClass('active');
                    e.preventDefault();
                });

            });



        </script>

    </body>
</html>
