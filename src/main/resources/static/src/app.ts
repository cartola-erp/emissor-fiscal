
function addListeners() {
    const chkTodasLojas = document.getElementById('chkTodasLojas') as HTMLInputElement;
    const chkExportarInventario = document.getElementById('chkExportarInventario') as HTMLInputElement;
    const cmbLojas = document.getElementById('cmbLojas') as HTMLSelectElement;
    
    chkTodasLojas.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
    chkExportarInventario.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
    cmbLojas.addEventListener("change", enableOrDisableButtonsInGerarSpedIcmsIpi);
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
        // chkExportarInventario.disabled = true
        // btnSelecionarInventario.disabled = true;
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
    if ( (chkTodasLojas.checked || cmbLojas.value != "") && chkExportarInventario.checked) {
        btnSelecionarInventario.disabled = false;
        return;
    } 
    btnSelecionarInventario.disabled = true;
}


