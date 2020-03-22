<#import "parts/foruserpage.ftlh" as f>

<@f.page true>

        <div class="col-sm-3 col-sm-offset-5 blog-sidebar">
            <div class="card w-400 my-3">
                <div class="card-body">
                   <img src="/img/${user.getUser_avatar()}" width="210" height="210"/>
                </div>

                <#if isCurrentUserPage>
                        <a class="btn btn-primary " href="/users/${user.getUsername()}/edit">Редактировать</a>
                    <br/>
                </#if>
            </div>
        </div>

        <div class="col-sm-5 col-sm-offset-5 blog-sidebar">

            <div class="card w-400 my-3">
                    <div class="card-body">
                        <h1 class="card-title">${user.getUsername()}</h1>
                        <p class="card-text">
                            Дата рождения: ${user.getBirthdate()}
                        </p>
                    </div>
            </div>

            <#if isCurrentUserPage>
                <div class="card w-400 my-3">
                    <div class="card-body">

                        <form action="/users/${user.getUsername()}" method="post">
                            <div class="form-group">
                                <label for="comment">Новое Сообщение:</label>
                                <textarea class="form-control" rows="2" id="comment" name="text"></textarea>
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
                                ${m.createdDate}
                            </i>
                            <br>
                            <br/>
                            <p class="card-text">
                                ${m.getText()}
                            </p>
                        </div>
                    </div>
                </#list>
            </#if>


        </div>

    </div>

</@f.page>