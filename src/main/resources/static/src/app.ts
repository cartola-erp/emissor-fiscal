// onclick="enableOrDisableButtonsInGerarSpedIcmsIpi()"
 // ds

function addListeners() {
    const chkTodasLojas = document.getElementById('chkTodasLojas') as HTMLInputElement;
    const chkExportarInventario = document.getElementById('chkExportarInventario') as HTMLInputElement;
    const cmbLojas = document.getElementById('cmbLojas') as HTMLSelectElement;
    
    chkTodasLojas.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
    chkExportarInventario.addEventListener("click", enableOrDisableButtonsInGerarSpedIcmsIpi);
    cmbLojas.addEventListener("change", enableOrDisableButtonsInGerarSpedIcmsIpi);
}

function enableOrDisableButtonsInGerarSpedIcmsIpi() {
    // var chkTodasLojas = document.getElementById('chkTodasLojas');
    const chkTodasLojas = document.getElementById('chkTodasLojas') as HTMLInputElement;

    // var chkExportarInventario = document.getElementById('chkExportarInventario');
    const chkExportarInventario = document.getElementById('chkExportarInventario') as HTMLInputElement;
    const btnSelecionarInventario = document.getElementById('btnSelecionarInventario') as HTMLButtonElement;

    const cmbLojas = document.getElementById('cmbLojas') as HTMLSelectElement;
    
 
    if (chkTodasLojas.checked){ 
        cmbLojas.value = "opLoja";
        // cmbLojas.text = "Todas as lojas serão exportadas"
        cmbLojas.disabled = true;
        // chkExportarInventario.disabled = false;
        btnSelecionarInventario.disabled = false;
    } else {
        cmbLojas.disabled = false
        // chkExportarInventario.disabled = true
        btnSelecionarInventario.disabled = true;
    }

    if (!chkTodasLojas.checked && cmbLojas.value == "") {
        chkExportarInventario.disabled = true
    } else {
        chkExportarInventario.disabled = false;
    }
    
    if (chkExportarInventario.checked && chkExportarInventario.click) {

    }
 
}


