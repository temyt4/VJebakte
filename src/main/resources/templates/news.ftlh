<#import "parts/foruserpage.ftlh" as f>

<@f.page false>
    <div class="col-sm-5 col-sm-offset-5 blog-sidebar">
        <#if userMessage??>
            <#list userMessage as m>
                <div class="card w-400 my-3">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a href="/users/${m.getAuthorName()}">${m.getAuthorName()}</a>
                        </h5>
                        <i>${m.getCreatedDate()}</i>
                        <br>
                        <p class="card-text">
                            ${m.getText()}
                        </p>
                    </div>
                </div>
            </#list>
        </#if>
        <#if commMessage??>
            <#list commMessage as m>
                <div class="card w-400 my-3">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a href="/communities/${m.getAuthorName()}">${m.getAuthorName()}</a>
                        </h5>
                        <p class="card-text">
                            <#if m.getText()??> ${m.getText()} </#if>
                        </p>
                        <i>${m.getCreatedDate()}</i>
                        <br>
                        <br/>
                        <img src="/img/${m.getFilename()}" height="450" width="400"/>
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