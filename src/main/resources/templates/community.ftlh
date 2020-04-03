<#import "parts/foruserpage.ftlh" as f>

<@f.page false>

    <div class="col-sm-5 col-sm-offset-5 blog-sidebar">

        <div class="card w-400 my-3">
            <div class="card-body">
                <h2>${comm.getName()}</h2>

            </div>
        </div>

        <#if isCommAdmin>

            <div class="card w-400 my-3">
                <div class="card-body">
                    <form action="/communities/${comm.getName()}" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="comment">Новое Сообщение:</label>
                            <textarea class="form-control" rows="2" id="comment" name="text"></textarea>
                            <div class="custom-file col-sm-6">
                                <input type="file" name="file" id="avatar" placeholder="file"/>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            </div>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
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
                        <h5 class="card-title">${m.getAuthorName()}</h5>
                        <p class="card-text">
                            <#if m.getText()??> ${m.getText()} </#if>
                        </p>
                        <i>${m.getCreatedDate()}</i>
                        <br>
                        <br/>
                        <#if m.getFilename()??>
                            <img src="/img/${m.getFilename()}" height="450" width="400"/>
                        </#if>
                        <br/>


                        <a class="col align-self-right"
                           href="/<#if m.isUser()>touser<#else>tomessage</#if>/${m.getUni()}/<#if m.meLiked(currentUser)>unLike<#else>like</#if>">
                            <#if m.meLiked(currentUser)>
                                <i class="fas fa-heart"> ${m.getLikes()?size}</i>
                            <#else>
                                <i class="far fa-heart"> ${m.getLikes()?size}</i>
                            </#if>
                        </a>

                        <#include "parts/comments.ftlh"/>
                    </div>
                </div>
            </#list>
        </#if>
    </div>

    <div class="col-sm-3 col-sm-offset-5 blog-sidebar">
        <div class="card w-400 my-3">
            <div class="card-body">
                <img src="/img/${comm.getAvatar()}" width="210" height="210"/>
            </div>

            <#if isCommAdmin>

                <div class="card-body">
                    <a class="btn btn-primary " href="/communities/${comm.getName()}/edit">Редактировать</a>
                </div>

            </#if>

            <div class="card-body">
                <i>Подписчиков: ${subs}</i>
            </div>

            <div class="card-body">
                <form action="/communities/${comm.getName()}/<#if isSub>unsub<#else>sub</#if>" method="post">
                    <button class="btn btn-primary" type="submit"><#if isSub>Отписаться<#else>Подписаться</#if></button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>

    </div>

</@f.page>