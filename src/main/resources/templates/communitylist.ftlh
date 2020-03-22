<#import "parts/foruserpage.ftlh" as f>

<@f.page false>

    <div class="col-sm-5 col-sm-offset-5 blog-sidebar">
        <#if communities??>
            <#list communities as com>
                <div>
                    <a href="/communities/${com.getName()}">${com.getName()}</a>
                </div>
            </#list>
        </#if>
    </div>

    <div class="col-sm-3 col-sm-offset-5 blog-sidebar">

    </div>

    </div>
</@f.page>