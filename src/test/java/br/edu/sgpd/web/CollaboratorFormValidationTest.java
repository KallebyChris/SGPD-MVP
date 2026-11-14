package br.edu.sgpd.web;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CollaboratorFormValidationTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void rejectsMissingNameAndMalformedEmail() {
        CollaboratorForm form = validForm();
        form.setName(" ");
        form.setEmail("email-invalido");

        var violations = validator.validate(form);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void acceptsFormWithRequiredFieldsAndValidEmail() {
        assertFalse(validator.validate(validForm()).iterator().hasNext());
    }

    private CollaboratorForm validForm() {
        CollaboratorForm form = new CollaboratorForm();
        form.setName("Ana Exemplo");
        form.setEmail("ana@example.test");
        form.setJobTitle("Analista de RH");
        form.setHireDate(LocalDate.of(2024, 1, 15));
        return form;
    }
}
