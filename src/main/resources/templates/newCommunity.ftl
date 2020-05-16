<#import "parts/foruserpage.ftl" as f>

<@f.page true>
    <div class="col-sm-3 col-sm-offset-5 blog-sidebar">
        <form method="post" action="/communities/addNew">
            <div class="form-group">
                <label for="username">Comm Name</label>
                <input type="text" class="form-control" id="name" placeholder="name" name="name"/>
                <input type="hidden" name="_csrf" value="${_csrf.getToken()}"/>
            </div>

            <div class="form-group">
                <div class="custom-file col-sm-6">
                    <input type="file" name="avatar" id="avatar" placeholder="avatar"/>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.getToken()}"/>
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