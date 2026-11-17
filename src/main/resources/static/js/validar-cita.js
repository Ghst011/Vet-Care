function validarYGuardarCita() {
    const idMascota = document.getElementById("idMascota").value;
    if (!idMascota) {
        Swal.fire({ title: 'Mascota no encontrada', text: 'Selecciona una mascota válida de la lista de sugerencias.', icon: 'warning', confirmButtonColor: '#2f6b4f', confirmButtonText: 'Entendido' });
        return;
    }

    const idVeterinario = document.querySelector('select[name="idVeterinario"]').value;
    if (!idVeterinario) {
        Swal.fire({ title: 'Falta el veterinario', text: 'Selecciona un veterinario para la cita.', icon: 'warning', confirmButtonColor: '#2f6b4f', confirmButtonText: 'Entendido' });
        return;
    }

    const fecha = document.querySelector('input[name="fecha"]').value;
    if (!fecha) {
        Swal.fire({ title: 'Falta la fecha', text: 'Selecciona la fecha de la cita.', icon: 'warning', confirmButtonColor: '#2f6b4f', confirmButtonText: 'Entendido' });
        return;
    }

    const hora = document.querySelector('input[name="hora"]').value;
    if (!hora) {
        Swal.fire({ title: 'Falta la hora', text: 'Selecciona la hora de la cita.', icon: 'warning', confirmButtonColor: '#2f6b4f', confirmButtonText: 'Entendido' });
        return;
    }

    const form = document.getElementById('formCita');

    // Limpia campos ocultos de un intento anterior
    form.querySelectorAll('input[name="idsServicio"], input[name="cantidades"]').forEach(el => el.remove());

    const checkboxesMarcados = document.querySelectorAll('.servicio-checkbox:checked');

    if (checkboxesMarcados.length === 0) {
        Swal.fire({ title: 'Falta seleccionar servicios', text: 'Marca al menos un servicio para la cita.', icon: 'warning', confirmButtonColor: '#2f6b4f', confirmButtonText: 'Entendido' });
        return;
    }

    checkboxesMarcados.forEach(checkbox => {
        const hiddenServicio = document.createElement('input');
        hiddenServicio.type = 'hidden';
        hiddenServicio.name = 'idsServicio';
        hiddenServicio.value = checkbox.value;
        form.appendChild(hiddenServicio);

        const hiddenCantidad = document.createElement('input');
        hiddenCantidad.type = 'hidden';
        hiddenCantidad.name = 'cantidades';
        hiddenCantidad.value = 1; // siempre 1, ya no se pregunta cantidad
        form.appendChild(hiddenCantidad);
    });

    Swal.fire({
        title: '¿Registrar esta cita?',
        text: 'Se guardará la cita con los datos y servicios indicados.',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#198754',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Aceptar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            form.submit();
        }
    });
}