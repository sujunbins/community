//提交回复功能
function post_comment() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment(questionId, 1, content);


}

function comment(targetId, type, content) {

    if (!content) {
        alert("不能回复空内容");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                $("comment_section").hide();
                window.location.reload();
            } else {
                var isAccepted = confirm(response.message);
                if (isAccepted) {
                    window.open("https://github.com/login/oauth/authorize?client_id=340ab4e63e9750ac9a40&redirect_uri=http://localhost:8887/callback?scope=user&state=1");
                    window.localStorage.setItem("closable", true);
                } else {
                    alert(response.message);
                }

            }

        },
        datatype: "json"

    });
}

function comment1(e) {
    var id = e.getAttribute("data-id");
    var content = $("#input-" + id).val();

    comment(id, 2, content);

}

//展开二级评论功能
function collapseComment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");

    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");

            //标记二级评论状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {

                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left ",

                    }).append($("<img/>", {
                        "class": "media-object img-rounded index_image",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content

                    })).append($("<div/>", {
                        "class": "menu_comment"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));
                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",

                    }).append(mediaElement);
                    ;

                    subCommentContainer.prepend(commentElement);
                });

                //展开二级评论
                comments.addClass("in");

                //标记二级评论状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            })
        }

    }

}

//点赞功能
function post_like(e) {


    like_count(e)
}

function like_count(e) {
    var id = e.getAttribute("data-like");
    var comment = $("#like-" + id);

    $.ajax({
        url: "/comment/like",
        contentType: 'application/json',
        data: {
            id: id
        },
        success: function (response) {
            if (response.code == 200) {
                comment.text(parseInt(comment.text()) + 1);

            } else {
                alert(response.message);
            }


        }

    })


}
function showSelectTag() {
    $("#select-tag").show();

}
function selectTag(value) {
    var previous = $("#tag").val();
    if(previous.indexOf()==-1)
    {
        if(previous)
        {
            $("#tag").val(previous+'，'+value);
        }else
        {
            $("#tag").val(value);
        }
    }
}