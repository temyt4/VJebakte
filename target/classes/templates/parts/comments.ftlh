<a class="btn btn-primary" data-toggle="collapse" href="#${m.getUni()}" role="button" aria-expanded="false" aria-controls="${m.getUni()}">
    Show Comments
</a>

<div class="collapse" id="${m.getUni()}">
    <div class="card w-100 my-3">
        <div class="card-body">
            <#if m.getComments()??>
                <ul class="list-group">
                <#list m.getComments() as c>
                    <li class="list-group-item">
                        <i>${c.getAuthorname()}    ${c.getCreatedDate()}</i>
                        <p>${c.getText()}</p>
                        <#if c.getFilename()??>
                            <img src="/img/${c.getFilename()}" width="60" height="60"/>
                        </#if>
                    </li>
                    </ul>
                </#list>
            </#if>
            <br/>
            <form method="post" action="/${m.getUni()}/addComment" enctype="multipart/form-data">
                <input type="text" class="form-control" id="text" placeholder="text" name="text"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <br/>
                <div class="custom-file col-sm-6">
                    <input type="file" name="photo" id="photo" placeholder="photo"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                </div>
                <div class="form-group">
                    <button class="btn btn-primary" type="submit">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
</div>