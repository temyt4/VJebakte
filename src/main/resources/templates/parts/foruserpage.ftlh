<#include "secur.ftlh">
<#import "common.ftlh" as c>

<#macro page userpage>
    <@c.page>
        <#if user??>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3 col-sm-offset-1 blog-sidebar">
                        <ol class="list-unstyled">
                            <li>
                                <a href="/users/${currentUser.getUsername()}">Моя Страница</a>
                            </li>
                            <li>
                                <a href="/news">Новости</a>
                            </li>
                            <li>
                                <a href="#">Сообщения</a>
                            </li>
                            <li>
                                <a href="/friends">Друзья</a>
                            </li>
                                <li>
                                    <a href="/communities">Группы</a>
                                </li>
                                <li>
                                    <a href="#">Фотографии</a>
                                </li>
                            </ol>
                        </div>
                          <#nested>
                    </div>
                </div>




            <#else>
                <#nested>
            </#if>
    </@c.page>
</#macro>