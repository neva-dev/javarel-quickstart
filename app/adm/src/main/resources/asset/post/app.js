$(function () {
    Handlebars.registerHelper('formatDate', function(date) {
        return moment(date).format('MMMM Do YYYY, h:mm:ss');
    });

    var $list = $('.post-list');
    var $form = $('.post-form');
    var listTemplate = Handlebars.compile($("script[type='text/x-handlebars-template']", $list).html());

    function render() {
        $.ajax({
            method: 'GET',
            url: $form.data('listUrl'),
            success: function (posts) {
                $list.html(listTemplate({posts: posts}));
            },
            error: function () {
                alert('Cannot render posts properly due to internal server error.');
            }
        });
    }

    $form.on('submit', function () {
        $.ajax({
            url: $form.data('createUrl'),
            data: new FormData(this),
            contentType: false,
            cache: false,
            type: 'POST',
            processData: false,
            success: function () {
                render();
                $form.reset();
            },
            error: function () {
                alert('Cannot add post due to internal server error.');
            }
        });

        return false;
    });

    $list.delegate('.post-delete', 'click', function () {
        var $link = $(this);

        if (confirm("Are you sure want to delete this post?")) {
            $.ajax({
                method: 'DELETE',
                url: $form.data('deleteUrl').split('<id>').join($link.data('id')),
                success: function () {
                    render();
                },
                error: function () {
                    alert('Cannot delete post due to internal server error.');
                }
            });
        }

        return false;
    });

    render();
});