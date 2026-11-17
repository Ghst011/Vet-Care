document.addEventListener("DOMContentLoaded", function () {
    const listaMascotas = mascotas.map(m => ({
        id: m.idMascota,
        texto: m.nombre + " - dueño: " + m.cliente.nombre + " " + m.cliente.apellido
    }));

    const inputBuscar = document.getElementById("buscarMascota");
    const inputId = document.getElementById("idMascota");
    const divSugerencias = document.getElementById("sugerenciasMascota");

    inputBuscar.addEventListener("input", function () {
        const texto = this.value.toLowerCase().trim();
        inputId.value = "";

        if (texto.length === 0) {
            divSugerencias.style.display = "none";
            return;
        }

        const coincidencias = listaMascotas.filter(m => m.texto.toLowerCase().includes(texto));

        if (coincidencias.length === 0) {
            divSugerencias.innerHTML = "<div style='padding:8px; color:#888;'>Sin resultados</div>";
            divSugerencias.style.display = "block";
            return;
        }

        divSugerencias.innerHTML = coincidencias.map(m =>
            `<div class="opcion-mascota" data-id="${m.id}" style="padding:8px; cursor:pointer; border-bottom:1px solid #eee;">${m.texto}</div>`
        ).join("");
        divSugerencias.style.display = "block";

        document.querySelectorAll(".opcion-mascota").forEach(div => {
            div.addEventListener("mouseover", () => div.style.background = "#eef2f5");
            div.addEventListener("mouseout", () => div.style.background = "#fff");
            div.addEventListener("click", function () {
                inputBuscar.value = this.textContent;
                inputId.value = this.dataset.id;
                divSugerencias.style.display = "none";
            });
        });
    });

    document.addEventListener("click", function (e) {
        if (e.target !== inputBuscar) {
            divSugerencias.style.display = "none";
        }
    });
});