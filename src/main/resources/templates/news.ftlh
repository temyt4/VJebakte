<#import "parts/foruserpage.ftlh" as f>

<@f.page false>
    <div class="col-sm-5 col-sm-offset-5 blog-sidebar">
        <#if messages??>
            <#list messages as m>
                <div class="card w-400 my-3">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a href="/<#if m.isUser()>users<#else>communities</#if>/${m.getAuthorName()}">${m.getAuthorName()}</a>
                        </h5>
                        <p class="card-text">
                            <#if m.getText()??> ${m.getText()} </#if>
                        </p>
                        <i>${m.getCreatedDate()}</i>
                        <br>
                        <br/>
                        <#if m.getFilename()??>
                            <img src="/img/${m.getFilename()}" height="450" width="400"/>
                        </#if>
                        <br>
                    </div>
                </div>
            </#list>
        </#if>

    </div>
    <div class="col-sm-3 col-sm-offset-5 blog-sidebar">

    </div>

    </div>

</@f.page>