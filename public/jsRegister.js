function redirigirAIndexR() {
    if (window.location.pathname !== "/Index.html") {
        window.location.href = "Index.html";
    }
}
function registrar() {
    var username = $('#usuario').val();
    var password = $('#contrasena').val();
    var password2 = $('#contrasena2').val();
    var email = $('#email').val();
    if (password == password2) {

        $.ajax({
            contentType: "application/json",
            type: 'POST',
            url: '/dsaApp/game/registrarUsuario',
            data: JSON.stringify({"nombre": username, "correo": email, "password": password}),
            dataType: 'json',
            success: function (result) {
                redirigirALogin();
            },
            error: function (error) {
                console.log(error);
                if (email == null || username == null || password == null)
                    alert("Has dejado algo en blanco, miralo de nuevo!");
                else
                    alert("Usuario o contraseña ya estan siendo usados, prueba de nuevo!");
            }
        });
    } else alert("Comprueba que las dos contraseñas son iguales.");
}