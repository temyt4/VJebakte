<#import "parts/foruserpage.ftlh" as f>

<@f.page true>

    <div class="col-sm-3 col-sm-offset-5 blog-sidebar">
        <form method="post" action="/communities/${comm.getName()}/edit" enctype="multipart/form-data">
            <div class="form-group">
                <label for="username">Edit Comm Name</label>
                <input type="text" class="form-control" id="name" placeholder="name" name="commname"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </div>

            <div class="form-group">
                <div class="custom-file col-sm-6">
                    <input type="file" name="avatar" id="avatar" placeholder="avatar"/>
                </div>
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