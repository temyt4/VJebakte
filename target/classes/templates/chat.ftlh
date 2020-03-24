<#import "parts/foruserpage.ftlh" as f>

<@f.page true>
    <div class="col-sm-5 col-sm-offset-5 blog-sidebar">

        <div class="card w-100 my-3">
            <div class="card-body">

                <#list messages as m>
                    <i>${m.getAuthorName()}  ${m.getCreatedDate()}</i>

                    <p class="card-text">${m.getText()}</p>

                    <#if m.getFilename()??>
                        <img src="/img/${m.getFilename()}" width="400" height="400"/>
                    </#if>

                </#list>

            </div>
        </div>

    <div class="card w-500 my-3">
        <div class="card-body">
            <form action="" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <textarea class="form-control" rows="1" id="comment" name="text"></textarea>
                    <br/>
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

    </div>





    <div class="col-sm-3 col-sm-offset-5 blog-sidebar">

    </div>

    </div>

</@f.page>