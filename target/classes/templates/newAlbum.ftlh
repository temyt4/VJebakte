<#import "parts/foruserpage.ftlh" as f>

<@f.page true>
    <div class="col-sm-3 col-sm-offset-5 blog-sidebar">
        <form method="post" action="/${currentUser.getUsername()}/albums/createNew" enctype="multipart/form-data">
            <div class="form-group">
                <label for="albumname">Album Name</label>
                <input type="text" class="form-control" id="albumname" placeholder="albumname" name="albumname"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </div>

            <div class="form-group">
                <button class="btn btn-primary" type="submit">Сохранить</button>
            </div>
        </form>
    </div>

    <div class="col-sm-3 col-sm-offset-5 blog-sidebar">

    </div>

    </div>
</@f.page>