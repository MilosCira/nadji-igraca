<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/custom.css">
        <title>nadjiigraca.com</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <c:if test="${email!=null}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Profil
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="http://localhost:8080/NadjiIgraca/logout">Izloguj se</a>
                    </div>
                </li>
            </c:if>
        </nav>
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">

                    <li>
                        <a href="http://localhost:8080/NadjiIgraca"><i class="fa fa-edit "></i>Početna</a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/NadjiIgraca/admin/prikaziDodajAdmina"><i class="fa fa-edit "></i>Dodaj Admina</a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/NadjiIgraca/admin/prikaziigraca"><i class="fa fa-edit "></i>Igrač</a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/NadjiIgraca/admin/prikazisport"><i class="fa fa-edit "></i>Sport</a>
                    </li>

                    <li>
                        <a href="http://localhost:8080/NadjiIgraca/admin/prikazidogovor"><i class="fa fa-edit "></i>Dogovor</a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/NadjiIgraca/admin/prikazidrzavu"><i class="fa fa-edit "></i>Država</a>
                    </li>

                    <li>
                        <a href="http://localhost:8080/NadjiIgraca/admin/prikazigrad"><i class="fa fa-edit "></i>Grad</a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/NadjiIgraca/admin/prikaziteren"><i class="fa fa-edit "></i>Teren</a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/NadjiIgraca/admin/prikazitip"><i class="fa fa-edit "></i>Tip</a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/NadjiIgraca/admin/prikaziocenu"><i class="fa fa-edit "></i>Ocena</a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/NadjiIgraca/admin/prikaziporuku"><i class="fa fa-edit "></i>Poruka</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div id="page-wrapper" >
            <div id="page-inner">
                <td><a href="http://localhost:8080/NadjiIgraca/admin/prikazidodajsport" class="btn btn-primary"/>Dodaj Sport</a></td>
                <div class="row">
                    <div class="col-md-12">

                    </div>
                    <table class="table table-dark" >
                        <thead>
                            <tr>
                                <th scope="col">Broj</th>
                                <th scope="col">Ime Sport</th>
                                <th scope="col">Izmeni Sport</th>
                                <th scope="col">Obrisi Sport</th>      
                            </tr>
                        </thead>
                        <c:forEach items="${sport}" var="sport">
                            <tbody>

                                <tr>
                                    <th scope="row"><c:out value="${sport.id}"/></th>
                                    <td><c:out value="${sport.ime}"/></td>
                                    <td><a href="http://localhost:8080/NadjiIgraca/admin/izmenisport?id=<c:out value='${sport.id}'/>" class="btn btn-primary"/>Izmeni Sport</td>
                                    <td><a href="http://localhost:8080/NadjiIgraca/admin/obrisisport?id=<c:out value='${sport.id}'/>" class="btn btn-primary"/>Obrisi Sport</td>
                                </tr>

                            </tbody>
                        </c:forEach>
                    </table>
                </div>              
                <hr/>

            </div>
        </div>
        <div class="footer">


            <div class="row">
                <div class="col-lg-12" id="fot">
                    &copy; Made by Miloš Ćirković
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script> 
    </body>
</html>
