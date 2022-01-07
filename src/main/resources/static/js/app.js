function addListeners() {
    const chkTodasLojas = document.getElementById('chkTodasLojas');
    const chkExportarInventario = document.getElementById('chkExportarInventario');
    const cmbLojas = document.getElementById('cmbLojas');
    chkTodasLojas.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
    chkExportarInventario.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
    cmbLojas.addEventListener("change", enableOrDisableButtonsInGerarSpedIcmsIpi);
}
function enableOrDisableButtonsInGerarSpedIcmsIpi() {
    const cmbLojas = document.getElementById('cmbLojas');
    const chkTodasLojas = document.getElementById('chkTodasLojas');
    const chkExportarInventario = document.getElementById('chkExportarInventario');
    const txtInventarioSelecionado = document.getElementById('txtInventarioSelecionado');
    if (chkTodasLojas.checked) {
        cmbLojas.value = "opLoja";
        cmbLojas.disabled = true;
    }
    else {
        cmbLojas.disabled = false;
        // chkExportarInventario.disabled = true
        // btnSelecionarInventario.disabled = true;
    }
    if (!chkTodasLojas.checked && cmbLojas.value == "") {
        chkExportarInventario.disabled = true;
        chkExportarInventario.checked = false;
    }
    else {
        chkExportarInventario.disabled = false;
        txtInventarioSelecionado.value = "";
        txtInventarioSelecionado.placeholder = "Clique No botão (+) ao lado para buscar o INVENTARIO";
    }
    atualizarBtnSelecionarInventario(cmbLojas, chkTodasLojas, chkExportarInventario);
}
function atualizarBtnSelecionarInventario(cmbLojas, chkTodasLojas, chkExportarInventario) {
    const btnSelecionarInventario = document.getElementById('btnSelecionarInventario');
    if ((chkTodasLojas.checked || cmbLojas.value != "") && chkExportarInventario.checked) {
        btnSelecionarInventario.disabled = false;
        return;
    }
    btnSelecionarInventario.disabled = true;
}
