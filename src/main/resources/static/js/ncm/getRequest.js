// SERÁ USADO QUANDO TENTAR FAZER UMA REQUISIÇÃO VIA GET: paracarregar a tabela de NCM no MODALA

// @see https://www.youtube.com/watch?v=Y_w9KjOrEXk&feature=youtu.be&t=968
// @see https://github.com/Java-Techie-jt/spring-boot-ajax/blob/8c5033075a2061388e42953841bb4c5bc5d54e7c/src/main/resources/static/getrequest.js
// @see https://github.com/Java-Techie-jt/spring-boot-ajax/blob/master/src/main/resources/static/postrequest.js

GET : $(document).ready(
    function() {
        // GET REQUEST
        $('button[name="btnPesquisarModalNcm"]').click(function(event){
            event.preventDefault();
            var numeroNcm = $("#txtNumeroNcmModal").val();
            ajaxGet(numeroNcm);
        });

        // DO GET
        function ajaxGet(numeroNcm) {
            $.ajax({
                type: "GET",
                url : "/api/v1/ncm/numero/"+numeroNcm,
                success : function(result) {
                    console.log("NCMS buscados",result.data);
                    montaTabela(result.data)
                    addSpanMsgSucesso();
                },
                error: function(evt) {
                    addSpanMsgErro();
                    console.log("ERROR : ", evt);
                }
            })
        }

    }
)

 // Function para CRIAR tabela no DOM com os DADOS carregados
 function montaTabela(listNcm) {
    console.log("Entrei na function montaTabela()");

    let btnAdicionarInicio = "<button type='button' class='btn btn-outline-primary ajusta-botao-tbl' data-dismiss='modal' onclick='addNcmToForm(this)' ";
    let btnAdicionarFim = "<span data-feather='plus'>Adicionar ncm</span>" +"</button>";
    // APAGANDO AS LINHAS ANTERIORES
    $("#tbNcmModal").empty(); 
    for (var i = 0; listNcm.length > i; i++) {
        let id = listNcm[i].id;
        let textValue= `${listNcm[i].numero} - Ex: ${listNcm[i].excecao} - ${listNcm[i].descricao}`;
        $("#tbNcmModal").append('<tr>'
            + '<td>' +listNcm[i].numero  +'</td>'
            + '<td>' +listNcm[i].excecao  +'</td>'
            + '<td>' +listNcm[i].descricao  +'</td>'
            + '<td>' +`${btnAdicionarInicio}  id="${id}" value="${textValue}" > ${btnAdicionarFim}`
                // + "<input type='hidden'  value=" + id +"/>"
            + '</td>'
        + '</tr>');
        console.log(listNcm[i].numero)
    }

}

/* PEGANDO ALGUMAS PROPRIEDADES DO BOTÃO, para ADICIONAR o NCM NO FORM
*  value = É o texto formatado para ser exibido no textBox (placeholder do txt)
*  idNcm = É o mesmo id do Banco de dados (PK), coloco no value do txt
* Essa função é CHAMADA, nos botões CRIADOS pela FUNCTION : montaTabela();
*/
function addNcmToForm(btnAdicionarNcmModal) {
        let text = btnAdicionarNcmModal.value;
        let idNcm = btnAdicionarNcmModal.id;
        console.log(`Passando os valores: (id) = ${idNcm} | (descricao) = ${text} , para o FORM`);

        const txtNcmId = document.getElementById("txtNcmId");
        const txtNcmDescricao = document.getElementById("txtNcmDescricao");

        txtNcmId.value = idNcm;
        txtNcmDescricao.value = text;
}

function addSpanMsgSucesso() {
    $("#spanMensagemErroModal").removeClass("alert alert-danger alert-dismissible").empty();
    $("#spanMensagemSucessoModal").addClass("alert alert-success alert-dismissible").html("<span> Ncm buscado com sucesso </span>");
}


function addSpanMsgErro() {
    $("#spanMensagemSucessoModal").removeClass("alert alert-success alert-dismissible").empty();
    $("#spanMensagemErroModal").addClass("alert alert-danger alert-dismissible").html("<span> Erro ao tentar buscar o NCM com o número informado  </span>");
}