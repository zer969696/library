$(document).ready(function auth() {
    let login = prompt("LOGIN");
    let password = prompt("PASSWORD");

    let jsonData = {
        username: login,
        password: password
    };

    if (login != null && login !== "" && password != null && password !=="") {
        $.ajax({
            url: '/library' + '/auth',
            dataType: "json",
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            data: JSON.stringify(jsonData),
            type: "post",
            success: function(data, textStatus, xhr) {
                if (xhr.status === 200) {
                    window.location.href="/";
                } else {
                    alert("Wrong password or login");
                    auth();
                }
            },
            complete: function(xhr, textStatus) {
                if (xhr.status === 200) {
                    window.location.href="/";
                } else {
                    alert("Wrong password or login");
                    auth();
                }
            }
        });
    }
});

