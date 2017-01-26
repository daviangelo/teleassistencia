package util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Camila Siqueira
 */
@FacesConverter(value = "convertCNPJ")
public class CNPJConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor)
            throws ConverterException {
        if (valor != null || valor != "") {
            valor = valor.toString().replaceAll("[- /.]", "");
        }
        return valor;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object valor)
            throws ConverterException {
        if (valor == null) {
            return "";
        }

        String v = valor.toString();
//        String cnpj = v.substring(0, 2) + "." + v.substring(2, 5) + "." + v.substring(5, 8) + "/" + v.substring(8, 12) + "-" + v.substring(12, 14);
        return v.substring(0, 2) + "." + v.substring(2, 5) + "." + v.substring(5, 8) + "/" + v.substring(8, 12) + "-" + v.substring(12, 14);
    }
}
