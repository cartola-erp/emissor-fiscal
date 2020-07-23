package net.cartola.emissorfiscal.tributacao;

public class CalculoImpostoIcms00 extends CalculoImposto {
	
	private static final long serialVersionUID = 473206680965885837L;
	
	private CalculoImpostoDifal calcImpostoDifal = new CalculoImpostoDifal();

	public CalculoImpostoDifal getCalcImpostoDifal() {
		return calcImpostoDifal;
	}

	public void setCalcImpostoDifal(CalculoImpostoDifal calcImpostoDifal) {
		this.calcImpostoDifal = calcImpostoDifal;
	}
	
}
