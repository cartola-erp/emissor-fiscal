
function addListeners() {
    const chkTodasLojas = document.getElementById('chkTodasLojas') as HTMLInputElement;
    const chkExportarInventario = document.getElementById('chkExportarInventario') as HTMLInputElement;
    const cmbLojas = document.getElementById('cmbLojas') as HTMLSelectElement;
    const btnSelecionarInventario = document.getElementById('btnSelecionarInventario') as HTMLButtonElement;

    chkTodasLojas.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
    chkExportarInventario.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
    cmbLojas.addEventListener("change", enableOrDisableButtonsInGerarSpedIcmsIpi);
    btnSelecionarInventario.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
}

function enableOrDisableButtonsInGerarSpedIcmsIpi() {
    const cmbLojas = document.getElementById('cmbLojas') as HTMLSelectElement;
    const chkTodasLojas = document.getElementById('chkTodasLojas') as HTMLInputElement;

    const chkExportarInventario = document.getElementById('chkExportarInventario') as HTMLInputElement;
    const txtInventarioSelecionado = document.getElementById('txtInventarioSelecionado') as HTMLInputElement;

    if (chkTodasLojas.checked){ 
        cmbLojas.value = "opLoja";
        cmbLojas.disabled = true;
    } else {
        cmbLojas.disabled = false
    }

    if (!chkTodasLojas.checked && cmbLojas.value == "") {
        chkExportarInventario.disabled = true;
        chkExportarInventario.checked = false;
    } else {
        chkExportarInventario.disabled = false;
        txtInventarioSelecionado.value= "";
        txtInventarioSelecionado.placeholder = "Clique No botão (+) ao lado para buscar o INVENTARIO";
    }
    atualizarBtnSelecionarInventario(cmbLojas, chkTodasLojas, chkExportarInventario);
}

function atualizarBtnSelecionarInventario(cmbLojas : HTMLSelectElement, chkTodasLojas : HTMLInputElement, chkExportarInventario : HTMLInputElement ) {
    const btnSelecionarInventario = document.getElementById('btnSelecionarInventario') as HTMLButtonElement;
    if (chkExportarInventario.checked && chkTodasLojas.checked) {
        btnSelecionarInventario.setAttribute('data-target', "#modal-add-inventario-periodo");
    } else {
        btnSelecionarInventario.setAttribute('data-target', "#modal-add-inventario"); 
    }
    
    if ( (chkTodasLojas.checked || cmbLojas.value != "") && chkExportarInventario.checked) {
        btnSelecionarInventario.disabled = false;
        return;
    }

    btnSelecionarInventario.disabled = true;
}


