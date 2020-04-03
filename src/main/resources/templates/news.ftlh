<#import "parts/foruserpage.ftlh" as f>

<@f.page false>
    <div class="col-sm-5 col-sm-offset-5 blog-sidebar">
        <#if messages??>
            <#list messages as m>
                <div class="card w-400 my-3">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a href="<#if m.isUser()>users/${m.getAuthorName()}<#else>/communities/${m.getAuthorName()}</#if>">${m.getAuthorName()}</a>
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
                        <br/>

                        <a class="col align-self-right"
                           href="/<#if m.isUser()>touser<#else>tomessage</#if>/${m.getUni()}/<#if m.meLiked(currentUser)>unLike<#else>like</#if>">
                            <#if m.meLiked(currentUser)>
                                <i class="fas fa-heart"> ${m.getLikes()?size}</i>
                            <#else>
                                <i class="far fa-heart"> ${m.getLikes()?size}</i>
                            </#if>
                        </a>

                        <#include "parts/comments.ftlh"/>

                    </div>
                </div>
            </#list>
        </#if>


    </div>
    <div class="col-sm-3 col-sm-offset-5 blog-sidebar">

    </div>

    </div>

</@f.page>