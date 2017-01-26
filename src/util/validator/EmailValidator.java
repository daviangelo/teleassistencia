package util.validator;

import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {

    private static final Pattern PATTERN = Pattern.compile("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null || ((String) value).isEmpty()) {
            return; // Let required="true" handle.
        }

        if (!PATTERN.matcher((String) value).matches()) {
            String summary = context.getApplication().evaluateExpressionGet(context, "E-mail inválido", String.class);
            String detail = context.getApplication().evaluateExpressionGet(context, "Digite um e-mail válido", String.class);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
        }
    }

}
