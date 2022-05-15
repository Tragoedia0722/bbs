$(function () {
    $("#publishBtn").click(publish);
});

function publish() {
    $("#publishModal").modal("hide");
    var title = $("#recipient-name").val();
    var content = $("#message-text").val();

    $.post(
        "http://localhost:8080/discuss/add",
        {
            "title": title,
            "content": content
        },
        function (data) {
            $("#hintBody").text(data.msg);
            $("#hintModal").modal("show");
            setTimeout(function () {
                if (data.code === 1) {
                    window.location.reload();
                }
                $("#hintModal").modal("hide");
            }, 2000);
        }
    );


}