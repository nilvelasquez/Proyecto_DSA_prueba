verificarSesion();
function redirigirALoginT() {
    if (window.location.pathname !== "/Login.html") {
        window.location.href = "Login.html";
    }
}
function verificarSesion() {
    var token = localStorage.getItem('token');
    if (!token) {
        redirigirALoginT();
        return;
    }
    else {
        return;
    }
}
function listadeObjetos() {
    var token = localStorage.getItem('token');
    if (!token) {
        redirigirALoginT();
        return;
    }
    $("#tcuerpo").empty();
    $.ajax({
        type: 'GET',
        url: "/dsaApp/game/listaObjetos",
        dataType: 'json',
        success: function (result) {
            for (let i = 0; i < result.length; i++) {
                console.log("i: " + i, result[i]);
                $("#tabla").append(
                    "<tr> <td>" + result[i].nombre +
                    "</td> <td>" + result[i].descripcion +
                    "</td> <td>" + result[i].precio
                );
            }
        },
        error: function (error) {
            alert("Sin datos de compra.");
            console.log(error);
        }
    });
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
                redirigirALoginT();
            },
            error: function(error) {
                console.log(error);
                localStorage.removeItem('token');
                redirigirALoginT();
            }
        });
    } else {
        localStorage.removeItem('token');
        redirigirALoginT();
    }
}
function comprarItem(item) {
    var userName = localStorage.getItem("activeUser");
    var idItem = item;
    if (confirm("Seguro que quieres comprar este item?") == true) {
        $.ajax({
            type: 'PUT',
            url: "dsaApp/item/PurchaseItem/" + idItem + "/" + userName,
            dataType: 'json',
            success: function (result) {
                localStorage.setItem("coins", result.coins);
                window.location.href = "Tienda.html";
                alert('Bought succesfully');
            },
            error: function (error) {
                alert('Purchase failed');
            },
        })
    }
}