package br.com.winter.validation;

import br.com.winter.entity.Comercio;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class ComercioValidation {

    private final String[] ufs = {
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT",
            "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO",
            "RR", "SC", "SP", "SE", "TO"
    };

    private final String[] ddds = {
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "21", "22",
            "24", "27", "28", "31", "32", "33", "34", "35", "37", "38", "41",
            "42", "43", "44", "45", "46", "47", "48", "49", "51", "53", "54",
            "55", "61", "62", "63", "64", "65", "66", "67", "68", "69", "71",
            "73", "74", "75", "77", "79", "81", "82", "83", "84", "85", "86",
            "87", "88", "89", "91", "92", "93", "94", "95", "96", "97", "98",
            "99"
    };

    private final Map<String, String[]> dddsPorUf = new HashMap<>();

    public ComercioValidation() {
        dddsPorUf.put("AC", new String[]{"68"});
        dddsPorUf.put("AL", new String[]{"82"});
        dddsPorUf.put("AM", new String[]{"92", "97"});
        dddsPorUf.put("AP", new String[]{"96"});
        dddsPorUf.put("BA", new String[]{"71", "73", "74", "75", "77"});
        dddsPorUf.put("CE", new String[]{"85", "88"});
        dddsPorUf.put("DF", new String[]{"61"});
        dddsPorUf.put("ES", new String[]{"27", "28"});
        dddsPorUf.put("GO", new String[]{"61", "62", "64"});
        dddsPorUf.put("MA", new String[]{"98", "99"});
        dddsPorUf.put("MG", new String[]{"31", "32", "33", "34", "35", "37", "38"});
        dddsPorUf.put("MS", new String[]{"67"});
        dddsPorUf.put("MT", new String[]{"65", "66"});
        dddsPorUf.put("PA", new String[]{"91", "93", "94"});
        dddsPorUf.put("PB", new String[]{"83"});
        dddsPorUf.put("PE", new String[]{"81", "87"});
        dddsPorUf.put("PI", new String[]{"86", "89"});
        dddsPorUf.put("PR", new String[]{"41", "42", "43", "44", "45", "46"});
        dddsPorUf.put("RJ", new String[]{"21", "22", "24"});
        dddsPorUf.put("RN", new String[]{"84"});
        dddsPorUf.put("RO", new String[]{"69"});
        dddsPorUf.put("RR", new String[]{"95"});
        dddsPorUf.put("RS", new String[]{"51", "53", "54", "55"});
        dddsPorUf.put("SC", new String[]{"47", "48", "49"});
        dddsPorUf.put("SE", new String[]{"79"});
        dddsPorUf.put("SP", new String[]{"11", "12", "13", "14", "15", "16", "17", "18", "19"});
        dddsPorUf.put("TO", new String[]{"63"});
    }

    public Boolean siglaValida(Comercio comercio) {
        return comercio.getEstado() != null && Arrays.asList(ufs).contains(comercio.getEstado().toUpperCase());
    }

    public Boolean validarDDD(Comercio comercio) {
        if (comercio.getTel() == null || comercio.getTel().length() < 2) return false;
        return Arrays.asList(ddds).contains(comercio.getTel().substring(0, 2));
    }

    public Boolean validarDDDporUFs(Comercio comercio) {
        if (comercio.getEstado() == null || comercio.getTel() == null || comercio.getTel().length() < 2)
            return false;

        String[] ddd = dddsPorUf.get(comercio.getEstado().toUpperCase());
        if (ddd == null) return false;

        return Arrays.asList(ddd).contains(comercio.getTel().substring(0, 2));
    }
}
