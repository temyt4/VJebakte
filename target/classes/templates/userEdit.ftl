<#import "parts/foruserpage.ftl" as f>

<@f.page true>

    <div class="col-sm-3 col-sm-offset-5 blog-sidebar">
        <form method="post" action="/users/${user.getUsername()}/edit" enctype="multipart/form-data">
            <div class="form-group">
                <label for="username">Edit Username</label>
                <input type="text" class="form-control" id="username" placeholder="username" name="username"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
            </div>
            <div class="form-group">
                <label for="password">Edit Password</label>
                <input type="password" name="password" class="form-control" id="password" placeholder="password"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
            </div>
            <div class="form-group">
                <label for="birthdate">Edit BirthDate</label>
                <input type="text" class="form-control" name="birthdate" id="birthdate" placeholder="birthdate"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
            </div>
            <div class="form-group">
                <div class="custom-file col-sm-6">
                    <input type="file" name="avatar" id="avatar" placeholder="avatar"/>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
            </div>
            <div class="form-group">
                <label for="email">Edit Email</label>
                <input type="email" name="email" class="form-control" id="email" placeholder="email"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
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