<#include "secur.ftlh">

<link rel="stylesheet" href="http://getbootstrap.com/dist/css/bootstrap.min.css">
<nav class="navbar navbar-expand-sm bg-primary navbar-dark">
    <!-- Контейнер (определяет ширину компонента Navbar) -->
    <div class="container">
        <!-- Бренд и кнопка «Гамбургер» -->
        <a class="navbar-brand" href="#">VJebakte</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbar-example" aria-controls="navbar-example"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <!-- Основная часть меню (может содержать ссылки, формы и другие элементы) -->
        <div class="collapse navbar-collapse" id="navbar-example">
            <!-- Этот блок расположен слева -->
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">
                        Первая ссылка <span class="sr-only">(текущая)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Вторая ссылка</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
                       role="button" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">
                        Выпадающее меню
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="#">Третья ссылка</a>
                        <a class="dropdown-item" href="#">Четвертая ссылка</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Пятая ссылка</a>
                    </div>
                </li>
            </ul>
            <!-- Этот блок расположен справа -->
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="search" placeholder="Search"
                       aria-label="Search">
                <button class="btn btn-outline-info my-2 my-sm-0" type="submit">Search</button>
            </form>

            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <#if know>
                    <button class="btn btn-primary" type="submit">Sign Out</button>
                </#if>
            </form>
        </div>
    </div>
</nav>
