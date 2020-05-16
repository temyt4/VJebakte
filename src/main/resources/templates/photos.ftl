<#import "parts/foruserpage.ftl" as f>

<@f.page true>
    <div class="col-sm-5 col-sm-offset-5 blog-sidebar">

        <#if isCurrentUser>
            <form method="post" action="/${currentUser.getUsername()}/albums/${albumname}/addPhoto"
                  enctype="multipart/form-data">
                <div class="form-group">
                    <div class="custom-file col-sm-6">
                        <input type="file" name="photo" id="photo" placeholder="photo"/>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </div>
                <div class="form-group">
                    <button class="btn btn-primary" type="submit">Сохранить</button>
                </div>
            </form>
        </#if>
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <#list photos as p>
                    <li data-target="#carouselExampleIndicators" data-slide-to="${photos?seq_index_of(p)}"
                        class="<#if photos?seq_index_of(p)==0>active</#if>"></li>
                </#list>
            </ol>

                    <div class="carousel-inner">
                        <#list photos as m>
                                <div class="carousel-item <#if photos?seq_index_of(m)==0>active</#if>">
                                    <div class="card w-100 my-3">
                                        <div class="card-body">
                                            <img class="d-block w-100" src="/img/${m.getName()}" width="450" height="450"/>
                                            <br>
                                            <br/>
                                            <a class="btn btn-primary"
                                               href="/photo/${m.getUni()}/<#if m.meLiked(currentUser)>unLike<#else>like</#if>">
                                                <#if m.meLiked(currentUser)>
                                                    <i class="fas fa-heart"> ${m.getLikes()?size}</i>
                                                <#else>
                                                    <i class="far fa-heart"> ${m.getLikes()?size}</i>
                                                </#if>
                                            </a>

                                            <br>
                                            <br/>

                                    <#include "parts/comments.ftl"/>

                                        </div>
                                    </div>
                    </div>
                    </#list>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>

    </div>


    </div>
</@f.page>