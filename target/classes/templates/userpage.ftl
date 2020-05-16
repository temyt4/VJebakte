<#import "parts/foruserpage.ftl" as f>

<@f.page true>

        <div class="col-sm-3 col-sm-offset-5 blog-sidebar">
            <div class="card w-400 my-3">
                <div class="card-body">
                   <img src="/img/${user.getUser_avatar()}" width="210" height="210"/>
                </div>

                <#if isCurrentUserPage>
                    <a class="btn btn-primary " href="/users/${user.getUsername()}/edit">Редактировать</a>
                    <br/>
                <#else>
                    <form action="/users/${user.getUsername()}/<#if isFriend>delete<#else>add</#if>" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button class="btn btn-primary"
                                type="submit"><#if isFriend>Удалить<#else>Добавить</#if></button>
                    </form>

                </#if>
            </div>
        </div>

        <div class="col-sm-5 col-sm-offset-5 blog-sidebar">

            <div class="card w-400 my-3">
                    <div class="card-body">
                        <h1 class="card-title">${user.getUsername()}</h1>
                        <p class="card-text">
                            Дата рождения: ${birthdate}
                        </p>
                    </div>
            </div>

            <#if isCurrentUserPage>
                <div class="card w-400 my-3">
                    <div class="card-body">

                        <form action="/users/${user.getUsername()}" method="post">
                            <div class="form-group">
                                <label for="comment">Новое Сообщение:</label>
                                <textarea class="form-control" rows="2" id="comment" name="textMes"></textarea>
                                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            </div>
                            <button class="btn btn-primary" type="submit">Опубликовать</button>
                        </form>

                    </div>
                </div>
            </#if>

            <#if messages??>
                <#list messages as m>
                    <div class="card w-400 my-3">
                        <div class="card-body">
                            <h5 class="card-title">${user.getUsername()}</h5>
                            <i>
                                ${m.getStringTime()?string}
                            </i>
                            <br>
                            <br/>
                            <p class="card-text">
                                ${m.getText()}
                            </p>
                            <br>
                            <br/>


                            <a class="col align-self-right"
                               href="/<#if m.isUser()>touser<#else>tomessage</#if>/${m.getUni()}/<#if m.meLiked(currentUser)>unLike<#else>like</#if>/${way}">
                                <#if m.meLiked(currentUser)>
                                    <i class="fas fa-heart"> ${m.getLikes()?size}</i>
                                <#else>
                                    <i class="far fa-heart"> ${m.getLikes()?size}</i>
                                </#if>
                            </a>

                            <#include "parts/comments.ftl"/>
                        </div>
                    </div>
                </#list>
            </#if>


        </div>

    </div>

</@f.page>