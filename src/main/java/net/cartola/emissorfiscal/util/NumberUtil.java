//package net.cartola.emissorfiscal.util;
//
//import java.math.BigDecimal;
//import java.text.NumberFormat;
//import java.util.Locale;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//
///**
// * 14/02/2010 14:08:48
// * @author Murilo
// */
//public class NumberUtil {
//	
//	private static final Logger LOG = Logger.getLogger(NumberUtil.class.getName());
//
//    private static final NumberFormat DECIMAL_BANCO = NumberFormat.getNumberInstance(Locale.US);
//    private static final NumberFormat DECIMAL = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
//
//    static {
//        int produtoQuantidadePrecisao = Integer.parseInt(System.getProperty("decimal.precisao", "2"));
//        DECIMAL_BANCO.setMaximumFractionDigits(produtoQuantidadePrecisao);
//        DECIMAL_BANCO.setMinimumFractionDigits(produtoQuantidadePrecisao);
//        DECIMAL_BANCO.setGroupingUsed(false);
//
//        DECIMAL.setMaximumFractionDigits(produtoQuantidadePrecisao);
//        DECIMAL.setMinimumFractionDigits(produtoQuantidadePrecisao);
//        DECIMAL.setGroupingUsed(false);
//    }
//
//    public static int soma(int [] vetor){
//        int soma = 0;
//        for(int posicao=0;posicao<vetor.length;posicao++){
//            soma += vetor[posicao];
//        }
//        return soma;
//    }
//
//    public static String decimalBanco(double valor){
//        return DECIMAL_BANCO.format(valor);
//    }
//    
//    public static String decimalBanco(BigDecimal valor){
//        if (valor == null) {
//            valor = BigDecimal.ZERO;
//        }
//        return DECIMAL_BANCO.format(valor);
//    }
//    
//    public static String decimalBanco(Double valor){
//        if (valor == null) {
//            valor = 0d;
//        }
//        return DECIMAL_BANCO.format(valor);
//    }
//    
//    public static String decimalBanco(Float valor){
//        if (valor == null) {
//            valor = 0f;
//        }
//        return DECIMAL_BANCO.format(valor);
//    }
//    
//    public static String decimalBanco(double valor, int casas) {
//        return decimalBanco((Double) valor, casas);
//    }
//
//    public static String decimalBanco(float valor, int casas) {
//        return decimalBanco((Float) valor, casas);
//    }
//    
//    public static String decimalBanco(Number valor, int casas){
//        if (valor == null) {
//            valor = 0d;
//        }
//        NumberFormat decimalLocal = NumberFormat.getNumberInstance(Locale.US);
//        decimalLocal.setMaximumFractionDigits(casas);
//        decimalLocal.setMinimumFractionDigits(casas);
//        decimalLocal.setGroupingUsed(false);
//        return decimalLocal.format(valor);
//    }
//    
//    public static String decimal(BigDecimal valor) {
//        if(valor==null) {
//            return "";
//        } else {
//            return decimal(valor.doubleValue(), false);
//        }
//    }
//
//    public static String decimal(double valor) {
//        return decimal(valor, false);
//    }
//    
//    public static String decimal(Double valor) {
//        return decimal(valor, false);
//    }
//    
//    public static String decimal(Double valor, boolean separadorDeGrupo) {
//        if (valor == null) {
//            valor = 0d;
//        }
//        return decimal(valor, 2, separadorDeGrupo);
//    }
//    
//    public static String decimal(Double valor, int casas) {
//        return decimal(valor, casas, false);
//    }
//    
//    public static String decimal(Double valor, int casas, boolean separadorDeGrupo) {
//        if (valor == null) {
//            valor = 0d;
//        }
//        NumberFormat decimalLocal = NumberFormat.getNumberInstance();
//        decimalLocal.setMaximumFractionDigits(casas);
//        decimalLocal.setMinimumFractionDigits(casas);
//        decimalLocal.setGroupingUsed(separadorDeGrupo);
//        return decimalLocal.format(valor);
//    }
//    
//    public static BigDecimal getBigDecimal(String text) {
//        if(text==null || "".equals(text)) {
//            return BigDecimal.ZERO;
//        } else {
//            text = text.replace(",", ".");
//            BigDecimal valor = BigDecimal.ZERO;
//            try {
//                valor = BigDecimal.valueOf(DECIMAL_BANCO.parse(text).doubleValue());
//            } catch(NumberFormatException ex) {
//                ex.printStackTrace(System.err);
//            } finally {
//                return valor;
//            }
//        }
//    }
//
//    public static double getDouble(String text) {
//        if(text==null || "".equals(text)) {
//            return 0d;
//        } else {
//            text = text.replace(",", ".");
//            double valor = 0d;
//            try {
//                valor = DECIMAL_BANCO.parse(text).doubleValue();
//            } catch(NumberFormatException ex) {
//                ex.printStackTrace(System.err);
//            } finally {
//                return valor;
//            }
//        }
//    }
//
//    public static int getIntegerNullSafe(String string) {
//        if(string==null || "".equals(string.trim())) {
//            return 0;
//        }
//        int valor = 0;
//        try {
//            valor = Integer.parseInt(StringUtil.somenteNumeros(string));
//        } catch(Exception ex) {
//            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Erro de formatacao", ex);
//        }
//        return valor;
//    }
//
//    public static long getLongNullSafe(String string) {
//        if(string==null || "".equals(string.trim())) {
//            return 0;
//        }
//        long valor = 0;
//        try {
//            valor = Long.parseLong(StringUtil.somenteNumeros(string));
//        } catch(Exception ex) {
//            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Erro de formatacao", ex);
//        }
//        return valor;
//    }
//    
//     public static String getNullSafeForUI(Number number) {
//        if (number == null) {
//            return "";
//        }
//        
//        if(number instanceof Float || number instanceof Double) {
//            return decimal(number.doubleValue());
//        } else {
//            return Long.toString(number.longValue());
//        }
//    }
//     
//    public static int moduloDe10(String numeros) {
//        return moduloDe10(numeros, 2, true);
//    } 
//    
//    public static int moduloDe10(String numeros, int multiplicadorInicial, boolean somaMultiplicacaoMaior10) {
//        int multiplicador = multiplicadorInicial;
//        int soma_numeros = 0;
//
//        for (int i = numeros.length(); i > 0; i--) {
//            int multiplicado = Integer.parseInt(numeros.substring(i - 1, i));
//            int multiplicacao = multiplicado * multiplicador;
//
//            if (multiplicacao >= 10 && somaMultiplicacaoMaior10) {
//                multiplicacao = 1 + (multiplicacao - 10);
//            }
//            soma_numeros += multiplicacao;
////            Ambiente.debug("moduloDe10() :: multiplicador = " + multiplicador + " :: multiplicado :: " + multiplicado + " :: multiplicacao :: " + multiplicacao + " :: soma :: " + soma_numeros);
//
//            multiplicador = (multiplicador % 2) + 1;
//        }
//        int dac = 10 - (soma_numeros % 10);
//
//        if (dac == 10) {
//            dac = 0;
//        }
//        return dac;
//    }
//
//    public static int moduloDe11(String numeros) {
//        int multiplicador = 4;
//        int multiplicacao = 0;
//        int soma_numeros = 0;
//
//        for (int i = 0; i < numeros.length(); i++) {
//            int multiplicado = Integer.parseInt(numeros.substring(i, i + 1));
//            multiplicacao = multiplicado * multiplicador;
//
//            soma_numeros = soma_numeros + multiplicacao;
////            Ambiente.debug("moduloDe11() :: multiplicador = " + multiplicador + " :: multiplicado :: " + multiplicado + " :: multiplicacao :: " + multiplicacao + " :: soma :: " + soma_numeros);
//
//            multiplicador = ((multiplicador + 5) % 8) + 2;
//        }
//
//        int dac = 11 - (soma_numeros % 11);
//
//        if (dac == 0 || dac == 1 || dac > 9) {
//            dac = 1;
//        }
//        return dac;
//    }
//
//}
