function redirigirALoginM() {
    if (window.location.pathname !== "/Login.html") {
        window.location.href = "Login.html";
    }
}
function cerrarSession() {
    var token = localStorage.getItem('token');
    if (token) {
        $.ajax({
            type: 'POST',
            url: '/dsaApp/game/cerrarSession',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function(result) {
                localStorage.removeItem('token');
                redirigirALoginM();
            },
            error: function(error) {
                console.log(error);
                localStorage.removeItem('token');
                redirigirALoginM();
            }
        });
    } else {
        localStorage.removeItem('token');
        redirigirALoginM();
    }
}