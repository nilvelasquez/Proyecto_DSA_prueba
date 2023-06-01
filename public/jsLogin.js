function redirigirAIndexL() {
    if (window.location.pathname !== "/Index.html") {
        window.location.href = "Index.html";
    }
}
function redirigirATiendaL() {
    if (window.location.pathname !== "/Tienda.html") {
        window.location.href = "Tienda.html";
    }
}
function logear(){
    var correo = $('#usuario').val();
    var password = $('#contrasena').val();
    $.ajax({
        contentType: "application/json",
        type: 'POST',
        url: '/dsaApp/game/login',
        data: JSON.stringify({ "correo": correo, "password": password }),
        dataType: 'json',
        success: function(result){
            localStorage.setItem('token', result.token);
            redirigirATiendaL()
        },
        error: function(error){
            if (correo == "" || password == "")
                alert("Te has dejado algo en blaco, compruebalo de nuevo!");
            else{
                alert("Correo o contraseña incorrecta, prueba de nuevo!");
            }
        }
    });
}

