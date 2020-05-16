<#import "parts/foruserpage.ftl" as f>

<@f.page false>


    <div class="col-sm-5 col-sm-offset-5 blog-sidebar">
        <#if friends??>
            <#list friends as user>
                <div>
                    <a href="/users/${user}">${user}</a>
                </div>
            </#list>
        </#if>
    </div>

    <div class="col-sm-3 col-sm-offset-5 blog-sidebar">

    </div>

    </div>
</@f.page>