window.addEventListener('load', function() {
    const formulario = document.querySelector('#add_new_paciente');

    formulario.addEventListener('submit', function(event) {
        const formData = {
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            dni: document.querySelector('#dni').value,
            fechaIngreso: document.querySelector('#fechaIngreso').value,
            domicilio: {
                calle: document.querySelector('#calle').value,
                numero: document.querySelector('#numero').value,
                localidad: document.querySelector('#localidad').value,
                provincia: document.querySelector('#provincia').value
            }
        }

        const url = '/pacientes';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {

                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                 '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                 '<strong></strong> Paciente agregado </div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();
        }).catch(error => {
              let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                               '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                               '<strong> Error intente nuevamente</strong> </div>'

              document.querySelector('#response').innerHTML = errorAlert;
              document.querySelector('#response').style.display = "block";

              resetUploadForm();});
    });

    function resetUploadForm(){
        document.querySelector('#nombre').value = "";
        document.querySelector('#apellido').value = "";
        document.querySelector('#dni').value = "";
        document.querySelector('#fechaIngreso').value = "";
        document.querySelector('#calle').value ="";
        document.querySelector('#numero').value = "";
        document.querySelector('#localidad').value = "";
        document.querySelector('#provincia').value = "";
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "html/pacienteList.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});


/* $(document).ready(function() {
    $("#add_new_paciente").submit(function(evt) {
        evt.preventDefault();
        let formData = {
            nombre : $("#nombre").val(),
            apellido :  $("#apellido").val(),
            matricula: $("#matricula").val(),
        }
        $.ajax({
            url: '/pacientes',
            type: 'POST',
            contentType : "application/json",
            data: JSON.stringify(formData),
            dataType : 'json',
            async: false,
            cache: false,
            success: function (response) {
                let odontologo = response
               console.log(response)
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong></strong> Paciente agregado </div>'
                $("#response").append(successAlert);
                $("#response").css({"display": "block"});
                resetUploadForm();
            },
            error: function (response) {
                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong> Error intente nuevamente</strong> </div>'
                $("#response").append(errorAlert);
                $("#response").css({"display": "block"});
                resetUploadForm();
            }
        });
    });
    function resetUploadForm(){
        $("#nombre").val("");
        $("#apellido").val("");
        $("#matricula").val("");
    }
    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            $(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/paciente.html") {
            $(".nav .nav-item a:last").addClass("active");
        }
    })();
}); */