<#import "parts/common.ftl" as c>
<@c.page>
    <form action="" method="post">
        <div class="form-group">
            <label class="col-sm-2 col-form-label"> User Name : </label>
            <div class="col-sm-6">
                <input type="text" id="username" name="username" class="form-control" placeholder="User Name" value="username"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 col-form-label"> Password : </label>
            <div class="col-sm-6">
                <input  type="password" id="password" name="password" class="form-control" placeholder="Password" value="password"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 col-form-label">BirthDate</label>
            <div class="col-sm-6">
                <input type="text" name="birthdate" class="form-control" placeholder="birthdate"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 col-form-label">UserAvatar</label>
            <div class="col-sm-6">
                <input type="file" name="avatar" class="form-control" placeholder="useravatar"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="email"/>
            </div>
        </div>


        <input type="hidden" name="_csrf" value="${_csrf.getToken()}"/>

        <div class="form-group">
            <div class="col-sm-20">
                <button class="btn btn-primary" type="submit">Sign In</button>
            </div>
        </div>
    </form>
</@c.page>