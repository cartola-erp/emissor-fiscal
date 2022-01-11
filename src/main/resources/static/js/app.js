function addListeners() {
    const chkExportarSpedTodasLojas = document.getElementById('chkExportarSpedTodasLojas');
    const chkExportarInventario = document.getElementById('chkExportarInventario');
    const cmbLojas = document.getElementById('cmbLojas');
    const btnSelecionarInventario = document.getElementById('btnSelecionarInventario');
    chkExportarSpedTodasLojas.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
    chkExportarInventario.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
    cmbLojas.addEventListener("change", enableOrDisableButtonsInGerarSpedIcmsIpi);
    btnSelecionarInventario.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
}
function enableOrDisableButtonsInGerarSpedIcmsIpi() {
    const cmbLojas = document.getElementById('cmbLojas');
    const chkExportarSpedTodasLojas = document.getElementById('chkExportarSpedTodasLojas');
    const chkExportarInventario = document.getElementById('chkExportarInventario');
    const txtInventarioSelecionado = document.getElementById('txtInventarioSelecionado');
    if (chkExportarSpedTodasLojas.checked) {
        cmbLojas.value = "opLoja";
        cmbLojas.disabled = true;
    }
    else {
        cmbLojas.disabled = false;
    }
    if (!chkExportarSpedTodasLojas.checked && cmbLojas.value == "") {
        chkExportarInventario.disabled = true;
        chkExportarInventario.checked = false;
    }
    else {
        chkExportarInventario.disabled = false;
        txtInventarioSelecionado.value = "";
        txtInventarioSelecionado.placeholder = "Clique No botão (+) ao lado para buscar o INVENTARIO";
    }
    atualizarBtnSelecionarInventario(cmbLojas, chkExportarSpedTodasLojas, chkExportarInventario);
}
function atualizarBtnSelecionarInventario(cmbLojas, chkExportarSpedTodasLojas, chkExportarInventario) {
    const btnSelecionarInventario = document.getElementById('btnSelecionarInventario');
    // const txtInventarioSelecionado = document.getElementById('btnSelecionarInventario') as HTMLInputElement;
    if (chkExportarInventario.checked && chkExportarSpedTodasLojas.checked) {
        btnSelecionarInventario.setAttribute('data-target', "#modal-add-inventario-periodo");
    }
    else {
        btnSelecionarInventario.setAttribute('data-target', "#modal-add-inventario");
    }
    if ((chkExportarSpedTodasLojas.checked || cmbLojas.value != "") && chkExportarInventario.checked) {
        btnSelecionarInventario.disabled = false;
        return;
    }
    btnSelecionarInventario.disabled = true;
}
