<#import "parts/foruserpage.ftlh" as f>

<@f.page true>

    <div class="col-sm-5 col-sm-offset-5 blog-sidebar">

        <#if isCurrentUser>
            <a href="/${currentUser.getUsername()}/albums/createNew" class="btn btn-primary">Новый альбом</a>
        </#if>

        <#list albums as a>
            <div class="card w-60 my-3">
                <div class="card-body">
                    <a href="/${currentUser.getUsername()}/albums/${a.getName()}" class="card-title">${a.getName()}</a>
                    <img src="/img/${a.getLastPhoto()}" width="400" height="400">
                </div>
            </div>
        </#list>

    </div>


    </div>
</@f.page>