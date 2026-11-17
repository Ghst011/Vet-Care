document.addEventListener("DOMContentLoaded", function () {
    const listaClientes = clientes.map(c => ({
        id: c.idPersona,
        texto: c.nombre + " " + c.apellido + " - DNI " + c.dni
    }));

    const inputBuscar = document.getElementById("buscarCliente");
    const inputId = document.getElementById("clienteSeleccionado");
    const divSugerencias = document.getElementById("sugerenciasCliente");

    inputBuscar.addEventListener("input", function () {
        const texto = this.value.toLowerCase().trim();
        inputId.value = "";

        if (texto.length === 0) {
            divSugerencias.style.display = "none";
            return;
        }

        const coincidencias = listaClientes.filter(c => c.texto.toLowerCase().includes(texto));

        if (coincidencias.length === 0) {
            divSugerencias.innerHTML = "<div class='list-group-item text-muted'>Sin resultados</div>";
            divSugerencias.style.display = "block";
            return;
        }

        divSugerencias.innerHTML = coincidencias.map(c =>
            `<button type="button" class="list-group-item list-group-item-action opcion-cliente" data-id="${c.id}">${c.texto}</button>`
        ).join("");
        divSugerencias.style.display = "block";

        document.querySelectorAll(".opcion-cliente").forEach(btn => {
            btn.addEventListener("click", function () {
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

function validarYGuardarMascota() {
    const idCliente = document.getElementById("clienteSeleccionado").value;

    if (!idCliente) {
        Swal.fire({
            title: 'Cliente no encontrado',
            html: 'El cliente que escribiste no existe en el sistema.<br>Primero debes registrarlo.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#2f6b4f',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Registrar cliente',
            cancelButtonText: 'Corregir búsqueda'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = "/clientes/nuevo";
            }
        });
        return;
    }

    document.getElementById("formMascota").submit();
}